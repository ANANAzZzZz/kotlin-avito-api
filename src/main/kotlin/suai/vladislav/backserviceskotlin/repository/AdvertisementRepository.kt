package suai.vladislav.backserviceskotlin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import suai.vladislav.backserviceskotlin.entity.Advertisement
import suai.vladislav.backserviceskotlin.entity.User

@Repository
interface AdvertisementRepository : JpaRepository<Advertisement, Long> {

    @Query("SELECT a FROM Advertisement a JOIN FETCH a.owner")
    fun findAllWithOwner(): List<Advertisement>

    @Query("SELECT a FROM Advertisement a JOIN FETCH a.owner WHERE a.id = :id")
    fun findByIdWithOwner(@Param("id") id: Long): Advertisement?

    fun findByOwner(owner: User): List<Advertisement>

    @Query("SELECT a FROM Advertisement a JOIN FETCH a.owner WHERE a.owner.id = :ownerId")
    fun findByOwnerIdWithOwner(@Param("ownerId") ownerId: Long): List<Advertisement>

    fun findByOwnerId(ownerId: Long): List<Advertisement>

    @Query("SELECT a FROM Advertisement a JOIN FETCH a.owner WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    fun findByNameContainingIgnoreCaseWithOwner(@Param("name") name: String): List<Advertisement>

    @Query("SELECT a FROM Advertisement a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    fun findByNameContainingIgnoreCase(@Param("name") name: String): List<Advertisement>

    @Query("SELECT a FROM Advertisement a JOIN FETCH a.owner WHERE LOWER(a.description) LIKE LOWER(CONCAT('%', :description, '%'))")
    fun findByDescriptionContainingIgnoreCaseWithOwner(@Param("description") description: String): List<Advertisement>

    @Query("SELECT a FROM Advertisement a WHERE LOWER(a.description) LIKE LOWER(CONCAT('%', :description, '%'))")
    fun findByDescriptionContainingIgnoreCase(@Param("description") description: String): List<Advertisement>

    @Query("SELECT a FROM Advertisement a JOIN FETCH a.owner WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(a.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    fun searchByKeywordWithOwner(@Param("keyword") keyword: String): List<Advertisement>

    @Query("SELECT a FROM Advertisement a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(a.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    fun searchByKeyword(@Param("keyword") keyword: String): List<Advertisement>
}
