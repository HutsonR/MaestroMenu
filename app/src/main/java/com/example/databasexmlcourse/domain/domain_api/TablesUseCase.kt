package com.example.databasexmlcourse.domain.domain_api

import com.example.databasexmlcourse.domain.models.TableItem
import com.example.databasexmlcourse.domain.util.Resource

interface TablesUseCase {
    suspend fun insert(item: TableItem): Resource

    suspend fun getTableById(id: String): Resource

    suspend fun getAll(): List<TableItem>

    suspend fun deleteById(itemId: String): Resource

    suspend fun deleteAll()

    suspend fun update(item: TableItem): Resource
}