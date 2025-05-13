package com.webII.HealthManager.controller.jpa;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/consultorio/paciente")
public class PacienteController {


    @GetMapping
    public String paciente(Model model) {
        return "pacienteList";
    }
}
