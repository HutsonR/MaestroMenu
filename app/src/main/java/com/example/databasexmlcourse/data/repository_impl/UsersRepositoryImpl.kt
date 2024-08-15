package com.example.databasexmlcourse.data.repository_impl

import com.example.databasexmlcourse.data.database.UsersDao
import com.example.databasexmlcourse.data.models.UsersEntity
import com.example.databasexmlcourse.data.models.asEntity
import com.example.databasexmlcourse.data.models.asExternalModel
import com.example.databasexmlcourse.data.repository_api.UsersRepository
import com.example.databasexmlcourse.domain.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor (
    private val usersDao: UsersDao
): UsersRepository {

    override suspend fun insert(item: User) = withContext(Dispatchers.IO) {
        usersDao.insert(item.asEntity())
    }

    override suspend fun checkUser(username: String): List<User?> = withContext(Dispatchers.IO) {
        val usersEntities = usersDao.checkUser(username)
        usersEntities.map { it?.asExternalModel() }
    }

    override suspend fun checkUserById(userId: String): Boolean = withContext(Dispatchers.IO) {
        usersDao.checkUserById(userId)
    }

    override suspend fun getUserById(userId: String): User? = withContext(Dispatchers.IO) {
        val userEntity: UsersEntity? = usersDao.getUserById(userId)
        userEntity?.asExternalModel()
    }

    override suspend fun getAll(): List<User> = withContext(Dispatchers.IO) {
        val usersEntities: List<UsersEntity> = usersDao.getAll()
        usersEntities.map { it.asExternalModel() }
    }

    override suspend fun deleteById(itemId: String) = withContext(Dispatchers.IO) {
        usersDao.deleteById(itemId)
    }

    override suspend fun deleteAll() = withContext(Dispatchers.IO) {
        usersDao.deleteAll()
    }

    override suspend fun update(item: User) = withContext(Dispatchers.IO) {
        usersDao.update(item.asEntity())
    }

}