package com.example.databasexmlcourse.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "orders")
data class OrdersEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val userId: String,
    val createdAt: String,
    val tableId: String,
    val totalAmount: Int,
    val status: String,
    val usersCount: Int
)
