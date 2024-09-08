package com.example.databasexmlcourse.data.repository_impl

import com.example.databasexmlcourse.data.database.DishesDao
import com.example.databasexmlcourse.data.database.OrdersDao
import com.example.databasexmlcourse.data.models.asEntity
import com.example.databasexmlcourse.data.models.asExternalModel
import com.example.databasexmlcourse.data.repository_api.OrdersRepository
import com.example.databasexmlcourse.domain.models.OrdersItem
import javax.inject.Inject

class OrdersRepositoryImpl @Inject constructor(
    private val ordersDao: OrdersDao,
    private val dishesDao: DishesDao
) : OrdersRepository {

    override suspend fun getOrderById(id: String): OrdersItem? {
//        val orderEntity = ordersDao.getOrderById(id) ?: return null
//        val orderDishesEntities = ordersDao.getDishesForOrder(id)
//        val dishes = orderDishesEntities.map { orderDishEntity ->
//            val dishEntity = dishesDao.getDishById(orderDishEntity.dishId)
//            dishEntity?.asExternalModel(orderDishEntity)
//        }.filterNotNull()
//
//        return orderEntity.asExternalModel(dishes)
        return null
    }

    override suspend fun getAllOrders(): List<OrdersItem> {
//        return ordersDao.getAllOrders().map { orderEntity ->
//            val orderDishesEntities = ordersDao.getDishesForOrder(orderEntity.id)
//            val dishes = orderDishesEntities.map { orderDishEntity ->
//                val dishEntity = dishesDao.getDishById(orderDishEntity.dishId)
//                dishEntity?.asExternalModel(orderDishEntity)
//            }.filterNotNull()
//
//            orderEntity.asExternalModel(dishes)
//        }
        return emptyList()
    }

    override suspend fun insertOrder(order: OrdersItem) {
        ordersDao.insertOrder(order.asEntity())
        order.dishes.forEach { dish ->
            ordersDao.insertOrderDish(dish.asEntity(order.id))
        }
    }

    override suspend fun updateOrder(order: OrdersItem) {
        ordersDao.updateOrder(order.asEntity())
        ordersDao.deleteDishesForOrder(order.id)
        order.dishes.forEach { dish ->
            ordersDao.insertOrderDish(dish.asEntity(order.id))
        }
    }

    override suspend fun deleteOrder(id: String) {
        ordersDao.deleteOrder(id)
    }
}
