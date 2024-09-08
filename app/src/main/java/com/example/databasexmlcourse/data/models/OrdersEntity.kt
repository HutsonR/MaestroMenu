package com.example.databasexmlcourse.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.databasexmlcourse.domain.models.DishItem
import com.example.databasexmlcourse.domain.models.OrdersItem
import java.util.UUID

@Entity(
    tableName = "orders",
    foreignKeys = [
        ForeignKey(
            entity = UsersEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TablesEntity::class,
            parentColumns = ["id"],
            childColumns = ["tableId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["userId"]),
        Index(value = ["tableId"])
    ]
)
data class OrdersEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "userId") val userId: String,
    @ColumnInfo(name = "createdAt") val createdAt: String,
    @ColumnInfo(name = "tableId") val tableId: String,
    @ColumnInfo(name = "totalAmount") val totalAmount: Int,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "usersCount") val usersCount: Int
)
fun OrdersEntity.asExternalModel(dishes: List<DishItem>) = OrdersItem(
    id = id,
    userId = userId,
    createdAt = createdAt,
    tableId = tableId,
    dishes = dishes,  // dishes нужно передавать отдельно
    totalAmount = totalAmount,
    status = status,
    usersCount = usersCount
)

fun OrdersItem.asEntity() = OrdersEntity(
    id = id.ifBlank { UUID.randomUUID().toString() },
    userId = userId,
    createdAt = createdAt,
    tableId = tableId,
    totalAmount = totalAmount,
    status = status,
    usersCount = usersCount
)

