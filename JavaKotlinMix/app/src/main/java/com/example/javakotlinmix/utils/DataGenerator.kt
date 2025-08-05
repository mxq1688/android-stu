package com.example.javakotlinmix.utils

import com.example.javakotlinmix.data.model.User

/**
 * 数据生成器 - 使用Kotlin编写
 * 展示Kotlin的集合操作和数据类生成
 */
object DataGenerator {
    
    private val departments = listOf(
        "技术部", "产品部", "设计部", "运营部", "市场部", "人事部", "财务部"
    )
    
    private val sampleNames = listOf(
        "张三", "李四", "王五", "赵六", "孙七", "周八", "吴九", "郑十",
        "刘明", "陈华", "杨磊", "黄强", "周敏", "吴静", "徐丽", "朱波",
        "马超", "冯勇", "曹操", "刘备", "关羽", "张飞", "诸葛亮", "赵云"
    )
    
    /**
     * 生成示例用户数据
     * @return 用户列表
     */
    @JvmStatic // 让Java可以直接调用
    fun generateSampleUsers(): List<User> {
        return (1..20).map { index ->
            val name = sampleNames.random()
            val department = departments.random()
            val email = generateEmail(name, index)
            val phone = generatePhone()
            val isActive = (0..10).random() > 2 // 80%的用户是活跃的
            
            User(
                index,
                name,
                email,
                "",
                phone,
                department,
                isActive
            )
        }
    }
    
    /**
     * 生成单个用户
     */
    @JvmStatic
    fun generateRandomUser(id: Int): User {
        val name = sampleNames.random()
        val department = departments.random()
        val email = generateEmail(name, id)
        val phone = generatePhone()
        val isActive = (0..1).random() == 1
        
        return User(id, name, email, "", phone, department, isActive)
    }
    
    /**
     * 生成邮箱地址
     */
    private fun generateEmail(name: String, id: Int): String {
        val domains = listOf("company.com", "example.com", "test.com", "demo.com")
        val pinyin = when (name) {
            "张三" -> "zhangsan"
            "李四" -> "lisi"
            "王五" -> "wangwu"
            "赵六" -> "zhaoliu"
            "孙七" -> "sunqi"
            else -> "user$id"
        }
        return "$pinyin@${domains.random()}"
    }
    
    /**
     * 生成手机号
     */
    private fun generatePhone(): String {
        val prefixes = listOf("138", "139", "150", "151", "152", "158", "159", "188", "189")
        val prefix = prefixes.random()
        val suffix = (10000000..99999999).random()
        return "$prefix$suffix"
    }
    
    /**
     * 获取所有部门列表 - 提供给Java调用
     */
    @JvmStatic
    fun getDepartments(): List<String> = departments
    
    /**
     * 根据部门生成用户
     */
    @JvmStatic
    fun generateUsersByDepartment(department: String, count: Int): List<User> {
        return (1..count).map { index ->
            val name = sampleNames.random()
            val email = generateEmail(name, index)
            val phone = generatePhone()
            val isActive = (0..1).random() == 1
            
            User(
                System.currentTimeMillis().toInt() + index,
                name,
                email,
                "",
                phone,
                department,
                isActive
            )
        }
    }
} 