package com.example.databasexmlcourse.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.example.databasexmlcourse.domain.models.DishItem

@Entity(
    tableName = "order_dishes",
    primaryKeys = ["orderId", "dishId"],
    foreignKeys = [
        ForeignKey(
            entity = OrdersEntity::class,
            parentColumns = ["id"],
            childColumns = ["orderId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = DishesEntity::class,
            parentColumns = ["id"],
            childColumns = ["dishId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["orderId"]),
        Index(value = ["dishId"])
    ]
)
data class OrderDishesEntity(
    @ColumnInfo(name = "orderId") val orderId: String,
    @ColumnInfo(name = "dishId") val dishId: String,
    @ColumnInfo(name = "count") val count: Int
)

fun OrderDishesEntity.asExternalModel(dish: DishItem) = dish.copy(
    count = this.count
)

fun DishItem.asEntity(orderId: String) = OrderDishesEntity(
    orderId = orderId,
    dishId = id,
    count = count
)
