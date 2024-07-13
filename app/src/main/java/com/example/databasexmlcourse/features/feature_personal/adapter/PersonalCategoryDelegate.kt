package com.example.databasexmlcourse.features.feature_personal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.databasexmlcourse.core.composite.CompositeDelegate
import com.example.databasexmlcourse.core.composite.CompositeItem
import com.example.databasexmlcourse.databinding.DialogFragmentCategoryItemBinding
import com.example.databasexmlcourse.features.feature_personal.adapter.models.PersonalCategoryListItem

class PersonalCategoryDelegate(
    private val onContentClick: (text: String) -> Unit
) : CompositeDelegate<PersonalCategoryListItem, DialogFragmentCategoryItemBinding>() {

    override fun canUseForViewType(item: CompositeItem) = item is PersonalCategoryListItem

    override fun provideBinding(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) = DialogFragmentCategoryItemBinding.inflate(inflater, parent, false)

    override fun DialogFragmentCategoryItemBinding.bind(item: PersonalCategoryListItem) {
        contentLayout.setOnClickListener {
            onContentClick(item.text)
        }
        title.text = item.text
    }

}