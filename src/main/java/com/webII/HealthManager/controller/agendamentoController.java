package com.webII.HealthManager.controller;


import com.webII.HealthManager.repository.DisponibilidadeRepository;
import com.webII.HealthManager.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/consultorio/agendamento")
public class agendamentoController {

    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private DisponibilidadeRepository disponibilidadeRepository;

    @GetMapping()
    public String listarAgendamentos(Model model) {
        model.addAttribute("agendamentos", disponibilidadeRepository.findDisponibilidadeAgendadas());
        return "agendamento/agendamentoList";
    }

}
