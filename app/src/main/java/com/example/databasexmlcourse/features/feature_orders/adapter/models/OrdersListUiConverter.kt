package com.example.databasexmlcourse.features.feature_orders.adapter.models

import com.example.databasexmlcourse.domain.models.OrdersItem

internal class OrdersListUiConverter {

    fun convertToOrdersListItem(items: List<OrdersItem>, isLoading: Boolean): List<OrdersListItem> {
        return mutableListOf<OrdersListItem>().apply {
            for (item in items) {
                add(item.convertToOrderListItem())
            }

            if (isLoading) {
                add(OrdersListItem.Loading)
            }
        }
    }

    private fun OrdersItem.convertToOrderListItem() =
        OrdersListItem.OrderListItem(
            id = this.id,
            tableNumber = this.tableId,
            timeStamps = this.createdAt,
            totalAmount = this.totalAmount,
            status = this.status,
            dishes = this.dishes
        )
}