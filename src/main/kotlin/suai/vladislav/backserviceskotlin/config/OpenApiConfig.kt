package suai.vladislav.backserviceskotlin.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("BDUI Admin API & E-commerce Backend")
                    .version("1.0.0")
                    .description("""
                        REST API для взаимодействия админ-панели BDUI с backend сервером для управления продуктами, экранами, переменными и аналитикой.
                        Также включает API для e-commerce платформы с пользователями, объявлениями, корзинами и заказами.
                        
                        ## Аутентификация
                        API использует JWT Bearer токены. Получите токен через `/auth/login` и передавайте в заголовке:
                        ```
                        Authorization: Bearer <your_token>
                        ```
                        
                        ## Rate Limiting
                        - Free Tier: 100 requests/minute
                        - Pro Tier: 1000 requests/minute
                        - Enterprise: Unlimited
                        
                        ## Поддержка
                        - Документация: https://docs.bdui.com/api
                        - Support: api-support@bdui.com
                        - Discord: https://discord.gg/bdui
                    """.trimIndent())
                    .contact(
                        Contact()
                            .name("BDUI API Support")
                            .email("api@bdui.com")
                            .url("https://docs.bdui.com")
                    )
                    .license(
                        License()
                            .name("MIT")
                            .url("https://opensource.org/licenses/MIT")
                    )
            )
            .servers(
                listOf(
                    Server().url("https://api.bdui.com/v1").description("Production server"),
                    Server().url("https://sandbox-api.bdui.com/v1").description("Sandbox server (testing)"),
                    Server().url("http://localhost:8080/backservices/api").description("Local development server"),
                    Server().url("http://localhost:5050/v1").description("BDUI Local development server")
                )
            )
            .addSecurityItem(SecurityRequirement().addList("bearerAuth"))
            .components(
                io.swagger.v3.oas.models.Components()
                    .addSecuritySchemes(
                        "bearerAuth",
                        SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                    )
            )
    }
}
