package com.example.databasexmlcourse.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "order_items")
data class OrderItemsEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val orderId: String,
    val itemId: String,
    val quantity: Int
)
