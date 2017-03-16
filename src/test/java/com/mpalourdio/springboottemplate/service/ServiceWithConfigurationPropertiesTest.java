package com.mpalourdio.springboottemplate.service;

import com.mpalourdio.springboottemplate.AbstractTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ServiceWithConfigurationPropertiesTest extends AbstractTestRunner {

    @Autowired
    private ServiceWithConfigurationProperties serviceWithConfigurationProperties;

    @Test
    public void testPropertiesHaveBeenAutomaticallySet() {
        Assert.assertEquals("first", serviceWithConfigurationProperties.getFirstProperty());
    }
}
