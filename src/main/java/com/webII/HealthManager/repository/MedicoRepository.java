package com.webII.HealthManager.repository;


import com.webII.HealthManager.model.ConsultaEntity;
import com.webII.HealthManager.model.MedicoEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class MedicoRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(MedicoEntity medico) {
        em.persist(medico);
    }

    public List<MedicoEntity> medicos() {
        return em.createQuery("from MedicoEntity", MedicoEntity.class).getResultList();
    }

    public MedicoEntity medico(Long id) {
        return em.find(MedicoEntity.class, id);
    }

    public void update(MedicoEntity medico) {
        if (medico.getId() != null && em.find(MedicoEntity.class, medico.getId()) != null) {
            em.merge(medico);
        } else {
            throw new IllegalArgumentException("Médico não encontrado para atualização");
        }
    }

    public void remove(Long id) {
        MedicoEntity m = medico(id);
        if (m != null) {
            em.remove(m);
        }
    }

    public List<ConsultaEntity> consultasPorMedico(Long idMedico) {
        MedicoEntity medico = medico(idMedico);
        return medico != null ? medico.getConsultas() : List.of();
    }

    public List<MedicoEntity> buscarPorNome(String nome) {
        return em.createQuery(
                        "FROM MedicoEntity m WHERE m.nome LIKE :nome",
                        MedicoEntity.class)
                .setParameter("nome", "%" + nome + "%")
                .getResultList();
    }
}
