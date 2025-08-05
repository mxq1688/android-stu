package com.example.javakotlinmix.data.repository

import androidx.lifecycle.LiveData
import com.example.javakotlinmix.data.database.AppDatabase
import com.example.javakotlinmix.data.database.UserDao
import com.example.javakotlinmix.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * 用户数据仓库 - 使用Kotlin编写
 * 展示Kotlin Coroutines和扩展函数的使用
 */
class UserRepository(private val userDao: UserDao) {
    
    // LiveData方式 - 兼容Java代码
    fun getAllUsers(): LiveData<List<User>> = userDao.getAllUsers()
    
    // Flow方式 - Kotlin协程
    fun getAllUsersFlow(): Flow<List<User>> = userDao.getAllUsersFlow()
    
    fun getActiveUsers(): LiveData<List<User>> = userDao.getActiveUsers()
    
    fun getUsersByDepartment(department: String): LiveData<List<User>> = 
        userDao.getUsersByDepartment(department)
    
    fun searchUsers(query: String): LiveData<List<User>> = userDao.searchUsers(query)
    
    // 使用Kotlin协程的suspend函数
    suspend fun getUserById(userId: Int): User? = withContext(Dispatchers.IO) {
        userDao.getUserById(userId)
    }
    
    suspend fun insertUser(user: User) = withContext(Dispatchers.IO) {
        userDao.insertUser(user)
    }
    
    suspend fun insertUsers(users: List<User>) = withContext(Dispatchers.IO) {
        userDao.insertUsers(users)
    }
    
    suspend fun updateUser(user: User) = withContext(Dispatchers.IO) {
        userDao.updateUser(user)
    }
    
    suspend fun deleteUser(user: User) = withContext(Dispatchers.IO) {
        userDao.deleteUser(user)
    }
    
    suspend fun deleteUserById(userId: Int) = withContext(Dispatchers.IO) {
        userDao.deleteUserById(userId)
    }
    
    suspend fun deleteAllUsers() = withContext(Dispatchers.IO) {
        userDao.deleteAllUsers()
    }
    
    suspend fun getUserCount(): Int = withContext(Dispatchers.IO) {
        userDao.getUserCount()
    }
    
    suspend fun getActiveUserCount(): Int = withContext(Dispatchers.IO) {
        userDao.getActiveUserCount()
    }
    
    // Kotlin扩展函数 - 为Repository添加便利方法
    suspend fun toggleUserStatus(userId: Int) = withContext(Dispatchers.IO) {
        userDao.getUserById(userId)?.let { user ->
            val updatedUser = User().apply {
                id = user.id
                name = user.name
                email = user.email
                avatar = user.avatar
                phone = user.phone
                department = user.department
                isActive = !user.isActive
                createdAt = user.createdAt
            }
            userDao.updateUser(updatedUser)
        }
    }
    
    companion object {
        // 单例模式 - 兼容Java调用
        @Volatile
        private var INSTANCE: UserRepository? = null
        
        fun getInstance(database: AppDatabase): UserRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = UserRepository(database.userDao())
                INSTANCE = instance
                instance
            }
        }
    }
} 