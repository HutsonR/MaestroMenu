package com.example.databasexmlcourse.data.repository_api

import com.example.databasexmlcourse.domain.models.DishItem

interface DishesRepository {
    suspend fun insert(item: DishItem)

    suspend fun getDishById(userId: String): DishItem?

    suspend fun getAll(): List<DishItem>

    suspend fun deleteById(itemId: String)

    suspend fun deleteAll()

    suspend fun update(item: DishItem)
}