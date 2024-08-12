package com.example.databasexmlcourse.features.feature_orders.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.databasexmlcourse.R
import com.example.databasexmlcourse.core.composite.CompositeDelegate
import com.example.databasexmlcourse.core.composite.CompositeItem
import com.example.databasexmlcourse.databinding.ItemOrderBinding
import com.example.databasexmlcourse.features.feature_orders.adapter.models.OrdersListItem
import com.example.databasexmlcourse.features.feature_orders.util.OrderStatus

class OrderDelegate(
    private val onCancelClick: (id: String) -> Unit,
    private val onActionClick: (id: String) -> Unit
) : CompositeDelegate<OrdersListItem.OrderListItem, ItemOrderBinding>() {

    override fun canUseForViewType(item: CompositeItem) = item is OrdersListItem.OrderListItem

    override fun provideBinding(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) = ItemOrderBinding.inflate(inflater, parent, false)

    override fun ItemOrderBinding.bind(item: OrdersListItem.OrderListItem) {
        cancelButton.apply {
            visibility = if (item.status == OrderStatus.AWAIT.status) {
                View.VISIBLE
            } else {
                View.GONE
            }
            setOnClickListener {
                onCancelClick(item.id)
            }
        }
        mainButton.apply {
            text = when (item.status) {
                OrderStatus.AWAIT.status -> root.context.getString(R.string.orders_item_btn_take)
                OrderStatus.IN_PROGRESS.status -> root.context.getString(R.string.orders_item_btn_finish)
                else -> root.context.getString(R.string.orders_item_btn_take)
            }
            setOnClickListener {
                onActionClick(item.id)
            }
        }
        table.text = String.format(
            "%s%s",
            root.context.getString(R.string.orders_table),
            item.tableNumber
        )
        status.text = String.format(
            "%s %s",
            root.context.getString(R.string.orders_status),
            item.status
        )
        // lists with dishes with name and bold count
        lists.text = item.dishes.joinToString("\n") { "${it.name}  x${it.count}" }
        amount.text = String.format(
            "%s %s%s",
            root.context.getString(R.string.orders_amount),
            item.totalAmount,
            root.context.getString(R.string.ruble)
        )
    }

}