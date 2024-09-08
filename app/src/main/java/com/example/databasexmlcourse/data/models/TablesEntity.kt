package com.example.databasexmlcourse.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.databasexmlcourse.domain.models.TableItem
import java.util.UUID

@Entity(
    tableName = "tables",
    foreignKeys = [ForeignKey(
        entity = TableStatusesEntity::class,
        parentColumns = ["id"],
        childColumns = ["tableStatusId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["tableStatusId"])]
)
data class TablesEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val number: Int,
    @ColumnInfo(name = "tableStatusId") val tableStatusId: String
)
fun TablesEntity.asExternalModel() = TableItem(
    id = id,
    number = number,
    tableStatusId = tableStatusId
)
fun TableItem.asEntity() = TablesEntity(
    id = id.ifBlank { UUID.randomUUID().toString() },
    number = number,
    tableStatusId = tableStatusId
)