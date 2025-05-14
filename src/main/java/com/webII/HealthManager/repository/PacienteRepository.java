package com.webII.HealthManager.repository;


import com.webII.HealthManager.model.PacienteEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class PacienteRepository {


    @PersistenceContext
    private EntityManager em;


    public void save(PacienteEntity paciente){
        em.persist(paciente);
    }


    public List<PacienteEntity> pacientes() {
    return em.createQuery("from PacienteEntity", PacienteEntity.class).getResultList();
        };

    public PacienteEntity paciente(Long id_paciente) {
        return em.find(PacienteEntity.class,id_paciente);
    };

    public void update(PacienteEntity paciente){
        // Verifica se o ID está presente para evitar inserção
        if (paciente.getId() != null && em.find(PacienteEntity.class, paciente.getId()) != null) {
            em.merge(paciente);
        } else {
            throw new IllegalArgumentException("Paciente não encontrado para atualização");
        }
    }

    public void remove (Long id){
        PacienteEntity p = paciente(id);
        if (p != null){
            em.remove(p);
        }
    }
}
