package suai.vladislav.backserviceskotlin.service.impl

import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import suai.vladislav.backserviceskotlin.dto.*
import suai.vladislav.backserviceskotlin.entity.ShippingMethod
import suai.vladislav.backserviceskotlin.exception.ResourceNotFoundException
import suai.vladislav.backserviceskotlin.repository.ShippingMethodRepository
import suai.vladislav.backserviceskotlin.service.ShippingMethodService
import java.math.BigDecimal

@Service
@Transactional(readOnly = true)
class ShippingMethodServiceImpl(
    private val shippingMethodRepository: ShippingMethodRepository
) : ShippingMethodService {

    @Cacheable(value = ["shippingMethods"])
    override fun findAll(): List<ShippingMethodDto> =
        shippingMethodRepository.findAll().map { it.toDto() }

    @Cacheable(value = ["shippingMethodById"], key = "#id")
    override fun findById(id: Long): ShippingMethodDto =
        shippingMethodRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Shipping method not found with id: $id") }
            .toDto()

    override fun searchByName(name: String): List<ShippingMethodDto> =
        shippingMethodRepository.findByNameContainingIgnoreCase(name).map { it.toDto() }

    @Cacheable(value = ["shippingMethodsByPrice"], key = "#maxPrice")
    override fun findByPriceLessThanEqual(maxPrice: BigDecimal): List<ShippingMethodDto> =
        shippingMethodRepository.findByPriceLessThanEqualOrderByPriceAsc(maxPrice).map { it.toDto() }

    @Cacheable(value = ["shippingMethodsByPriceRange"], key = "#minPrice.toString() + '-' + #maxPrice.toString()")
    override fun findByPriceBetween(minPrice: BigDecimal, maxPrice: BigDecimal): List<ShippingMethodDto> =
        shippingMethodRepository.findByPriceBetweenOrderByPriceAsc(minPrice, maxPrice).map { it.toDto() }

    @Transactional
    @CacheEvict(value = ["shippingMethods", "shippingMethodsByPrice", "shippingMethodsByPriceRange"], allEntries = true)
    override fun create(shippingMethodCreateDto: ShippingMethodCreateDto): ShippingMethodDto {
        val shippingMethod = ShippingMethod(
            name = shippingMethodCreateDto.name,
            price = shippingMethodCreateDto.price,
            badge = shippingMethodCreateDto.badge,
            deliveryTime = shippingMethodCreateDto.deliveryTime
        )
        return shippingMethodRepository.save(shippingMethod).toDto()
    }

    @Transactional
    @CacheEvict(value = ["shippingMethods", "shippingMethodById", "shippingMethodsByPrice", "shippingMethodsByPriceRange"], allEntries = true)
    override fun update(id: Long, shippingMethodUpdateDto: ShippingMethodUpdateDto): ShippingMethodDto {
        val existingShippingMethod = shippingMethodRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Shipping method not found with id: $id") }

        val updatedShippingMethod = existingShippingMethod.copy(
            name = shippingMethodUpdateDto.name ?: existingShippingMethod.name,
            price = shippingMethodUpdateDto.price ?: existingShippingMethod.price,
            badge = shippingMethodUpdateDto.badge ?: existingShippingMethod.badge,
            deliveryTime = shippingMethodUpdateDto.deliveryTime ?: existingShippingMethod.deliveryTime
        )

        return shippingMethodRepository.save(updatedShippingMethod).toDto()
    }

    @Transactional
    @CacheEvict(value = ["shippingMethods", "shippingMethodById", "shippingMethodsByPrice", "shippingMethodsByPriceRange"], allEntries = true)
    override fun deleteById(id: Long) {
        if (!shippingMethodRepository.existsById(id)) {
            throw ResourceNotFoundException("Shipping method not found with id: $id")
        }
        shippingMethodRepository.deleteById(id)
    }

    private fun ShippingMethod.toDto() = ShippingMethodDto(
        id = id,
        name = name,
        price = price,
        badge = badge,
        deliveryTime = deliveryTime
    )
}
