package suai.vladislav.backserviceskotlin.service

import suai.vladislav.backserviceskotlin.dto.*

interface ShipService {
    fun findAll(): List<ShipDto>
    fun findById(id: Long): ShipDto
    fun findByReceiverId(receiverId: Long): List<ShipDto>
    fun findByShippingMethodId(shippingMethodId: Long): List<ShipDto>
    fun findByPaymentMethodId(paymentMethodId: Long): List<ShipDto>
    fun searchByName(name: String): List<ShipDto>
    fun create(shipCreateDto: ShipCreateDto): ShipDto
    fun update(id: Long, shipUpdateDto: ShipUpdateDto): ShipDto
    fun deleteById(id: Long)
}
