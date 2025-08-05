package com.example.javamvvm.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.javamvvm.data.model.User;

import java.util.List;

@Dao
public interface UserDao {
    
    @Query("SELECT * FROM users")
    LiveData<List<User>> getAllUsers();
    
    @Query("SELECT * FROM users WHERE id = :userId")
    LiveData<User> getUserById(int userId);
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsers(List<User> users);
    
    @Update
    void updateUser(User user);
    
    @Delete
    void deleteUser(User user);
    
    @Query("DELETE FROM users")
    void deleteAllUsers();
} 