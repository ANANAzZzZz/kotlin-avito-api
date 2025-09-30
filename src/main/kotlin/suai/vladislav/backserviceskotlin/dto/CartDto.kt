package suai.vladislav.backserviceskotlin.dto

import jakarta.validation.constraints.NotNull

data class CartDto(
    val id: Long? = null,
    @field:NotNull
    val userId: Long,
    val advertisements: List<AdvertisementDto> = emptyList()
)

data class CartAdvertisementDto(
    val id: Long? = null,
    @field:NotNull
    val cartId: Long,
    @field:NotNull
    val advertisementId: Long,
    val advertisement: AdvertisementDto? = null
)

data class AddToCartDto(
    @field:NotNull
    val cartId: Long,
    @field:NotNull
    val advertisementId: Long
)
