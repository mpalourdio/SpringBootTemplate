package com.mpalourdio.springboottemplate.service;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UselessBean {

    private String testPro = "inclass";

    private final ABeanIWantToMock aBeanIWantToMock;

    public UselessBean(final ABeanIWantToMock aBeanIWantToMock) {
        this.aBeanIWantToMock = aBeanIWantToMock;
    }

    public Boolean iWantToMockThisMethod() {
        return aBeanIWantToMock.iAlwaysReturnTrue();
    }

    public String getTestPro() {
        return testPro;
    }

    public void setTestPro(final String testPro) {
        this.testPro = testPro;
    }
}
