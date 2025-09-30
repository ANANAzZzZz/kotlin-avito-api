package suai.vladislav.backserviceskotlin.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class ShipDto(
    val id: Long? = null,
    @field:NotBlank
    val name: String,
    @field:NotNull
    val shippingMethodId: Long,
    val shippingMethodName: String? = null,
    @field:NotNull
    val paymentMethodId: Long,
    val paymentMethodName: String? = null,
    @field:NotNull
    val receiverId: Long,
    val receiverName: String? = null,
    @field:NotBlank
    val address: String
)

data class ShipCreateDto(
    @field:NotBlank
    val name: String,
    @field:NotNull
    val shippingMethodId: Long,
    @field:NotNull
    val paymentMethodId: Long,
    @field:NotNull
    val receiverId: Long,
    @field:NotBlank
    val address: String
)

data class ShipUpdateDto(
    val name: String? = null,
    val shippingMethodId: Long? = null,
    val paymentMethodId: Long? = null,
    val receiverId: Long? = null,
    val address: String? = null
)
