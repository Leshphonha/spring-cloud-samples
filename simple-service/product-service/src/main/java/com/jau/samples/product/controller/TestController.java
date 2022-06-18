package com.jau.samples.product.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/info/product")
    public String home() {
        log.info("Hello World! This is Product Service");
        return "Hello World! This is Product Service";
    }
}
