package com.example.databasexmlcourse.features.feature_menu.adapter.models

import com.example.databasexmlcourse.core.composite.CompositeItem

data class MenuCategoryListItem(
    override val id: String,
    val text: String
): CompositeItem