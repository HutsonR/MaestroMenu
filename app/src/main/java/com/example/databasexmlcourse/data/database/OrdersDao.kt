package com.example.databasexmlcourse.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.databasexmlcourse.data.models.OrderDishesEntity
import com.example.databasexmlcourse.data.models.OrdersEntity

@Dao
interface OrdersDao {
    @Query("SELECT * FROM orders WHERE id = :id")
    suspend fun getOrderById(id: String): OrdersEntity?

    @Query("SELECT * FROM orders")
    suspend fun getAllOrders(): List<OrdersEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrdersEntity)

    @Update
    suspend fun updateOrder(order: OrdersEntity)

    @Query("DELETE FROM orders WHERE id = :id")
    suspend fun deleteOrder(id: String)

    @Query("SELECT * FROM order_dishes WHERE orderId = :orderId")
    suspend fun getDishesForOrder(orderId: String): List<OrderDishesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderDish(orderDish: OrderDishesEntity)

    @Query("DELETE FROM order_dishes WHERE orderId = :orderId")
    suspend fun deleteDishesForOrder(orderId: String)
}
