package org.test.bookpub;

import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.test.bookpub.annotations.UsedForTesting;
import org.test.bookpubstarter.dbcount.EnableDbCounting;

@Configuration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = @ComponentScan.Filter(UsedForTesting.class))
@EnableScheduling
@EnableDbCounting
public class BookPubApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookPubApplication.class, args);
	}

	@Bean
	@Profile("logger")
	public StartupRunner scheduleRunner() {
		return new StartupRunner();
	}

    @Bean
    public CommandLineRunner configValuePrinter(@Value("${my.config.value:}") String configValue) {
        return args -> LogFactory.getLog(getClass()).info("Value of my.config.value property is: " + configValue);
    }

}
