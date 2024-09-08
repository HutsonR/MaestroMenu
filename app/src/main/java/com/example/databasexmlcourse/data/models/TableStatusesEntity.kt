package com.example.databasexmlcourse.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.databasexmlcourse.domain.models.TableStatus
import java.util.UUID

@Entity(tableName = "table_statuses")
data class TableStatusesEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String
)

fun TableStatusesEntity.asExternalModel() = TableStatus(
    id = id,
    name = name
)
fun TableStatus.asEntity() = TableStatusesEntity(
    name = name
)
