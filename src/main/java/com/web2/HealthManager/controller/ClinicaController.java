package com.web2.HealthManager.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clinica")
public class ClinicaController {

    @GetMapping("/gerencia")
    public String home(Model model) {
        return "clinica/home";
    }
}
