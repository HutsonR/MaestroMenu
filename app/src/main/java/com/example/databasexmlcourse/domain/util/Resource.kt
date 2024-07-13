package com.example.databasexmlcourse.domain.util

sealed class Resource {
    data class Success<out T>(val data: T) : Resource()
    data class Failed(val exception: Exception) : Resource()
}