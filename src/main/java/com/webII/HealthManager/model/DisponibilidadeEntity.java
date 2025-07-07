package com.webII.HealthManager.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "disponibilidade")
public class DisponibilidadeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_disponibilidade;

    @ManyToOne
    @JoinColumn(name = "id_medico", nullable = false)
    private MedicoEntity medico;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate data;

    private LocalTime hora_inicio;

    private LocalTime hora_fim;

    private String status;

    public Long getId_disponibilidade() {
        return id_disponibilidade;
    }

    public MedicoEntity getMedico() {
        return medico;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
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
