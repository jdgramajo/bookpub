package org.test.bookpubstarter.dbcount;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.data.repository.CrudRepository;

public class DbCountHealthIndicator implements HealthIndicator {

    private CrudRepository repository;

    public DbCountHealthIndicator(CrudRepository repository) {
        this.repository = repository;
    }

    @Override
    public Health health() {
        try {
            long count = repository.count();
            if (count >= 0) {
                return Health.up().withDetail("count", count).build();
            }
            return Health.unknown().withDetail("count", count).build();
        } catch (Exception e) {
            return Health.down(e).build();
        }
    }

}
