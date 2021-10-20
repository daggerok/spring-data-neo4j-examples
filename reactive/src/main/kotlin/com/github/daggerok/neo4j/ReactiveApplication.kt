package com.github.daggerok.neo4j

import org.neo4j.driver.Driver
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.transaction.ReactiveNeo4jTransactionManager
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository
import org.springframework.data.repository.query.Param
import org.springframework.transaction.ReactiveTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import java.time.Instant

@SpringBootApplication
@Import(ReactiveTransactionalConfig::class)
class ReactiveApplication

fun main(args: Array<String>) {
    runApplication<ReactiveApplication>(*args)
}

@Node
data class Author(
    val firstName: String = "",
    val lastName: String = "",
    val at: Instant = Instant.now(),
    @Id @GeneratedValue val id: Long? = null,
)

interface AuthorRepository : ReactiveNeo4jRepository<Author, Long> {
    fun findAllByLastNameContainsIgnoreCaseOrderByAt(@Param("name") name: String): Flux<Author>
}

@Configuration
@EnableTransactionManagement
class ReactiveTransactionalConfig {

    @Bean
    fun reactiveTransactionManager(driver: Driver): ReactiveTransactionManager =
        ReactiveNeo4jTransactionManager(driver)
}

@RestController
@Transactional(readOnly = true)
class AuthorResource(private val authorRepository: AuthorRepository) {

    @PostMapping("/api/v1/author")
    @Transactional(readOnly = false)
    fun postAuthor(@RequestBody author: Author) =
        authorRepository.save(author)

    @GetMapping("/api/v1/author")
    fun getAuthors() =
        authorRepository.findAll()

    @GetMapping("/api/v1/author/{name}")
    fun findAuthors(@PathVariable name: String) =
        authorRepository.findAllByLastNameContainsIgnoreCaseOrderByAt(name)
}
