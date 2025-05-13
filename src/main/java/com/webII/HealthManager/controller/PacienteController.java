package com.webII.HealthManager.controller;

import com.webII.HealthManager.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/consultorio/paciente")
public class PacienteController {


    @Autowired
    PacienteRepository PacienteRepository;

    @GetMapping
    public String paciente(Model model) {
        model.addAttribute("pacientes", PacienteRepository.pacientes());
        System.out.println(PacienteRepository.pacientes());
        return "pacienteList";
    }
}
