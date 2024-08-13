package com.example.databasexmlcourse.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.databasexmlcourse.data.models.UsersEntity
import com.example.databasexmlcourse.data.models.UsersTypesEntity

@Dao
interface UserTypesDao {
    @Insert
    suspend fun insert(item: UsersTypesEntity)

    @Query("SELECT * FROM users_types ORDER BY id DESC")
    suspend fun getAll(): List<UsersTypesEntity>

    @Query("DELETE FROM users_types WHERE id = :itemId")
    suspend fun deleteById(itemId: String)

    @Query("DELETE FROM users_types")
    suspend fun deleteAll()
}
