package com.jau.samples.platform.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("product-service")
public interface ProductService {

    @GetMapping(value = "/info/product")
    String home();
}
