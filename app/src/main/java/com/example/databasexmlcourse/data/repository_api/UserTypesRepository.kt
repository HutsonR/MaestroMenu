package com.example.databasexmlcourse.data.repository_api

import com.example.databasexmlcourse.domain.models.UserType
import com.example.databasexmlcourse.domain.util.Resource

interface UserTypesRepository {
    suspend fun insert(item: UserType)

    suspend fun getTypeById(itemId: String): UserType?

    suspend fun getAll(): List<UserType>

    suspend fun deleteById(itemId: String)

    suspend fun deleteAll()
}