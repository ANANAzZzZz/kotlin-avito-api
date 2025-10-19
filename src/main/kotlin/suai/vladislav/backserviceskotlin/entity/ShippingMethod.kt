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

    @Column(name = "badge", length = 255)
    val badge: String? = null,

    @Column(name = "delivery_time", length = 255)
    val deliveryTime: String? = null,

    @OneToMany(mappedBy = "shippingMethod", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val ships: MutableList<Ship> = mutableListOf()
)
