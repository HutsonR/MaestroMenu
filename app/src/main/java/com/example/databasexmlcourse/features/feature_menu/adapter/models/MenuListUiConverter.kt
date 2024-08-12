package com.example.databasexmlcourse.features.feature_menu.adapter.models

import com.example.databasexmlcourse.domain.models.DishItem

internal class MenuListUiConverter {

    fun convertToMenuListItem(items: List<DishItem>, isLoading: Boolean): List<MenuListItem> {
        return mutableListOf<MenuListItem>().apply {
            for (item in items) {
                add(item.convertToMenuListItem())
            }

            if (isLoading) {
                add(MenuListItem.Loading)
            }
        }
    }

    private fun DishItem.convertToMenuListItem() =
        MenuListItem.DishListItem(
            id = this.id,
            name = this.name,
            price = this.price,
            category = this.dishCategoryId
        )
}