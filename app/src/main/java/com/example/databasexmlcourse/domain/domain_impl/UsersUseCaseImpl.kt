package com.example.databasexmlcourse.domain.domain_impl

import com.example.databasexmlcourse.data.repository_api.UsersRepository
import com.example.databasexmlcourse.domain.domain_api.UsersUseCase
import com.example.databasexmlcourse.domain.models.User
import com.example.databasexmlcourse.domain.util.Resource
import org.springframework.security.crypto.bcrypt.BCrypt
import javax.inject.Inject

class UsersUseCaseImpl @Inject constructor (
    private val usersRepository: UsersRepository
): UsersUseCase {
    override suspend fun insert(item: User): Resource {
        return if (item.fio.isEmpty() || item.username.isEmpty() || item.password.isEmpty())
            Resource.Failed(Exception("Fields can not be empty"))
        else {
            val hashedPassword = hashPassword(item.password)
            val userWithHashedPassword = item.copy(password = hashedPassword)
            Resource.Success(usersRepository.insert(userWithHashedPassword))
        }
    }

    override suspend fun checkUser(username: String, password: String): Resource {
        val users = usersRepository.checkUser(username)
        val user = users.firstOrNull { it != null && checkPassword(password, it.password) }
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

    // Хэширование пароля
    private fun hashPassword(password: String): String {
        val salt = BCrypt.gensalt()
        return BCrypt.hashpw(password, salt)
    }

    // Проверка пароля
    private fun checkPassword(plainPassword: String, hashedPassword: String): Boolean {
        return BCrypt.checkpw(plainPassword, hashedPassword)
    }

}