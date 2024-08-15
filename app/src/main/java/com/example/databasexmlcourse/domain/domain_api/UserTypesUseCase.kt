package com.example.databasexmlcourse.domain.domain_api

import com.example.databasexmlcourse.domain.models.UserType
import com.example.databasexmlcourse.domain.util.Resource

interface UserTypesUseCase {
    suspend fun insert(item: UserType): Resource

    suspend fun getTypeById(itemId: String): Resource

    suspend fun getAll(): List<UserType>

    suspend fun deleteById(itemId: String): Resource

    suspend fun deleteAll()
}