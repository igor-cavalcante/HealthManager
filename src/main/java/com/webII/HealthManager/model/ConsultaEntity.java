package com.webII.HealthManager.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "consulta")
public class ConsultaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "disponibilidade_id", nullable = false, unique = true)
    private DisponibilidadeEntity disponibilidade;

    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false)
    private PacienteEntity paciente;

    @ManyToOne
    @JoinColumn(name = "id_medico", nullable = false)
    private MedicoEntity medico;

    private Double valor;

    private String observacao;

    private  String Status;


    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }

    public PacienteEntity getPaciente() { return paciente; }
    public void setPaciente(PacienteEntity paciente) { this.paciente = paciente; }

    public MedicoEntity getMedico() { return medico; }
    public void setMedico(MedicoEntity medico) { this.medico = medico; }

    public DisponibilidadeEntity getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(DisponibilidadeEntity disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
