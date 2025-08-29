package com.webII.HealthManager.controller;


import com.webII.HealthManager.model.AgendamentoEntity;
import com.webII.HealthManager.model.DisponibilidadeEntity;
import com.webII.HealthManager.model.MedicoEntity;
import com.webII.HealthManager.repository.DisponibilidadeRepository;
import com.webII.HealthManager.repository.MedicoRepository;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/consultorio/disponibilidade")
public class DisponibilidadeController {

    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private DisponibilidadeRepository disponibilidadeRepository;

    @GetMapping
    public String listarDisponibilidades(Model model) {
        // Buscar apenas disponibilidades ATIVAS (DISPONIVEL)
        model.addAttribute("disponibilidades", disponibilidadeRepository.findDisponibilidadesAtivas());
        return "disponibilidade/disponibilidadeList"; // Corrigido o nome do template
    }

    @GetMapping("/novo")
    public String novoAgendamento(@RequestParam(name = "nome", required = false) String nome, Model model) {
        model.addAttribute("disponibilidade", new DisponibilidadeEntity());

        List<MedicoEntity> medicos;
        if (nome != null && !nome.isBlank()) {
            medicos = medicoRepository.buscarPorNome(nome);
        } else {
            medicos = medicoRepository.medicos();
        }

        model.addAttribute("medicos", medicos);
        model.addAttribute("nomeBuscado", nome); // opcional: manter valor no input de busca
        return "disponibilidade/disponibilidadeForm";
    }

    @PostMapping("/salvar")
    public String salvarDisponibilidade(@ModelAttribute DisponibilidadeEntity disponibilidade,
                                        @RequestParam("medicoId") Long medicoId,
                                        Model model) {

        // Converte o ID para objeto Médico
        MedicoEntity medico = medicoRepository.medico(medicoId);

        disponibilidade.setMedico(medico);
        disponibilidade.setStatus("DISPONIVEL");
        disponibilidadeRepository.save(disponibilidade);

        return "redirect:/consultorio/disponibilidade";
    }



}
