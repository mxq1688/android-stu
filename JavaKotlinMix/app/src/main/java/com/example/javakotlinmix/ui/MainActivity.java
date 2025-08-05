package com.example.javakotlinmix.ui;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.javakotlinmix.data.model.User;
import com.example.javakotlinmix.databinding.ActivityMainBinding;
import com.example.javakotlinmix.ui.adapter.UserAdapter;
import com.example.javakotlinmix.ui.viewmodel.UserViewModel;
import com.example.javakotlinmix.utils.DataGenerator;

/**
 * 主Activity - 使用Java编写
 * 展示Java调用Kotlin工具类和协程代码
 */
public class MainActivity extends AppCompatActivity implements UserAdapter.OnUserClickListener {
    
    private ActivityMainBinding binding;
    private UserViewModel viewModel;
    private UserAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        initializeComponents();
        setupRecyclerView();
        observeData();
        setupClickListeners();
    }
    
    private void initializeComponents() {
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        adapter = new UserAdapter(this);
    }
    
    private void setupRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
    }
    
    private void observeData() {
        // 观察用户列表
        viewModel.getAllUsers().observe(this, users -> {
            adapter.submitList(users);
            updateEmptyState(users.isEmpty());
            updateUserCount(users.size());
        });
        
        // 观察加载状态
        viewModel.getIsLoading().observe(this, isLoading -> {
            binding.progressBar.setVisibility(isLoading ? 
                android.view.View.VISIBLE : android.view.View.GONE);
            binding.fabAdd.setEnabled(!isLoading);
        });
        
        // 观察状态消息
        viewModel.getStatusMessage().observe(this, message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                binding.tvStatus.setText(message);
                viewModel.clearStatusMessage();
            }
        });
    }
    
    private void setupClickListeners() {
        // 添加用户按钮
        binding.fabAdd.setOnClickListener(v -> {
            // 调用Kotlin工具类生成随机用户
            User newUser = DataGenerator.generateRandomUser(
                (int) System.currentTimeMillis());
            viewModel.insertUser(newUser);
        });
        
        // 显示活跃用户
        binding.btnActiveUsers.setOnClickListener(v -> {
            viewModel.getActiveUsers().observe(this, users -> {
                adapter.submitList(users);
                binding.tvStatus.setText("显示活跃用户: " + users.size() + " 个");
            });
        });
        
        // 显示所有用户
        binding.btnAllUsers.setOnClickListener(v -> {
            viewModel.getAllUsers().observe(this, users -> {
                adapter.submitList(users);
                binding.tvStatus.setText("显示所有用户: " + users.size() + " 个");
            });
        });
        
        // 按部门筛选
        binding.btnFilterDept.setOnClickListener(v -> {
            // 获取第一个部门作为示例
            String department = DataGenerator.getDepartments().get(0);
            viewModel.getUsersByDepartment(department).observe(this, users -> {
                adapter.submitList(users);
                binding.tvStatus.setText("显示" + department + "用户: " + users.size() + " 个");
            });
        });
        
        // 搜索功能
        binding.searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return true;
            }
            
            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 2) {
                    performSearch(newText);
                } else if (newText.isEmpty()) {
                    // 清空搜索时显示所有用户
                    viewModel.getAllUsers().observe(MainActivity.this, users -> {
                        adapter.submitList(users);
                    });
                }
                return true;
            }
        });
    }
    
    private void performSearch(String query) {
        viewModel.searchUsers(query).observe(this, users -> {
            adapter.submitList(users);
            binding.tvStatus.setText("搜索结果: " + users.size() + " 个用户");
        });
    }
    
    private void updateEmptyState(boolean isEmpty) {
        binding.tvEmpty.setVisibility(isEmpty ? 
            android.view.View.VISIBLE : android.view.View.GONE);
        binding.recyclerView.setVisibility(isEmpty ? 
            android.view.View.GONE : android.view.View.VISIBLE);
    }
    
    private void updateUserCount(int count) {
        binding.tvUserCount.setText("用户总数: " + count);
    }
    
    // UserAdapter.OnUserClickListener 接口实现
    @Override
    public void onUserClick(User user) {
        Toast.makeText(this, "点击用户: " + user.getName(), Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void onUserEdit(User user) {
        // 简单的编辑示例 - 切换用户状态
        viewModel.toggleUserStatus(user.getId());
    }
    
    @Override
    public void onUserDelete(User user) {
        viewModel.deleteUser(user);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
} 