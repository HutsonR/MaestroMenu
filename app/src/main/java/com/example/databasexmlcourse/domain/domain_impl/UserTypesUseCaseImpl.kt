package com.example.databasexmlcourse.domain.domain_impl

import com.example.databasexmlcourse.data.repository_api.UserTypesRepository
import com.example.databasexmlcourse.domain.domain_api.UserTypesUseCase
import com.example.databasexmlcourse.domain.models.UserType
import com.example.databasexmlcourse.domain.util.Resource
import javax.inject.Inject

class UserTypesUseCaseImpl @Inject constructor (
    private val userTypesRepository: UserTypesRepository
): UserTypesUseCase {
    override suspend fun insert(item: UserType): Resource {
        return if (item.name.isEmpty())
            Resource.Failed(Exception("Fields can not be empty"))
        else {
            Resource.Success(userTypesRepository.insert(item))
        }
    }

    override suspend fun getTypeById(itemId: String): Resource {
        val type = userTypesRepository.getTypeById(itemId)
        return if (type != null) {
            Resource.Success(type)
        } else {
            Resource.Failed(Exception("User not found"))
        }
    }

    override suspend fun getAll(): List<UserType> {
        return userTypesRepository.getAll()
    }

    override suspend fun deleteById(itemId: String): Resource =
        Resource.Success(userTypesRepository.deleteById(itemId))

    override suspend fun deleteAll() =
        userTypesRepository.deleteAll()

}