package com.webII.HealthManager.controller;

import com.webII.HealthManager.model.PacienteEntity;
import com.webII.HealthManager.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/consultorio/paciente")
public class PacienteController {


    @Autowired
    PacienteRepository PacienteRepository;
    @Autowired
    private PacienteRepository pacienteRepository;

    @GetMapping
    public String paciente(Model model) {
        model.addAttribute("pacientes", PacienteRepository.pacientes());
        System.out.println(PacienteRepository.pacientes());
        return "paciente/pacienteList";
    }


    @GetMapping("/editar/{id}")
    public String pacienteEdit(@PathVariable Long id, Model model){
        PacienteEntity paciente = PacienteRepository.paciente(id);
        model.addAttribute("paciente",paciente);
        return "paciente/pacienteForm";
    }

    @PostMapping("/editar")
    public String saveEdit(@ModelAttribute("paciente") PacienteEntity paciente){
        pacienteRepository.update(paciente);
        return "redirect:redirect:/consultorio/paciente";
    }
}
