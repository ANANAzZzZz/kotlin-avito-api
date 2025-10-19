package suai.vladislav.backserviceskotlin.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "payment_methods")
data class PaymentMethod(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @NotBlank
    @Column(nullable = false)
    val name: String,

    @Column(nullable = true)
    val image: String? = null,

    @Column(name = "description", length = 500)
    val description: String? = null,

    @OneToMany(mappedBy = "paymentMethod", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val ships: MutableList<Ship> = mutableListOf()
)
