package com.webII.HealthManager.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/consultorio/medico")
public class MedicoController {



    @GetMapping
    public String medico(Model model) {
        return "medico/medicoList";
    }
}
