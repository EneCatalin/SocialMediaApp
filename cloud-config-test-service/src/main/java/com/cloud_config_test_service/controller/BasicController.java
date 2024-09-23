package com.cloud_config_test_service.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class BasicController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello from Sample App";
    }
}
