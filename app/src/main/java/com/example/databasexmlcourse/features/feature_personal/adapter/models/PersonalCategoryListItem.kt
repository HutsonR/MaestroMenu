package com.example.databasexmlcourse.features.feature_personal.adapter.models

import com.example.databasexmlcourse.core.composite.CompositeItem

data class PersonalCategoryListItem(
    override val id: String,
    val text: String
): CompositeItem