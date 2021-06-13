package com.nictritionist.backend.bff.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/hello")
public class WakeUpAndSayHelloController {

    @GetMapping
    public String wakeUpAndSayHello() {
        return "Hello!";
    }
}
