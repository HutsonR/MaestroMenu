package com.example.databasexmlcourse.data.repository_impl

import com.example.databasexmlcourse.data.database.TableStatusesDao
import com.example.databasexmlcourse.data.models.asEntity
import com.example.databasexmlcourse.data.models.asExternalModel
import com.example.databasexmlcourse.data.repository_api.TableStatusesRepository
import com.example.databasexmlcourse.domain.models.TableStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TableStatusesRepositoryImpl @Inject constructor (
    private val tableStatusesDao: TableStatusesDao
): TableStatusesRepository {

    override suspend fun insert(item: TableStatus) = withContext(Dispatchers.IO) {
        tableStatusesDao.insert(item.asEntity())
    }

    override suspend fun getTableStatusById(itemId: String): TableStatus? = withContext(Dispatchers.IO) {
        val userTypeEntity = tableStatusesDao.getTableStatusById(itemId)
        userTypeEntity?.asExternalModel()
    }

    override suspend fun getAll(): List<TableStatus> = withContext(Dispatchers.IO) {
        val userTypesEntities = tableStatusesDao.getAll()
        userTypesEntities.map { it.asExternalModel() }
    }

    override suspend fun deleteById(itemId: String) = withContext(Dispatchers.IO) {
        tableStatusesDao.deleteById(itemId)
    }

    override suspend fun deleteAll() = withContext(Dispatchers.IO) {
        tableStatusesDao.deleteAll()
    }

}