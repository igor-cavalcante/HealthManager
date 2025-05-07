package com.web2.HealthManager.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clinica")
public class PacienteController {

    @GetMapping("/gerencia")
    public String mostrarFormulario(Model model) {
        return "clinica/home";
    }

}
