package com.example.databasexmlcourse.features.feature_orders.add.adapter.models

import com.example.databasexmlcourse.domain.models.DishItem

class OrderDishItemUiConverter {

    fun convertToOrdersListItem(items: List<DishItem>, isLoading: Boolean): List<OrderDishListItem> {
        return mutableListOf<OrderDishListItem>().apply {
            for (item in items) {
                add(item.convertToDishListItem())
            }

            if (isLoading) {
                add(OrderDishListItem.Loading)
            }
        }
    }

    private fun DishItem.convertToDishListItem() =
        OrderDishListItem.OrderDish(
            id = this.id,
            name = this.name,
            price = this.price,
            count = this.count
        )
}