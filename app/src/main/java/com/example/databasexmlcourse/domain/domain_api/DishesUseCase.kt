package com.example.databasexmlcourse.domain.domain_api

import com.example.databasexmlcourse.domain.models.DishItem
import com.example.databasexmlcourse.domain.util.Resource

interface DishesUseCase {
    suspend fun insert(item: DishItem): Resource

    suspend fun getDishById(id: String): Resource

    suspend fun getAll(): List<DishItem>

    suspend fun deleteById(itemId: String): Resource

    suspend fun deleteAll()

    suspend fun update(item: DishItem): Resource
}