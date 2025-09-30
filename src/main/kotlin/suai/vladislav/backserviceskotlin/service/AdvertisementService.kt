package suai.vladislav.backserviceskotlin.service

import suai.vladislav.backserviceskotlin.dto.*

interface AdvertisementService {
    fun findAll(): List<AdvertisementDto>
    fun findById(id: Long): AdvertisementDto
    fun findByOwnerId(ownerId: Long): List<AdvertisementDto>
    fun searchByName(name: String): List<AdvertisementDto>
    fun searchByDescription(description: String): List<AdvertisementDto>
    fun searchByKeyword(keyword: String): List<AdvertisementDto>
    fun create(advertisementCreateDto: AdvertisementCreateDto): AdvertisementDto
    fun update(id: Long, advertisementUpdateDto: AdvertisementUpdateDto): AdvertisementDto
    fun deleteById(id: Long)
}
