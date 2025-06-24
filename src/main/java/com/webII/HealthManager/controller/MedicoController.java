package com.webII.HealthManager.controller;


import com.webII.HealthManager.model.ConsultaEntity;
import com.webII.HealthManager.model.MedicoEntity;
import com.webII.HealthManager.repository.MedicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @PostMapping("/salvar")
    public String salvarMedico(@Valid @ModelAttribute("medico") MedicoEntity medico, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "medico/medicoForm";
        }
        medicoRepository.save(medico);
        return "redirect:/consultorio/medico";
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


    @PostMapping("/editar")
    public String editarMedico(@Valid @ModelAttribute("medico") MedicoEntity medico, BindingResult result, Model model) {

        if(result.hasErrors()) {
            return "medico/medicoForm";
        }
            medicoRepository.update(medico);
            return "redirect:/consultorio/medico";
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

    @GetMapping("/search/")
    public String buscarMedico( @RequestParam(required = false) String name, Model model) {
        if(name != null && !name.isEmpty()) {
            model.addAttribute("medicos", medicoRepository.buscarPorNome(name));
        } else {
            model.addAttribute("medicos", medicoRepository.medicos());
        }
        return "medico/medicoList";
    }



}
