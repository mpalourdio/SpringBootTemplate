package com.mpalourdio.hello.service;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UselessBean {
    private String testPro = "inclass";

    public String getTestPro() {
        return testPro;
    }

    public void setTestPro(String testPro) {
        this.testPro = testPro;
    }
}
