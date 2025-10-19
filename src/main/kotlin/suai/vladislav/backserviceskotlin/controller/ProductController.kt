package suai.vladislav.backserviceskotlin.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import suai.vladislav.backserviceskotlin.dto.ProductCreateDto
import suai.vladislav.backserviceskotlin.dto.ProductDto
import suai.vladislav.backserviceskotlin.dto.ProductUpdateDto
import suai.vladislav.backserviceskotlin.service.ProductService

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product", description = "API для управления продуктами")
class ProductController(
    private val productService: ProductService
) {

    @GetMapping
    @Operation(summary = "Получить все продукты")
    fun getAllProducts(): ResponseEntity<List<ProductDto>> {
        return ResponseEntity.ok(productService.findAll())
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить продукт по ID")
    fun getProductById(@PathVariable id: Long): ResponseEntity<ProductDto> {
        return ResponseEntity.ok(productService.findById(id))
    }

    @GetMapping("/workflow/{workflowId}")
    @Operation(summary = "Получить продукты по workflow ID")
    fun getProductsByWorkflowId(@PathVariable workflowId: String): ResponseEntity<List<ProductDto>> {
        return ResponseEntity.ok(productService.findByWorkflowId(workflowId))
    }

    @PostMapping
    @Operation(summary = "Создать новый продукт")
    fun createProduct(@Valid @RequestBody productCreateDto: ProductCreateDto): ResponseEntity<ProductDto> {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(productCreateDto))
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить продукт")
    fun updateProduct(
        @PathVariable id: Long,
        @Valid @RequestBody productUpdateDto: ProductUpdateDto
    ): ResponseEntity<ProductDto> {
        return ResponseEntity.ok(productService.update(id, productUpdateDto))
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить продукт")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteProduct(@PathVariable id: Long) {
        productService.deleteById(id)
    }
}

