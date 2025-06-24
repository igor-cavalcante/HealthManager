package com.webII.HealthManager.controller;

import com.webII.HealthManager.model.ConsultaEntity;
import com.webII.HealthManager.model.MedicoEntity;
import com.webII.HealthManager.model.PacienteEntity;
import com.webII.HealthManager.repository.ConsultaRepository;
import com.webII.HealthManager.repository.MedicoRepository;
import com.webII.HealthManager.repository.PacienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@Controller
@RequestMapping("/consultorio/consulta")
public class ConsultaController {

    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;

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

    @PostMapping("/salvar")
    public String salvarConsulta(@Valid @ModelAttribute("consulta") ConsultaEntity consulta, BindingResult result,Model model) {
        // Verifica se o médico existe
        MedicoEntity medico = medicoRepository.medico(consulta.getMedico().getId());
        if (medico == null) {
            // Retorna um erro ou redireciona caso o médico não seja encontrado
            return "redirect:/erro?message=Médico não encontrado";
        }

        // Verifica se o paciente existe
        PacienteEntity paciente = pacienteRepository.paciente(consulta.getPaciente().getId());
        if (paciente == null) {
            // Retorna um erro ou redireciona caso o paciente não seja encontrado
            return "redirect:/erro?message=Paciente não encontrado";
        }

        // Atribui os objetos recuperados à consulta
        consulta.setMedico(medico);
        consulta.setPaciente(paciente);

        if(result.hasErrors()) {
            List<MedicoEntity> medicos = medicoRepository.medicos();
            List<PacienteEntity> pacientes = pacienteRepository.pacientes();
            model.addAttribute("medicos", medicos);
            model.addAttribute("pacientes", pacientes);
            return "consulta/consultaForm";
        }

        // Salva a consulta
        consultaRepository.save(consulta);

        // Redireciona para a lista de consultas após o salvamento
        return "redirect:/consultorio/consulta";
    }

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
        consultaExistente.setData(consulta.getData());
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
