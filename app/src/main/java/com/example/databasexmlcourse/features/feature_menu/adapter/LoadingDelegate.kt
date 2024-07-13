package com.example.databasexmlcourse.features.feature_menu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.databasexmlcourse.core.composite.CompositeDelegate
import com.example.databasexmlcourse.core.composite.CompositeItem
import com.example.databasexmlcourse.databinding.LoadingItemBinding

class LoadingDelegate : CompositeDelegate<MenuListItem.Loading, LoadingItemBinding>() {

    override fun canUseForViewType(item: CompositeItem) = item is MenuListItem.Loading

    override fun provideBinding(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) = LoadingItemBinding.inflate(inflater, parent, false)

    override fun LoadingItemBinding.bind(item: MenuListItem.Loading) = Unit
}