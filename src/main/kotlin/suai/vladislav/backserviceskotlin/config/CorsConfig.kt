package suai.vladislav.backserviceskotlin.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class CorsConfig {

    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()

        // Разрешаем все источники (для разработки)
        config.allowedOriginPatterns = listOf("*")

        // Разрешаем все HTTP методы
        config.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")

        // Разрешаем все заголовки
        config.allowedHeaders = listOf("*")

        // Разрешаем credentials (cookies, authorization headers)
        config.allowCredentials = true

        // Указываем, какие заголовки могут быть доступны в ответе
        config.exposedHeaders = listOf(
            "Authorization",
            "Content-Type",
            "Content-Disposition",
            "Access-Control-Allow-Headers",
            "Access-Control-Allow-Origin"
        )

        // Максимальное время кэширования preflight запросов (в секундах)
        config.maxAge = 3600L

        source.registerCorsConfiguration("/**", config)

        return CorsFilter(source)
    }
}

