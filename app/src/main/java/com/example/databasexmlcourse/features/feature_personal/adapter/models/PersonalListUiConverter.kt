package com.example.databasexmlcourse.features.feature_personal.adapter.models

import com.example.databasexmlcourse.domain.models.PersonalItem

internal class PersonalListUiConverter {

    fun convertToPersonalListItem(items: List<PersonalItem>, isLoading: Boolean): List<PersonalListItem> {
        return mutableListOf<PersonalListItem>().apply {
            for (item in items) {
                add(convertToPersonalListItem(item))
            }

            if (isLoading) {
                add(PersonalListItem.Loading)
            }
        }
    }

    private fun convertToPersonalListItem(item: PersonalItem): PersonalListItem =
        PersonalListItem.UserListItem(
            id = item.id,
            name = item.name,
            type = item.type
        )
}