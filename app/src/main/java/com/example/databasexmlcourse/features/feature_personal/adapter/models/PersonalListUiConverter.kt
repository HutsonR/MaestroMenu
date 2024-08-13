package com.example.databasexmlcourse.features.feature_personal.adapter.models

import com.example.databasexmlcourse.domain.models.PersonalItem

internal class PersonalListUiConverter {

    fun convertToPersonalListItem(items: List<PersonalItem>, isLoading: Boolean): List<PersonalListItem> {
        return mutableListOf<PersonalListItem>().apply {
            for (item in items) {
                add(item.convertToPersonalListItem())
            }

            if (isLoading) {
                add(PersonalListItem.Loading)
            }
        }
    }

    private fun PersonalItem.convertToPersonalListItem() =
        PersonalListItem.UserListItem(
            id = this.id,
            name = this.fio,
            type = this.type
        )
}