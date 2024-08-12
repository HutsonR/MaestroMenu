package com.example.databasexmlcourse.features.common.dialogs.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.databasexmlcourse.R
import com.example.databasexmlcourse.core.composite.CompositeDelegate
import com.example.databasexmlcourse.core.composite.CompositeItem
import com.example.databasexmlcourse.databinding.DialogFragmentCategoryItemBinding
import com.example.databasexmlcourse.databinding.ItemOrderBinding
import com.example.databasexmlcourse.features.common.dialogs.adapter.models.DialogSearcherListItem
import com.example.databasexmlcourse.features.feature_orders.adapter.models.OrdersListItem
import com.example.databasexmlcourse.features.feature_orders.util.OrderStatus

class DialogSearcherDelegate(
    private val onItemClick: (text: String) -> Unit
) : CompositeDelegate<DialogSearcherListItem, DialogFragmentCategoryItemBinding>() {

    override fun canUseForViewType(item: CompositeItem) = item is DialogSearcherListItem

    override fun provideBinding(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) = DialogFragmentCategoryItemBinding.inflate(inflater, parent, false)

    override fun DialogFragmentCategoryItemBinding.bind(item: DialogSearcherListItem) {
        contentLayout.setOnClickListener {
            onItemClick(item.text)
        }
        title.text = item.text
    }

}