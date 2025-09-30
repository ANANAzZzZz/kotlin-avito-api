package suai.vladislav.backserviceskotlin.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import suai.vladislav.backserviceskotlin.dto.*
import suai.vladislav.backserviceskotlin.entity.PaymentMethod
import suai.vladislav.backserviceskotlin.exception.ResourceNotFoundException
import suai.vladislav.backserviceskotlin.repository.PaymentMethodRepository
import suai.vladislav.backserviceskotlin.service.PaymentMethodService

@Service
@Transactional
class PaymentMethodServiceImpl(
    private val paymentMethodRepository: PaymentMethodRepository
) : PaymentMethodService {

    override fun findAll(): List<PaymentMethodDto> =
        paymentMethodRepository.findAll().map { it.toDto() }

    override fun findById(id: Long): PaymentMethodDto =
        paymentMethodRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Payment method not found with id: $id") }
            .toDto()

    override fun searchByName(name: String): List<PaymentMethodDto> =
        paymentMethodRepository.findByNameContainingIgnoreCase(name).map { it.toDto() }

    override fun create(paymentMethodCreateDto: PaymentMethodCreateDto): PaymentMethodDto {
        val paymentMethod = PaymentMethod(
            name = paymentMethodCreateDto.name,
            image = paymentMethodCreateDto.image
        )
        return paymentMethodRepository.save(paymentMethod).toDto()
    }

    override fun update(id: Long, paymentMethodUpdateDto: PaymentMethodUpdateDto): PaymentMethodDto {
        val existingPaymentMethod = paymentMethodRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Payment method not found with id: $id") }

        val updatedPaymentMethod = existingPaymentMethod.copy(
            name = paymentMethodUpdateDto.name ?: existingPaymentMethod.name,
            image = paymentMethodUpdateDto.image ?: existingPaymentMethod.image
        )

        return paymentMethodRepository.save(updatedPaymentMethod).toDto()
    }

    override fun deleteById(id: Long) {
        if (!paymentMethodRepository.existsById(id)) {
            throw ResourceNotFoundException("Payment method not found with id: $id")
        }
        paymentMethodRepository.deleteById(id)
    }

    private fun PaymentMethod.toDto() = PaymentMethodDto(
        id = id,
        name = name,
        image = image
    )
}
