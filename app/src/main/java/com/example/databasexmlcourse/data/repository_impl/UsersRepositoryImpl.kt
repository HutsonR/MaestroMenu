package com.example.databasexmlcourse.data.repository_impl

import com.example.databasexmlcourse.data.database.UsersDao
import com.example.databasexmlcourse.data.models.UsersEntity
import com.example.databasexmlcourse.data.models.asEntity
import com.example.databasexmlcourse.data.models.asExternalModel
import com.example.databasexmlcourse.data.repository_api.UsersRepository
import com.example.databasexmlcourse.domain.models.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor (
    private val usersDao: UsersDao
): UsersRepository {

    override suspend fun insert(item: Users) = withContext(Dispatchers.IO) {
        usersDao.insert(item.asEntity())
    }

    override suspend fun getAll(): List<Users> = withContext(Dispatchers.IO) {
        val usersEntities: List<UsersEntity> = usersDao.getAll()
        usersEntities.map { it.asExternalModel() }
    }

    override suspend fun deleteById(itemId: Int) = withContext(Dispatchers.IO) {
        usersDao.deleteById(itemId)
    }

    override suspend fun deleteAll() = withContext(Dispatchers.IO) {
        usersDao.deleteAll()
    }

    override suspend fun update(item: Users) = withContext(Dispatchers.IO) {
        usersDao.update(item.asEntity())
    }

}