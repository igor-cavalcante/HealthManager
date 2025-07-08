package com.webII.HealthManager.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "agenda")
public class AgendamentoEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_agenda;

    @ManyToOne
    @JoinColumn(name = "id_medico", nullable = false)
    private MedicoEntity medico;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate data_agendamento;

    private LocalTime hora_inicio;

    private LocalTime hora_fim;

    private String status;


    public Long getId_agendamento() {
        return id_agenda;
    }

    public void setId_agendamento(Long id_agenda) {
        this.id_agenda = id_agenda;
    }

    public MedicoEntity getMedico() { return medico; }

    public void setMedico(MedicoEntity medico) { this.medico = medico; }

    public LocalDate getData_agendamento() {
        return data_agendamento;
    }

    public void setData_agendamento(LocalDate data_agendamento) {
        this.data_agendamento = data_agendamento;
    }

    public LocalTime getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(LocalTime hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public LocalTime getHora_fim() {
        return hora_fim;
    }

    public void setHora_fim(LocalTime hora_fim) {
        this.hora_fim = hora_fim;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
