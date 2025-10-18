package suai.vladislav.backserviceskotlin.config

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@Configuration
@EnableCaching
class CacheConfig {

    @Bean
    fun cacheManager(): CacheManager {
        val cacheManager = CaffeineCacheManager(
            // Advertisement caches
            "advertisements",
            "advertisementById",
            "advertisementsByOwner",

            // User caches
            "users",
            "userById",
            "userByEmail",
            "usersByRole",
            "usersByRating",

            // Payment method caches
            "paymentMethods",
            "paymentMethodById",

            // Shipping method caches
            "shippingMethods",
            "shippingMethodById",
            "shippingMethodsByPrice",
            "shippingMethodsByPriceRange",

            // Ship caches
            "ships",
            "shipById",
            "shipsByReceiver",
            "shipsByShippingMethod",
            "shipsByPaymentMethod",

            // Cart caches
            "carts",
            "cartById",
            "cartByUser",
            "cartWithAdvertisements",
            "recommendedAdvertisements"
        )
        cacheManager.setCaffeine(caffeineCacheBuilder())
        return cacheManager
    }

    private fun caffeineCacheBuilder(): Caffeine<Any, Any> {
        return Caffeine.newBuilder()
            .initialCapacity(200)
            .maximumSize(1000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .recordStats()
    }
}
