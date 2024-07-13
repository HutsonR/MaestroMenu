package com.example.databasexmlcourse.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "table_statuses")
data class TableStatusesEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val tableId: String,
    val status: String
)
