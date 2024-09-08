package com.example.databasexmlcourse.domain.domain_api

import com.example.databasexmlcourse.domain.models.TableStatus
import com.example.databasexmlcourse.domain.util.Resource

interface TableStatusesUseCase {
    suspend fun insert(item: TableStatus): Resource

    suspend fun getDishCategoryById(itemId: String): Resource

    suspend fun getAll(): List<TableStatus>

    suspend fun deleteById(itemId: String): Resource

    suspend fun deleteAll()
}