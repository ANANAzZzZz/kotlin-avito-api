package suai.vladislav.backserviceskotlin.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import suai.vladislav.backserviceskotlin.dto.*
import suai.vladislav.backserviceskotlin.entity.Advertisement
import suai.vladislav.backserviceskotlin.exception.ResourceNotFoundException
import suai.vladislav.backserviceskotlin.repository.AdvertisementRepository
import suai.vladislav.backserviceskotlin.repository.UserRepository
import suai.vladislav.backserviceskotlin.service.AdvertisementService

@Service
@Transactional
class AdvertisementServiceImpl(
    private val advertisementRepository: AdvertisementRepository,
    private val userRepository: UserRepository
) : AdvertisementService {

    override fun findAll(): List<AdvertisementDto> =
        advertisementRepository.findAll().map { it.toDto() }

    override fun findById(id: Long): AdvertisementDto =
        advertisementRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Advertisement not found with id: $id") }
            .toDto()

    override fun findByOwnerId(ownerId: Long): List<AdvertisementDto> =
        advertisementRepository.findByOwnerId(ownerId).map { it.toDto() }

    override fun searchByName(name: String): List<AdvertisementDto> =
        advertisementRepository.findByNameContainingIgnoreCase(name).map { it.toDto() }

    override fun searchByDescription(description: String): List<AdvertisementDto> =
        advertisementRepository.findByDescriptionContainingIgnoreCase(description).map { it.toDto() }

    override fun searchByKeyword(keyword: String): List<AdvertisementDto> =
        advertisementRepository.searchByKeyword(keyword).map { it.toDto() }

    override fun create(advertisementCreateDto: AdvertisementCreateDto): AdvertisementDto {
        val owner = userRepository.findById(advertisementCreateDto.ownerId)
            .orElseThrow { ResourceNotFoundException("User not found with id: ${advertisementCreateDto.ownerId}") }

        val advertisement = Advertisement(
            name = advertisementCreateDto.name,
            description = advertisementCreateDto.description,
            owner = owner
        )

        return advertisementRepository.save(advertisement).toDto()
    }

    override fun update(id: Long, advertisementUpdateDto: AdvertisementUpdateDto): AdvertisementDto {
        val existingAdvertisement = advertisementRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Advertisement not found with id: $id") }

        val updatedAdvertisement = existingAdvertisement.copy(
            name = advertisementUpdateDto.name ?: existingAdvertisement.name,
            description = advertisementUpdateDto.description ?: existingAdvertisement.description
        )

        return advertisementRepository.save(updatedAdvertisement).toDto()
    }

    override fun deleteById(id: Long) {
        if (!advertisementRepository.existsById(id)) {
            throw ResourceNotFoundException("Advertisement not found with id: $id")
        }
        advertisementRepository.deleteById(id)
    }

    private fun Advertisement.toDto() = AdvertisementDto(
        id = id,
        name = name,
        description = description,
        ownerId = owner.id,
        ownerName = "${owner.firstName} ${owner.lastName}"
    )
}
