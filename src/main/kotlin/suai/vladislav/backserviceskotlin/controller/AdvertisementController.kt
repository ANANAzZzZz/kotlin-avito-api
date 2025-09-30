package suai.vladislav.backserviceskotlin.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import suai.vladislav.backserviceskotlin.dto.*
import suai.vladislav.backserviceskotlin.service.AdvertisementService

@RestController
@RequestMapping("/api/advertisements")
@Tag(name = "Advertisement", description = "Advertisement management API")
class AdvertisementController(
    private val advertisementService: AdvertisementService
) {

    @GetMapping
    @Operation(summary = "Get all advertisements")
    fun getAllAdvertisements(): ResponseEntity<List<AdvertisementDto>> {
        return ResponseEntity.ok(advertisementService.findAll())
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get advertisement by ID")
    fun getAdvertisementById(@PathVariable id: Long): ResponseEntity<AdvertisementDto> {
        return ResponseEntity.ok(advertisementService.findById(id))
    }

    @GetMapping("/owner/{ownerId}")
    @Operation(summary = "Get advertisements by owner ID")
    fun getAdvertisementsByOwnerId(@PathVariable ownerId: Long): ResponseEntity<List<AdvertisementDto>> {
        return ResponseEntity.ok(advertisementService.findByOwnerId(ownerId))
    }

    @GetMapping("/search/name")
    @Operation(summary = "Search advertisements by name")
    fun searchAdvertisementsByName(@RequestParam name: String): ResponseEntity<List<AdvertisementDto>> {
        return ResponseEntity.ok(advertisementService.searchByName(name))
    }

    @GetMapping("/search/description")
    @Operation(summary = "Search advertisements by description")
    fun searchAdvertisementsByDescription(@RequestParam description: String): ResponseEntity<List<AdvertisementDto>> {
        return ResponseEntity.ok(advertisementService.searchByDescription(description))
    }

    @GetMapping("/search")
    @Operation(summary = "Search advertisements by keyword")
    fun searchAdvertisementsByKeyword(@RequestParam keyword: String): ResponseEntity<List<AdvertisementDto>> {
        return ResponseEntity.ok(advertisementService.searchByKeyword(keyword))
    }

    @PostMapping
    @Operation(summary = "Create new advertisement")
    fun createAdvertisement(@Valid @RequestBody advertisementCreateDto: AdvertisementCreateDto): ResponseEntity<AdvertisementDto> {
        val createdAdvertisement = advertisementService.create(advertisementCreateDto)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAdvertisement)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update advertisement")
    fun updateAdvertisement(
        @PathVariable id: Long,
        @Valid @RequestBody advertisementUpdateDto: AdvertisementUpdateDto
    ): ResponseEntity<AdvertisementDto> {
        return ResponseEntity.ok(advertisementService.update(id, advertisementUpdateDto))
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete advertisement")
    fun deleteAdvertisement(@PathVariable id: Long): ResponseEntity<Void> {
        advertisementService.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}
