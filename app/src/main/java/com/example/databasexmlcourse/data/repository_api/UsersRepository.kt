package com.example.databasexmlcourse.data.repository_api

import com.example.databasexmlcourse.domain.models.User

interface UsersRepository {
    suspend fun insert(item: User)

    suspend fun checkUser(username: String, password: String): User?

    suspend fun checkUserById(userId: String): Boolean

    suspend fun getAll(): List<User>

    suspend fun deleteById(itemId: String)

    suspend fun deleteAll()

    suspend fun update(item: User)
}