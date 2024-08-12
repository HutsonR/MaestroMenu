package com.example.databasexmlcourse.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DishItem(
    val id: String,
    val name: String,
    val price: Int,
    val dishCategoryId: String,
    val count: Int
): Parcelable
