package com.example.databasexmlcourse.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "users_types")
data class UsersTypesEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String
)
