package com.example.databasexmlcourse.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.databasexmlcourse.data.models.DishCategoriesEntity
import com.example.databasexmlcourse.data.models.UsersEntity
import com.example.databasexmlcourse.data.models.UsersTypesEntity

@Dao
interface DishCategoriesDao {
    @Insert
    suspend fun insert(item: DishCategoriesEntity)

    @Query("SELECT * FROM dish_categories WHERE id = :itemId")
    suspend fun getDishCategoryById(itemId: String): DishCategoriesEntity?

    @Query("SELECT * FROM dish_categories ORDER BY id DESC")
    suspend fun getAll(): List<DishCategoriesEntity>

    @Query("DELETE FROM dish_categories WHERE id = :itemId")
    suspend fun deleteById(itemId: String)

    @Query("DELETE FROM dish_categories")
    suspend fun deleteAll()
}
