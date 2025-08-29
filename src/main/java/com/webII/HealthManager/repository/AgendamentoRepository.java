package com.webII.HealthManager.repository;

import com.webII.HealthManager.model.AgendamentoEntity;
import com.webII.HealthManager.model.MedicoEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class AgendamentoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<AgendamentoEntity> listarTodos() {
        return entityManager.createQuery("SELECT a FROM AgendamentoEntity a JOIN FETCH a.medico", AgendamentoEntity.class)
                .getResultList();
    }


    public void salvar(AgendamentoEntity agendamento) {
        entityManager.persist(agendamento);
    }

    public List<MedicoEntity> buscarPorNome(String nome) {
        return entityManager.createQuery(
                        "SELECT m FROM MedicoEntity m WHERE m.nome LIKE :nome",
                        MedicoEntity.class)
                .setParameter("nome", "%" + nome + "%")
                .getResultList();
    }

    public AgendamentoEntity findById(Long id) {
        return entityManager.find(AgendamentoEntity.class, id);
    }

}
