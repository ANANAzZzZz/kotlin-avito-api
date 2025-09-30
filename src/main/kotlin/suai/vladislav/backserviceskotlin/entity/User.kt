package suai.vladislav.backserviceskotlin.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.DecimalMax
import jakarta.validation.constraints.DecimalMin

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: UserRole,

    @Email
    @NotBlank
    @Column(unique = true, nullable = false)
    val email: String,

    @NotBlank
    @Column(nullable = false)
    val password: String,

    @NotBlank
    @Column(name = "last_name", nullable = false)
    val lastName: String,

    @NotBlank
    @Column(name = "first_name", nullable = false)
    val firstName: String,

    @Column(nullable = false)
    val address: String,

    @DecimalMin("0.0")
    @DecimalMax("5.0")
    @Column(nullable = false)
    val rating: Float = 0.0f,

    @OneToMany(mappedBy = "owner", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val advertisements: MutableList<Advertisement> = mutableListOf(),

    @OneToOne(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val cart: Cart? = null,

    @OneToMany(mappedBy = "receiver", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val ships: MutableList<Ship> = mutableListOf()
)

enum class UserRole {
    USER, ADMIN, MODERATOR
}
