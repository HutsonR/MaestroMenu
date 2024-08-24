package com.example.databasexmlcourse.domain.domain_api

import com.example.databasexmlcourse.domain.models.DishCategory
import com.example.databasexmlcourse.domain.util.Resource

interface DishCategoriesUseCase {
    suspend fun insert(item: DishCategory): Resource

    suspend fun getDishCategoryById(itemId: String): Resource

    suspend fun getAll(): List<DishCategory>

    suspend fun deleteById(itemId: String): Resource

    suspend fun deleteAll()
}