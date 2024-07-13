package com.example.databasexmlcourse.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "tables")
data class TablesEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val number: Int
)
