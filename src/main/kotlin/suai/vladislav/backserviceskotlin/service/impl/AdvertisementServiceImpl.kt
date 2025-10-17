package suai.vladislav.backserviceskotlin.service.impl

import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import suai.vladislav.backserviceskotlin.dto.*
import suai.vladislav.backserviceskotlin.entity.Advertisement
import suai.vladislav.backserviceskotlin.exception.ResourceNotFoundException
import suai.vladislav.backserviceskotlin.repository.AdvertisementRepository
import suai.vladislav.backserviceskotlin.repository.UserRepository
import suai.vladislav.backserviceskotlin.service.AdvertisementService

@Service
@Transactional(readOnly = true)
class AdvertisementServiceImpl(
    private val advertisementRepository: AdvertisementRepository,
    private val userRepository: UserRepository
) : AdvertisementService {

    @Cacheable(value = ["advertisements"])
    override fun findAll(): List<AdvertisementDto> =
        advertisementRepository.findAllWithOwner().map { it.toDto() }

    @Cacheable(value = ["advertisementById"], key = "#id")
    override fun findById(id: Long): AdvertisementDto =
        advertisementRepository.findByIdWithOwner(id)
            ?.toDto()
            ?: throw ResourceNotFoundException("Advertisement not found with id: $id")

    @Cacheable(value = ["advertisementsByOwner"], key = "#ownerId")
    override fun findByOwnerId(ownerId: Long): List<AdvertisementDto> =
        advertisementRepository.findByOwnerIdWithOwner(ownerId).map { it.toDto() }

    override fun searchByName(name: String): List<AdvertisementDto> =
        advertisementRepository.findByNameContainingIgnoreCaseWithOwner(name).map { it.toDto() }

    override fun searchByDescription(description: String): List<AdvertisementDto> =
        advertisementRepository.findByDescriptionContainingIgnoreCaseWithOwner(description).map { it.toDto() }

    override fun searchByKeyword(keyword: String): List<AdvertisementDto> =
        advertisementRepository.searchByKeywordWithOwner(keyword).map { it.toDto() }

    @Transactional
    @CacheEvict(value = ["advertisements", "advertisementsByOwner"], allEntries = true)
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

    @Transactional
    @CacheEvict(value = ["advertisements", "advertisementById", "advertisementsByOwner"], allEntries = true)
    override fun update(id: Long, advertisementUpdateDto: AdvertisementUpdateDto): AdvertisementDto {
        val existingAdvertisement = advertisementRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Advertisement not found with id: $id") }

        val updatedAdvertisement = existingAdvertisement.copy(
            name = advertisementUpdateDto.name ?: existingAdvertisement.name,
            description = advertisementUpdateDto.description ?: existingAdvertisement.description
        )

        return advertisementRepository.save(updatedAdvertisement).toDto()
    }

    @Transactional
    @CacheEvict(value = ["advertisements", "advertisementById", "advertisementsByOwner"], allEntries = true)
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
