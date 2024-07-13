package com.example.databasexmlcourse.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.databasexmlcourse.domain.models.Users
import java.util.UUID

@Entity(tableName = "users")
data class UsersEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val username: String,
    val password: String
)

fun UsersEntity.asExternalModel() = Users(
    id = id,
    username = username,
    password = password
)
fun Users.asEntity() = UsersEntity(
    id = id,
    username = username,
    password = password
)