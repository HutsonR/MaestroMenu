package com.example.databasexmlcourse.domain.domain_api

import com.example.databasexmlcourse.domain.models.User
import com.example.databasexmlcourse.domain.util.Resource

interface UsersUseCase {
    suspend fun insert(item: User): Resource

    suspend fun checkUser(username: String, password: String): Resource

    suspend fun checkUserById(userId: String): Boolean

    suspend fun getAll(): List<User>

    suspend fun deleteById(itemId: String): Resource

    suspend fun deleteAll()

    suspend fun update(item: User): Resource
}