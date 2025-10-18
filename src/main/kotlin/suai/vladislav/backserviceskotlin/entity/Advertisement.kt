package suai.vladislav.backserviceskotlin.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

@Entity
@Table(name = "advertisements")
data class Advertisement(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @NotBlank
    @Column(nullable = false)
    val name: String,

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    val description: String,

    @Column(nullable = false, precision = 10, scale = 2)
    val price: BigDecimal = BigDecimal.ZERO,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    @NotNull
    val owner: User,

    @OneToMany(mappedBy = "advertisement", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val cartAdvertisements: MutableList<CartAdvertisement> = mutableListOf()
)
