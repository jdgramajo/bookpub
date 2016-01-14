package org.test.bookpub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.test.bookpub.formatters.BookFormatter;
import org.test.bookpub.repository.BookRepository;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new BookFormatter(bookRepository));
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(false).setUseTrailingSlashMatch(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/internal/**").addResourceLocations("classpath:/");
    }

    @Bean
    public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                container.setSessionTimeout(1, TimeUnit.MINUTES);
            }
        };
    }

}
