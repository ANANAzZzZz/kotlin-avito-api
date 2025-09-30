package suai.vladislav.backserviceskotlin.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import suai.vladislav.backserviceskotlin.dto.*
import suai.vladislav.backserviceskotlin.entity.User
import suai.vladislav.backserviceskotlin.entity.UserRole
import suai.vladislav.backserviceskotlin.exception.ResourceNotFoundException
import suai.vladislav.backserviceskotlin.exception.ResourceAlreadyExistsException
import suai.vladislav.backserviceskotlin.repository.UserRepository
import suai.vladislav.backserviceskotlin.service.UserService

@Service
@Transactional
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {

    override fun findAll(): List<UserDto> =
        userRepository.findAll().map { it.toDto() }

    override fun findById(id: Long): UserDto =
        userRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("User not found with id: $id") }
            .toDto()

    override fun findByEmail(email: String): UserDto =
        userRepository.findByEmail(email)
            .orElseThrow { ResourceNotFoundException("User not found with email: $email") }
            .toDto()

    override fun findByRole(role: UserRole): List<UserDto> =
        userRepository.findByRole(role).map { it.toDto() }

    override fun findByRatingGreaterThanEqual(minRating: Float): List<UserDto> =
        userRepository.findByRatingGreaterThanEqualOrderByRatingDesc(minRating).map { it.toDto() }

    override fun searchByName(name: String): List<UserDto> =
        userRepository.findByNameContainingIgnoreCase(name).map { it.toDto() }

    override fun create(userCreateDto: UserCreateDto): UserDto {
        if (userRepository.existsByEmail(userCreateDto.email)) {
            throw ResourceAlreadyExistsException("User with email ${userCreateDto.email} already exists")
        }

        val user = User(
            role = userCreateDto.role,
            email = userCreateDto.email,
            password = userCreateDto.password, // В реальном приложении пароль должен быть зашифрован
            lastName = userCreateDto.lastName,
            firstName = userCreateDto.firstName,
            address = userCreateDto.address
        )

        return userRepository.save(user).toDto()
    }

    override fun update(id: Long, userUpdateDto: UserUpdateDto): UserDto {
        val existingUser = userRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("User not found with id: $id") }

        // Проверяем, что email не занят другим пользователем
        userUpdateDto.email?.let { newEmail ->
            if (newEmail != existingUser.email && userRepository.existsByEmail(newEmail)) {
                throw ResourceAlreadyExistsException("User with email $newEmail already exists")
            }
        }

        val updatedUser = existingUser.copy(
            role = userUpdateDto.role ?: existingUser.role,
            email = userUpdateDto.email ?: existingUser.email,
            password = userUpdateDto.password ?: existingUser.password,
            lastName = userUpdateDto.lastName ?: existingUser.lastName,
            firstName = userUpdateDto.firstName ?: existingUser.firstName,
            address = userUpdateDto.address ?: existingUser.address,
            rating = userUpdateDto.rating ?: existingUser.rating
        )

        return userRepository.save(updatedUser).toDto()
    }

    override fun deleteById(id: Long) {
        if (!userRepository.existsById(id)) {
            throw ResourceNotFoundException("User not found with id: $id")
        }
        userRepository.deleteById(id)
    }

    override fun existsByEmail(email: String): Boolean =
        userRepository.existsByEmail(email)

    private fun User.toDto() = UserDto(
        id = id,
        role = role,
        email = email,
        password = password,
        lastName = lastName,
        firstName = firstName,
        address = address,
        rating = rating
    )
}
