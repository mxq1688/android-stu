package com.example.kotlincompose.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: Int = 0,
    val name: String = "",
    val email: String = "",
    val avatar: String = "",
    val isOnline: Boolean = false
) 