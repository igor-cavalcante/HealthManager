package com.webII.HealthManager.model;


import jakarta.persistence.*;

import java.util.List;

@Entity
@DiscriminatorValue("MEDICO")
public class MedicoEntity extends Pessoa {

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConsultaEntity> consultas;

    public List<ConsultaEntity> getConsultas() { return consultas; }
    public void setConsultas(List<ConsultaEntity> consultas) { this.consultas = consultas; }

    private String crm;

    public String getCrm() { return crm; }
    public void setCrm(String crm) { this.crm = crm; }
}
