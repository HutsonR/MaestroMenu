package com.example.databasexmlcourse.data.repository_api

import com.example.databasexmlcourse.domain.models.TableStatus

interface TableStatusesRepository {
    suspend fun insert(item: TableStatus)

    suspend fun getTableStatusById(itemId: String): TableStatus?

    suspend fun getAll(): List<TableStatus>

    suspend fun deleteById(itemId: String)

    suspend fun deleteAll()
}