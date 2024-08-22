package com.example.databasexmlcourse.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.databasexmlcourse.domain.models.User
import java.util.UUID

@Entity(
    tableName = "users",
    foreignKeys = [ForeignKey(
        entity = UsersTypesEntity::class,
        parentColumns = ["id"],
        childColumns = ["userTypeId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["userTypeId"])]
)
data class UsersEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val fio: String,
    val username: String,
    val password: String,
    @ColumnInfo(name = "userTypeId") val userTypeId: String
)

fun UsersEntity.asExternalModel() = User(
    id = id,
    fio = fio,
    username = username,
    password = password,
    userType = userTypeId
)
fun User.asEntity() = UsersEntity(
    id = id.ifBlank { UUID.randomUUID().toString() },
    fio = fio,
    username = username,
    password = password,
    userTypeId = userType
)