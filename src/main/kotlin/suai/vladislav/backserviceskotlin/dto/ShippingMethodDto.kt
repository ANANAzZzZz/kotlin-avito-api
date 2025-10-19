package suai.vladislav.backserviceskotlin.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.DecimalMin
import java.math.BigDecimal

data class ShippingMethodDto(
    val id: Long? = null,
    @field:NotBlank
    val name: String,
    @field:DecimalMin("0.0")
    val price: BigDecimal,
    val badge: String? = null,
    val deliveryTime: String? = null
)

data class ShippingMethodCreateDto(
    @field:NotBlank
    val name: String,
    @field:DecimalMin("0.0")
    val price: BigDecimal,
    val badge: String? = null,
    val deliveryTime: String? = null
)

data class ShippingMethodUpdateDto(
    val name: String? = null,
    @field:DecimalMin("0.0")
    val price: BigDecimal? = null,
    val badge: String? = null,
    val deliveryTime: String? = null
)
