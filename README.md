# 🚀 PPRB-Hack: Enterprise E-Commerce Backend

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.22-purple.svg)](https://kotlinlang.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Latest-blue.svg)](https://www.postgresql.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

> Высокопроизводительный REST API бэкенд для e-commerce платформы с продвинутой системой корзины, рекомендаций и управления заказами. Разработан для масштабирования и готов к production.

---

## 📋 Содержание

- [О проекте](#-о-проекте)
- [Ключевые возможности](#-ключевые-возможности)
- [Технологический стек](#-технологический-стек)
- [Архитектура](#-архитектура)
- [Быстрый старт](#-быстрый-старт)
- [API Документация](#-api-документация)
- [Структура базы данных](#-структура-базы-данных)
- [Конфигурация](#-конфигурация)
- [Оптимизация и производительность](#-оптимизация-и-производительность)
- [Развертывание](#-развертывание)
- [Тестирование](#-тестирование)
- [Разработка](#-разработка)
- [Участники](#-участники)

---

## 🎯 О проекте

**PPRB-Hack** — это современное backend-решение для e-commerce платформы, построенное на Spring Boot и Kotlin. Проект предоставляет полнофункциональный REST API для управления пользователями, товарами, корзинами покупок и заказами с интеллектуальной системой рекомендаций.

### Основные преимущества

- ⚡ **Высокая производительность** — оптимизированные SQL-запросы с индексами и batch-processing
- 🔒 **Безопасность** — валидация данных, обработка исключений, готовность к интеграции JWT
- 📊 **Масштабируемость** — поддержка connection pooling (HikariCP) и кэширования
- 🛒 **Умная корзина** — группировка товаров по магазинам с автоматическим подсчетом итогов
- 📚 **API-документация** — автоматическая генерация Swagger/OpenAPI документации
- 🔄 **Транзакционность** — ACID-гарантии для всех критических операций

---

## ✨ Ключевые возможности

### 👤 Управление пользователями
- Регистрация и аутентификация пользователей
- Поддержка различных ролей (SELLER, BUYER, ADMIN)
- Управление профилями и адресами доставки
- Валидация email и паролей

### 📦 Управление товарами (Объявлениями)
- CRUD операции для товаров
- Полнотекстовый поиск по названию и описанию
- Фильтрация по владельцу и ценовому диапазону
- Поддержка изображений и метаданных

### 🛒 Интеллектуальная корзина покупок
- Автоматическая группировка товаров по продавцам
- Управление количеством и выбором товаров
- Подсчет промежуточных и общих сумм
- Кэширование для быстрого доступа
- REST API с детальной информацией о товарах

### 🎁 Система рекомендаций
- Рекомендации товаров на основе содержимого корзины
- Приоритизация товаров от тех же продавцов
- Исключение уже добавленных товаров
- Настраиваемый лимит рекомендаций

### 🚚 Управление доставкой
- Множество методов доставки
- Способы оплаты
- Управление отправлениями (Ships)
- Связь между заказами, методами оплаты и доставки

### 🔧 Дополнительные возможности
- Глобальная обработка ошибок с детальными сообщениями
- Валидация входных данных (Bean Validation)
- Индексы БД для оптимизации запросов
- Логирование операций
- Готовность к горизонтальному масштабированию

---

## 🛠 Технологический стек

### Backend
- **Kotlin 1.9.22** — современный JVM-язык с null-safety и функциональным программированием
- **Spring Boot 3.5.6** — enterprise-ready фреймворк
- **Spring Data JPA** — упрощенная работа с базой данных
- **Hibernate** — ORM для управления персистентностью
- **PostgreSQL** — надежная реляционная СУБД

### Дополнительные технологии
- **HikariCP** — быстрый connection pool
- **Jackson Kotlin Module** — сериализация/десериализация JSON
- **SpringDoc OpenAPI** — автоматическая генерация API-документации
- **Hibernate Validator** — валидация bean-объектов
- **Kotlin Logging** — структурированное логирование

### Инструменты разработки
- **Maven** — управление зависимостями и сборка
- **IntelliJ IDEA** — рекомендуемая IDE
- **Git** — контроль версий

---

## 🏗 Архитектура

Проект следует принципам **Clean Architecture** и **SOLID**, используя многослойную архитектуру:

```
┌─────────────────────────────────────────────────────┐
│                  Controller Layer                    │
│         (REST API, Валидация запросов)              │
└───────────────────┬─────────────────────────────────┘
                    │
┌───────────────────▼─────────────────────────────────┐
│                   Service Layer                      │
│      (Бизнес-логика, Транзакции, Кэширование)      │
└───────────────────┬─────────────────────────────────┘
                    │
┌───────────────────▼─────────────────────────────────┐
│                 Repository Layer                     │
│         (Доступ к данным, Запросы к БД)            │
└───────────────────┬─────────────────────────────────┘
                    │
┌───────────────────▼─────────────────────────────────┐
│                   Database Layer                     │
│                    PostgreSQL                        │
└─────────────────────────────────────────────────────┘
```

### Структура пакетов

```
suai.vladislav.backserviceskotlin/
├── config/                    # Конфигурация Spring
│   ├── CacheConfig.kt        # Настройка кэширования
│   ├── OpenApiConfig.kt      # Swagger документация
│   └── DataInitializer.kt    # Инициализация данных
├── controller/                # REST контроллеры
│   ├── AdvertisementController.kt
│   ├── CartController.kt
│   ├── UserController.kt
│   ├── PaymentMethodController.kt
│   ├── ShippingMethodController.kt
│   ├── ShipController.kt
│   └── GlobalExceptionHandler.kt
├── service/                   # Бизнес-логика
│   ├── AdvertisementService.kt
│   ├── CartService.kt
│   ├── UserService.kt
│   └── impl/                 # Реализации сервисов
│       ├── AdvertisementServiceImpl.kt
│       ├── CartServiceImpl.kt
│       └── UserServiceImpl.kt
├── repository/                # Data Access Layer
│   ├── AdvertisementRepository.kt
│   ├── CartRepository.kt
│   ├── CartAdvertisementRepository.kt
│   └── UserRepository.kt
├── entity/                    # JPA сущности
│   ├── Advertisement.kt
│   ├── Cart.kt
│   ├── CartAdvertisement.kt
│   ├── User.kt
│   ├── PaymentMethod.kt
│   ├── ShippingMethod.kt
│   └── Ship.kt
├── dto/                       # Data Transfer Objects
│   ├── AdvertisementDto.kt
│   ├── CartDto.kt
│   ├── UserDto.kt
│   └── ...
└── exception/                 # Кастомные исключения
    ├── ResourceNotFoundException.kt
    ├── ResourceAlreadyExistsException.kt
    └── InvalidRequestException.kt
```

---

## 🚀 Быстрый старт

### Требования

- **Java 17+** или **OpenJDK 17+**
- **Maven 3.8+**
- **PostgreSQL 12+**
- **Git**

### Установка и запуск

1. **Клонируйте репозиторий**
```bash
git clone https://github.com/your-username/kotlin-avito-api.git
cd kotlin-avito-api
```

2. **Настройте базу данных PostgreSQL**
```sql
CREATE DATABASE ecommerce_db;
CREATE USER ecommerce_user WITH PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE ecommerce_db TO ecommerce_user;
```

3. **Настройте переменные окружения**

**Windows (CMD):**
```cmd
set DB_URL=jdbc:postgresql://localhost:5432/ecommerce_db
set DB_USERNAME=ecommerce_user
set DB_PASSWORD=your_password
```

**Windows (PowerShell):**
```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/ecommerce_db"
$env:DB_USERNAME="ecommerce_user"
$env:DB_PASSWORD="your_password"
```

**Linux/MacOS:**
```bash
export DB_URL=jdbc:postgresql://localhost:5432/ecommerce_db
export DB_USERNAME=ecommerce_user
export DB_PASSWORD=your_password
```

4. **Выполните миграцию базы данных (опционально)**
```bash
psql -U ecommerce_user -d ecommerce_db -f database-migration.sql
```

5. **Соберите проект**
```bash
mvn clean install
```

6. **Запустите приложение**
```bash
mvn spring-boot:run
```

Приложение запустится на `http://localhost:8080/backservices`

### Проверка работоспособности

Откройте в браузере Swagger UI для интерактивного тестирования API:
```
http://localhost:8080/backservices/swagger-ui.html
```

---

## 📚 API Документация

### Основные эндпоинты

#### 👤 Пользователи (`/api/users`)

| Метод | Endpoint | Описание |
|-------|----------|----------|
| GET | `/api/users` | Получить всех пользователей |
| GET | `/api/users/{id}` | Получить пользователя по ID |
| GET | `/api/users/email/{email}` | Найти пользователя по email |
| POST | `/api/users` | Создать нового пользователя |
| PUT | `/api/users/{id}` | Обновить пользователя |
| DELETE | `/api/users/{id}` | Удалить пользователя |

#### 📦 Объявления/Товары (`/api/advertisements`)

| Метод | Endpoint | Описание |
|-------|----------|----------|
| GET | `/api/advertisements` | Получить все объявления |
| GET | `/api/advertisements/{id}` | Получить объявление по ID |
| GET | `/api/advertisements/owner/{ownerId}` | Объявления конкретного продавца |
| GET | `/api/advertisements/search?keyword={keyword}` | Полнотекстовый поиск |
| POST | `/api/advertisements` | Создать новое объявление |
| PUT | `/api/advertisements/{id}` | Обновить объявление |
| DELETE | `/api/advertisements/{id}` | Удалить объявление |

#### 🛒 Корзина (`/api/carts`)

| Метод | Endpoint | Описание |
|-------|----------|----------|
| GET | `/api/carts` | Получить все корзины |
| GET | `/api/carts/{id}` | Получить корзину по ID |
| GET | `/api/carts/user/{userId}` | Корзина конкретного пользователя |
| GET | `/api/carts/{id}/with-advertisements` | **Корзина с товарами по магазинам** |
| GET | `/api/carts/{id}/recommendations` | Рекомендованные товары |
| POST | `/api/carts` | Создать корзину |
| POST | `/api/carts/add` | Добавить товар в корзину |
| DELETE | `/api/carts/{cartId}/advertisements/{adId}` | Удалить товар из корзины |
| PATCH | `/api/carts/{cartId}/advertisements/{adId}` | Обновить товар (количество/выбор) |

#### 💳 Способы оплаты (`/api/payment-methods`)

| Метод | Endpoint | Описание |
|-------|----------|----------|
| GET | `/api/payment-methods` | Получить все способы оплаты |
| GET | `/api/payment-methods/{id}` | Получить способ оплаты по ID |
| POST | `/api/payment-methods` | Создать способ оплаты |
| PUT | `/api/payment-methods/{id}` | Обновить способ оплаты |
| DELETE | `/api/payment-methods/{id}` | Удалить способ оплаты |

#### 🚚 Методы доставки (`/api/shipping-methods`)

| Метод | Endpoint | Описание |
|-------|----------|----------|
| GET | `/api/shipping-methods` | Получить все методы доставки |
| GET | `/api/shipping-methods/{id}` | Получить метод по ID |
| POST | `/api/shipping-methods` | Создать метод доставки |
| PUT | `/api/shipping-methods/{id}` | Обновить метод доставки |
| DELETE | `/api/shipping-methods/{id}` | Удалить метод доставки |

#### 📮 Отправления (`/api/ships`)

| Метод | Endpoint | Описание |
|-------|----------|----------|
| GET | `/api/ships` | Получить все отправления |
| GET | `/api/ships/{id}` | Получить отправление по ID |
| GET | `/api/ships/user/{userId}` | Отправления пользователя |
| POST | `/api/ships` | Создать отправление |
| PATCH | `/api/ships/{id}/status` | Обновить статус отправления |

### Примеры запросов

#### Создание пользователя
```json
POST /api/users
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "SecurePass123",
  "firstName": "Иван",
  "lastName": "Иванов",
  "role": "BUYER",
  "address": "г. Москва, ул. Ленина, д. 1",
  "phoneNumber": "+7 900 123-45-67"
}
```

#### Добавление товара в корзину
```json
POST /api/carts/add
Content-Type: application/json

{
  "cartId": 1,
  "advertisementId": 5,
  "quantity": 2
}
```

#### Получение корзины с товарами
```json
GET /api/carts/2/with-advertisements

Response:
{
  "id": 2,
  "userId": 3,
  "shopGroups": [
    {
      "shopId": 4,
      "shopName": "Сидор Сидоров",
      "items": [
        {
          "id": 4,
          "advertisementId": 4,
          "name": "MacBook Pro 16",
          "description": "Apple MacBook Pro 16 с процессором M3 Pro...",
          "price": 250000.00,
          "quantity": 1,
          "selected": true,
          "itemTotal": 250000.00
        }
      ],
      "shopTotal": 250000.00
    },
    {
      "shopId": 2,
      "shopName": "Иван Иванов",
      "items": [
        {
          "id": 5,
          "advertisementId": 2,
          "name": "iPhone 15 Pro",
          "description": "Новый iPhone 15 Pro...",
          "price": 120000.00,
          "quantity": 1,
          "selected": true,
          "itemTotal": 120000.00
        }
      ],
      "shopTotal": 120000.00
    }
  ],
  "totalAmount": 370000.00,
  "selectedItemsCount": 2,
  "totalItemsCount": 2
}
```

#### Обновление количества товара в корзине
```json
PATCH /api/carts/2/advertisements/4
Content-Type: application/json

{
  "quantity": 3,
  "selected": true
}
```

---

## 🗄 Структура базы данных

### ER-диаграмма

```
┌─────────────┐         ┌──────────────────┐         ┌─────────────┐
│    Users    │1       *│ Advertisements   │*       1│    Carts    │
├─────────────┤◄────────┤                  ├────────►├─────────────┤
│ id (PK)     │  owner  │ id (PK)          │  cart   │ id (PK)     │
│ email       │         │ name             │         │ user_id (FK)│
│ password    │         │ description      │         └─────────────┘
│ first_name  │         │ price            │                │
│ last_name   │         │ owner_id (FK)    │                │
│ role        │         └──────────────────┘                │
│ address     │                  │                          │
│ phone       │                  │*                         │*
└─────────────┘                  │                          │
                                 │                          │
                        ┌────────▼──────────┐              │
                        │ CartAdvertisement │◄─────────────┘
                        ├───────────────────┤
                        │ id (PK)           │
                        │ cart_id (FK)      │
                        │ advertisement_id  │
                        │ quantity          │
                        │ selected          │
                        └───────────────────┘

┌─────────────────┐         ┌──────────────┐         ┌──────────────────┐
│ PaymentMethods  │1       *│    Ships     │*       1│ ShippingMethods  │
├─────────────────┤◄────────┼──────────────┼────────►├──────────────────┤
│ id (PK)         │         │ id (PK)      │         │ id (PK)          │
│ name            │         │ user_id (FK) │         │ name             │
│ image           │         │ payment_id   │         │ description      │
└─────────────────┘         │ shipping_id  │         │ price            │
                            │ status       │         │ delivery_time    │
                            └──────────────┘         └──────────────────┘
```

### Основные таблицы

#### users
- `id` — уникальный идентификатор
- `email` — email (unique)
- `password` — хэшированный пароль
- `first_name`, `last_name` — имя и фамилия
- `role` — роль (BUYER, SELLER, ADMIN)
- `address` — адрес доставки
- `phone_number` — телефон

#### advertisements
- `id` — уникальный идентификатор
- `name` — название товара
- `description` — описание
- `price` — цена (DECIMAL)
- `owner_id` — владелец (FK → users)

#### carts
- `id` — уникальный идентификатор
- `user_id` — пользователь (FK → users, unique)

#### cart_advertisements
- `id` — уникальный идентификатор
- `cart_id` — корзина (FK → carts)
- `advertisement_id` — товар (FK → advertisements)
- `quantity` — количество
- `selected` — выбран для покупки

#### payment_methods
- `id` — уникальный идентификатор
- `name` — название способа оплаты
- `image` — иконка/изображение

#### shipping_methods
- `id` — уникальный идентификатор
- `name` — название способа доставки
- `description` — описание
- `price` — стоимость доставки
- `estimated_delivery_time` — ориентировочное время доставки

#### ships
- `id` — уникальный идентификатор
- `user_id` — пользователь (FK → users)
- `payment_method_id` — способ оплаты (FK → payment_methods)
- `shipping_method_id` — способ доставки (FK → shipping_methods)
- `status` — статус отправления

### Индексы для производительности

```sql
-- Основные индексы
CREATE INDEX idx_cart_advertisements_cart_id ON cart_advertisements(cart_id);
CREATE INDEX idx_cart_advertisements_advertisement_id ON cart_advertisements(advertisement_id);
CREATE INDEX idx_advertisements_owner_id ON advertisements(owner_id);
CREATE INDEX idx_advertisements_price ON advertisements(price);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_ships_user_id ON ships(user_id);
```

---

## ⚙️ Конфигурация

### application.yaml

```yaml
server:
  port: 8080
  servlet:
    context-path: /backservices

spring:
  application:
    name: back-services-kotlin

  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
    driver-class-name: org.postgresql.Driver
    hikari:
      maximum-pool-size: 10
      minimum-idle: 5
      connection-timeout: 20000

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: false
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        jdbc:
          batch_size: 20
          fetch_size: 50
```

### Переменные окружения

| Переменная | Описание | Пример |
|------------|----------|--------|
| `DB_URL` | JDBC URL базы данных | `jdbc:postgresql://localhost:5432/ecommerce_db` |
| `DB_USERNAME` | Имя пользователя БД | `ecommerce_user` |
| `DB_PASSWORD` | Пароль БД | `your_secure_password` |
| `SERVER_PORT` | Порт сервера (опционально) | `8080` |

---

## ⚡ Оптимизация и производительность

### Кэширование

Проект использует Spring Cache для оптимизации частых запросов:

```kotlin
@Cacheable(value = ["carts"], key = "#id")
fun findById(id: Long): CartDto

@CacheEvict(value = ["carts"], allEntries = true)
fun create(userId: Long): CartDto
```

**Кэшируемые операции:**
- Получение корзины по ID
- Получение корзины пользователя
- Корзина с товарами и группировкой
- Рекомендации товаров

### Connection Pooling (HikariCP)

Оптимизированная конфигурация пула соединений:
- Максимум соединений: 10
- Минимум idle: 5
- Timeout: 20 секунд
- Max lifetime: 20 минут

### Batch Processing

Hibernate настроен для batch-обработки:
- Batch size: 20
- Fetch size: 50
- Order inserts/updates: включено

### Индексы базы данных

Все внешние ключи и часто используемые поля проиндексированы для быстрого поиска.

### Ленивая загрузка (Lazy Loading)

Связи между сущностями используют `FetchType.LAZY` для минимизации запросов к БД.

---

## 🚢 Развертывание

### Docker (рекомендуется)

Создайте `Dockerfile`:

```dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/PPRB-hack-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

Создайте `docker-compose.yml`:

```yaml
version: '3.8'

services:
  postgres:
    image: postgres:15
    environment:
      POSTGRES_DB: ecommerce_db
      POSTGRES_USER: ecommerce_user
      POSTGRES_PASSWORD: secure_password
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

  backend:
    build: .
    ports:
      - "8080:8080"
    environment:
      DB_URL: jdbc:postgresql://postgres:5432/ecommerce_db
      DB_USERNAME: ecommerce_user
      DB_PASSWORD: secure_password
    depends_on:
      - postgres

volumes:
  postgres_data:
```

Запуск:
```bash
docker-compose up -d
```

### Heroku

```bash
heroku create your-app-name
heroku addons:create heroku-postgresql:hobby-dev
heroku config:set DB_URL=$(heroku config:get DATABASE_URL)
git push heroku main
```

### AWS Elastic Beanstalk

```bash
eb init -p docker your-app-name
eb create production-environment
eb deploy
```

---

## 🧪 Тестирование

### Запуск тестов

```bash
mvn test
```

### Тестирование API через HTTP файлы

Проект включает файлы `*.http` для тестирования в IntelliJ IDEA:

**api-requests.http:**
```http
### Get all users
GET http://localhost:8080/backservices/api/users

### Get cart with advertisements
GET http://localhost:8080/backservices/api/carts/2/with-advertisements

### Add item to cart
POST http://localhost:8080/backservices/api/carts/add
Content-Type: application/json

{
  "cartId": 2,
  "advertisementId": 5,
  "quantity": 1
}
```

### Postman коллекция

Импортируйте OpenAPI спецификацию в Postman:
```
http://localhost:8080/backservices/v3/api-docs
```

---

## 💻 Разработка

### Настройка окружения

1. Установите IntelliJ IDEA с Kotlin plugin
2. Установите PostgreSQL
3. Клонируйте репозиторий
4. Откройте проект в IDE
5. Настройте переменные окружения
6. Запустите `BackServicesKotlinApplication.kt`

### Код-стайл

Проект следует официальному Kotlin coding conventions:
- Используйте `camelCase` для переменных и функций
- Используйте `PascalCase` для классов
- Максимальная длина строки: 120 символов
- Используйте data classes для DTO

### Коммиты

Следуйте Conventional Commits:
```
feat: добавлена система рекомендаций товаров
fix: исправлена ошибка расчета итоговой суммы корзины
docs: обновлена документация API
refactor: оптимизированы SQL-запросы корзины
```

### git flow

- `master` — production-ready код
- `develop` — разработка новых фич
- `feature/*` — новые функции
- `bugfix/*` — исправление багов
- `hotfix/*` — срочные исправления

---


