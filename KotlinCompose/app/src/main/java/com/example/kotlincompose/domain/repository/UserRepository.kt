package com.example.kotlincompose.domain.repository

import com.example.kotlincompose.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getAllUsers(): Flow<List<User>>
    suspend fun getUserById(userId: Int): User?
    fun searchUsers(query: String): Flow<List<User>>
    suspend fun insertUser(user: User)
    suspend fun insertUsers(users: List<User>)
    suspend fun updateUser(user: User)
    suspend fun deleteUser(user: User)
    suspend fun deleteAllUsers()
} 