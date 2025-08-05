package com.example.javamvvm.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.javamvvm.data.model.User;
import com.example.javamvvm.data.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserViewModel extends AndroidViewModel {
    
    private UserRepository repository;
    private LiveData<List<User>> allUsers;
    private MutableLiveData<String> loadingStatus = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    
    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
        allUsers = repository.getAllUsers();
        
        // 初始化示例数据
        loadSampleData();
    }
    
    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }
    
    public LiveData<String> getLoadingStatus() {
        return loadingStatus;
    }
    
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
    
    public void insertUser(User user) {
        repository.insert(user);
    }
    
    public void updateUser(User user) {
        repository.update(user);
    }
    
    public void deleteUser(User user) {
        repository.delete(user);
    }
    
    private void loadSampleData() {
        loadingStatus.setValue("Loading...");
        
        List<User> sampleUsers = new ArrayList<>();
        sampleUsers.add(new User(1, "张三", "zhangsan@example.com", ""));
        sampleUsers.add(new User(2, "李四", "lisi@example.com", ""));
        sampleUsers.add(new User(3, "王五", "wangwu@example.com", ""));
        
        repository.insertAll(sampleUsers);
        loadingStatus.setValue("Loaded");
    }
    
    public void refreshUsers() {
        loadingStatus.setValue("Refreshing...");
        // 这里可以添加网络请求逻辑
        loadingStatus.setValue("Refreshed");
    }
} 