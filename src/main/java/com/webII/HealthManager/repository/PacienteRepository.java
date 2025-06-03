package com.webII.HealthManager.repository;


import com.webII.HealthManager.model.ConsultaEntity;
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

    public void update(PacienteEntity paciente) {
        if (paciente.getId() != null) {
            // Carrega a entidade existente do banco
            PacienteEntity pacienteExistente = em.find(PacienteEntity.class, paciente.getId());

            if (pacienteExistente != null) {
                // Atualiza os campos necessários
                pacienteExistente.setNome(paciente.getNome());
                pacienteExistente.setTelefone(paciente.getTelefone());
                em.merge(pacienteExistente);  // Faz o merge no objeto carregado
            } else {
                throw new IllegalArgumentException("Paciente não encontrado para atualização");
            }
        } else {
            throw new IllegalArgumentException("ID do paciente não pode ser nulo");
        }
    }

    public void remove (Long id){
        PacienteEntity p = paciente(id);
        if (p != null){
            em.remove(p);
        }
    }

    public List<PacienteEntity> SearchByName (String name){
        return em.createQuery("FROM PacienteEntity p WHERE p.nome like :nome", PacienteEntity.class)
                .setParameter("nome", "%" + name + "%")
                .getResultList();
    }

}
