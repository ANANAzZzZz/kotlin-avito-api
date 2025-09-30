package suai.vladislav.backserviceskotlin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import suai.vladislav.backserviceskotlin.entity.PaymentMethod

@Repository
interface PaymentMethodRepository : JpaRepository<PaymentMethod, Long> {

    fun findByNameContainingIgnoreCase(name: String): List<PaymentMethod>

    @Query("SELECT pm FROM PaymentMethod pm WHERE LOWER(pm.name) = LOWER(:name)")
    fun findByNameIgnoreCase(@Param("name") name: String): List<PaymentMethod>
}
