package suai.vladislav.backserviceskotlin.service

import suai.vladislav.backserviceskotlin.dto.*
import java.math.BigDecimal

interface ShippingMethodService {
    fun findAll(): List<ShippingMethodDto>
    fun findById(id: Long): ShippingMethodDto
    fun searchByName(name: String): List<ShippingMethodDto>
    fun findByPriceLessThanEqual(maxPrice: BigDecimal): List<ShippingMethodDto>
    fun findByPriceBetween(minPrice: BigDecimal, maxPrice: BigDecimal): List<ShippingMethodDto>
    fun create(shippingMethodCreateDto: ShippingMethodCreateDto): ShippingMethodDto
    fun update(id: Long, shippingMethodUpdateDto: ShippingMethodUpdateDto): ShippingMethodDto
    fun deleteById(id: Long)
}
