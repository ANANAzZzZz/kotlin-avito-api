package suai.vladislav.backserviceskotlin.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "products")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @NotBlank
    @Column(nullable = false)
    val name: String,

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    val description: String,

    @Column(name = "total_screens", nullable = false)
    val totalScreens: Int,

    @Column(name = "total_components", nullable = false)
    val totalComponents: Int,

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "workflow", columnDefinition = "jsonb")
    val workflow: String,

    @Column(name = "workflow_id", nullable = false)
    val workflowId: String
)
