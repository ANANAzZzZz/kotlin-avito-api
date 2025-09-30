package suai.vladislav.backserviceskotlin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import suai.vladislav.backserviceskotlin.entity.Cart
import suai.vladislav.backserviceskotlin.entity.User
import java.util.*

@Repository
interface CartRepository : JpaRepository<Cart, Long> {

    fun findByUser(user: User): Optional<Cart>

    fun findByUserId(userId: Long): Optional<Cart>

    @Query("SELECT c FROM Cart c JOIN FETCH c.cartAdvertisements ca JOIN FETCH ca.advertisement WHERE c.id = :cartId")
    fun findByIdWithAdvertisements(@Param("cartId") cartId: Long): Optional<Cart>
}
