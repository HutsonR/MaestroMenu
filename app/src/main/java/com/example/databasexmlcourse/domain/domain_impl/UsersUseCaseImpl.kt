package com.example.databasexmlcourse.domain.domain_impl

import com.example.databasexmlcourse.data.repository_api.UsersRepository
import com.example.databasexmlcourse.domain.domain_api.UsersUseCase
import com.example.databasexmlcourse.domain.models.Users
import com.example.databasexmlcourse.domain.util.Resource
import javax.inject.Inject

class UsersUseCaseImpl @Inject constructor (
    private val usersRepository: UsersRepository
): UsersUseCase {
    override suspend fun insert(item: Users): Resource {
        return if (item.username.isEmpty() || item.password.isEmpty())
            Resource.Failed(Exception("Fields can not be empty"))
        else {
            Resource.Success(usersRepository.insert(item))
        }
    }

    override suspend fun getAll(): List<Users> {
        return usersRepository.getAll()
    }

    override suspend fun deleteById(itemId: Int): Resource =
        Resource.Success(usersRepository.deleteById(itemId))

    override suspend fun deleteAll() =
        usersRepository.deleteAll()

    override suspend fun update(item: Users): Resource =
        Resource.Success(usersRepository.update(item))

}