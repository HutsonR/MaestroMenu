package com.example.databasexmlcourse.features.feature_menu.adapter.models

import com.example.databasexmlcourse.core.composite.CompositeItem

sealed class MenuListItem(override val id: String): CompositeItem {

    data class DishListItem(
        override val id: String,
        val name: String,
        val price: Int,
        val category: String
    ): MenuListItem(id)

    data object Loading: MenuListItem("Loading")

}