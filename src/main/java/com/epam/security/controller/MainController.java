package com.epam.security.controller;

import com.epam.security.service.BruteForceProtectionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final BruteForceProtectionService bruteForceProtectionService;

    public MainController(BruteForceProtectionService bruteForceProtectionService) {
        this.bruteForceProtectionService = bruteForceProtectionService;
    }

    @GetMapping("/info")
    public String greeting() {
        return "info";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/blocked")
    public String blockedUsers(Model model) {
        model.addAttribute("blockedUsers", bruteForceProtectionService.getBlockedUsers());
        return "blocked";
    }
}
