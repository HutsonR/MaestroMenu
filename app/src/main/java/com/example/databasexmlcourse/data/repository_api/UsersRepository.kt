package com.example.databasexmlcourse.data.repository_api

import com.example.databasexmlcourse.domain.models.Users

interface UsersRepository {
    suspend fun insert(item: Users)

    suspend fun getAll(): List<Users>

    suspend fun deleteById(itemId: Int)

    suspend fun deleteAll()

    suspend fun update(item: Users)
}