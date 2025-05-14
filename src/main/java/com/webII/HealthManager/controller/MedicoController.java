package com.webII.HealthManager.controller;


import com.webII.HealthManager.model.ConsultaEntity;
import com.webII.HealthManager.model.MedicoEntity;
import com.webII.HealthManager.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/consultorio/medico")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @GetMapping
    public String listarMedicos(Model model) {
        model.addAttribute("medicos", medicoRepository.medicos());
        return "medico/medicoList";
    }

    @GetMapping("/novo")
    public String novoMedico(Model model) {
        model.addAttribute("medico", new MedicoEntity());
        return "medico/medicoForm";
    }

    @GetMapping("/editar/{id}")
    public String editarMedico(@PathVariable Long id, Model model) {
        MedicoEntity medico = medicoRepository.medico(id);
        if (medico != null) {
            model.addAttribute("medico", medico);
            return "medico/medicoForm";
        } else {
            model.addAttribute("mensagem", "Médico não encontrado.");
            return "erro";
        }
    }


    @PostMapping("/salvar")
    public String salvarMedico(@ModelAttribute MedicoEntity medico) {
        medicoRepository.save(medico);
        return "redirect:/consultorio/medico";
    }

    @PostMapping("/editar")
    public String editarMedico(@ModelAttribute MedicoEntity medico) {
        try {
            medicoRepository.update(medico);
            return "redirect:/consultorio/medico";
        } catch (IllegalArgumentException e) {
            return "erro";
        }
    }


    @GetMapping("/deletar/{id}")
    public String deletarMedico(@PathVariable Long id) {
        medicoRepository.remove(id);
        return "redirect:/consultorio/medico";
    }

        @GetMapping("/{id}/consultas")
        public String listarConsultas(@PathVariable Long id, Model model) {
            MedicoEntity medico = medicoRepository.medico(id);
            if (medico != null) {
                List<ConsultaEntity> consultas = medicoRepository.consultasPorMedico(id);
                model.addAttribute("consultas", consultas);
                model.addAttribute("medicoNome", medico.getNome());
                return "medico/medicoConsultas";
            } else {
                model.addAttribute("mensagem", "Médico não encontrado.");
                return "erro";
            }
        }
}
