package com.example.databasexmlcourse.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.databasexmlcourse.domain.models.User
import com.example.databasexmlcourse.domain.models.UserType
import java.util.UUID

@Entity(tableName = "users_types")
data class UsersTypesEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String
)

fun UsersTypesEntity.asExternalModel() = UserType(
    id = id,
    name = name
)
fun UserType.asEntity() = UsersTypesEntity(
    name = name
)
