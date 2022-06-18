package com.jau.samples.platform.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "order-service", fallbackFactory = OrderServiceFallbackFactory.class)
public interface OrderService {

    @GetMapping(value = "/info/order")
    String home();
}
