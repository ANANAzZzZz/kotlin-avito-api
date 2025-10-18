package suai.vladislav.backserviceskotlin.service.impl

import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.data.domain.PageRequest
import suai.vladislav.backserviceskotlin.dto.*
import suai.vladislav.backserviceskotlin.entity.Cart
import suai.vladislav.backserviceskotlin.entity.CartAdvertisement
import suai.vladislav.backserviceskotlin.exception.ResourceNotFoundException
import suai.vladislav.backserviceskotlin.exception.ResourceAlreadyExistsException
import suai.vladislav.backserviceskotlin.exception.InvalidRequestException
import suai.vladislav.backserviceskotlin.repository.CartRepository
import suai.vladislav.backserviceskotlin.repository.CartAdvertisementRepository
import suai.vladislav.backserviceskotlin.repository.UserRepository
import suai.vladislav.backserviceskotlin.repository.AdvertisementRepository
import suai.vladislav.backserviceskotlin.service.CartService
import java.math.BigDecimal

@Service
@Transactional(readOnly = true)
class CartServiceImpl(
    private val cartRepository: CartRepository,
    private val cartAdvertisementRepository: CartAdvertisementRepository,
    private val userRepository: UserRepository,
    private val advertisementRepository: AdvertisementRepository
) : CartService {

    @Cacheable(value = ["carts"])
    override fun findAll(): List<CartDto> =
        cartRepository.findAll().map { it.toDto() }

    @Cacheable(value = ["cartById"], key = "#id")
    override fun findById(id: Long): CartDto =
        cartRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Cart not found with id: $id") }
            .toDto()

    @Cacheable(value = ["cartByUser"], key = "#userId")
    override fun findByUserId(userId: Long): CartDto =
        cartRepository.findByUserId(userId)
            .orElseThrow { ResourceNotFoundException("Cart not found for user with id: $userId") }
            .toDto()

    @Transactional
    @CacheEvict(value = ["carts", "cartByUser"], allEntries = true)
    override fun create(userId: Long): CartDto {
        val user = userRepository.findById(userId)
            .orElseThrow { ResourceNotFoundException("User not found with id: $userId") }

        if (cartRepository.findByUserId(userId).isPresent) {
            throw ResourceAlreadyExistsException("Cart already exists for user with id: $userId")
        }

        val cart = Cart(user = user)
        return cartRepository.save(cart).toDto()
    }

    @Transactional
    @CacheEvict(value = ["carts", "cartById", "cartByUser", "cartWithAdvertisements"], allEntries = true)
    override fun deleteById(id: Long) {
        if (!cartRepository.existsById(id)) {
            throw ResourceNotFoundException("Cart not found with id: $id")
        }
        cartRepository.deleteById(id)
    }

    @Transactional
    @CacheEvict(value = ["cartWithAdvertisements", "cartById", "cartByUser"], allEntries = true)
    override fun addAdvertisementToCart(addToCartDto: AddToCartDto): CartAdvertisementDto {
        val cart = cartRepository.findById(addToCartDto.cartId)
            .orElseThrow { ResourceNotFoundException("Cart not found with id: ${addToCartDto.cartId}") }

        val advertisement = advertisementRepository.findById(addToCartDto.advertisementId)
            .orElseThrow { ResourceNotFoundException("Advertisement not found with id: ${addToCartDto.advertisementId}") }

        // Проверяем, что объявление еще не добавлено в корзину
        val existingItem = cartAdvertisementRepository.findByCartIdAndAdvertisementId(
            addToCartDto.cartId,
            addToCartDto.advertisementId
        )

        if (existingItem.isPresent) {
            // Если товар уже есть, увеличиваем количество
            val item = existingItem.get()
            item.quantity += addToCartDto.quantity
            return cartAdvertisementRepository.save(item).toDto()
        }

        val cartAdvertisement = CartAdvertisement(
            cart = cart,
            advertisement = advertisement,
            quantity = addToCartDto.quantity,
            selected = true
        )

        return cartAdvertisementRepository.save(cartAdvertisement).toDto()
    }

    @Transactional
    @CacheEvict(value = ["cartWithAdvertisements", "cartById", "cartByUser"], allEntries = true)
    override fun removeAdvertisementFromCart(cartId: Long, advertisementId: Long) {
        if (!cartAdvertisementRepository.findByCartIdAndAdvertisementId(cartId, advertisementId).isPresent) {
            throw ResourceNotFoundException("Advertisement not found in cart")
        }
        cartAdvertisementRepository.deleteByCartIdAndAdvertisementId(cartId, advertisementId)
    }

    @Transactional
    @CacheEvict(value = ["cartWithAdvertisements", "cartById", "cartByUser"], allEntries = true)
    override fun updateCartItem(cartId: Long, advertisementId: Long, updateDto: UpdateCartItemDto): CartAdvertisementDto {
        val cartItem = cartAdvertisementRepository.findByCartIdAndAdvertisementId(cartId, advertisementId)
            .orElseThrow { ResourceNotFoundException("Cart item not found") }

        updateDto.quantity?.let {
            if (it < 1) {
                throw InvalidRequestException("Quantity must be at least 1")
            }
            cartItem.quantity = it
        }
        updateDto.selected?.let { cartItem.selected = it }
        updateDto.liked?.let { cartItem.liked = it }

        return cartAdvertisementRepository.save(cartItem).toDto()
    }

    @Cacheable(value = ["cartWithAdvertisements"], key = "#cartId")
    override fun getCartWithAdvertisements(cartId: Long): CartDto {
        val cart = cartRepository.findById(cartId)
            .orElseThrow { ResourceNotFoundException("Cart not found with id: $cartId") }

        val cartItems = cartAdvertisementRepository.findByCartIdWithAdvertisement(cartId)

        // Группируем товары по магазинам (владельцам) и формируем список
        val shopGroups = cartItems
            .groupBy { it.advertisement.owner.id }
            .map { (ownerId, items) ->
                val owner = items.first().advertisement.owner
                val cartItemDtos = items.map { cartItem ->
                    val itemTotal = cartItem.advertisement.price.multiply(BigDecimal(cartItem.quantity))
                    CartItemDto(
                        id = cartItem.id,
                        advertisementId = cartItem.advertisement.id,
                        name = cartItem.advertisement.name,
                        description = cartItem.advertisement.description,
                        price = cartItem.advertisement.price,
                        quantity = cartItem.quantity,
                        selected = cartItem.selected,
                        liked = cartItem.liked,
                        itemTotal = itemTotal
                    )
                }

                val shopTotal = cartItemDtos
                    .filter { it.selected }
                    .fold(BigDecimal.ZERO) { acc, item -> acc.add(item.itemTotal) }

                ShopCartGroup(
                    shopId = ownerId,
                    shopName = "${owner.firstName} ${owner.lastName}",
                    shopRating = owner.rating,
                    items = cartItemDtos,
                    shopTotal = shopTotal
                )
            }

        // Подсчитываем общую сумму только по выбранным товарам
        val totalAmount = shopGroups
            .sumOf { it.shopTotal }

        val selectedItemsCount = cartItems.count { it.selected }
        val totalItemsCount = cartItems.size

        return CartDto(
            id = cart.id,
            userId = cart.user.id,
            shopGroups = shopGroups,
            totalAmount = totalAmount,
            selectedItemsCount = selectedItemsCount,
            totalItemsCount = totalItemsCount
        )
    }

    @Cacheable(value = ["recommendedAdvertisements"], key = "#cartId + '_' + #limit")
    override fun getRecommendedAdvertisements(cartId: Long, limit: Int): List<AdvertisementDto> {
        // Проверяем существование корзины
        if (!cartRepository.existsById(cartId)) {
            throw ResourceNotFoundException("Cart not found with id: $cartId")
        }

        // Получаем ID товаров, которые уже в корзине
        val cartAdvertisementIds = cartAdvertisementRepository.findByCartId(cartId)
            .map { it.advertisement.id }
            .toSet()

        // Если корзина пуста, возвращаем случайные товары
        if (cartAdvertisementIds.isEmpty()) {
            val pageable = PageRequest.of(0, limit)
            return advertisementRepository.findRandomExcludingIds(emptySet(), pageable)
                .map { it.toAdvertisementDto() }
        }

        // Получаем ID владельцев товаров в корзине для рекомендаций от тех же продавцов
        val ownerIds = cartAdvertisementRepository.findByCartIdWithAdvertisement(cartId)
            .map { it.advertisement.owner.id }
            .toSet()

        // Ищем товары от тех же продавцов, которых нет в корзине
        val pageable = PageRequest.of(0, limit)
        val recommendations = if (ownerIds.isNotEmpty()) {
            advertisementRepository.findByOwnerIdsNotInIds(ownerIds, cartAdvertisementIds, pageable)
        } else {
            advertisementRepository.findRandomExcludingIds(cartAdvertisementIds, pageable)
        }

        return recommendations.map { it.toAdvertisementDto() }
    }

    private fun Cart.toDto() = CartDto(
        id = id,
        userId = user.id,
        shopGroups = emptyList(),
        totalAmount = BigDecimal.ZERO,
        selectedItemsCount = 0,
        totalItemsCount = 0
    )

    private fun CartAdvertisement.toDto() = CartAdvertisementDto(
        id = id,
        cartId = cart.id,
        advertisementId = advertisement.id,
        advertisement = advertisement.toAdvertisementDto(),
        quantity = quantity,
        selected = selected,
        liked = liked
    )

    private fun suai.vladislav.backserviceskotlin.entity.Advertisement.toAdvertisementDto() = AdvertisementDto(
        id = id,
        name = name,
        description = description,
        price = price,
        ownerId = owner.id,
        ownerName = "${owner.firstName} ${owner.lastName}"
    )
}
