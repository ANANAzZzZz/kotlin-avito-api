package suai.vladislav.backserviceskotlin.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class CartDto(
    val id: Long? = null,
    @field:NotNull
    val userId: Long,
    val shopGroups: List<ShopCartGroup> = emptyList(),
    val totalAmount: BigDecimal = BigDecimal.ZERO,
    val selectedItemsCount: Int = 0,
    val totalItemsCount: Int = 0
)

data class ShopCartGroup(
    val shopId: Long,
    val shopName: String,
    val shopRating: Float,
    val items: List<CartItemDto>,
    val shopTotal: BigDecimal = BigDecimal.ZERO
)

data class CartItemDto(
    val id: Long? = null,
    val advertisementId: Long,
    val name: String,
    val description: String,
    val price: BigDecimal,
    var quantity: Int = 1,
    var selected: Boolean = true,
    var liked: Boolean = false,
    val itemTotal: BigDecimal = BigDecimal.ZERO
)

data class CartAdvertisementDto(
    val id: Long? = null,
    @field:NotNull
    val cartId: Long,
    @field:NotNull
    val advertisementId: Long,
    val advertisement: AdvertisementDto? = null,
    var quantity: Int = 1,
    var selected: Boolean = true,
    var liked: Boolean = false
)

data class AddToCartDto(
    @field:NotNull
    val cartId: Long,
    @field:NotNull
    val advertisementId: Long,
    @field:Min(1)
    val quantity: Int = 1
)

data class UpdateCartItemDto(
    @field:Min(1)
    val quantity: Int? = null,
    val selected: Boolean? = null,
    val liked: Boolean? = null
)
