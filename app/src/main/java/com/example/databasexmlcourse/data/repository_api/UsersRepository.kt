package com.example.databasexmlcourse.data.repository_api

import com.example.databasexmlcourse.domain.models.User

interface UsersRepository {
    suspend fun insert(item: User)

    suspend fun checkUser(username: String): List<User?>

    suspend fun checkUserById(userId: String): Boolean

    suspend fun getUserById(userId: String): User?

    suspend fun getAll(): List<User>

    suspend fun deleteById(itemId: String)

    suspend fun deleteAll()

    suspend fun update(item: User)
}