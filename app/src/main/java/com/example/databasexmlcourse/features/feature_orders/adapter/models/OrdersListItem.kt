package com.example.databasexmlcourse.features.feature_orders.adapter.models

import com.example.databasexmlcourse.core.composite.CompositeItem
import com.example.databasexmlcourse.domain.models.DishItem

sealed class OrdersListItem(override val id: String): CompositeItem {

    data class OrderListItem(
        override val id: String,
        val tableNumber: String,
        val timeStamps: String,
        val dishes: List<DishItem>,
        val totalAmount: Int,
        val status: String
    ): OrdersListItem(id)

    data object Loading: OrdersListItem("Loading")

}