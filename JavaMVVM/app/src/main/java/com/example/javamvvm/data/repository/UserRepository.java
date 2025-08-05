package com.example.javamvvm.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.javamvvm.data.database.AppDatabase;
import com.example.javamvvm.data.database.UserDao;
import com.example.javamvvm.data.model.User;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {
    
    private UserDao userDao;
    private LiveData<List<User>> allUsers;
    private ExecutorService executor;
    
    public UserRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        userDao = database.userDao();
        allUsers = userDao.getAllUsers();
        executor = Executors.newFixedThreadPool(4);
    }
    
    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }
    
    public LiveData<User> getUserById(int userId) {
        return userDao.getUserById(userId);
    }
    
    public void insert(User user) {
        executor.execute(() -> userDao.insertUser(user));
    }
    
    public void insertAll(List<User> users) {
        executor.execute(() -> userDao.insertUsers(users));
    }
    
    public void update(User user) {
        executor.execute(() -> userDao.updateUser(user));
    }
    
    public void delete(User user) {
        executor.execute(() -> userDao.deleteUser(user));
    }
    
    public void deleteAll() {
        executor.execute(() -> userDao.deleteAllUsers());
    }
} 