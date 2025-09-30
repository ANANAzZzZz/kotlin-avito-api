package suai.vladislav.backserviceskotlin.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class AdvertisementDto(
    val id: Long? = null,
    @field:NotBlank
    val name: String,
    @field:NotBlank
    val description: String,
    @field:NotNull
    val ownerId: Long,
    val ownerName: String? = null
)

data class AdvertisementCreateDto(
    @field:NotBlank
    val name: String,
    @field:NotBlank
    val description: String,
    @field:NotNull
    val ownerId: Long
)

data class AdvertisementUpdateDto(
    val name: String? = null,
    val description: String? = null
)
