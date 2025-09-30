package suai.vladislav.backserviceskotlin.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import suai.vladislav.backserviceskotlin.dto.*
import suai.vladislav.backserviceskotlin.entity.UserRole
import suai.vladislav.backserviceskotlin.service.UserService

@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "User management API")
class UserController(
    private val userService: UserService
) {

    @GetMapping
    @Operation(summary = "Get all users")
    fun getAllUsers(): ResponseEntity<List<UserDto>> {
        return ResponseEntity.ok(userService.findAll())
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    fun getUserById(@PathVariable id: Long): ResponseEntity<UserDto> {
        return ResponseEntity.ok(userService.findById(id))
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get user by email")
    fun getUserByEmail(@PathVariable email: String): ResponseEntity<UserDto> {
        return ResponseEntity.ok(userService.findByEmail(email))
    }

    @GetMapping("/role/{role}")
    @Operation(summary = "Get users by role")
    fun getUsersByRole(@PathVariable role: UserRole): ResponseEntity<List<UserDto>> {
        return ResponseEntity.ok(userService.findByRole(role))
    }

    @GetMapping("/rating")
    @Operation(summary = "Get users by minimum rating")
    fun getUsersByRating(@RequestParam minRating: Float): ResponseEntity<List<UserDto>> {
        return ResponseEntity.ok(userService.findByRatingGreaterThanEqual(minRating))
    }

    @GetMapping("/search")
    @Operation(summary = "Search users by name")
    fun searchUsersByName(@RequestParam name: String): ResponseEntity<List<UserDto>> {
        return ResponseEntity.ok(userService.searchByName(name))
    }

    @PostMapping
    @Operation(summary = "Create new user")
    fun createUser(@Valid @RequestBody userCreateDto: UserCreateDto): ResponseEntity<UserDto> {
        val createdUser = userService.create(userCreateDto)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user")
    fun updateUser(
        @PathVariable id: Long,
        @Valid @RequestBody userUpdateDto: UserUpdateDto
    ): ResponseEntity<UserDto> {
        return ResponseEntity.ok(userService.update(id, userUpdateDto))
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Void> {
        userService.deleteById(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/exists")
    @Operation(summary = "Check if user exists by email")
    fun existsByEmail(@RequestParam email: String): ResponseEntity<Boolean> {
        return ResponseEntity.ok(userService.existsByEmail(email))
    }
}
