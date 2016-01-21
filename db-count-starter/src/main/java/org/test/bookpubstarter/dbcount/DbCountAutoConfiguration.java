package org.test.bookpubstarter.dbcount;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

@Configuration
public class DbCountAutoConfiguration {

    @Bean
    public DBCountRunner dbCountRunner(Collection<CrudRepository> repositories) {
        return new DBCountRunner(repositories);
    }

}
