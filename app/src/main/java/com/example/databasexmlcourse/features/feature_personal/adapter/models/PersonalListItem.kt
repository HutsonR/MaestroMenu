package com.example.databasexmlcourse.features.feature_personal.adapter.models

import com.example.databasexmlcourse.core.composite.CompositeItem
import com.example.databasexmlcourse.domain.models.UserType

sealed class PersonalListItem(override val id: String): CompositeItem {

    data class UserListItem(
        override val id: String,
        val name: String,
        val type: UserType
    ): PersonalListItem(id)

    data object Loading: PersonalListItem("Loading")

}