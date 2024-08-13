package com.example.databasexmlcourse.data.repository_impl

import com.example.databasexmlcourse.data.database.UserTypesDao
import com.example.databasexmlcourse.data.models.asEntity
import com.example.databasexmlcourse.data.models.asExternalModel
import com.example.databasexmlcourse.data.repository_api.UserTypesRepository
import com.example.databasexmlcourse.domain.models.UserType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserTypesRepositoryImpl @Inject constructor (
    private val userTypesDao: UserTypesDao
): UserTypesRepository {

    override suspend fun insert(item: UserType) = withContext(Dispatchers.IO) {
        userTypesDao.insert(item.asEntity())
    }

    override suspend fun getAll(): List<UserType> = withContext(Dispatchers.IO) {
        val userTypesEntities = userTypesDao.getAll()
        userTypesEntities.map { it.asExternalModel() }
    }

    override suspend fun deleteById(itemId: String) = withContext(Dispatchers.IO) {
        userTypesDao.deleteById(itemId)
    }

    override suspend fun deleteAll() = withContext(Dispatchers.IO) {
        userTypesDao.deleteAll()
    }

}