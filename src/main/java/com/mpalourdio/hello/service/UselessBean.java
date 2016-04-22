package com.mpalourdio.hello.service;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UselessBean {
    private String testPro = "inclass";

    public String getTestPro() {
        return this.testPro;
    }

    public void setTestPro(final String testPro) {
        this.testPro = testPro;
    }
}
