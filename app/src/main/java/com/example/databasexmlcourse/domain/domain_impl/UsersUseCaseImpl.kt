package com.example.databasexmlcourse.domain.domain_impl

import com.example.databasexmlcourse.data.repository_api.UsersRepository
import com.example.databasexmlcourse.domain.domain_api.UsersUseCase
import com.example.databasexmlcourse.domain.models.User
import com.example.databasexmlcourse.domain.util.Resource
import javax.inject.Inject

class UsersUseCaseImpl @Inject constructor (
    private val usersRepository: UsersRepository
): UsersUseCase {
    override suspend fun insert(item: User): Resource {
        return if (item.fio.isEmpty() || item.username.isEmpty() || item.password.isEmpty())
            Resource.Failed(Exception("Fields can not be empty"))
        else {
            Resource.Success(usersRepository.insert(item))
        }
    }

    override suspend fun checkUser(username: String, password: String): Resource {
        val user = usersRepository.checkUser(username, password)
        return if (user != null) {
            Resource.Success(user.id)
        } else {
            Resource.Failed(Exception("User not found"))
        }
    }

    override suspend fun checkUserById(userId: String): Boolean =
        usersRepository.checkUserById(userId)

    override suspend fun getUserById(userId: String): Resource {
        val user = usersRepository.getUserById(userId)
        return if (user != null) {
            Resource.Success(user)
        } else {
            Resource.Failed(Exception("User not found"))
        }
    }

    override suspend fun getAll(): List<User> {
        return usersRepository.getAll()
    }

    override suspend fun deleteById(itemId: String): Resource =
        Resource.Success(usersRepository.deleteById(itemId))

    override suspend fun deleteAll() =
        usersRepository.deleteAll()

    override suspend fun update(item: User): Resource =
        Resource.Success(usersRepository.update(item))

}