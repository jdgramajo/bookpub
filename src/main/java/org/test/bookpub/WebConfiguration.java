package org.test.bookpub;

import org.springframework.context.annotation.Configuration;
import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.context.annotation.Bean;

@Configuration
public class WebConfiguration {
    
    @Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }
    

}
