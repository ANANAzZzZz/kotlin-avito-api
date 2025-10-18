package suai.vladislav.backserviceskotlin.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.URL
import java.math.BigDecimal

data class AdvertisementDto(
    val id: Long? = null,
    @field:NotBlank
    val name: String,
    @field:NotBlank
    val description: String,
    val price: BigDecimal = BigDecimal.ZERO,
    @field:NotNull
    val ownerId: Long,
    val ownerName: String? = null,
    @field:URL
    val imageUrl: String? = null
)

data class AdvertisementCreateDto(
    @field:NotBlank
    val name: String,
    @field:NotBlank
    val description: String,
    val price: BigDecimal = BigDecimal.ZERO,
    @field:NotNull
    val ownerId: Long,
    @field:URL
    val imageUrl: String? = null
)

data class AdvertisementUpdateDto(
    val name: String? = null,
    val description: String? = null,
    val price: BigDecimal? = null,
    @field:URL
    val imageUrl: String? = null
)
