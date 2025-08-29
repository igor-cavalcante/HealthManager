package com.webII.HealthManager.repository;


import com.webII.HealthManager.model.ConsultaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public class ConsultaRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(ConsultaEntity consulta) {
        if (consulta.getId() == null) {
            em.persist(consulta);
        } else {
            em.merge(consulta);
        }
    }

    public List<ConsultaEntity> consultas() {
        return em.createQuery("SELECT c FROM ConsultaEntity c JOIN FETCH c.medico", ConsultaEntity.class).getResultList();
    }
    
    public ConsultaEntity consulta(Long id) {
        return em.find(ConsultaEntity.class, id);
    }

    public void update(ConsultaEntity consulta) {
        em.merge(consulta);
    }

    public void remove(Long id) {
        ConsultaEntity c = consulta(id);
        if (c != null) em.remove(c);
    }


    //bucar os horarios disponiveis
    public List<ConsultaEntity> buscarHorarioMedico(Long id){
        return em.createQuery(
                "SELECT c FROM ConsultaEntity c JOIN c.agendamento p WHERE p.id_medico LIKE :id",
                ConsultaEntity.class)
                .setParameter("id","%" +id+"%")
                .getResultList();
    }

    // Novos métodos de busca:
    public List<ConsultaEntity> buscarPorPacienteNome(String nome) {
        return em.createQuery(
                        "SELECT c FROM ConsultaEntity c JOIN c.paciente p WHERE p.nome LIKE :nome",
                        ConsultaEntity.class)
                .setParameter("nome", "%" + nome + "%")
                .getResultList();
    }

    public List<ConsultaEntity> buscarPorMedicoNome(String nome) {
        return em.createQuery(
                        "SELECT c FROM ConsultaEntity c JOIN c.medico m WHERE m.nome LIKE :nome",
                        ConsultaEntity.class)
                .setParameter("nome", "%" + nome + "%")
                .getResultList();
    }

    public List<ConsultaEntity> buscarPorData(LocalDate data) {
        return em.createQuery(
                        "SELECT c FROM ConsultaEntity c WHERE DATE(c.data) = DATE(:data)",
                        ConsultaEntity.class)
                .setParameter("data", data.atStartOfDay())
                .getResultList();
    }


}
