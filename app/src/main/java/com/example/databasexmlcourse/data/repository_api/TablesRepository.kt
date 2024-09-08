package com.example.databasexmlcourse.data.repository_api

import com.example.databasexmlcourse.domain.models.TableItem

interface TablesRepository {
    suspend fun insert(item: TableItem)

    suspend fun getTableById(userId: String): TableItem?

    suspend fun getAll(): List<TableItem>

    suspend fun deleteById(itemId: String)

    suspend fun deleteAll()

    suspend fun update(item: TableItem)
}