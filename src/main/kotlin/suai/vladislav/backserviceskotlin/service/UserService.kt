package suai.vladislav.backserviceskotlin.service

import suai.vladislav.backserviceskotlin.dto.*
import suai.vladislav.backserviceskotlin.entity.UserRole

interface UserService {
    fun findAll(): List<UserDto>
    fun findById(id: Long): UserDto
    fun findByEmail(email: String): UserDto
    fun findByRole(role: UserRole): List<UserDto>
    fun findByRatingGreaterThanEqual(minRating: Float): List<UserDto>
    fun searchByName(name: String): List<UserDto>
    fun create(userCreateDto: UserCreateDto): UserDto
    fun update(id: Long, userUpdateDto: UserUpdateDto): UserDto
    fun deleteById(id: Long)
    fun existsByEmail(email: String): Boolean
}
