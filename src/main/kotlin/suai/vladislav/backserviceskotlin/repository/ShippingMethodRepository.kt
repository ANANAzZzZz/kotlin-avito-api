package suai.vladislav.backserviceskotlin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import suai.vladislav.backserviceskotlin.entity.ShippingMethod
import java.math.BigDecimal

@Repository
interface ShippingMethodRepository : JpaRepository<ShippingMethod, Long> {

    fun findByNameContainingIgnoreCase(name: String): List<ShippingMethod>

    @Query("SELECT sm FROM ShippingMethod sm WHERE sm.price <= :maxPrice ORDER BY sm.price ASC")
    fun findByPriceLessThanEqualOrderByPriceAsc(@Param("maxPrice") maxPrice: BigDecimal): List<ShippingMethod>

    @Query("SELECT sm FROM ShippingMethod sm WHERE sm.price BETWEEN :minPrice AND :maxPrice ORDER BY sm.price ASC")
    fun findByPriceBetweenOrderByPriceAsc(@Param("minPrice") minPrice: BigDecimal, @Param("maxPrice") maxPrice: BigDecimal): List<ShippingMethod>
}
