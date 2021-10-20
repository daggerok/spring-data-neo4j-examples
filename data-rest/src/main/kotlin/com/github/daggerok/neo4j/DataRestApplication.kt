package com.github.daggerok.neo4j

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.time.Instant

@SpringBootApplication
@EnableTransactionManagement
class DataRestApplication

fun main(args: Array<String>) {
    runApplication<DataRestApplication>(*args)
}

@Node
data class Author(
    val firstName: String = "",
    val lastName: String = "",
    val at: Instant = Instant.now(),
    @Id @GeneratedValue val id: Long? = null,
)

@RepositoryRestResource(collectionResourceRel = "authors", path = "author")
interface AuthorRepository : org.springframework.data.repository.PagingAndSortingRepository<Author, Long> {
    fun findAllByLastNameContainsIgnoreCaseOrderByAt(@Param("name") name: String): List<Author>
}
