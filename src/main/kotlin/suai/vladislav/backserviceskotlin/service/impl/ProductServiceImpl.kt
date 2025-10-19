package suai.vladislav.backserviceskotlin.service.impl

import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import suai.vladislav.backserviceskotlin.dto.ProductCreateDto
import suai.vladislav.backserviceskotlin.dto.ProductDto
import suai.vladislav.backserviceskotlin.dto.ProductUpdateDto
import suai.vladislav.backserviceskotlin.entity.Product
import suai.vladislav.backserviceskotlin.exception.ResourceNotFoundException
import suai.vladislav.backserviceskotlin.repository.ProductRepository
import suai.vladislav.backserviceskotlin.service.ProductService

@Service
@Transactional(readOnly = true)
class ProductServiceImpl(
    private val productRepository: ProductRepository
) : ProductService {

    @Cacheable(value = ["products"])
    override fun findAll(): List<ProductDto> =
        productRepository.findAll().map { it.toDto() }

    @Cacheable(value = ["productById"], key = "#id")
    override fun findById(id: Long): ProductDto =
        productRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Product not found with id: $id") }
            .toDto()

    @Cacheable(value = ["productsByWorkflowId"], key = "#workflowId")
    override fun findByWorkflowId(workflowId: String): List<ProductDto> =
        productRepository.findByWorkflowId(workflowId).map { it.toDto() }

    @Transactional
    @CacheEvict(value = ["products", "productsByWorkflowId"], allEntries = true)
    override fun create(productCreateDto: ProductCreateDto): ProductDto {
        val product = Product(
            name = productCreateDto.name,
            description = productCreateDto.description,
            totalScreens = productCreateDto.totalScreens,
            totalComponents = productCreateDto.totalComponents,
            workflow = productCreateDto.getWorkflowAsString(),
            workflowId = productCreateDto.workflowId
        )
        return productRepository.save(product).toDto()
    }

    @Transactional
    @CacheEvict(value = ["products", "productById", "productsByWorkflowId"], allEntries = true)
    override fun update(id: Long, productUpdateDto: ProductUpdateDto): ProductDto {
        val product = productRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Product not found with id: $id") }

        val updatedProduct = product.copy(
            name = productUpdateDto.name ?: product.name,
            description = productUpdateDto.description ?: product.description,
            totalScreens = productUpdateDto.totalScreens ?: product.totalScreens,
            totalComponents = productUpdateDto.totalComponents ?: product.totalComponents,
            workflow = productUpdateDto.getWorkflowAsString() ?: product.workflow,
            workflowId = productUpdateDto.workflowId ?: product.workflowId
        )

        return productRepository.save(updatedProduct).toDto()
    }

    @Transactional
    @CacheEvict(value = ["products", "productById", "productsByWorkflowId"], allEntries = true)
    override fun deleteById(id: Long) {
        if (!productRepository.existsById(id)) {
            throw ResourceNotFoundException("Product not found with id: $id")
        }
        productRepository.deleteById(id)
    }

    private fun Product.toDto() = ProductDto(
        id = id,
        name = name,
        description = description,
        totalScreens = totalScreens,
        totalComponents = totalComponents,
        workflow = workflow,
        workflowId = workflowId
    )
}
