package com.example.javamvvm.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.javamvvm.data.model.User;
import com.example.javamvvm.databinding.ActivityMainBinding;
import com.example.javamvvm.ui.adapter.UserAdapter;
import com.example.javamvvm.ui.viewmodel.UserViewModel;

public class MainActivity extends AppCompatActivity implements UserAdapter.OnUserClickListener {
    
    private ActivityMainBinding binding;
    private UserViewModel viewModel;
    private UserAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        initViewModel();
        initRecyclerView();
        observeData();
        setupClickListeners();
    }
    
    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }
    
    private void initRecyclerView() {
        adapter = new UserAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
    }
    
    private void observeData() {
        // 观察用户列表数据变化
        viewModel.getAllUsers().observe(this, users -> {
            adapter.submitList(users);
            binding.tvEmpty.setVisibility(users.isEmpty() ? 
                android.view.View.VISIBLE : android.view.View.GONE);
        });
        
        // 观察加载状态
        viewModel.getLoadingStatus().observe(this, status -> {
            binding.tvStatus.setText(status);
        });
        
        // 观察错误信息
        viewModel.getErrorMessage().observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private void setupClickListeners() {
        binding.fabAdd.setOnClickListener(v -> {
            // 添加新用户
            User newUser = new User();
            newUser.setId((int) System.currentTimeMillis());
            newUser.setName("新用户 " + System.currentTimeMillis() % 1000);
            newUser.setEmail("user" + (System.currentTimeMillis() % 1000) + "@example.com");
            viewModel.insertUser(newUser);
        });
        
        binding.btnRefresh.setOnClickListener(v -> {
            viewModel.refreshUsers();
        });
    }
    
    @Override
    public void onUserClick(User user) {
        Toast.makeText(this, "点击了用户: " + user.getName(), Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void onUserEdit(User user) {
        // 简单的编辑示例
        user.setName(user.getName() + " (已编辑)");
        viewModel.updateUser(user);
        Toast.makeText(this, "编辑用户: " + user.getName(), Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void onUserDelete(User user) {
        viewModel.deleteUser(user);
        Toast.makeText(this, "删除用户: " + user.getName(), Toast.LENGTH_SHORT).show();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
} 