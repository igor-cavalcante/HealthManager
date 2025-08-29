package com.webII.HealthManager.repository;


import com.webII.HealthManager.model.ConsultaEntity;
import com.webII.HealthManager.model.DisponibilidadeEntity;
import com.webII.HealthManager.model.MedicoEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class DisponibilidadeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<DisponibilidadeEntity> findDisponibilidadesAtivas() {
        return entityManager.createQuery("SELECT d FROM DisponibilidadeEntity d WHERE d.status = 'DISPONIVEL'", DisponibilidadeEntity.class).getResultList();
    }

    public void save(DisponibilidadeEntity disponibilidade) {
        entityManager.persist(disponibilidade);
    }

}
