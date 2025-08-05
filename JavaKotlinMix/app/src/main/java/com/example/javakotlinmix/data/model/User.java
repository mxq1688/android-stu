package com.example.javakotlinmix.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 用户数据模型 - 使用Java编写
 * 展示传统Java POJO的写法
 */
@Entity(tableName = "users")
public class User {
    @PrimaryKey
    private int id;
    private String name;
    private String email;
    private String avatar;
    private String phone;
    private String department;
    private boolean isActive;
    private long createdAt;

    public User() {}

    public User(int id, String name, String email, String avatar, 
                String phone, String department, boolean isActive) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.phone = phone;
        this.department = department;
        this.isActive = isActive;
        this.createdAt = System.currentTimeMillis();
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", department='" + department + '\'' +
                ", isActive=" + isActive +
                '}';
    }
} 