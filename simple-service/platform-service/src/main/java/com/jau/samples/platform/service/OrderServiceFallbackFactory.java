package com.jau.samples.platform.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceFallbackFactory implements FallbackFactory<OrderService> {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceFallbackFactory.class);

    @Override
    public OrderService create(Throwable cause) {
        log.error("order-service调用失败", cause);

        return new OrderService() {
            @Override
            public String home() {
                return "服务出小差了请稍后再试 By feign";
            }
        };
    }
}
