package com.webII.HealthManager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@DiscriminatorValue("PACIENTE")
public class PacienteEntity extends Pessoa {

    @NotBlank(message = "O telefone é obrigatório")
    @Size(min = 14, max = 15, message = "Telefone deve ter entre 14 e 15 caracteres")
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
