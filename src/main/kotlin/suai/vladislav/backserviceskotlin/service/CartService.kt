package suai.vladislav.backserviceskotlin.service

import suai.vladislav.backserviceskotlin.dto.*

interface CartService {
    fun findAll(): List<CartDto>
    fun findById(id: Long): CartDto
    fun findByUserId(userId: Long): CartDto
    fun create(userId: Long): CartDto
    fun deleteById(id: Long)
    fun addAdvertisementToCart(addToCartDto: AddToCartDto): CartAdvertisementDto
    fun removeAdvertisementFromCart(cartId: Long, advertisementId: Long)
    fun getCartWithAdvertisements(cartId: Long): CartDto
    fun updateCartItem(cartId: Long, advertisementId: Long, updateDto: UpdateCartItemDto): CartAdvertisementDto
    fun updateShopGroupSelection(cartId: Long, shopId: Long, selected: Boolean): CartDto
    fun getRecommendedAdvertisements(cartId: Long, limit: Int = 10): List<AdvertisementDto>
}
