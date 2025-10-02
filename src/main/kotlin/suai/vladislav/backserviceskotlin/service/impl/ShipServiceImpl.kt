package suai.vladislav.backserviceskotlin.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import suai.vladislav.backserviceskotlin.dto.*
import suai.vladislav.backserviceskotlin.entity.Ship
import suai.vladislav.backserviceskotlin.exception.ResourceNotFoundException
import suai.vladislav.backserviceskotlin.repository.*
import suai.vladislav.backserviceskotlin.service.ShipService

@Service
@Transactional
class ShipServiceImpl(
    private val shipRepository: ShipRepository,
    private val userRepository: UserRepository,
    private val shippingMethodRepository: ShippingMethodRepository,
    private val paymentMethodRepository: PaymentMethodRepository
) : ShipService {

    override fun findAll(): List<ShipDto> =
        shipRepository.findAll().map { it.toDto() }

    override fun findById(id: Long): ShipDto =
        shipRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Ship not found with id: $id") }
            .toDto()

    override fun findByReceiverId(receiverId: Long): List<ShipDto> =
        shipRepository.findByReceiverId(receiverId).map { it.toDto() }

    override fun findByShippingMethodId(shippingMethodId: Long): List<ShipDto> =
        shipRepository.findByShippingMethodId(shippingMethodId).map { it.toDto() }

    override fun findByPaymentMethodId(paymentMethodId: Long): List<ShipDto> =
        shipRepository.findByPaymentMethodId(paymentMethodId).map { it.toDto() }

    override fun searchByName(name: String): List<ShipDto> =
        shipRepository.findByNameContainingIgnoreCase(name).map { it.toDto() }

    override fun create(shipCreateDto: ShipCreateDto): ShipDto {
        val receiver = userRepository.findById(shipCreateDto.receiverId)
            .orElseThrow { ResourceNotFoundException("User not found with id: ${shipCreateDto.receiverId}") }

        val owner = userRepository.findById(shipCreateDto.ownerId)
            .orElseThrow { ResourceNotFoundException("User not found with id: ${shipCreateDto.ownerId}") }

        val shippingMethod = shippingMethodRepository.findById(shipCreateDto.shippingMethodId)
            .orElseThrow { ResourceNotFoundException("Shipping method not found with id: ${shipCreateDto.shippingMethodId}") }

        val paymentMethod = paymentMethodRepository.findById(shipCreateDto.paymentMethodId)
            .orElseThrow { ResourceNotFoundException("Payment method not found with id: ${shipCreateDto.paymentMethodId}") }

        val ship = Ship(
            name = shipCreateDto.name,
            shippingMethod = shippingMethod,
            paymentMethod = paymentMethod,
            receiver = receiver,
            owner = owner,
            address = shipCreateDto.address
        )

        return shipRepository.save(ship).toDto()
    }

    override fun update(id: Long, shipUpdateDto: ShipUpdateDto): ShipDto {
        val existingShip = shipRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Ship not found with id: $id") }

        var updatedShip = existingShip

        shipUpdateDto.receiverId?.let { receiverId ->
            val receiver = userRepository.findById(receiverId)
                .orElseThrow { ResourceNotFoundException("User not found with id: $receiverId") }
            updatedShip = updatedShip.copy(receiver = receiver)
        }

        shipUpdateDto.ownerId?.let { ownerId ->
            val owner = userRepository.findById(ownerId)
                .orElseThrow { ResourceNotFoundException("User not found with id: $ownerId") }
            updatedShip = updatedShip.copy(owner = owner)
        }

        shipUpdateDto.shippingMethodId?.let { shippingMethodId ->
            val shippingMethod = shippingMethodRepository.findById(shippingMethodId)
                .orElseThrow { ResourceNotFoundException("Shipping method not found with id: $shippingMethodId") }
            updatedShip = updatedShip.copy(shippingMethod = shippingMethod)
        }

        shipUpdateDto.paymentMethodId?.let { paymentMethodId ->
            val paymentMethod = paymentMethodRepository.findById(paymentMethodId)
                .orElseThrow { ResourceNotFoundException("Payment method not found with id: $paymentMethodId") }
            updatedShip = updatedShip.copy(paymentMethod = paymentMethod)
        }

        updatedShip = updatedShip.copy(
            name = shipUpdateDto.name ?: updatedShip.name,
            address = shipUpdateDto.address ?: updatedShip.address
        )

        return shipRepository.save(updatedShip).toDto()
    }

    override fun deleteById(id: Long) {
        if (!shipRepository.existsById(id)) {
            throw ResourceNotFoundException("Ship not found with id: $id")
        }
        shipRepository.deleteById(id)
    }

    private fun Ship.toDto() = ShipDto(
        id = id,
        name = name,
        shippingMethodId = shippingMethod.id,
        shippingMethodName = shippingMethod.name,
        paymentMethodId = paymentMethod.id,
        paymentMethodName = paymentMethod.name,
        receiverId = receiver.id,
        receiverName = "${receiver.firstName} ${receiver.lastName}",
        ownerId = owner.id,
        ownerName = "${owner.firstName} ${owner.lastName}",
        address = address
    )
}
