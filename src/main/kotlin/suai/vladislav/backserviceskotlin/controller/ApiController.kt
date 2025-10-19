package suai.vladislav.backserviceskotlin.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import suai.vladislav.backserviceskotlin.dto.*
import suai.vladislav.backserviceskotlin.service.PaymentMethodService
import suai.vladislav.backserviceskotlin.service.ShippingMethodService
import suai.vladislav.backserviceskotlin.service.UserService

@RestController
@RequestMapping("/api")
@Tag(name = "API", description = "General API endpoints")
class ApiController(
    private val userService: UserService,
    private val shippingMethodService: ShippingMethodService,
    private val paymentMethodService: PaymentMethodService
) {

    @GetMapping("/getListDelivery")
    @Operation(summary = "Get list of delivery methods")
    fun getListDelivery(): ResponseEntity<List<ShippingMethodDto>> {
        return ResponseEntity.ok(shippingMethodService.findAll())
    }

    @GetMapping("/getUserInfo/{id}")
    @Operation(summary = "Get user information by ID")
    fun getUserInfo(@PathVariable id: Long): ResponseEntity<UserDto> {
        return ResponseEntity.ok(userService.findById(id))
    }

    @GetMapping("/getListPayments")
    @Operation(summary = "Get list of payment methods")
    fun getListPayments(): ResponseEntity<List<PaymentMethodDto>> {
        return ResponseEntity.ok(paymentMethodService.findAll())
    }

    @PutMapping("/updateUserInfo/{id}")
    @Operation(summary = "Update user information")
    fun updateUserInfo(
        @PathVariable id: Long,
        @Valid @RequestBody userUpdateDto: UserUpdateDto
    ): ResponseEntity<UserDto> {
        return ResponseEntity.ok(userService.update(id, userUpdateDto))
    }

    @PostMapping("/selectDelivery")
    @Operation(summary = "Select delivery method for user")
    fun selectDelivery(
        @Valid @RequestBody deliverySelectionDto: DeliverySelectionDto
    ): ResponseEntity<DeliverySelectionResponseDto> {
        val user = userService.findById(deliverySelectionDto.userId)
        val shippingMethod = shippingMethodService.findById(deliverySelectionDto.shippingMethodId)

        return ResponseEntity.ok(
            DeliverySelectionResponseDto(
                userId = user.id!!,
                shippingMethodId = shippingMethod.id!!,
                shippingMethodName = shippingMethod.name,
                price = shippingMethod.price,
                message = "Delivery method selected successfully"
            )
        )
    }
}
