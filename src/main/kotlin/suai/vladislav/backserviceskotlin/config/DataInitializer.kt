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
    private val shippingMethodRepository: ShippingMethodRepository,
    private val shipRepository: ShipRepository,
    private val cartAdvertisementRepository: CartAdvertisementRepository
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

        val user3 = User(
            role = UserRole.USER,
            email = "user3@example.com",
            password = "user123",
            lastName = "Сидоров",
            firstName = "Сидор",
            address = "г. Казань, ул. Баумана, д. 5",
            rating = 4.5f
        )

        val user4 = User(
            role = UserRole.USER,
            email = "user4@example.com",
            password = "user123",
            lastName = "Смирнова",
            firstName = "Анна",
            address = "г. Екатеринбург, пр. Ленина, д. 25",
            rating = 4.8f
        )

        val user5 = User(
            role = UserRole.USER,
            email = "user5@example.com",
            password = "user123",
            lastName = "Козлов",
            firstName = "Дмитрий",
            address = "г. Новосибирск, ул. Красный проспект, д. 15",
            rating = 3.5f
        )

        val user6 = User(
            role = UserRole.MODERATOR,
            email = "moderator@example.com",
            password = "mod123",
            lastName = "Модератов",
            firstName = "Модератор",
            address = "г. Москва, ул. Тверская, д. 7",
            rating = 4.9f
        )

        val user7 = User(
            role = UserRole.USER,
            email = "user7@example.com",
            password = "user123",
            lastName = "Волкова",
            firstName = "Елена",
            address = "г. Краснодар, ул. Красная, д. 120",
            rating = 4.0f
        )

        val user8 = User(
            role = UserRole.USER,
            email = "user8@example.com",
            password = "user123",
            lastName = "Морозов",
            firstName = "Алексей",
            address = "г. Владивосток, ул. Светланская, д. 50",
            rating = 3.2f
        )

        val savedAdmin = userRepository.save(admin)
        val savedUser1 = userRepository.save(user1)
        val savedUser2 = userRepository.save(user2)
        val savedUser3 = userRepository.save(user3)
        val savedUser4 = userRepository.save(user4)
        val savedUser5 = userRepository.save(user5)
        val savedUser6 = userRepository.save(user6)
        val savedUser7 = userRepository.save(user7)
        val savedUser8 = userRepository.save(user8)

        // Создаем объявления
        val ad1 = Advertisement(
            name = "iPhone 15 Pro",
            description = "Новый iPhone 15 Pro в отличном состоянии. Комплект полный.",
            owner = savedUser1,
            imageUrl = "https://images.unsplash.com/photo-1695048133142-1a20484d2569?w=800",
            price = 100000.00.toBigDecimal()
        )

        val ad2 = Advertisement(
            name = "MacBook Air M2",
            description = "Ноутбук Apple MacBook Air с чипом M2, 8GB RAM, 256GB SSD",
            owner = savedUser1,
            imageUrl = "https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=800",
            price = 200000.00.toBigDecimal()
        )

        val ad3 = Advertisement(
            name = "Samsung Galaxy S24",
            description = "Флагманский смартфон Samsung с отличной камерой",
            owner = savedUser2,
            imageUrl = "https://images.unsplash.com/photo-1610945415295-d9bbf067e59c?w=800",
            price = 90000.00.toBigDecimal()
        )

        val ad4 = Advertisement(
            name = "iPad Pro 12.9",
            description = "Планшет Apple iPad Pro 12.9 дюймов, 256GB, Wi-Fi + Cellular",
            owner = savedUser2,
            imageUrl = "https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0?w=800",
            price = 120000.00.toBigDecimal()
        )

        val ad5 = Advertisement(
            name = "Sony PlayStation 5",
            description = "Игровая консоль Sony PlayStation 5, disc edition, 2 геймпада в комплекте",
            owner = savedUser3,
            imageUrl = "https://images.unsplash.com/photo-1606813907291-d86efa9b94db?w=800",
            price = 50000.00.toBigDecimal()
        )

        val ad6 = Advertisement(
            name = "MacBook Pro 16",
            description = "Apple MacBook Pro 16 с процессором M3 Pro, 32GB RAM, 1TB SSD. Идеальное состояние.",
            owner = savedUser3,
            imageUrl = "https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=800",
            price = 250000.00.toBigDecimal()
        )

        val ad7 = Advertisement(
            name = "Nintendo Switch OLED",
            description = "Nintendo Switch OLED модель, белого цвета, полный комплект + 3 игры",
            owner = savedUser4,
            imageUrl = "https://images.unsplash.com/photo-1578303512597-81e6cc155b3e?w=800",
            price = 40000.00.toBigDecimal()
        )

        val ad8 = Advertisement(
            name = "AirPods Pro 2",
            description = "Беспроводные наушники Apple AirPods Pro 2-го поколения с USB-C",
            owner = savedUser4,
            imageUrl = "https://images.unsplash.com/photo-1572569511254-d8f925fe2cbb?w=800",
            price = 20000.00.toBigDecimal()
        )

        val ad9 = Advertisement(
            name = "Samsung 4K TV 55\"",
            description = "Телевизор Samsung 55 дюймов, 4K UHD, Smart TV, HDR10+",
            owner = savedUser5,
            imageUrl = "https://images.unsplash.com/photo-1593359677879-a4bb92f829d1?w=800",
            price = 80000.00.toBigDecimal()
        )

        val ad10 = Advertisement(
            name = "Dell XPS 15",
            description = "Ноутбук Dell XPS 15, Intel i7, 16GB RAM, 512GB SSD, NVIDIA GTX 1650",
            owner = savedUser5,
            imageUrl = "https://images.unsplash.com/photo-1588872657578-7efd1f1555ed?w=800",
            price = 150000.00.toBigDecimal()
        )

        val ad11 = Advertisement(
            name = "Canon EOS R6",
            description = "Беззеркальная камера Canon EOS R6 с объективом RF 24-105mm",
            owner = savedUser6,
            imageUrl = "https://images.unsplash.com/photo-1606983340126-99ab4feaa64-8?w=800",
            price = 180000.00.toBigDecimal()
        )

        val ad12 = Advertisement(
            name = "DJI Mavic Air 2",
            description = "Квадрокоптер DJI Mavic Air 2 с 4K камерой, запасные аккумуляторы в комплекте",
            owner = savedUser6,
            imageUrl = "https://images.unsplash.com/photo-1473968512647-3e447244af8f?w=800",
            price = 90000.00.toBigDecimal()
        )

        val ad13 = Advertisement(
            name = "Apple Watch Series 9",
            description = "Умные часы Apple Watch Series 9, 45mm, GPS + Cellular, титановый корпус",
            owner = savedUser7,
            imageUrl = "https://images.unsplash.com/photo-1434493789847-2f02dc6ca35d?w=800",
            price = 60000.00.toBigDecimal()
        )

        val ad14 = Advertisement(
            name = "Dyson V15 Detect",
            description = "Беспроводной пылесос Dyson V15 Detect, полный комплект насадок",
            owner = savedUser7,
            imageUrl = "https://images.unsplash.com/photo-1558317374-067fb5f30001?w=800",
            price = 70000.00.toBigDecimal()
        )

        val ad15 = Advertisement(
            name = "Kindle Paperwhite",
            description = "Электронная книга Amazon Kindle Paperwhite 11-го поколения, 16GB",
            owner = savedUser8,
            imageUrl = "https://images.unsplash.com/photo-1592496431122-2349e0fbc666?w=800",
            price = 15000.00.toBigDecimal()
        )

        val ad16 = Advertisement(
            name = "GoPro Hero 12 Black",
            description = "Экшн-камера GoPro Hero 12 Black с аксессуарами для съемки",
            owner = savedUser8,
            imageUrl = "https://images.unsplash.com/photo-1519638399535-1b036603ac77?w=800",
            price = 40000.00.toBigDecimal()
        )

        val ad17 = Advertisement(
            name = "Xiaomi Mi Band 8",
            description = "Фитнес-браслет Xiaomi Mi Band 8, AMOLED дисплей, водонепроницаемый",
            owner = savedUser1,
            imageUrl = "https://images.unsplash.com/photo-1575311373937-040b8e1fd5b6?w=800",
            price = 4000.00.toBigDecimal()
        )

        val ad18 = Advertisement(
            name = "Beats Studio Pro",
            description = "Беспроводные наушники Beats Studio Pro с шумоподавлением",
            owner = savedUser2,
            imageUrl = "https://images.unsplash.com/photo-1484704849700-f032a568e944?w=800",
            price = 25000.00.toBigDecimal()
        )

        val ad19 = Advertisement(
            name = "Lenovo Legion 5 Pro",
            description = "Игровой ноутбук Lenovo Legion 5 Pro, AMD Ryzen 7, RTX 4060, 32GB RAM",
            owner = savedUser3,
            imageUrl = "https://images.unsplash.com/photo-1603302576837-37561b2e2302?w=800",
            price = 180000.00.toBigDecimal()
        )

        val ad20 = Advertisement(
            name = "Samsung Galaxy Tab S9",
            description = "Планшет Samsung Galaxy Tab S9, 11 дюймов, 256GB, S Pen в комплекте",
            owner = savedUser4,
            imageUrl = "https://images.unsplash.com/photo-1561154464-82e9adf32764?w=800",
            price = 70000.00.toBigDecimal()
        )

        advertisementRepository.saveAll(listOf(
            ad1, ad2, ad3, ad4, ad5, ad6, ad7, ad8, ad9, ad10,
            ad11, ad12, ad13, ad14, ad15, ad16, ad17, ad18, ad19, ad20
        ))

        // Создаем корзины для пользователей
        val cart1 = Cart(user = savedUser1)
        val cart2 = Cart(user = savedUser2)
        val cart3 = Cart(user = savedUser3)
        val cart4 = Cart(user = savedUser4)
        val cart5 = Cart(user = savedUser5)
        val cart6 = Cart(user = savedUser6)
        val cart7 = Cart(user = savedUser7)
        val cart8 = Cart(user = savedUser8)
        cartRepository.saveAll(listOf(cart1, cart2, cart3, cart4, cart5, cart6, cart7, cart8))

        // Создаем способы оплаты с рабочими URL картинок
        val creditCard = PaymentMethod(
            name = "Банковская карта",
            image = "https://cdn-icons-png.flaticon.com/512/1611/1611179.png"
        )

        val cash = PaymentMethod(
            name = "Наличные",
            image = "https://cdn-icons-png.flaticon.com/512/639/639365.png"
        )

        val paypal = PaymentMethod(
            name = "PayPal",
            image = "https://cdn-icons-png.flaticon.com/512/174/174861.png"
        )

        val sbp = PaymentMethod(
            name = "СБП (Система быстрых платежей)",
            image = "https://upload.wikimedia.org/wikipedia/ru/thumb/c/c7/%D0%A1%D0%91%D0%9F_%D0%BB%D0%BE%D0%B3%D0%BE%D1%82%D0%B8%D0%BF.svg/1200px-%D0%A1%D0%91%D0%9F_%D0%BB%D0%BE%D0%B3%D0%BE%D1%82%D0%B8%D0%BF.svg.png"
        )

        val yandexPay = PaymentMethod(
            name = "Яндекс.Пей",
            image = "https://www.gateline.net/wp-content/uploads/Yandex_Pay_logo.png"
        )

        val applePay = PaymentMethod(
            name = "Apple Pay",
            image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTWxYUQvdwKXZ9meVu4Jx6fr7nNNo99TLl-bA&s"
        )

        val googlePay = PaymentMethod(
            name = "Google Pay",
            image = "https://cdn-icons-png.flaticon.com/512/6124/6124998.png"
        )

        val crypto = PaymentMethod(
            name = "Криптовалюта",
            image = "https://cdn-icons-png.flaticon.com/512/825/825508.png"
        )

        val savedCreditCard = paymentMethodRepository.save(creditCard)
        val savedCash = paymentMethodRepository.save(cash)
        val savedPaypal = paymentMethodRepository.save(paypal)
        val savedSbp = paymentMethodRepository.save(sbp)
        val savedYandexPay = paymentMethodRepository.save(yandexPay)
        val savedApplePay = paymentMethodRepository.save(applePay)
        val savedGooglePay = paymentMethodRepository.save(googlePay)
        val savedCrypto = paymentMethodRepository.save(crypto)

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

        val russianPost = ShippingMethod(
            name = "Почта России",
            price = BigDecimal("300.00")
        )

        val cdek = ShippingMethod(
            name = "СДЭК",
            price = BigDecimal("400.00")
        )

        val boxberry = ShippingMethod(
            name = "Boxberry",
            price = BigDecimal("350.00")
        )

        val ozon = ShippingMethod(
            name = "Ozon Rocket",
            price = BigDecimal("450.00")
        )

        val yandexDelivery = ShippingMethod(
            name = "Яндекс.Доставка",
            price = BigDecimal("600.00")
        )

        val dpd = ShippingMethod(
            name = "DPD",
            price = BigDecimal("550.00")
        )

        val savedCourier = shippingMethodRepository.save(courier)
        val savedPickup = shippingMethodRepository.save(pickup)
        val savedExpress = shippingMethodRepository.save(express)
        val savedRussianPost = shippingMethodRepository.save(russianPost)
        val savedCdek = shippingMethodRepository.save(cdek)
        val savedBoxberry = shippingMethodRepository.save(boxberry)
        val savedOzon = shippingMethodRepository.save(ozon)
        val savedYandexDelivery = shippingMethodRepository.save(yandexDelivery)
        val savedDpd = shippingMethodRepository.save(dpd)

        // Получаем сохраненные объявления для использования в корзинах и отправках
        val savedAd1 = advertisementRepository.findAll()[0]
        val savedAd2 = advertisementRepository.findAll()[1]
        val savedAd3 = advertisementRepository.findAll()[2]
        val savedAd4 = advertisementRepository.findAll()[3]
        val savedAd5 = advertisementRepository.findAll()[4]
        val savedAd6 = advertisementRepository.findAll()[5]
        val savedAd7 = advertisementRepository.findAll()[6]
        val savedAd8 = advertisementRepository.findAll()[7]
        val savedAd9 = advertisementRepository.findAll()[8]
        val savedAd10 = advertisementRepository.findAll()[9]

        // Получаем сохраненные корзины
        val savedCart1 = cartRepository.findAll()[0]
        val savedCart2 = cartRepository.findAll()[1]
        val savedCart3 = cartRepository.findAll()[2]
        val savedCart4 = cartRepository.findAll()[3]
        val savedCart5 = cartRepository.findAll()[4]

        // Создаем связи корзин с объявлениями (CartAdvertisement)
        val cartAd1 = CartAdvertisement(
            cart = savedCart1,
            advertisement = savedAd3  // user1 добавил в корзину товар от user2
        )

        val cartAd2 = CartAdvertisement(
            cart = savedCart1,
            advertisement = savedAd5  // user1 добавил в корзину товар от user3
        )

        val cartAd3 = CartAdvertisement(
            cart = savedCart2,
            advertisement = savedAd1  // user2 добавил в корзину товар от user1
        )

        val cartAd4 = CartAdvertisement(
            cart = savedCart2,
            advertisement = savedAd6  // user2 добавил в корзину товар от user3
        )

        val cartAd5 = CartAdvertisement(
            cart = savedCart3,
            advertisement = savedAd2  // user3 добавил в корзину товар от user1
        )

        val cartAd6 = CartAdvertisement(
            cart = savedCart3,
            advertisement = savedAd4  // user3 добавил в корзину товар от user2
        )

        val cartAd7 = CartAdvertisement(
            cart = savedCart4,
            advertisement = savedAd7  // user4 добавил в корзину товар от user4
        )

        val cartAd8 = CartAdvertisement(
            cart = savedCart4,
            advertisement = savedAd9  // user4 добавил в корзину товар от user5
        )

        val cartAd9 = CartAdvertisement(
            cart = savedCart5,
            advertisement = savedAd8  // user5 добавил в корзину товар от user4
        )

        val cartAd10 = CartAdvertisement(
            cart = savedCart5,
            advertisement = savedAd10  // user5 добавил в корзину товар от user5
        )

        cartAdvertisementRepository.saveAll(listOf(
            cartAd1, cartAd2, cartAd3, cartAd4, cartAd5,
            cartAd6, cartAd7, cartAd8, cartAd9, cartAd10
        ))

        // Создаем отправки товаров (Ship)
        val ship1 = Ship(
            name = "Отправка iPhone 15 Pro",
            shippingMethod = savedExpress,
            paymentMethod = savedCreditCard,
            receiver = savedUser2,
            owner = savedUser1,
            address = "г. Москва, ул. Пушкина, д. 10"
        )

        val ship2 = Ship(
            name = "Отправка MacBook Air M2",
            shippingMethod = savedCourier,
            paymentMethod = savedApplePay,
            receiver = savedUser3,
            owner = savedUser1,
            address = "г. Казань, ул. Баумана, д. 5"
        )

        val ship3 = Ship(
            name = "Отправка Samsung Galaxy S24",
            shippingMethod = savedCdek,
            paymentMethod = savedSbp,
            receiver = savedUser1,
            owner = savedUser2,
            address = "г. Санкт-Петербург, ул. Ленина, д. 1"
        )

        val ship4 = Ship(
            name = "Отправка iPad Pro 12.9",
            shippingMethod = savedPickup,
            paymentMethod = savedCash,
            receiver = savedUser4,
            owner = savedUser2,
            address = "г. Екатеринбург, пр. Ленина, д. 25"
        )

        val ship5 = Ship(
            name = "Отправка Sony PlayStation 5",
            shippingMethod = savedBoxberry,
            paymentMethod = savedYandexPay,
            receiver = savedUser5,
            owner = savedUser3,
            address = "г. Новосибирск, ул. Красный проспект, д. 15"
        )

        val ship6 = Ship(
            name = "Отправка MacBook Pro 16",
            shippingMethod = savedExpress,
            paymentMethod = savedCreditCard,
            receiver = savedUser6,
            owner = savedUser3,
            address = "г. Москва, ул. Тверская, д. 7"
        )

        val ship7 = Ship(
            name = "Отправка Nintendo Switch OLED",
            shippingMethod = savedOzon,
            paymentMethod = savedGooglePay,
            receiver = savedUser7,
            owner = savedUser4,
            address = "г. Краснодар, ул. Красная, д. 120"
        )

        val ship8 = Ship(
            name = "Отправка AirPods Pro 2",
            shippingMethod = savedCourier,
            paymentMethod = savedPaypal,
            receiver = savedUser8,
            owner = savedUser4,
            address = "г. Владивосток, ул. Светланская, д. 50"
        )

        val ship9 = Ship(
            name = "Отправка Samsung 4K TV 55\"",
            shippingMethod = savedYandexDelivery,
            paymentMethod = savedCreditCard,
            receiver = savedUser1,
            owner = savedUser5,
            address = "г. Санкт-Петербург, ул. Ленина, д. 1"
        )

        val ship10 = Ship(
            name = "Отправка Dell XPS 15",
            shippingMethod = savedDpd,
            paymentMethod = savedSbp,
            receiver = savedUser2,
            owner = savedUser5,
            address = "г. Москва, ул. Пушкина, д. 10"
        )

        val ship11 = Ship(
            name = "Отправка Canon EOS R6",
            shippingMethod = savedRussianPost,
            paymentMethod = savedCash,
            receiver = savedUser3,
            owner = savedUser6,
            address = "г. Казань, ул. Баумана, д. 5"
        )

        val ship12 = Ship(
            name = "Отправка DJI Mavic Air 2",
            shippingMethod = savedCdek,
            paymentMethod = savedCrypto,
            receiver = savedUser4,
            owner = savedUser6,
            address = "г. Екатеринбург, пр. Ленина, д. 25"
        )

        shipRepository.saveAll(listOf(
            ship1, ship2, ship3, ship4, ship5, ship6,
            ship7, ship8, ship9, ship10, ship11, ship12
        ))

        println("Тестовые данные успешно созданы!")
        println("Создано пользователей: ${userRepository.count()}")
        println("Создано объявлений: ${advertisementRepository.count()}")
        println("Создано корзин: ${cartRepository.count()}")
        println("Создано товаров в корзинах: ${cartAdvertisementRepository.count()}")
        println("Создано способов оплаты: ${paymentMethodRepository.count()}")
        println("Создано способов доставки: ${shippingMethodRepository.count()}")
        println("Создано отправок: ${shipRepository.count()}")
    }
}
