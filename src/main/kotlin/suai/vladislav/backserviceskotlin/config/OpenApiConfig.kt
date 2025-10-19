package suai.vladislav.backserviceskotlin.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.Contact
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
                        # REST API для управления продуктами и e-commerce платформы
                        
                        ## Описание
                        Данный API предоставляет полный CRUD функционал для работы с различными сущностями:
                        
                        ### 📦 Products (Продукты)
                        Управление продуктами с поддержкой JSONB для хранения сложных workflow сценариев.
                        - Создание, чтение, обновление и удаление продуктов
                        - Поиск продуктов по workflow ID
                        - Хранение метаданных: количество экранов, компонентов, описание
                        - Поддержка JSONB для workflow (любая структура JSON)
                        
                        ### 👥 Users (Пользователи)
                        Полное управление пользователями платформы.
                        - Регистрация и управление профилями
                        - Получение списка всех пользователей
                        - CRUD операции над пользователями
                        
                        ### 📢 Advertisements (Объявления)
                        Размещение и управление объявлениями.
                        - Создание объявлений от имени пользователей
                        - Просмотр активных объявлений
                        - Редактирование и удаление объявлений
                        
                        ### 🛒 Cart (Корзина)
                        Управление корзиной покупок.
                        - Добавление товаров в корзину
                        - Изменение количества товаров
                        - Группировка товаров по магазинам
                        - Удаление товаров из корзины
                        - Очистка корзины
                        
                        ### 🚚 Shipping Methods (Способы доставки)
                        Управление методами доставки.
                        - CRUD операции над способами доставки
                        - Настройка стоимости и времени доставки
                        
                        ### 💳 Payment Methods (Способы оплаты)
                        Управление методами оплаты.
                        - Добавление и удаление способов оплаты
                        - Поддержка различных типов оплаты
                        
                        ### ⛵ Ships (Корабли)
                        Управление кораблями для доставки.
                        - Регистрация кораблей
                        - Обновление информации о кораблях
                        
                        ## Особенности API
                        
                        ### ⚡ Производительность
                        - Кэширование запросов с использованием Caffeine
                        - Оптимизированные SQL запросы
                        - Connection pooling через HikariCP
                        - Batch processing для массовых операций
                        
                        ### 🗄️ База данных
                        - PostgreSQL с поддержкой JSONB
                        - Hibernate ORM для работы с данными
                        - Автоматические индексы для оптимизации запросов
                        
                        ### 🔄 CORS
                        - Полная поддержка CORS для работы с фронтендом
                        - Разрешены все источники (идеально для разработки)
                        
                        ### 📝 Формат данных
                        - JSON для всех запросов и ответов
                        - Поддержка JSONB для сложных структур данных
                        - CamelCase именование полей
                        
                        ## Примеры использования
                        
                        ### Создание продукта с workflow
                        ```json
                        POST /api/products
                        {
                          "name": "Авито — Корзина",
                          "description": "Демонстрационный сценарий",
                          "totalScreens": 11,
                          "totalComponents": 25,
                          "workflowId": "avito-cart-demo",
                          "workflow": {
                            "id": "avito-cart-demo",
                            "screens": [...],
                            "variables": {...}
                          }
                        }
                        ```
                        
                        ### Добавление товара в корзину
                        ```json
                        POST /api/cart/{userId}/items
                        {
                          "advertisementId": 123,
                          "quantity": 2
                        }
                        ```
                        
                        ## Контактная информация
                        - Email: api-support@bdui.com
                        - Репозиторий: https://github.com/vladislav/kotlin-avito-api
                    """.trimIndent())
                    .contact(
                        Contact()
                            .name("API Support Team")
                            .email("api-support@bdui.com")
                    )
            )
            .servers(
                listOf(
                    Server()
                        .url("https://sandkittens.me/backservices")
                        .description("Local Development Server"),
                )
            )
    }
}
