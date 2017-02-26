package com.mpalourdio.springboottemplate.service;

import app.config.BeanConfig;
import com.mpalourdio.springboottemplate.AbstractTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@Import({BeanConfig.class})
public class ServiceWithConfigurationPropertiesTest extends AbstractTestRunner {

    @Autowired
    private ServiceWithConfigurationProperties serviceWithConfigurationProperties;

    @Test
    public void testPropertiesHaveBeenAutomaticallySet() {
        Assert.assertEquals("first", serviceWithConfigurationProperties.getFirstProperty());
    }
}
