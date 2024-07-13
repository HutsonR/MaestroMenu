package com.example.databasexmlcourse.features.feature_personal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.databasexmlcourse.core.composite.CompositeDelegate
import com.example.databasexmlcourse.core.composite.CompositeItem
import com.example.databasexmlcourse.databinding.LoadingItemBinding
import com.example.databasexmlcourse.features.feature_personal.adapter.models.PersonalListItem

class PersonalLoadingDelegate : CompositeDelegate<PersonalListItem.Loading, LoadingItemBinding>() {

    override fun canUseForViewType(item: CompositeItem) = item is PersonalListItem.Loading

    override fun provideBinding(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) = LoadingItemBinding.inflate(inflater, parent, false)

    override fun LoadingItemBinding.bind(item: PersonalListItem.Loading) = Unit
}