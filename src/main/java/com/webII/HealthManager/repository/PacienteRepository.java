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

    public List<PacienteEntity> pacientes() {
    return em.createQuery("from PacienteEntity", PacienteEntity.class).getResultList();
        }
}
