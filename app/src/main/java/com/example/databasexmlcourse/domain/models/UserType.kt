package com.example.databasexmlcourse.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserType(
    val id: String = "",
    val name: String
): Parcelable
