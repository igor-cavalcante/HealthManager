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

    // Rota para abrir o formulário de novo paciente
    @GetMapping("/novo")
    public String novoPaciente(Model model) {
        model.addAttribute("paciente", new PacienteEntity());
        return "paciente/pacienteForm";
    }


    // Rota para salvar novo paciente
    @PostMapping("/salvar")
    public String savePaciente(@ModelAttribute("paciente") PacienteEntity paciente, Model model) {
        try {
            pacienteRepository.save(paciente);
        } catch (IllegalArgumentException e) {
            model.addAttribute("mensagem", e.getMessage());
            return "/paciente/erro";
        }
        return "redirect:/consultorio/paciente";
    }

    @GetMapping("/deletar/{id}")
    public String deletePaciente(@PathVariable Long id) {
        pacienteRepository.remove(id);
        return "redirect:/";
    }



    @GetMapping("/editar/{id}")
    public String pacienteEdit(@PathVariable Long id, Model model){
        PacienteEntity paciente = PacienteRepository.paciente(id);
        model.addAttribute("paciente",paciente);
        return "paciente/pacienteForm";
    }

    @PostMapping("/editar")
    public String saveEdit(@ModelAttribute("paciente") PacienteEntity paciente, Model model){
        try {
            pacienteRepository.update(paciente);
        } catch (IllegalArgumentException e) {
            model.addAttribute("mensagem",e.getMessage());
            // Tratar erro ou redirecionar para uma página de erro
            return "/paciente/erro";
        }
        return "redirect:/consultorio/paciente";
    }
}
