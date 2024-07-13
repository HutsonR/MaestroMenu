package com.example.databasexmlcourse.features.feature_menu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.databasexmlcourse.core.composite.CompositeDelegate
import com.example.databasexmlcourse.core.composite.CompositeItem
import com.example.databasexmlcourse.databinding.DialogFragmentCategoryItemMenuBinding

class MenuCategoryDelegate(
    private val onContentClick: (text: String) -> Unit
) : CompositeDelegate<MenuCategoryListItem, DialogFragmentCategoryItemMenuBinding>() {

    override fun canUseForViewType(item: CompositeItem) = item is MenuCategoryListItem

    override fun provideBinding(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) = DialogFragmentCategoryItemMenuBinding.inflate(inflater, parent, false)

    override fun DialogFragmentCategoryItemMenuBinding.bind(item: MenuCategoryListItem) {
        contentLayout.setOnClickListener {
            onContentClick(item.text)
        }
        title.text = item.text
    }

}