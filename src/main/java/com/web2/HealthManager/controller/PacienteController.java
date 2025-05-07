package com.web2.HealthManager.controller;

import com.web2.HealthManager.model.entity.Paciente;
import com.web2.HealthManager.model.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/clinica/paciente")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @GetMapping
    public String listPacientes(Model model) {
        List<Paciente> pacientes = pacienteRepository.listarTodos();
        model.addAttribute("pacientes", pacientes);
        System.out.println("Pacientes: " + pacientes.size());
        return "paciente/pacienteList";
    }


    // Página de criação de paciente
    @GetMapping("/new")
    public String newPaciente(Model model) {
        model.addAttribute("paciente", new Paciente());
        return "paciente/pacienteForm";
    }

    // Salvar novo paciente
    @PostMapping("/save")
    public String savePaciente(@ModelAttribute Paciente paciente) {
        pacienteRepository.save(paciente);
        return "redirect:/clinica/paciente";
    }

    // Página de edição de paciente
    @GetMapping("/edit/{id}")
    public String editPaciente(@PathVariable("id") Long id, Model model) {
        Paciente paciente = pacienteRepository.paciente(id);
        model.addAttribute("paciente", paciente);
        return "paciente/pacienteForm";
    }




    @PostMapping("/update/{id}")
    public String updatePaciente(@PathVariable("id") Long id, @ModelAttribute Paciente paciente) {
        paciente.setId(id); // Garantir que o paciente atualizado tenha o ID correto
        pacienteRepository.save(paciente);
        return "redirect:/clinica/paciente";
    }

    // Excluir paciente
    @GetMapping("/delete/{id}")
    public String deletePaciente(@PathVariable("id") Long id) {
        Paciente paciente = pacienteRepository.paciente(id);
        if (paciente != null) {
            pacienteRepository.delete(paciente);
        }
        return "redirect:/clinica/paciente";
    }

}
