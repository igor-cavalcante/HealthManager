package com.webII.HealthManager.controller;


import com.webII.HealthManager.model.ConsultaEntity;
import com.webII.HealthManager.model.DisponibilidadeEntity;
import com.webII.HealthManager.model.PacienteEntity;
import com.webII.HealthManager.repository.ConsultaRepository;
import com.webII.HealthManager.repository.DisponibilidadeRepository;
import com.webII.HealthManager.repository.MedicoRepository;
import com.webII.HealthManager.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/consultorio/agendamento")
public class agendamentoController {

    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private DisponibilidadeRepository disponibilidadeRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private ConsultaRepository consultaRepository;

    @GetMapping()
    public String listarAgendamentos(Model model) {
        model.addAttribute("agendamentos", disponibilidadeRepository.findDisponibilidadeAgendadas());
        return "agendamento/agendamentoList";
    }

    @GetMapping("/novo/{idDisponibilidade}")
    public String novoAgendamento(@PathVariable Long idDisponibilidade, Model model) {
        try {
            DisponibilidadeEntity disponibilidade = disponibilidadeRepository.findById(idDisponibilidade);

            if (disponibilidade == null || !"DISPONIVEL".equals(disponibilidade.getStatus())) {
                model.addAttribute("erro", "Horário não disponível");
                return "redirect:/consultorio/disponibilidade";
            }

            model.addAttribute("consulta", new ConsultaEntity());
            model.addAttribute("disponibilidade", disponibilidade);
            model.addAttribute("pacientes", pacienteRepository.pacientes());
            return "agendamento/agendarForm";

        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao carregar formulário");
            return "redirect:/consultorio/disponibilidade";
        }
    }


    @PostMapping("/salvar")
    public String salvarAgendamento(@ModelAttribute ConsultaEntity consulta,
                                    @RequestParam Long disponibilidadeId,
                                    @RequestParam Long pacienteId,
                                    Model model) {

        try {
            // 1. Buscar a disponibilidade
            DisponibilidadeEntity disponibilidade = disponibilidadeRepository.findById(disponibilidadeId);
            if (disponibilidade == null || !"DISPONIVEL".equals(disponibilidade.getStatus())) {
                model.addAttribute("erro", "Horário não disponível");
                return "redirect:/consultorio/disponibilidade";
            }

            // 2. Buscar o paciente
            PacienteEntity paciente = pacienteRepository.paciente(pacienteId);
            if (paciente == null) {
                model.addAttribute("erro", "Paciente não encontrado");
                model.addAttribute("disponibilidade", disponibilidade);
                model.addAttribute("pacientes", pacienteRepository.pacientes());
                return "agendamento/agendarForm";
            }

            // 3. Configurar a consulta
            consulta.setDisponibilidade(disponibilidade);
            consulta.setPaciente(paciente);
            consulta.setMedico(disponibilidade.getMedico());
            consulta.setStatus("AGENDADA");

            // 4. Salvar consulta
            consultaRepository.save(consulta);

            // 5. Atualizar status da disponibilidade
            disponibilidade.setStatus("AGENDADO");
            disponibilidadeRepository.save(disponibilidade);

            return "redirect:/consultorio/agendamento";

        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao agendar: " + e.getMessage());
            model.addAttribute("disponibilidade", disponibilidadeRepository.findById(disponibilidadeId));
            model.addAttribute("pacientes", pacienteRepository.pacientes());
            return "agendamento/agendarForm";
        }
    }

}
