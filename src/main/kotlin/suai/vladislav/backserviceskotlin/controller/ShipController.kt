package suai.vladislav.backserviceskotlin.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import suai.vladislav.backserviceskotlin.dto.*
import suai.vladislav.backserviceskotlin.service.ShipService

@RestController
@RequestMapping("/api/ships")
@Tag(name = "Ship", description = "Order management API")
class ShipController(
    private val shipService: ShipService
) {

    @GetMapping
    @Operation(summary = "Get all orders")
    fun getAllShips(): ResponseEntity<List<ShipDto>> {
        return ResponseEntity.ok(shipService.findAll())
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID")
    fun getShipById(@PathVariable id: Long): ResponseEntity<ShipDto> {
        return ResponseEntity.ok(shipService.findById(id))
    }

    @GetMapping("/receiver/{receiverId}")
    @Operation(summary = "Get orders by receiver ID")
    fun getShipsByReceiverId(@PathVariable receiverId: Long): ResponseEntity<List<ShipDto>> {
        return ResponseEntity.ok(shipService.findByReceiverId(receiverId))
    }

    @GetMapping("/shipping-method/{shippingMethodId}")
    @Operation(summary = "Get orders by shipping method ID")
    fun getShipsByShippingMethodId(@PathVariable shippingMethodId: Long): ResponseEntity<List<ShipDto>> {
        return ResponseEntity.ok(shipService.findByShippingMethodId(shippingMethodId))
    }

    @GetMapping("/payment-method/{paymentMethodId}")
    @Operation(summary = "Get orders by payment method ID")
    fun getShipsByPaymentMethodId(@PathVariable paymentMethodId: Long): ResponseEntity<List<ShipDto>> {
        return ResponseEntity.ok(shipService.findByPaymentMethodId(paymentMethodId))
    }

    @GetMapping("/search")
    @Operation(summary = "Search orders by name")
    fun searchShipsByName(@RequestParam name: String): ResponseEntity<List<ShipDto>> {
        return ResponseEntity.ok(shipService.searchByName(name))
    }

    @PostMapping
    @Operation(summary = "Create new order")
    fun createShip(@Valid @RequestBody shipCreateDto: ShipCreateDto): ResponseEntity<ShipDto> {
        val createdShip = shipService.create(shipCreateDto)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdShip)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update order")
    fun updateShip(
        @PathVariable id: Long,
        @Valid @RequestBody shipUpdateDto: ShipUpdateDto
    ): ResponseEntity<ShipDto> {
        return ResponseEntity.ok(shipService.update(id, shipUpdateDto))
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete order")
    fun deleteShip(@PathVariable id: Long): ResponseEntity<Void> {
        shipService.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}
