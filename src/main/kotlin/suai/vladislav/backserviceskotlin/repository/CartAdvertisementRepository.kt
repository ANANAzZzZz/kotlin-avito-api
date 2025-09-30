package suai.vladislav.backserviceskotlin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import suai.vladislav.backserviceskotlin.entity.CartAdvertisement
import suai.vladislav.backserviceskotlin.entity.Cart
import suai.vladislav.backserviceskotlin.entity.Advertisement
import java.util.*

@Repository
interface CartAdvertisementRepository : JpaRepository<CartAdvertisement, Long> {

    fun findByCart(cart: Cart): List<CartAdvertisement>

    fun findByCartId(cartId: Long): List<CartAdvertisement>

    fun findByCartAndAdvertisement(cart: Cart, advertisement: Advertisement): Optional<CartAdvertisement>

    fun findByCartIdAndAdvertisementId(cartId: Long, advertisementId: Long): Optional<CartAdvertisement>

    @Query("SELECT ca FROM CartAdvertisement ca JOIN FETCH ca.advertisement WHERE ca.cart.id = :cartId")
    fun findByCartIdWithAdvertisement(@Param("cartId") cartId: Long): List<CartAdvertisement>

    fun deleteByCartIdAndAdvertisementId(cartId: Long, advertisementId: Long)
}
