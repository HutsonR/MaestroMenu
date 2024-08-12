package com.example.databasexmlcourse.features.feature_orders.add.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.databasexmlcourse.core.composite.CompositeDelegate
import com.example.databasexmlcourse.core.composite.CompositeItem
import com.example.databasexmlcourse.databinding.LoadingItemBinding
import com.example.databasexmlcourse.features.feature_orders.add.adapter.models.OrderDishListItem

class OrderAddLoadingDelegate : CompositeDelegate<OrderDishListItem.Loading, LoadingItemBinding>() {

    override fun canUseForViewType(item: CompositeItem) = item is OrderDishListItem.Loading

    override fun provideBinding(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) = LoadingItemBinding.inflate(inflater, parent, false)

    override fun LoadingItemBinding.bind(item: OrderDishListItem.Loading) = Unit
}