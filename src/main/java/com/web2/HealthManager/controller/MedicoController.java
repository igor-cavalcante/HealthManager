package com.web2.HealthManager.controller;


import com.web2.HealthManager.model.entity.Medico;
import com.web2.HealthManager.model.repository.MedicoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/clinica/medico")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;


    @GetMapping
    public String medico(Model model) {
        List<Medico> medicos = medicoRepository.listMedicos();
        model.addAttribute("medicos", medicos);
        System.out.println("Pacientes: " + medicos.size());
        return "medico/medicoList";
    };

    @GetMapping("/new")
    public String novoMedicoForm(Model model) {
        model.addAttribute("medico", new Medico());
        return "medico/medicoForm";
    }

    @PostMapping("/save")
    @Transactional
    public String salvarMedico(@ModelAttribute Medico medico) {
        medicoRepository.salvarOuAtualizar(medico);
        return "redirect:/clinica/medico";
    }

    @GetMapping("/edit/{id}")
    public String editarMedico(@PathVariable Long id, Model model) {
        Medico medico = medicoRepository.buscarPorId(id);
        model.addAttribute("medico", medico);
        return "medico/medicoForm";
    }

    @GetMapping("/delete/{id}")
    @Transactional
    public String excluirMedico(@PathVariable Long id) {
        medicoRepository.excluir(id);
        return "redirect:/clinica/medico";
    }

}
