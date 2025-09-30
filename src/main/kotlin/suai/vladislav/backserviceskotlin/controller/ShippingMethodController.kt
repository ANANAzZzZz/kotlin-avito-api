package suai.vladislav.backserviceskotlin.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import suai.vladislav.backserviceskotlin.dto.*
import suai.vladislav.backserviceskotlin.service.ShippingMethodService
import java.math.BigDecimal

@RestController
@RequestMapping("/api/shipping-methods")
@Tag(name = "Shipping Method", description = "Shipping method management API")
class ShippingMethodController(
    private val shippingMethodService: ShippingMethodService
) {

    @GetMapping
    @Operation(summary = "Get all shipping methods")
    fun getAllShippingMethods(): ResponseEntity<List<ShippingMethodDto>> {
        return ResponseEntity.ok(shippingMethodService.findAll())
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get shipping method by ID")
    fun getShippingMethodById(@PathVariable id: Long): ResponseEntity<ShippingMethodDto> {
        return ResponseEntity.ok(shippingMethodService.findById(id))
    }

    @GetMapping("/search")
    @Operation(summary = "Search shipping methods by name")
    fun searchShippingMethodsByName(@RequestParam name: String): ResponseEntity<List<ShippingMethodDto>> {
        return ResponseEntity.ok(shippingMethodService.searchByName(name))
    }

    @GetMapping("/price/max")
    @Operation(summary = "Get shipping methods with price less than or equal to max price")
    fun getShippingMethodsByMaxPrice(@RequestParam maxPrice: BigDecimal): ResponseEntity<List<ShippingMethodDto>> {
        return ResponseEntity.ok(shippingMethodService.findByPriceLessThanEqual(maxPrice))
    }

    @GetMapping("/price/range")
    @Operation(summary = "Get shipping methods with price in range")
    fun getShippingMethodsByPriceRange(
        @RequestParam minPrice: BigDecimal,
        @RequestParam maxPrice: BigDecimal
    ): ResponseEntity<List<ShippingMethodDto>> {
        return ResponseEntity.ok(shippingMethodService.findByPriceBetween(minPrice, maxPrice))
    }

    @PostMapping
    @Operation(summary = "Create new shipping method")
    fun createShippingMethod(@Valid @RequestBody shippingMethodCreateDto: ShippingMethodCreateDto): ResponseEntity<ShippingMethodDto> {
        val createdShippingMethod = shippingMethodService.create(shippingMethodCreateDto)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdShippingMethod)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update shipping method")
    fun updateShippingMethod(
        @PathVariable id: Long,
        @Valid @RequestBody shippingMethodUpdateDto: ShippingMethodUpdateDto
    ): ResponseEntity<ShippingMethodDto> {
        return ResponseEntity.ok(shippingMethodService.update(id, shippingMethodUpdateDto))
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete shipping method")
    fun deleteShippingMethod(@PathVariable id: Long): ResponseEntity<Void> {
        shippingMethodService.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}
