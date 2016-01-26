package org.test.bookpub;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.test.bookpubstarter.dbcount.DbCountRunner;

import java.util.Collection;

@SpringBootApplication
@EnableScheduling
public class BookPubApplication {

	private final Log logger = LogFactory.getLog(getClass());

	public static void main(String[] args) {
		SpringApplication.run(BookPubApplication.class, args);
	}

	@Bean
	public StartupRunner scheduleRunner() {
		return new StartupRunner();
	}

	@Bean
	public DbCountRunner dbCountRunner(Collection<CrudRepository> repositories) {
		return new DbCountRunner(repositories) {
			@Override
			public void run(String... args) throws Exception {
				logger.info("Manually Declared DbCountRunner");
			}
		};
	}

}
