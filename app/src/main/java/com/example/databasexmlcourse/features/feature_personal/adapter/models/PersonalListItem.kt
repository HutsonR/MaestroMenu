package com.example.databasexmlcourse.features.feature_personal.adapter.models

import com.example.databasexmlcourse.core.composite.CompositeItem

sealed class PersonalListItem(override val id: String): CompositeItem {

    data class UserListItem(
        override val id: String,
        val name: String,
        val type: String
    ): PersonalListItem(id)

    data object Loading: PersonalListItem("Loading")

}