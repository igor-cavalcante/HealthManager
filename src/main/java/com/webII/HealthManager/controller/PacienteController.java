package com.webII.HealthManager.controller;

import com.webII.HealthManager.model.ConsultaEntity;
import com.webII.HealthManager.model.PacienteEntity;
import com.webII.HealthManager.repository.PacienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import javax.xml.transform.Result;
import java.util.List;

@Controller
@RequestMapping("/consultorio/paciente")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @GetMapping
    public String paciente(Model model) {
        model.addAttribute("pacientes", pacienteRepository.pacientes());
        System.out.println(pacienteRepository.pacientes());
        return "paciente/pacienteList";
    }

    // Rota para abrir o formulário de novo paciente
    @GetMapping("/novo")
    public String form( Model model ) {
        model.addAttribute("paciente", new PacienteEntity());
        return "paciente/pacienteForm";
    }


    // Rota para salvar novo paciente
    @PostMapping("/salvar")
    public String savePaciente(@Valid  @ModelAttribute("paciente") PacienteEntity paciente, BindingResult result,Model model ) {

        if (result.hasErrors()) {
            return "paciente/pacienteForm";
        }

        pacienteRepository.save(paciente);

        return "redirect:/consultorio/paciente";
    }



    @GetMapping("/editar/{id}")
    public String pacienteEdit(@PathVariable Long id, Model model){
        PacienteEntity paciente = pacienteRepository.paciente(id);
        model.addAttribute("paciente",paciente);
        return "paciente/pacienteForm";
    }

    @PostMapping("/editar")
    public String saveEdit( @Valid @ModelAttribute("paciente") PacienteEntity paciente,BindingResult result, Model model){
        if (result.hasErrors()) {
            return "paciente/pacienteForm";
        }

            pacienteRepository.update(paciente);
        return "redirect:/consultorio/paciente";
    }

    @GetMapping("/deletar/{id}")
    public String deletePaciente( @PathVariable Long id) {
        pacienteRepository.remove(id);
        return "redirect:/";
    }

    @GetMapping("/{id}/consultas")
    public String listarConsultas(@PathVariable Long id, Model model) {
        PacienteEntity paciente = pacienteRepository.paciente(id);
        if (paciente != null) {
            model.addAttribute("consultas", paciente.getConsultas());
            model.addAttribute("pacienteNome", paciente.getNome());
            return "paciente/pacienteConsultas";
        } else {
            model.addAttribute("mensagemErro", "Paciente não encontrado.");
            return "erro";
        }
    }

    @GetMapping("/search/")
    public String buscarPaciente( @RequestParam(required = false) String name, Model model) {
        if(name != null && !name.isEmpty()) {
            model.addAttribute("pacientes", pacienteRepository.SearchByName(name));
        } else {
            model.addAttribute("pacientes", pacienteRepository.pacientes());
        }
        return "paciente/pacienteList";
    }


}
