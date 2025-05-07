package com.web2.HealthManager.model.repository;

import com.web2.HealthManager.model.entity.Paciente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class PacienteRepository  {

    @PersistenceContext
    private EntityManager em;

    // Lista de pacientes
    public List<Paciente> listarTodos() {
        return em.createQuery("SELECT p FROM Paciente p", Paciente.class)
                .getResultList();
    }

    // Buscar paciente por ID
    public Paciente paciente(Long id) {
        return em.find(Paciente.class, id);
    }

    // Salvar ou atualizar paciente
    public void save(Paciente paciente) {
        if (paciente.getId() == null) {
            em.persist(paciente);
        } else {
            em.merge(paciente);
        }
    }

    // Deletar paciente
    public void delete(Paciente paciente) {
        if (em.contains(paciente)) {
            em.remove(paciente);
        } else {
            em.remove(em.merge(paciente));
        }
    }


}
