package com.balancer.service;

import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class TestContext {

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {

        UndertowEmbeddedServletContainerFactory factory = new UndertowEmbeddedServletContainerFactory();
        factory.setPort(TestApiConfig.PORT);
        factory.setSessionTimeout(10, TimeUnit.MINUTES);
        return factory;
    }
}
