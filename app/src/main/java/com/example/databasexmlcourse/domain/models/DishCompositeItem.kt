package com.example.databasexmlcourse.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DishCompositeItem(
    val id: String,
    val name: String,
    val price: Int,
    val category: DishCategory,
    val count: Int = 0
): Parcelable
