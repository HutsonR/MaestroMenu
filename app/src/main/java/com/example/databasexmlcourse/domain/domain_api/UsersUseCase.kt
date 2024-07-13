package com.example.databasexmlcourse.domain.domain_api

import com.example.databasexmlcourse.domain.models.Users
import com.example.databasexmlcourse.domain.util.Resource

interface UsersUseCase {
    suspend fun insert(item: Users): Resource

    suspend fun getAll(): List<Users>

    suspend fun deleteById(itemId: Int): Resource

    suspend fun deleteAll()

    suspend fun update(item: Users): Resource
}