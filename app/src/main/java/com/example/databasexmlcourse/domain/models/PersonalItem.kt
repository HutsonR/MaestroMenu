package com.example.databasexmlcourse.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PersonalItem(
    val id: String,
    val name: String,
    val type: String
): Parcelable
