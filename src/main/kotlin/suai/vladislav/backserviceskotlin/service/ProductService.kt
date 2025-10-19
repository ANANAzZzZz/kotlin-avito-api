package suai.vladislav.backserviceskotlin.service

import suai.vladislav.backserviceskotlin.dto.ProductCreateDto
import suai.vladislav.backserviceskotlin.dto.ProductDto
import suai.vladislav.backserviceskotlin.dto.ProductUpdateDto

interface ProductService {
    fun findAll(): List<ProductDto>
    fun findById(id: Long): ProductDto
    fun findByWorkflowId(workflowId: String): List<ProductDto>
    fun create(productCreateDto: ProductCreateDto): ProductDto
    fun update(id: Long, productUpdateDto: ProductUpdateDto): ProductDto
    fun deleteById(id: Long)
}

