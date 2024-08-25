package com.example.databasexmlcourse.features.feature_menu.adapter.models

import com.example.databasexmlcourse.domain.models.DishCompositeItem

internal class MenuListUiConverter {

    fun convertToMenuListItem(items: List<DishCompositeItem>, isLoading: Boolean): List<MenuListItem> {
        return mutableListOf<MenuListItem>().apply {
            for (item in items) {
                add(item.convertToMenuListItem())
            }

            if (isLoading) {
                add(MenuListItem.Loading)
            }
        }
    }

    private fun DishCompositeItem.convertToMenuListItem() =
        MenuListItem.DishListItem(
            id = this.id,
            name = this.name,
            price = this.price,
            category = this.category
        )
}