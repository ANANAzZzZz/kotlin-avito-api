package suai.vladislav.backserviceskotlin.dto

import com.fasterxml.jackson.annotation.JsonRawValue
import com.fasterxml.jackson.databind.JsonNode


data class ProductDto(
    val id: Long,
    val name: String,
    val description: String,
    val totalScreens: Int,
    val totalComponents: Int,
    @JsonRawValue
    val workflow: String,
    val workflowId: String
)

data class ProductCreateDto(
    val name: String,
    val description: String,
    val totalScreens: Int,
    val totalComponents: Int,
    val workflow: JsonNode,
    val workflowId: String
) {
    fun getWorkflowAsString(): String = workflow.toString()
}

data class ProductUpdateDto(
    val name: String?,
    val description: String?,
    val totalScreens: Int?,
    val totalComponents: Int?,
    val workflow: JsonNode?,
    val workflowId: String?
) {
    fun getWorkflowAsString(): String? = workflow?.toString()
}
