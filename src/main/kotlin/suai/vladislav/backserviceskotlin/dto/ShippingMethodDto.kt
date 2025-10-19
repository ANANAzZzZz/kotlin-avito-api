package suai.vladislav.backserviceskotlin.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class ShippingMethodDto(
    val id: Long? = null,
    @field:NotBlank
    val name: String,
    @field:DecimalMin("0.0")
    val price: BigDecimal
)

data class ShippingMethodCreateDto(
    @field:NotBlank
    val name: String,
    @field:DecimalMin("0.0")
    val price: BigDecimal
)

data class ShippingMethodUpdateDto(
    val name: String? = null,
    @field:DecimalMin("0.0")
    val price: BigDecimal? = null
)

data class DeliverySelectionDto(
    @field:NotNull
    val userId: Long,
    @field:NotNull
    val shippingMethodId: Long
)

data class DeliverySelectionResponseDto(
    val userId: Long,
    val shippingMethodId: Long,
    val shippingMethodName: String,
    val price: BigDecimal,
    val message: String
)
