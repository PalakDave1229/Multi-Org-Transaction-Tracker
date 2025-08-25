package com.example.mott.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @GetMapping("/success")
    public String loginSuccess() {
        return "Google Login Successful ✅";
    }
}