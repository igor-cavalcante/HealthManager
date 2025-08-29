package com.webII.HealthManager.controller;

import com.webII.HealthManager.model.*;
import com.webII.HealthManager.repository.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/consultorio/consulta")
public class ConsultaController {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private DisponibilidadeRepository disponibilidadeRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaRepository consultaRepository;


    @GetMapping
    public String listarConsultas(Model model) {
        model.addAttribute("consultas", consultaRepository.consultas());
        return "consulta/consultaList";
    }

    @GetMapping("/nova")
    public String novaConsulta(Model model) {
        ConsultaEntity consulta = new ConsultaEntity();
        List<MedicoEntity> medicos = medicoRepository.medicos();
        List<PacienteEntity> pacientes = pacienteRepository.pacientes();
        model.addAttribute("consulta", consulta);
        model.addAttribute("medicos", medicos);
        model.addAttribute("pacientes", pacientes);
        return "consulta/consultaForm"; // Nome do template Thymeleaf
    }

    @GetMapping("/horarios-disponiveis/{medicoId}")
    @ResponseBody
    public List<Map<String, Object>> getHorariosDisponiveis(@PathVariable Long medicoId) {
        MedicoEntity medico = medicoRepository.medico(medicoId);
        if (medico == null) {
            return Collections.emptyList();
        }

        List<DisponibilidadeEntity> disponibilidades = disponibilidadeRepository.findByMedicoAndStatus(medico, "DISPONIVEL");

        // Converter para formato mais simples para o JSON
        return disponibilidades.stream().map(disp -> {
            Map<String, Object> horario = new HashMap<>();
            horario.put("id", disp.getId());
            horario.put("data", disp.getData().toString()); // Formato ISO: yyyy-MM-dd
            horario.put("hora_inicio", disp.getHora_inicio().toString());
            horario.put("hora_fim", disp.getHora_fim().toString());
            return horario;
        }).collect(Collectors.toList());
    }

    // Salvar consulta
    @PostMapping("/salvar")
    public String salvarConsulta(@ModelAttribute ConsultaEntity consulta,
                                 @RequestParam("disponibilidadeId") Long disponibilidadeId,
                                 @RequestParam("paciente.id") Long pacienteId,
                                 Model model) {

        try {
            // 1. Buscar a disponibilidade
            DisponibilidadeEntity disponibilidade = disponibilidadeRepository.findById(disponibilidadeId);
            if (disponibilidade == null || !"DISPONIVEL".equals(disponibilidade.getStatus())) {
                model.addAttribute("erro", "Horário não disponível");
                model.addAttribute("medicos", medicoRepository.medicos());
                model.addAttribute("pacientes", pacienteRepository.pacientes());
                return "consulta/consultaForm";
            }

            // 2. Buscar o paciente
            PacienteEntity paciente = pacienteRepository.paciente(pacienteId);
            if (paciente == null) {
                model.addAttribute("erro", "Paciente não encontrado");
                model.addAttribute("medicos", medicoRepository.medicos());
                model.addAttribute("pacientes", pacienteRepository.pacientes());
                return "consulta/consultaForm";
            }

            // 3. Configurar a consulta
            consulta.setDisponibilidade(disponibilidade);
            consulta.setMedico(disponibilidade.getMedico());
            consulta.setPaciente(paciente);
            consulta.setStatus("AGENDADA");

            // 4. Salvar consulta
            consultaRepository.save(consulta);

            // 5. Atualizar status da disponibilidade
            disponibilidade.setStatus("AGENDADO");
            disponibilidadeRepository.save(disponibilidade);

            return "redirect:/consultorio/consulta";

        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao salvar consulta: " + e.getMessage());
            model.addAttribute("medicos", medicoRepository.medicos());
            model.addAttribute("pacientes", pacienteRepository.pacientes());
            return "consulta/consultaForm";
        }
    }


//    @PostMapping("/salvar")
//    public String salvarConsulta(@Valid @ModelAttribute("consulta") ConsultaEntity consulta, BindingResult result,Model model) {
//        // Verifica se o médico existe
//
//        MedicoEntity medico = medicoRepository.medico(consulta.getMedico().getId());
//        if (medico == null) {
//            // Retorna um erro ou redireciona caso o médico não seja encontrado
//            return "redirect:/erro?message=Médico não encontrado";
//       }
//        consulta.setMedico(medico);
//
//        // Verifica se o paciente existe
//        PacienteEntity paciente = pacienteRepository.paciente(consulta.getPaciente().getId());
//        if (paciente == null) {
//            // Retorna um erro ou redireciona caso o paciente não seja encontrado
//            return "redirect:/erro?message=Paciente não encontrado";
//        }
//
//        if (consulta.getAgendamento() != null && consulta.getAgendamento().getId_agendamento() != null) {
//            AgendamentoEntity agendamento = AgendamentoRepository.findById(consulta.getAgendamento().getId_agendamento());
//            if (agendamento == null) {
//                return "redirect:/erro?message=Agendamento não encontrado";
//            }
//            consulta.setAgendamento(agendamento);
//
//            // Você pode querer atualizar o status do agendamento para "ocupado" ou similar
//            agendamento.setStatus("AGENDADO"); // ou outro status que você preferir
//            AgendamentoRepository.salvar(agendamento);
//        }
//
//        if (result.hasErrors()) {
//            List<MedicoEntity> medicos = medicoRepository.medicos();
//            List<PacienteEntity> pacientes = pacienteRepository.pacientes();
//            model.addAttribute("medicos", medicos);
//            model.addAttribute("pacientes", pacientes);
//            return "consulta/consultaForm";
//        }
//
//        consultaRepository.save(consulta);
//        return "redirect:/consultorio/consulta";
//    }

    @GetMapping("/editar/{id}")
    public String editarConsulta(@PathVariable Long id, Model model) {
        // Busca a consulta pelo id
        ConsultaEntity consulta = consultaRepository.consulta(id);

        // Se não encontrar, redireciona para a lista
        if (consulta == null) {
            return "redirect:/consultorio/consulta";
        }

        // Busca médicos e pacientes
        List<MedicoEntity> medicos = medicoRepository.medicos();
        List<PacienteEntity> pacientes = pacienteRepository.pacientes();

        model.addAttribute("consulta", consulta);
        model.addAttribute("medicos", medicos);
        model.addAttribute("pacientes", pacientes);

        return "consulta/consultaForm"; // O mesmo formulário para edição
    }

    @PostMapping("/atualizar/{id}")
    public String atualizarConsulta(@PathVariable Long id, @Valid @ModelAttribute("consulta") ConsultaEntity consulta, BindingResult result,Model model) {
        if(result.hasErrors()) {
            List<MedicoEntity> medicos = medicoRepository.medicos();
            List<PacienteEntity> pacientes = pacienteRepository.pacientes();
            model.addAttribute("medicos", medicos);
            model.addAttribute("pacientes", pacientes);
            return "consulta/consultaForm";
        }

        // Busca a consulta pelo id
        ConsultaEntity consultaExistente = consultaRepository.consulta(id);

        // Se não encontrar, redireciona para a lista
        if (consultaExistente == null) {
            return "redirect:/consultorio/consulta";
        }

        // Verifica se o médico existe
        MedicoEntity medico = medicoRepository.medico(consulta.getMedico().getId());
        if (medico == null) {
            return "redirect:/erro?message=Médico não encontrado";
        }

        // Verifica se o paciente existe
        PacienteEntity paciente = pacienteRepository.paciente(consulta.getPaciente().getId());
        if (paciente == null) {
            return "redirect:/erro?message=Paciente não encontrado";
        }

        // Atribui os objetos recuperados à consulta existente
        //consultaExistente.setData(consulta.getData());
        consultaExistente.setValor(consulta.getValor());
        consultaExistente.setObservacao(consulta.getObservacao());
        consultaExistente.setMedico(medico);
        consultaExistente.setPaciente(paciente);

        // Atualiza a consulta existente
        consultaRepository.update(consultaExistente);

        return "redirect:/consultorio/consulta";
    }

    @GetMapping("/deletar/{id}")
    public String deletarConsulta(@PathVariable Long id) {
        consultaRepository.remove(id);
        return "redirect:/consultorio/consulta";
    }


    @GetMapping("/search")
    public String searchConsultas(
            @RequestParam(required = false) String paciente,
            @RequestParam(required = false) String medico,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data,
            Model model) {

        if (paciente != null && !paciente.isEmpty()) {
            model.addAttribute("consultas", consultaRepository.buscarPorPacienteNome(paciente));
        }
        if (medico != null && !medico.isEmpty()) {
            model.addAttribute("consultas", consultaRepository.buscarPorMedicoNome(medico));
        }
        if (data != null) {
            model.addAttribute("consultas", consultaRepository.buscarPorData(data));
        }
        if ((paciente == null || paciente.isEmpty()) &&
                (medico == null || medico.isEmpty()) &&
                data == null) {
            model.addAttribute("consultas", consultaRepository.consultas());
        }

        return "consulta/consultaList";
    }

}
