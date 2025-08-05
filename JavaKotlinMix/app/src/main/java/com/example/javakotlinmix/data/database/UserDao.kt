package com.example.javakotlinmix.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.javakotlinmix.data.model.User
import kotlinx.coroutines.flow.Flow

/**
 * 用户数据访问对象 - 使用Kotlin编写
 * 展示Kotlin的简洁语法和Coroutines支持
 */
@Dao
interface UserDao {
    
    @Query("SELECT * FROM users ORDER BY name ASC")
    fun getAllUsers(): LiveData<List<User>>
    
    @Query("SELECT * FROM users ORDER BY name ASC")
    fun getAllUsersFlow(): Flow<List<User>>
    
    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: Int): User?
    
    @Query("SELECT * FROM users WHERE isActive = 1 ORDER BY name ASC")
    fun getActiveUsers(): LiveData<List<User>>
    
    @Query("SELECT * FROM users WHERE department = :department ORDER BY name ASC")
    fun getUsersByDepartment(department: String): LiveData<List<User>>
    
    @Query("""
        SELECT * FROM users 
        WHERE name LIKE '%' || :query || '%' 
        OR email LIKE '%' || :query || '%' 
        OR department LIKE '%' || :query || '%'
        ORDER BY name ASC
    """)
    fun searchUsers(query: String): LiveData<List<User>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<User>)
    
    @Update
    suspend fun updateUser(user: User)
    
    @Delete
    suspend fun deleteUser(user: User)
    
    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun deleteUserById(userId: Int)
    
    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()
    
    @Query("SELECT COUNT(*) FROM users")
    suspend fun getUserCount(): Int
    
    @Query("SELECT COUNT(*) FROM users WHERE isActive = 1")
    suspend fun getActiveUserCount(): Int
} 