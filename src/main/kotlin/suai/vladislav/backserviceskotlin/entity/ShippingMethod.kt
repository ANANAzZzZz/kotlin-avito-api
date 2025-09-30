package suai.vladislav.backserviceskotlin.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.DecimalMin
import java.math.BigDecimal

@Entity
@Table(name = "shipping_methods")
data class ShippingMethod(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @NotBlank
    @Column(nullable = false)
    val name: String,

    @DecimalMin("0.0")
    @Column(nullable = false, precision = 10, scale = 2)
    val price: BigDecimal,

    @OneToMany(mappedBy = "shippingMethod", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val ships: MutableList<Ship> = mutableListOf()
)
