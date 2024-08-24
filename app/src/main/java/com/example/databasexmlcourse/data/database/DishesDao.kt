package com.example.databasexmlcourse.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.databasexmlcourse.data.models.DishesEntity
import com.example.databasexmlcourse.data.models.UsersEntity

@Dao
interface DishesDao {
    @Insert
    suspend fun insert(item: DishesEntity)

    @Query("SELECT * FROM dishes WHERE id = :userId")
    suspend fun getDishById(userId: String): DishesEntity?

    @Query("SELECT * FROM dishes ORDER BY id DESC")
    suspend fun getAll(): List<DishesEntity>

    @Query("DELETE FROM dishes WHERE id = :itemId")
    suspend fun deleteById(itemId: String)

    @Query("DELETE FROM dishes")
    suspend fun deleteAll()

    @Update
    suspend fun update(item: DishesEntity)
}
