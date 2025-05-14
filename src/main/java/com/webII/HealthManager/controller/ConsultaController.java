package com.webII.HealthManager.controller;

import com.webII.HealthManager.model.ConsultaEntity;
import com.webII.HealthManager.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/consultorio/consulta")
public class ConsultaController {

    @Autowired
    private ConsultaRepository consultaRepository;

    @GetMapping
    public String listarConsultas(Model model) {
        model.addAttribute("consultas", consultaRepository.consultas());
        return "consulta/consultaList";
    }

    @GetMapping("/nova")
    public String novaConsulta(Model model) {
        model.addAttribute("consulta", new ConsultaEntity());
        return "consulta/consultaForm";
    }

    @PostMapping("/salvar")
    public String salvarConsulta(@ModelAttribute ConsultaEntity consulta) {
        consultaRepository.save(consulta);
        return "redirect:/consultorio/consulta";
    }

    @GetMapping("/deletar/{id}")
    public String deletarConsulta(@PathVariable Long id) {
        consultaRepository.remove(id);
        return "redirect:/consultorio/consulta";
    }

}
