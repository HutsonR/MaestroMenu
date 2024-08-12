package com.example.databasexmlcourse.features.feature_orders.add.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.databasexmlcourse.R
import com.example.databasexmlcourse.core.composite.CompositeDelegate
import com.example.databasexmlcourse.core.composite.CompositeItem
import com.example.databasexmlcourse.databinding.DialogFragmentOrdersItemBinding
import com.example.databasexmlcourse.features.feature_orders.add.adapter.models.OrderDishListItem

class OrderAddDelegate(
    private val onRemoveClick: (id: String) -> Unit,
    private val onAddClick: (id: String) -> Unit
) : CompositeDelegate<OrderDishListItem.OrderDish, DialogFragmentOrdersItemBinding>() {

    override fun canUseForViewType(item: CompositeItem) = item is OrderDishListItem.OrderDish

    override fun provideBinding(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) = DialogFragmentOrdersItemBinding.inflate(inflater, parent, false)

    override fun DialogFragmentOrdersItemBinding.bind(item: OrderDishListItem.OrderDish) {
        removeButton.apply {
            visibility = if (item.count > 0) {
                View.VISIBLE
            } else {
                View.GONE
            }
            setOnClickListener {
                onRemoveClick(item.id)
            }
        }
        addButton.apply {
            setOnClickListener {
                onAddClick(item.id)
            }
        }
        count.text = item.count.toString()
        name.text = item.name
        price.text = String.format(
            "%s %s",
            item.price,
            root.context.getString(R.string.ruble)
        )
    }

}