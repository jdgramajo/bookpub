package org.test.bookpub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.test.bookpubstarter.dbcount.DbCountRunner;
import org.test.bookpubstarter.dbcount.EnableDbCounting;

import java.util.Collection;

@SpringBootApplication
@EnableScheduling
@EnableDbCounting
public class BookPubApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookPubApplication.class, args);
	}

	@Bean
	public StartupRunner scheduleRunner() {
		return new StartupRunner();
	}

}
