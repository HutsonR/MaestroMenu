package com.example.databasexmlcourse.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TableStatus(
    val id: String = "",
    val name: String
): Parcelable
