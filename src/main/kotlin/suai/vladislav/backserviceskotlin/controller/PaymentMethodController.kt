package suai.vladislav.backserviceskotlin.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import suai.vladislav.backserviceskotlin.dto.*
import suai.vladislav.backserviceskotlin.service.PaymentMethodService

@RestController
@RequestMapping("/api/payment-methods")
@Tag(name = "Payment Method", description = "Payment method management API")
class PaymentMethodController(
    private val paymentMethodService: PaymentMethodService
) {

    @GetMapping
    @Operation(summary = "Get all payment methods")
    fun getAllPaymentMethods(): ResponseEntity<List<PaymentMethodDto>> {
        return ResponseEntity.ok(paymentMethodService.findAll())
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get payment method by ID")
    fun getPaymentMethodById(@PathVariable id: Long): ResponseEntity<PaymentMethodDto> {
        return ResponseEntity.ok(paymentMethodService.findById(id))
    }

    @GetMapping("/search")
    @Operation(summary = "Search payment methods by name")
    fun searchPaymentMethodsByName(@RequestParam name: String): ResponseEntity<List<PaymentMethodDto>> {
        return ResponseEntity.ok(paymentMethodService.searchByName(name))
    }

    @PostMapping
    @Operation(summary = "Create new payment method")
    fun createPaymentMethod(@Valid @RequestBody paymentMethodCreateDto: PaymentMethodCreateDto): ResponseEntity<PaymentMethodDto> {
        val createdPaymentMethod = paymentMethodService.create(paymentMethodCreateDto)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPaymentMethod)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update payment method")
    fun updatePaymentMethod(
        @PathVariable id: Long,
        @Valid @RequestBody paymentMethodUpdateDto: PaymentMethodUpdateDto
    ): ResponseEntity<PaymentMethodDto> {
        return ResponseEntity.ok(paymentMethodService.update(id, paymentMethodUpdateDto))
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete payment method")
    fun deletePaymentMethod(@PathVariable id: Long): ResponseEntity<Void> {
        paymentMethodService.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}
