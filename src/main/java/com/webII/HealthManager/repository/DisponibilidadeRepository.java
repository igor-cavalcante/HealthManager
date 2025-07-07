package com.webII.HealthManager.repository;


import com.webII.HealthManager.model.AgendamentoEntity;
import com.webII.HealthManager.model.DisponibilidadeEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class DisponibilidadeRepository {

    @PersistenceContext
    private EntityManager em;


    public List<DisponibilidadeEntity> listarTodos() {
        return em.createQuery("SELECT a FROM DisponibilidadeEntity a JOIN FETCH a.medico", DisponibilidadeEntity.class)
                .getResultList();
    }





}
