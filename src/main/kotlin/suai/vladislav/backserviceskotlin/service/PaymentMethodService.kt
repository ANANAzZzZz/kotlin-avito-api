package suai.vladislav.backserviceskotlin.service

import suai.vladislav.backserviceskotlin.dto.*

interface PaymentMethodService {
    fun findAll(): List<PaymentMethodDto>
    fun findById(id: Long): PaymentMethodDto
    fun searchByName(name: String): List<PaymentMethodDto>
    fun create(paymentMethodCreateDto: PaymentMethodCreateDto): PaymentMethodDto
    fun update(id: Long, paymentMethodUpdateDto: PaymentMethodUpdateDto): PaymentMethodDto
    fun deleteById(id: Long)
}
