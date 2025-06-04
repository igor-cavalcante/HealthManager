package com.webII.HealthManager.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
@DiscriminatorValue("MEDICO")
public class MedicoEntity extends Pessoa {

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConsultaEntity> consultas;

    @NotBlank
    private String crm;

    public List<ConsultaEntity> getConsultas() { return consultas; }
    public void setConsultas(List<ConsultaEntity> consultas) { this.consultas = consultas; }

    public String getCrm() { return crm; }
    public void setCrm(String crm) { this.crm = crm; }
}
