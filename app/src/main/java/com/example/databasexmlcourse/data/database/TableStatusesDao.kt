package com.example.databasexmlcourse.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.databasexmlcourse.data.models.TableStatusesEntity

@Dao
interface TableStatusesDao {
    @Insert
    suspend fun insert(item: TableStatusesEntity)

    @Query("SELECT * FROM table_statuses WHERE id = :itemId")
    suspend fun getTableStatusById(itemId: String): TableStatusesEntity?

    @Query("SELECT * FROM table_statuses ORDER BY id DESC")
    suspend fun getAll(): List<TableStatusesEntity>

    @Query("DELETE FROM table_statuses WHERE id = :itemId")
    suspend fun deleteById(itemId: String)

    @Query("DELETE FROM table_statuses")
    suspend fun deleteAll()
}
