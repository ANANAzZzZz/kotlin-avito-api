package suai.vladislav.backserviceskotlin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import suai.vladislav.backserviceskotlin.entity.Product

@Repository
interface ProductRepository : JpaRepository<Product, Long> {
    fun findByWorkflowId(workflowId: String): List<Product>
}

