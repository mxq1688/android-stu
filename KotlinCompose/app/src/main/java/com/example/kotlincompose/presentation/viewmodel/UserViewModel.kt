package com.example.kotlincompose.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlincompose.domain.model.User
import com.example.kotlincompose.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UserUiState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = ""
)

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()
    
    private val _searchQuery = MutableStateFlow("")
    
    init {
        loadSampleData()
        observeUsers()
    }
    
    private fun observeUsers() {
        viewModelScope.launch {
            _searchQuery
                .debounce(300)
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    if (query.isBlank()) {
                        repository.getAllUsers()
                    } else {
                        repository.searchUsers(query)
                    }
                }
                .catch { throwable ->
                    _uiState.value = _uiState.value.copy(
                        error = throwable.message,
                        isLoading = false
                    )
                }
                .collect { users ->
                    _uiState.value = _uiState.value.copy(
                        users = users,
                        isLoading = false,
                        error = null
                    )
                }
        }
    }
    
    fun searchUsers(query: String) {
        _searchQuery.value = query
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }
    
    fun addUser(name: String, email: String) {
        viewModelScope.launch {
            try {
                val newUser = User(
                    id = System.currentTimeMillis().toInt(),
                    name = name,
                    email = email,
                    isOnline = (0..1).random() == 1
                )
                repository.insertUser(newUser)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }
    
    fun updateUser(user: User) {
        viewModelScope.launch {
            try {
                repository.updateUser(user)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }
    
    fun deleteUser(user: User) {
        viewModelScope.launch {
            try {
                repository.deleteUser(user)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
    
    private fun loadSampleData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            val sampleUsers = listOf(
                User(1, "张三", "zhangsan@example.com", "", true),
                User(2, "李四", "lisi@example.com", "", false),
                User(3, "王五", "wangwu@example.com", "", true),
                User(4, "赵六", "zhaoliu@example.com", "", false),
                User(5, "孙七", "sunqi@example.com", "", true)
            )
            
            try {
                repository.insertUsers(sampleUsers)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }
} 