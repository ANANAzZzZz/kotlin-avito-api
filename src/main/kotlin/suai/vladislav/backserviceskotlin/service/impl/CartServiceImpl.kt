package suai.vladislav.backserviceskotlin.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import suai.vladislav.backserviceskotlin.dto.*
import suai.vladislav.backserviceskotlin.entity.Cart
import suai.vladislav.backserviceskotlin.entity.CartAdvertisement
import suai.vladislav.backserviceskotlin.exception.ResourceNotFoundException
import suai.vladislav.backserviceskotlin.exception.ResourceAlreadyExistsException
import suai.vladislav.backserviceskotlin.repository.CartRepository
import suai.vladislav.backserviceskotlin.repository.CartAdvertisementRepository
import suai.vladislav.backserviceskotlin.repository.UserRepository
import suai.vladislav.backserviceskotlin.repository.AdvertisementRepository
import suai.vladislav.backserviceskotlin.service.CartService

@Service
@Transactional
class CartServiceImpl(
    private val cartRepository: CartRepository,
    private val cartAdvertisementRepository: CartAdvertisementRepository,
    private val userRepository: UserRepository,
    private val advertisementRepository: AdvertisementRepository
) : CartService {

    override fun findAll(): List<CartDto> =
        cartRepository.findAll().map { it.toDto() }

    override fun findById(id: Long): CartDto =
        cartRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Cart not found with id: $id") }
            .toDto()

    override fun findByUserId(userId: Long): CartDto =
        cartRepository.findByUserId(userId)
            .orElseThrow { ResourceNotFoundException("Cart not found for user with id: $userId") }
            .toDto()

    override fun create(userId: Long): CartDto {
        val user = userRepository.findById(userId)
            .orElseThrow { ResourceNotFoundException("User not found with id: $userId") }

        // Проверяем, что у пользователя еще нет корзины
        if (cartRepository.findByUserId(userId).isPresent) {
            throw ResourceAlreadyExistsException("Cart already exists for user with id: $userId")
        }

        val cart = Cart(user = user)
        return cartRepository.save(cart).toDto()
    }

    override fun deleteById(id: Long) {
        if (!cartRepository.existsById(id)) {
            throw ResourceNotFoundException("Cart not found with id: $id")
        }
        cartRepository.deleteById(id)
    }

    override fun addAdvertisementToCart(addToCartDto: AddToCartDto): CartAdvertisementDto {
        val cart = cartRepository.findById(addToCartDto.cartId)
            .orElseThrow { ResourceNotFoundException("Cart not found with id: ${addToCartDto.cartId}") }

        val advertisement = advertisementRepository.findById(addToCartDto.advertisementId)
            .orElseThrow { ResourceNotFoundException("Advertisement not found with id: ${addToCartDto.advertisementId}") }

        // Проверяем, что объявление еще не добавлено в корзину
        if (cartAdvertisementRepository.findByCartIdAndAdvertisementId(addToCartDto.cartId, addToCartDto.advertisementId).isPresent) {
            throw ResourceAlreadyExistsException("Advertisement already exists in cart")
        }

        val cartAdvertisement = CartAdvertisement(
            cart = cart,
            advertisement = advertisement
        )

        return cartAdvertisementRepository.save(cartAdvertisement).toDto()
    }

    override fun removeAdvertisementFromCart(cartId: Long, advertisementId: Long) {
        if (!cartAdvertisementRepository.findByCartIdAndAdvertisementId(cartId, advertisementId).isPresent) {
            throw ResourceNotFoundException("Advertisement not found in cart")
        }
        cartAdvertisementRepository.deleteByCartIdAndAdvertisementId(cartId, advertisementId)
    }

    override fun getCartWithAdvertisements(cartId: Long): CartDto {
        val cart = cartRepository.findByIdWithAdvertisements(cartId)
            .orElseThrow { ResourceNotFoundException("Cart not found with id: $cartId") }

        val advertisements = cartAdvertisementRepository.findByCartIdWithAdvertisement(cartId)
            .map { it.advertisement.toAdvertisementDto() }

        return cart.toDto().copy(advertisements = advertisements)
    }

    private fun Cart.toDto() = CartDto(
        id = id,
        userId = user.id,
        advertisements = emptyList()
    )

    private fun CartAdvertisement.toDto() = CartAdvertisementDto(
        id = id,
        cartId = cart.id,
        advertisementId = advertisement.id,
        advertisement = advertisement.toAdvertisementDto()
    )

    private fun suai.vladislav.backserviceskotlin.entity.Advertisement.toAdvertisementDto() = AdvertisementDto(
        id = id,
        name = name,
        description = description,
        ownerId = owner.id,
        ownerName = "${owner.firstName} ${owner.lastName}"
    )
}
