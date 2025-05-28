package com.webII.HealthManager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@DiscriminatorValue("PACIENTE")
public class PacienteEntity extends Pessoa {

    private String telefone;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConsultaEntity> consultas;

    public List<ConsultaEntity> getConsultas() { return consultas; }
    public void setConsultas(List<ConsultaEntity> consultas) { this.consultas = consultas; }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }


}
