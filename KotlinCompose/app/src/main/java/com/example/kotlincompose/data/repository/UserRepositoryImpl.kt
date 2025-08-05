package com.example.kotlincompose.data.repository

import com.example.kotlincompose.data.local.UserDao
import com.example.kotlincompose.domain.model.User
import com.example.kotlincompose.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {
    
    override fun getAllUsers(): Flow<List<User>> = userDao.getAllUsers()
    
    override suspend fun getUserById(userId: Int): User? = userDao.getUserById(userId)
    
    override fun searchUsers(query: String): Flow<List<User>> = userDao.searchUsers(query)
    
    override suspend fun insertUser(user: User) = userDao.insertUser(user)
    
    override suspend fun insertUsers(users: List<User>) = userDao.insertUsers(users)
    
    override suspend fun updateUser(user: User) = userDao.updateUser(user)
    
    override suspend fun deleteUser(user: User) = userDao.deleteUser(user)
    
    override suspend fun deleteAllUsers() = userDao.deleteAllUsers()
} 