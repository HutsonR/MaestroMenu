package com.example.databasexmlcourse.features.feature_menu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.databasexmlcourse.core.composite.CompositeDelegate
import com.example.databasexmlcourse.core.composite.CompositeItem
import com.example.databasexmlcourse.databinding.DialogFragmentCategoryItemBinding
import com.example.databasexmlcourse.features.feature_menu.adapter.models.MenuCategoryListItem

class MenuCategoryDelegate(
    private val onContentClick: (text: String) -> Unit
) : CompositeDelegate<MenuCategoryListItem, DialogFragmentCategoryItemBinding>() {

    override fun canUseForViewType(item: CompositeItem) = item is MenuCategoryListItem

    override fun provideBinding(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) = DialogFragmentCategoryItemBinding.inflate(inflater, parent, false)

    override fun DialogFragmentCategoryItemBinding.bind(item: MenuCategoryListItem) {
        contentLayout.setOnClickListener {
            onContentClick(item.text)
        }
        title.text = item.text
    }

}