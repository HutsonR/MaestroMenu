package com.example.databasexmlcourse.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TableCompositeItem(
    val id: String,
    val number: Int,
    val tableStatus: TableStatus
): Parcelable