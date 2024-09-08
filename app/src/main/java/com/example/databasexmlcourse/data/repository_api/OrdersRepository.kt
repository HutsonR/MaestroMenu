package com.example.databasexmlcourse.data.repository_api

import com.example.databasexmlcourse.domain.models.OrdersItem

interface OrdersRepository {
    suspend fun getOrderById(id: String): OrdersItem?
    suspend fun getAllOrders(): List<OrdersItem>
    suspend fun insertOrder(order: OrdersItem)
    suspend fun updateOrder(order: OrdersItem)
    suspend fun deleteOrder(id: String)
}
