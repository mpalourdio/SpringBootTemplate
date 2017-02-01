package app.config;

import com.mpalourdio.springboottemplate.generics.BeanFromConfigurationClass;
import com.mpalourdio.springboottemplate.model.Task;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    private final String username;

    public BeanConfig(@Value("${refreshable}") final String username) {
        this.username = username;
    }

    @Bean
    public BeanFromConfigurationClass<Task> beanFromConfigurationClass() {
        final Task task = new Task();
        task.setTaskName("fromBeanConfiguration");
        return new BeanFromConfigurationClass<>(Task.class, task);
    }
}

