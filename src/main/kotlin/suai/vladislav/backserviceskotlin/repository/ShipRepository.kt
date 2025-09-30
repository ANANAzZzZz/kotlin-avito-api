package suai.vladislav.backserviceskotlin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import suai.vladislav.backserviceskotlin.entity.Ship
import suai.vladislav.backserviceskotlin.entity.User

@Repository
interface ShipRepository : JpaRepository<Ship, Long> {

    fun findByReceiver(receiver: User): List<Ship>

    fun findByReceiverId(receiverId: Long): List<Ship>

    fun findByShippingMethodId(shippingMethodId: Long): List<Ship>

    fun findByPaymentMethodId(paymentMethodId: Long): List<Ship>

    @Query("SELECT s FROM Ship s JOIN FETCH s.receiver JOIN FETCH s.shippingMethod JOIN FETCH s.paymentMethod WHERE s.id = :shipId")
    fun findByIdWithDetails(@Param("shipId") shipId: Long): Ship?

    @Query("SELECT s FROM Ship s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    fun findByNameContainingIgnoreCase(@Param("name") name: String): List<Ship>
}
