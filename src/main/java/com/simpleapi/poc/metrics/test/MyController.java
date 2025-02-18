package com.simpleapi.poc.metrics.test;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MyController {

    @GetMapping("/hello")
    public String hello() {
        return "Ciao dal mio microservizio superfigo!";
    }

    @PostMapping("/greet")
    public String greet(@RequestBody String name) {
        return "Ciao, " + name + "!";
    }
}
