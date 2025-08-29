package com.webII.HealthManager.repository;


import com.webII.HealthManager.model.ConsultaEntity;
import com.webII.HealthManager.model.DisponibilidadeEntity;
import com.webII.HealthManager.model.MedicoEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
@Transactional
public class DisponibilidadeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<DisponibilidadeEntity> findDisponibilidadesAtivas() {
        return entityManager.createQuery("SELECT d FROM DisponibilidadeEntity d WHERE d.status = 'DISPONIVEL'", DisponibilidadeEntity.class).getResultList();
    }


    public List<DisponibilidadeEntity> findDisponibilidadeAgendadas() {
        return entityManager.createQuery("SELECT d FROM DisponibilidadeEntity d WHERE d.status = 'AGENDADO'", DisponibilidadeEntity.class).getResultList();
    }

    public List<DisponibilidadeEntity> findByMedicoAndStatus(MedicoEntity medico, String status) {
        try {
            return entityManager.createQuery(
                            "SELECT d FROM DisponibilidadeEntity d WHERE d.medico = :medico AND d.status = :status",
                            DisponibilidadeEntity.class)
                    .setParameter("medico", medico)
                    .setParameter("status", status)
                    .getResultList();
        } catch (Exception e) {
            System.out.println("Erro ao buscar disponibilidades: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public DisponibilidadeEntity findById(Long id) {
        return entityManager.find(DisponibilidadeEntity.class, id);
    }

    public void save(DisponibilidadeEntity disponibilidade) {
        entityManager.persist(disponibilidade);
    }

}
