package suai.vladislav.backserviceskotlin.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.Contact
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Back Services Kotlin API")
                    .version("1.0.0")
                    .description("CRUD API for e-commerce platform with users, advertisements, carts and orders")
                    .contact(
                        Contact()
                            .name("Vladislav")
                            .email("vladislav@suai.ru")
                    )
            )
    }
}
