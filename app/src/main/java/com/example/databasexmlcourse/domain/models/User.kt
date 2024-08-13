package com.example.databasexmlcourse.domain.models

data class User(
    val id: String = "",
    val fio: String,
    val username: String,
    val password: String,
    val userType: String
)