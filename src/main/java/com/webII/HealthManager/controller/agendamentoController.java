package com.webII.HealthManager.controller;

import com.webII.HealthManager.model.*;
import com.webII.HealthManager.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/consultorio/agendamento")
public class agendamentoController {

    @Autowired
    private AgendamentoRepository agendamentoRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private DisponibilidadeRepository disponibilidadeRepository;


    @GetMapping()
    public String listarAgendamentos(Model model) {
        List<AgendamentoEntity> agendamentos = agendamentoRepository.listarTodos();
        model.addAttribute("agendamentos", agendamentos);
        return "agendamento/agendamentoList";
    }

    @GetMapping("/novo")
    public String novoAgendamento( Model model) {
        model.addAttribute("disponibilidade", disponibilidadeRepository.findDisponibilidadesAtivas());
        model.addAttribute("pacientes", pacienteRepository.pacientes());
        return "agendamento/agendarForm";
    }

    @GetMapping("/novo/{idDisponibilidade}")
    public String novoAgendamento(@PathVariable Long idDisponibilidade, Model model) {
        try {
            DisponibilidadeEntity disponibilidade = disponibilidadeRepository.findById(idDisponibilidade);

            if (disponibilidade == null) {
                model.addAttribute("erro", "Horário não encontrado");
                return "redirect:/consultorio/disponibilidade";
            }

            if (!"DISPONIVEL".equals(disponibilidade.getStatus())) {
                model.addAttribute("erro", "Este horário não está mais disponível");
                return "redirect:/consultorio/disponibilidade";
            }

            model.addAttribute("disponibilidade", disponibilidade);
            model.addAttribute("pacientes", pacienteRepository.pacientes());
            return "agendamento/agendarForm";

        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao carregar formulário: " + e.getMessage());
            return "redirect:/consultorio/disponibilidade";
        }
    }

    @PostMapping("/salvar")
    public String salvarAgendamento(@RequestParam Long disponibilidadeId,
                                    @RequestParam Long pacienteId,
                                    Model model) {

        try {
            // 1. Buscar a disponibilidade
            DisponibilidadeEntity disponibilidade = disponibilidadeRepository.findById(disponibilidadeId);
            if (disponibilidade == null) {
                model.addAttribute("erro", "Horário não encontrado");
                return "redirect:/consultorio/disponibilidade";
            }

            // 2. Verificar se ainda está disponível
            if (!"DISPONIVEL".equals(disponibilidade.getStatus())) {
                model.addAttribute("erro", "Este horário não está mais disponível");
                return "redirect:/consultorio/disponibilidade";
            }

            // 3. Buscar o paciente
            PacienteEntity paciente = pacienteRepository.paciente(pacienteId);
            if (paciente == null) {
                model.addAttribute("erro", "Paciente não encontrado");
                model.addAttribute("disponibilidade", disponibilidade);
                model.addAttribute("pacientes", pacienteRepository.pacientes());
                return "agendamento/agendarForm";
            }

            // 4. Criar agendamento
            AgendamentoEntity agendamento = new AgendamentoEntity();
            agendamento.setDisponibilidade(disponibilidade);
            agendamento.setPaciente(paciente);
            agendamento.setDataCriacao(LocalDateTime.now());
            agendamentoRepository.salvar(agendamento);

            // 5. Atualizar status da disponibilidade
            disponibilidade.setStatus("AGENDADO");
            disponibilidadeRepository.save(disponibilidade);

            return "redirect:/consultorio/agendamento";

        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao agendar: " + e.getMessage());
            return "redirect:/consultorio/disponibilidade";
        }
    }

}
