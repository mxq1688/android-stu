package com.example.javakotlinmix.ui.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.javakotlinmix.data.database.AppDatabase;
import com.example.javakotlinmix.data.model.User;
import com.example.javakotlinmix.data.repository.UserRepository;
import com.example.javakotlinmix.utils.DataGenerator;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.launch;
import java.util.List;

/**
 * 用户ViewModel - 使用Java编写
 * 展示Java调用Kotlin协程代码的方式
 */
public class UserViewModel extends AndroidViewModel {
    
    private UserRepository repository;
    private LiveData<List<User>> allUsers;
    private MutableLiveData<String> statusMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    
    // 协程作用域 - 用于调用Kotlin suspend函数
    private CoroutineScope viewModelScope = CoroutineScope(Dispatchers.Main + new Job());
    
    public UserViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getDatabase(application);
        repository = UserRepository.getInstance(database);
        allUsers = repository.getAllUsers();
        
        // 初始化示例数据
        loadSampleData();
    }
    
    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }
    
    public LiveData<List<User>> getActiveUsers() {
        return repository.getActiveUsers();
    }
    
    public LiveData<List<User>> getUsersByDepartment(String department) {
        return repository.getUsersByDepartment(department);
    }
    
    public LiveData<List<User>> searchUsers(String query) {
        return repository.searchUsers(query);
    }
    
    public LiveData<String> getStatusMessage() {
        return statusMessage;
    }
    
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }
    
    /**
     * 插入用户 - Java调用Kotlin suspend函数
     */
    public void insertUser(User user) {
        isLoading.setValue(true);
        // 使用协程调用Kotlin suspend函数
        viewModelScope.launch(Dispatchers.IO, (scope, continuation) -> {
            try {
                repository.insertUser(user, continuation);
                viewModelScope.launch(Dispatchers.Main, (mainScope, mainContinuation) -> {
                    isLoading.setValue(false);
                    statusMessage.setValue("用户添加成功");
                    return null;
                });
            } catch (Exception e) {
                viewModelScope.launch(Dispatchers.Main, (mainScope, mainContinuation) -> {
                    isLoading.setValue(false);
                    statusMessage.setValue("添加失败: " + e.getMessage());
                    return null;
                });
            }
            return null;
        });
    }
    
    /**
     * 更新用户
     */
    public void updateUser(User user) {
        isLoading.setValue(true);
        viewModelScope.launch(Dispatchers.IO, (scope, continuation) -> {
            try {
                repository.updateUser(user, continuation);
                viewModelScope.launch(Dispatchers.Main, (mainScope, mainContinuation) -> {
                    isLoading.setValue(false);
                    statusMessage.setValue("用户更新成功");
                    return null;
                });
            } catch (Exception e) {
                viewModelScope.launch(Dispatchers.Main, (mainScope, mainContinuation) -> {
                    isLoading.setValue(false);
                    statusMessage.setValue("更新失败: " + e.getMessage());
                    return null;
                });
            }
            return null;
        });
    }
    
    /**
     * 删除用户
     */
    public void deleteUser(User user) {
        isLoading.setValue(true);
        viewModelScope.launch(Dispatchers.IO, (scope, continuation) -> {
            try {
                repository.deleteUser(user, continuation);
                viewModelScope.launch(Dispatchers.Main, (mainScope, mainContinuation) -> {
                    isLoading.setValue(false);
                    statusMessage.setValue("用户删除成功");
                    return null;
                });
            } catch (Exception e) {
                viewModelScope.launch(Dispatchers.Main, (mainScope, mainContinuation) -> {
                    isLoading.setValue(false);
                    statusMessage.setValue("删除失败: " + e.getMessage());
                    return null;
                });
            }
            return null;
        });
    }
    
    /**
     * 切换用户状态 - 调用Kotlin扩展函数
     */
    public void toggleUserStatus(int userId) {
        viewModelScope.launch(Dispatchers.IO, (scope, continuation) -> {
            try {
                repository.toggleUserStatus(userId, continuation);
                viewModelScope.launch(Dispatchers.Main, (mainScope, mainContinuation) -> {
                    statusMessage.setValue("用户状态已切换");
                    return null;
                });
            } catch (Exception e) {
                viewModelScope.launch(Dispatchers.Main, (mainScope, mainContinuation) -> {
                    statusMessage.setValue("状态切换失败: " + e.getMessage());
                    return null;
                });
            }
            return null;
        });
    }
    
    /**
     * 加载示例数据
     */
    private void loadSampleData() {
        isLoading.setValue(true);
        statusMessage.setValue("正在加载示例数据...");
        
        viewModelScope.launch(Dispatchers.IO, (scope, continuation) -> {
            try {
                List<User> sampleUsers = DataGenerator.generateSampleUsers();
                repository.insertUsers(sampleUsers, continuation);
                
                viewModelScope.launch(Dispatchers.Main, (mainScope, mainContinuation) -> {
                    isLoading.setValue(false);
                    statusMessage.setValue("示例数据加载完成");
                    return null;
                });
            } catch (Exception e) {
                viewModelScope.launch(Dispatchers.Main, (mainScope, mainContinuation) -> {
                    isLoading.setValue(false);
                    statusMessage.setValue("数据加载失败: " + e.getMessage());
                    return null;
                });
            }
            return null;
        });
    }
    
    public void clearStatusMessage() {
        statusMessage.setValue("");
    }
} 