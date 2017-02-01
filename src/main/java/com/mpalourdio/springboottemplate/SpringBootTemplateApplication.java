package com.mpalourdio.springboottemplate;

import app.config.BeanConfig;
import app.config.PropertyPlaceholderConfig;
import app.config.WebSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import java.util.Arrays;

@SpringBootApplication
@PropertySource(value = {
        "file:properties/global.properties",
        "file:properties/local.properties"
}, ignoreResourceNotFound = true)
@Import({
        WebSecurityConfig.class,
        BeanConfig.class,
        PropertyPlaceholderConfig.class
})
@EnableDiscoveryClient
public class SpringBootTemplateApplication {

    public static void main(final String[] args) {
        final ApplicationContext ctx = SpringApplication.run(SpringBootTemplateApplication.class, args);
        final String[] beanNames = ctx.getBeanDefinitionNames();

        Arrays.stream(beanNames)
                .sorted()
                .forEach(System.out::println);
    }
}
