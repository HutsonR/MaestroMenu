package com.example.databasexmlcourse.domain.domain_api

import com.example.databasexmlcourse.domain.models.OrdersItem
import com.example.databasexmlcourse.domain.util.Resource

interface OrdersUseCase {
    suspend fun insert(order: OrdersItem): Resource
    suspend fun getOrderById(id: String): Resource
    suspend fun getAll(): List<OrdersItem>
    suspend fun deleteById(id: String): Resource
    suspend fun deleteAll()
    suspend fun update(order: OrdersItem): Resource
}

