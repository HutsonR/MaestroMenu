package com.example.databasexmlcourse.features.feature_personal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.databasexmlcourse.core.composite.CompositeDelegate
import com.example.databasexmlcourse.core.composite.CompositeItem
import com.example.databasexmlcourse.databinding.ItemMenuBinding
import com.example.databasexmlcourse.databinding.ItemPersonalBinding
import com.example.databasexmlcourse.features.feature_menu.adapter.models.MenuListItem
import com.example.databasexmlcourse.features.feature_personal.adapter.models.PersonalListItem

class PersonalDelegate(
    private val onEditClick: (id: String) -> Unit
) : CompositeDelegate<PersonalListItem.UserListItem, ItemPersonalBinding>() {

    override fun canUseForViewType(item: CompositeItem) = item is PersonalListItem.UserListItem

    override fun provideBinding(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) = ItemPersonalBinding.inflate(inflater, parent, false)

    override fun ItemPersonalBinding.bind(item: PersonalListItem.UserListItem) {
        editButton.setOnClickListener {
            onEditClick(item.id)
        }
        title.text = item.name
        type.text = item.type.name
    }

}