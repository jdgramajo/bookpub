package org.test.bookpub

import org.hamcrest.CoreMatchers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.jdbc.datasource.init.DatabasePopulator
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.ConfigurableWebApplicationContext
import org.test.bookpub.entity.Book
import org.test.bookpub.repository.BookRepository
import spock.lang.Shared
import spock.lang.Specification

import javax.sql.DataSource

@WebAppConfiguration
@ContextConfiguration(classes = [BookPubApplication.class, TestMockBeansConfig],
    loader = SpringApplicationContextLoader.class)
class SpockBookRepositorySpecification extends Specification {

    @Autowired
    ConfigurableWebApplicationContext context

    @Shared
    boolean sharedSetupDone = false

    @Autowired
    private DataSource dataSource

    @Autowired
    private BookRepository bookRepository

    @Shared
    private MockMvc mockMvc

    void setup() {
        if(!sharedSetupDone) {
            sharedSetupDone = mockMvc = MockMvcBuilders.webAppContextSetup(context).build()
        }
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(context.getResource("classpath:/test-data.sql"))
        DatabasePopulatorUtils.execute(populator, dataSource)
    }

    @Transactional
    def "Test RESTful GET"() {
        when:
            def result = mockMvc.perform(MockMvcRequestBuilders.get("/books/${isbn}"))

        then:
            result.with {
                andExpect(MockMvcResultMatchers.status().isOk())
                andExpect(MockMvcResultMatchers.content().string(CoreMatchers.containsString(title)))
            }

        where:
            isbn                 |title
            "978-1-78439-302-1"  |"Learning Spring Boot"
            "972-1-78528-415-1"  |"Spring Boot Recipes"
    }

    @Transactional
    def "Insert another book"() {
        setup:
            def existingBook = bookRepository.findBookByIsbn("972-1-78528-415-1")
            def newBook = new Book("978-1-78528-415-1", "Some Future Book",
                    existingBook.getAuthor(), existingBook.getPublisher())

        expect:
            bookRepository.count() == 2

        when:
            def savedBook = bookRepository.save(newBook)

        then:
            bookRepository.count() == 3
            savedBook.id > -1
    }

}

