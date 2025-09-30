package suai.vladislav.backserviceskotlin.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import suai.vladislav.backserviceskotlin.dto.*
import suai.vladislav.backserviceskotlin.service.CartService

@RestController
@RequestMapping("/api/carts")
@Tag(name = "Cart", description = "Shopping cart management API")
class CartController(
    private val cartService: CartService
) {

    @GetMapping
    @Operation(summary = "Get all carts")
    fun getAllCarts(): ResponseEntity<List<CartDto>> {
        return ResponseEntity.ok(cartService.findAll())
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get cart by ID")
    fun getCartById(@PathVariable id: Long): ResponseEntity<CartDto> {
        return ResponseEntity.ok(cartService.findById(id))
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get cart by user ID")
    fun getCartByUserId(@PathVariable userId: Long): ResponseEntity<CartDto> {
        return ResponseEntity.ok(cartService.findByUserId(userId))
    }

    @GetMapping("/{id}/with-advertisements")
    @Operation(summary = "Get cart with all advertisements")
    fun getCartWithAdvertisements(@PathVariable id: Long): ResponseEntity<CartDto> {
        return ResponseEntity.ok(cartService.getCartWithAdvertisements(id))
    }

    @PostMapping
    @Operation(summary = "Create new cart for user")
    fun createCart(@RequestParam userId: Long): ResponseEntity<CartDto> {
        val createdCart = cartService.create(userId)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCart)
    }

    @PostMapping("/add-advertisement")
    @Operation(summary = "Add advertisement to cart")
    fun addAdvertisementToCart(@Valid @RequestBody addToCartDto: AddToCartDto): ResponseEntity<CartAdvertisementDto> {
        val cartAdvertisement = cartService.addAdvertisementToCart(addToCartDto)
        return ResponseEntity.status(HttpStatus.CREATED).body(cartAdvertisement)
    }

    @DeleteMapping("/{cartId}/advertisements/{advertisementId}")
    @Operation(summary = "Remove advertisement from cart")
    fun removeAdvertisementFromCart(
        @PathVariable cartId: Long,
        @PathVariable advertisementId: Long
    ): ResponseEntity<Void> {
        cartService.removeAdvertisementFromCart(cartId, advertisementId)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete cart")
    fun deleteCart(@PathVariable id: Long): ResponseEntity<Void> {
        cartService.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}
