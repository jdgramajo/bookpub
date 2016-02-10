package org.test.bookpub;

import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

@Configuration
public class MonitoringConfiguration {

    @Bean
    public Graphite graphite(@Value("${graphite.host}") String graphiteHost,
                             @Value("${graphite.port}") int graphitePort) {
        return new Graphite(new InetSocketAddress(graphiteHost, graphitePort));
    }

    @Bean
    public GraphiteReporter graphiteReporter(Graphite graphite, MetricRegistry registry) {
        GraphiteReporter reporter = GraphiteReporter.forRegistry(registry)
                .prefixedWith("bookpub.app")
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .filter(MetricFilter.ALL)
                .build(graphite);
        reporter.start(1, TimeUnit.MILLISECONDS);
        return reporter;
    }

    @Bean
    public ThreadStatesGaugeSet threadStatesGaugeSet(MetricRegistry registry) {
        ThreadStatesGaugeSet threadStatesGaugeSet = new ThreadStatesGaugeSet();
        registry.register("threads", threadStatesGaugeSet);
        return threadStatesGaugeSet;
    }

}
