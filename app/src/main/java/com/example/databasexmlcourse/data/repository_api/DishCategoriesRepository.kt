package com.example.databasexmlcourse.data.repository_api

import com.example.databasexmlcourse.domain.models.DishCategory

interface DishCategoriesRepository {
    suspend fun insert(item: DishCategory)

    suspend fun getDishCategoryById(itemId: String): DishCategory?

    suspend fun getAll(): List<DishCategory>

    suspend fun deleteById(itemId: String)

    suspend fun deleteAll()
}