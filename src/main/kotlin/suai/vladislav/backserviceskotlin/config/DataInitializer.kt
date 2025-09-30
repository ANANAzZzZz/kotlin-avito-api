package suai.vladislav.backserviceskotlin.config

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import suai.vladislav.backserviceskotlin.entity.*
import suai.vladislav.backserviceskotlin.repository.*
import java.math.BigDecimal

@Component
class DataInitializer(
    private val userRepository: UserRepository,
    private val advertisementRepository: AdvertisementRepository,
    private val cartRepository: CartRepository,
    private val paymentMethodRepository: PaymentMethodRepository,
    private val shippingMethodRepository: ShippingMethodRepository
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        if (userRepository.count() == 0L) {
            initializeTestData()
        }
    }

    private fun initializeTestData() {
        // Создаем пользователей
        val admin = User(
            role = UserRole.ADMIN,
            email = "admin@example.com",
            password = "admin123",
            lastName = "Admin",
            firstName = "System",
            address = "Admin Address",
            rating = 5.0f
        )

        val user1 = User(
            role = UserRole.USER,
            email = "user1@example.com",
            password = "user123",
            lastName = "Иванов",
            firstName = "Иван",
            address = "г. Санкт-Петербург, ул. Ленина, д. 1",
            rating = 4.2f
        )

        val user2 = User(
            role = UserRole.USER,
            email = "user2@example.com",
            password = "user123",
            lastName = "Петров",
            firstName = "Петр",
            address = "г. Москва, ул. Пушкина, д. 10",
            rating = 3.8f
        )

        val savedAdmin = userRepository.save(admin)
        val savedUser1 = userRepository.save(user1)
        val savedUser2 = userRepository.save(user2)

        // Создаем объявления
        val ad1 = Advertisement(
            name = "iPhone 15 Pro",
            description = "Новый iPhone 15 Pro в отличном состоянии. Комплект полный.",
            owner = savedUser1
        )

        val ad2 = Advertisement(
            name = "MacBook Air M2",
            description = "Ноутбук Apple MacBook Air с чипом M2, 8GB RAM, 256GB SSD",
            owner = savedUser1
        )

        val ad3 = Advertisement(
            name = "Samsung Galaxy S24",
            description = "Флагманский смартфон Samsung с отличной камерой",
            owner = savedUser2
        )

        advertisementRepository.saveAll(listOf(ad1, ad2, ad3))

        // Создаем корзины для пользователей
        val cart1 = Cart(user = savedUser1)
        val cart2 = Cart(user = savedUser2)
        cartRepository.saveAll(listOf(cart1, cart2))

        // Создаем способы оплаты
        val creditCard = PaymentMethod(
            name = "Банковская карта",
            image = "credit_card.png"
        )

        val cash = PaymentMethod(
            name = "Наличные",
            image = "cash.png"
        )

        val paypal = PaymentMethod(
            name = "PayPal",
            image = "paypal.png"
        )

        paymentMethodRepository.saveAll(listOf(creditCard, cash, paypal))

        // Создаем способы доставки
        val courier = ShippingMethod(
            name = "Курьерская доставка",
            price = BigDecimal("500.00")
        )

        val pickup = ShippingMethod(
            name = "Самовывоз",
            price = BigDecimal("0.00")
        )

        val express = ShippingMethod(
            name = "Экспресс доставка",
            price = BigDecimal("1000.00")
        )

        shippingMethodRepository.saveAll(listOf(courier, pickup, express))

        println("Тестовые данные успешно созданы!")
    }
}
