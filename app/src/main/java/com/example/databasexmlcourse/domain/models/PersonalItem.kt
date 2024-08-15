package com.example.databasexmlcourse.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PersonalItem(
    val id: String,
    val fio: String,
    val login: String,
    val password: String,
    val type: String
): Parcelable
