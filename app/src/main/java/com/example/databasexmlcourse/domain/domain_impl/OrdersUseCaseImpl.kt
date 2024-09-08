package com.example.databasexmlcourse.domain.domain_impl

import com.example.databasexmlcourse.data.repository_api.OrdersRepository
import com.example.databasexmlcourse.domain.domain_api.OrdersUseCase
import com.example.databasexmlcourse.domain.models.OrdersItem
import com.example.databasexmlcourse.domain.util.Resource
import javax.inject.Inject

class OrdersUseCaseImpl @Inject constructor(
    private val ordersRepository: OrdersRepository
) : OrdersUseCase {

    override suspend fun insert(order: OrdersItem): Resource {
        return if (order.userId.isEmpty() || order.tableId.isEmpty() || order.dishes.isEmpty()) {
            Resource.Failed(Exception("Fields cannot be empty or invalid"))
        } else {
            Resource.Success(ordersRepository.insertOrder(order))
        }
    }

    override suspend fun getOrderById(id: String): Resource {
        val order = ordersRepository.getOrderById(id)
        return if (order != null) {
            Resource.Success(order)
        } else {
            Resource.Failed(Exception("Order not found"))
        }
    }

    override suspend fun getAll(): List<OrdersItem> {
        return ordersRepository.getAllOrders()
    }

    override suspend fun deleteById(id: String): Resource {
        return try {
            ordersRepository.deleteOrder(id)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Failed(e)
        }
    }

    override suspend fun deleteAll() {
        ordersRepository.getAllOrders().forEach { order ->
            ordersRepository.deleteOrder(order.id)
        }
    }

    override suspend fun update(order: OrdersItem): Resource {
        return if (order.userId.isEmpty() || order.tableId.isEmpty() || order.dishes.isEmpty()) {
            Resource.Failed(Exception("Fields cannot be empty or invalid"))
        } else {
            Resource.Success(ordersRepository.updateOrder(order))
        }
    }
}
