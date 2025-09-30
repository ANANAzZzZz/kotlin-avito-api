package suai.vladislav.backserviceskotlin.dto

import suai.vladislav.backserviceskotlin.entity.UserRole
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.DecimalMax
import jakarta.validation.constraints.DecimalMin

data class UserDto(
    val id: Long? = null,
    @field:NotBlank
    val role: UserRole,
    @field:Email
    @field:NotBlank
    val email: String,
    @field:NotBlank
    val password: String,
    @field:NotBlank
    val lastName: String,
    @field:NotBlank
    val firstName: String,
    val address: String,
    @field:DecimalMin("0.0")
    @field:DecimalMax("5.0")
    val rating: Float = 0.0f
)

data class UserCreateDto(
    @field:NotBlank
    val role: UserRole,
    @field:Email
    @field:NotBlank
    val email: String,
    @field:NotBlank
    val password: String,
    @field:NotBlank
    val lastName: String,
    @field:NotBlank
    val firstName: String,
    val address: String
)

data class UserUpdateDto(
    val role: UserRole? = null,
    @field:Email
    val email: String? = null,
    val password: String? = null,
    val lastName: String? = null,
    val firstName: String? = null,
    val address: String? = null,
    @field:DecimalMin("0.0")
    @field:DecimalMax("5.0")
    val rating: Float? = null
)
