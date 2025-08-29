package com.webII.HealthManager.controller;

import com.webII.HealthManager.model.AgendamentoEntity;
import com.webII.HealthManager.model.ConsultaEntity;
import com.webII.HealthManager.model.MedicoEntity;
import com.webII.HealthManager.repository.AgendamentoRepository;
import com.webII.HealthManager.repository.ConsultaRepository;
import com.webII.HealthManager.repository.MedicoRepository;
import com.webII.HealthManager.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping()
    public String listarAgendamentos(Model model) {
        List<AgendamentoEntity> agendamentos = agendamentoRepository.listarTodos();
        model.addAttribute("agendamentos", agendamentos);
        return "agendamento/agendamentoList";
    }

    @GetMapping("/nova")
    public String novoAgendamento(@RequestParam(name = "nome", required = false) String nome, Model model) {
        model.addAttribute("agendamento", new AgendamentoEntity());

        List<MedicoEntity> medicos;
        if (nome != null && !nome.isBlank()) {
            medicos = medicoRepository.buscarPorNome(nome);
        } else {
            medicos = medicoRepository.medicos();
        }

        model.addAttribute("medicos", medicos);
        model.addAttribute("nomeBuscado", nome); // opcional: manter valor no input de busca
        return "agendamento/disponibilidadeForm";
    }


    @PostMapping("/salvar")
    public String salvarAgendamentos(@ModelAttribute AgendamentoEntity agendamento) {
        LocalTime hora = agendamento.getHora_inicio();
        LocalTime fim = agendamento.getHora_fim();

        while (!hora.isAfter(fim.minusHours(1))) {
            AgendamentoEntity novo = new AgendamentoEntity();
            novo.setData_agendamento(agendamento.getData_agendamento());
            novo.setHora_inicio(hora);
            novo.setHora_fim(hora.plusHours(1));
            novo.setStatus("DISPONIVEL");
            novo.setMedico(agendamento.getMedico());

            agendamentoRepository.salvar(novo);
            hora = hora.plusHours(1);
        }

        return "redirect:/consultorio/agendamento";
    }

    @GetMapping("/agendar/{id}")
    public String agendarConsulta(@PathVariable Long id, Model model) {
        AgendamentoEntity agendamento = agendamentoRepository.findById(id);

        if (agendamento == null) {
            return "redirect:/consultorio/agendamento"; // ou página de erro
        }

        ConsultaEntity consulta = new ConsultaEntity();
        consulta.setData(agendamento.getData_agendamento()); // Define a data vinda do agendamento
        consulta.setMedico(agendamento.getMedico()); // Define o médico do agendamento
        consulta.setAgendamento(agendamento); // Adiciona o agendamento à consulta

        model.addAttribute("consulta", consulta);
        model.addAttribute("pacientes", pacienteRepository.pacientes());
        model.addAttribute("agendamento", true);
        model.addAttribute("medicos", List.of(agendamento.getMedico())); // evita mostrar todos os médicos

        return "disponibilidadeForm";
    }


}
