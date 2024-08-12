package com.example.databasexmlcourse.features.feature_orders.add.adapter.models

import com.example.databasexmlcourse.core.composite.CompositeItem

sealed class OrderDishListItem(override val id: String): CompositeItem {

    data class OrderDish(
        override val id: String,
        val name: String,
        val price: Int,
        val count: Int
    ): OrderDishListItem(id)

    data object Loading: OrderDishListItem("Loading")

}