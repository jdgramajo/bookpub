package org.test.bookpubstarter.dbcount;

import com.codahale.metrics.MetricRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.CompositeHealthIndicator;
import org.springframework.boot.actuate.health.HealthAggregator;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

@Configuration
public class DbCountAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DbCountRunner dbCountRunner(Collection<CrudRepository> repositories) {
        return new DbCountRunner(repositories);
    }

    @Autowired
    private HealthAggregator healthAggregator;

    @Bean
    public HealthIndicator dbCountHealthIndicator(Collection<CrudRepository> repositories) {
        CompositeHealthIndicator compositeHealthIndicator = new CompositeHealthIndicator(healthAggregator);
        repositories.forEach( r ->
                compositeHealthIndicator.addHealthIndicator(DbCountRunner.getRepositoryName(r.getClass()),
                new DbCountHealthIndicator(r))
        );
        return compositeHealthIndicator;
    }

    @Bean
    public DbCountMetrics dbCountMetrics(Collection<CrudRepository> repositories, MetricRegistry registry) {
        DbCountMetrics dbCountMetrics = new DbCountMetrics(repositories);
        registry.registerAll(dbCountMetrics);
        return dbCountMetrics;
    }

}
