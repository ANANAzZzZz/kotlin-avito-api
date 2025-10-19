package suai.vladislav.backserviceskotlin.dto

import jakarta.validation.constraints.NotBlank

data class PaymentMethodDto(
    val id: Long? = null,
    @field:NotBlank
    val name: String,
    val image: String? = null,
    val description: String? = null
)

data class PaymentMethodCreateDto(
    @field:NotBlank
    val name: String,
    val image: String? = null,
    val description: String? = null
)

data class PaymentMethodUpdateDto(
    val name: String? = null,
    val image: String? = null,
    val description: String? = null
)
