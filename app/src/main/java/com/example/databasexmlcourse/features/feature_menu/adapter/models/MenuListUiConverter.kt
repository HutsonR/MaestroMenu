package com.example.databasexmlcourse.features.feature_menu.adapter.models

import com.example.databasexmlcourse.domain.models.DishItem

internal class MenuListUiConverter {

    fun convertToMenuListItem(dishes: List<DishItem>, isLoading: Boolean): List<MenuListItem> {
        return mutableListOf<MenuListItem>().apply {
            for (dish in dishes) {
                add(convertToMenuListItem(dish))
            }

            if (isLoading) {
                add(MenuListItem.Loading)
            }
        }
    }

    private fun convertToMenuListItem(dish: DishItem): MenuListItem =
        MenuListItem.DishListItem(
            id = dish.id,
            name = dish.name,
            price = dish.price,
            category = dish.dishCategoryId
        )
}