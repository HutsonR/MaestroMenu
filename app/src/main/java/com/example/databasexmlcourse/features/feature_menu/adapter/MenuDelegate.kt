package com.example.databasexmlcourse.features.feature_menu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.databasexmlcourse.core.composite.CompositeDelegate
import com.example.databasexmlcourse.core.composite.CompositeItem
import com.example.databasexmlcourse.databinding.DialogFragmentCategoryItemMenuBinding
import com.example.databasexmlcourse.databinding.ItemMenuBinding

class MenuDelegate(
    private val onEditClick: (id: String) -> Unit
) : CompositeDelegate<MenuListItem.DishListItem, ItemMenuBinding>() {

    override fun canUseForViewType(item: CompositeItem) = item is MenuListItem.DishListItem

    override fun provideBinding(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) = ItemMenuBinding.inflate(inflater, parent, false)

    override fun ItemMenuBinding.bind(item: MenuListItem.DishListItem) {
        editButton.setOnClickListener {
            onEditClick(item.id)
        }
        title.text = item.name
        price.text = "${item.price} ₽"
    }

}