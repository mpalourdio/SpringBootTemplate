package app.config;

import com.mpalourdio.springboottemplate.generics.BeanFromConfigurationClass;
import com.mpalourdio.springboottemplate.model.Task;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public BeanFromConfigurationClass<Task> beanFromConfigurationClass() {
        return new BeanFromConfigurationClass<>(Task.class);
    }
}

