package com.example.databasexmlcourse.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrdersItem(
    val id: String,
    val userId: String,
    val createdAt: String,
    val tableId: String,
    val dishes: List<DishItem>,
    val totalAmount: Int,
    val status: String,
    val usersCount: Int
): Parcelable

