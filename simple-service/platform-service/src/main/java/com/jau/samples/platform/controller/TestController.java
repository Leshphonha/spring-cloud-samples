package com.jau.samples.platform.controller;


import com.jau.samples.platform.service.OrderService;
import com.jau.samples.platform.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    private RestTemplate restTemplate;

    private OrderService orderService;

    private ProductService productService;

    private CircuitBreakerFactory circuitBreakerFactory;

    public TestController(RestTemplate restTemplate, OrderService orderService, ProductService productService,
                          CircuitBreakerFactory circuitBreakerFactory) {
        this.restTemplate = restTemplate;
        this.orderService = orderService;
        this.productService = productService;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    @GetMapping("info")
    public String home() {
        return "Hello World! This is Platform Service";
    }

    @GetMapping("/v1/route/order")
    public String routeOrder1() {
        return circuitBreakerFactory
                .create("slow")
                .run(() -> "By restTemplate " + restTemplate.getForObject("http://order-service/info/order", String.class),
                        throwable -> "服务出小差了请稍后再试 By restTemplate");
    }

    @GetMapping("/v1/route/product")
    public String routeProduct1() {
        return "By restTemplate " + restTemplate.getForObject("http://product-service/info/product", String.class);
    }

    @GetMapping("/v2/route/order")
    public String routeOrder2() {
        return "By feign " + orderService.home();
    }

    @GetMapping("/v2/route/product")
    public String routeProduct2() {
        return "By feign " + productService.home();
    }

    @GetMapping("/info/all/feign")
    public List<String> getAll1() {
        log.info("Hello World! This is Platform Service");
        List<String> result = new ArrayList<>();
        result.add("By feign " + orderService.home());
        result.add("By feign " + productService.home());
        return result;
    }

    @GetMapping("/info/all/restTemplate")
    public List<String> getAll2() {
        log.info("step 1");
        List<String> result = new ArrayList<>();
        result.add("By restTemplate " + restTemplate.getForObject("http://order-service/info/order", String.class));
        result.add("By restTemplate " + restTemplate.getForObject("http://product-service/info/product", String.class));
        return result;
    }

}
