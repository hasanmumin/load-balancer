package com.balancer.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dummy")
public class DummyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DummyController.class);

    @Value("${server.port}")
    private String port;

    @RequestMapping(method = RequestMethod.POST)
    public void dummy(@RequestBody String name) {
        LOGGER.info("Triggered server port is {} and name is {}", port, name);
    }
}
