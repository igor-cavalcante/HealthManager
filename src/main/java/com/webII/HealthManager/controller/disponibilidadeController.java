package com.webII.HealthManager.controller;


import com.webII.HealthManager.model.DisponibilidadeEntity;
import com.webII.HealthManager.repository.ConsultaRepository;
import com.webII.HealthManager.repository.DisponibilidadeRepository;
import com.webII.HealthManager.repository.MedicoRepository;
import com.webII.HealthManager.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/consultorio/disponibilidade")
public class disponibilidadeController {


    @Autowired
    private DisponibilidadeRepository disponibilidadeRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;

    @GetMapping
    public String ListDisponibilidade(Model model) {
        List<DisponibilidadeEntity> disponibilidadeEntityList = disponibilidadeRepository.listarTodos();
        model.addAttribute("disponibilidade", disponibilidadeEntityList);
        return "disponibilidade/disponibilidadeList";
    }




}
