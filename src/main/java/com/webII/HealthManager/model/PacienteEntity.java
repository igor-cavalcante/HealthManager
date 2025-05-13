package com.webII.HealthManager.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "paciente")
public class PacienteEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_paciente;
    private String nome;
    private  String telefone;


    public Long getId() {
        return id_paciente;
    }
    public void setId(Long id) {
        this.id_paciente = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }



}
