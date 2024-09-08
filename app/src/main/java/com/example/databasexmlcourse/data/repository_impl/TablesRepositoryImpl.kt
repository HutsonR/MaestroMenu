package com.example.databasexmlcourse.data.repository_impl

import com.example.databasexmlcourse.data.database.TablesDao
import com.example.databasexmlcourse.data.models.asEntity
import com.example.databasexmlcourse.data.models.asExternalModel
import com.example.databasexmlcourse.data.repository_api.TablesRepository
import com.example.databasexmlcourse.domain.models.DishItem
import com.example.databasexmlcourse.domain.models.TableItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TablesRepositoryImpl @Inject constructor (
    private val tablesDao: TablesDao
): TablesRepository {

    override suspend fun insert(item: TableItem) = withContext(Dispatchers.IO) {
        tablesDao.insert(item.asEntity())
    }

    override suspend fun getTableById(userId: String): TableItem? = withContext(Dispatchers.IO) {
        val userEntity = tablesDao.getTableById(userId)
        userEntity?.asExternalModel()
    }

    override suspend fun getAll(): List<TableItem> = withContext(Dispatchers.IO) {
        val usersEntities = tablesDao.getAll()
        usersEntities.map { it.asExternalModel() }
    }

    override suspend fun deleteById(itemId: String) = withContext(Dispatchers.IO) {
        tablesDao.deleteById(itemId)
    }

    override suspend fun deleteAll() = withContext(Dispatchers.IO) {
        tablesDao.deleteAll()
    }

    override suspend fun update(item: TableItem) = withContext(Dispatchers.IO) {
        tablesDao.update(item.asEntity())
    }

}