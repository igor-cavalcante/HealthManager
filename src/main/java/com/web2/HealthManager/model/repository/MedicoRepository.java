package com.web2.HealthManager.model.repository;

import com.web2.HealthManager.model.entity.Medico;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class MedicoRepository  {

    @PersistenceContext
    public EntityManager em;

    public List<Medico> listMedicos(){
        return em.createQuery("select m from Medico m", Medico.class).getResultList();
    };

    public Medico buscarPorId(Long id) {
        return em.find(Medico.class, id);
    }

    public void salvarOuAtualizar(Medico medico) {
        if (medico.getId() == null) {
            em.persist(medico);
        } else {
            em.merge(medico);
        }
    }

    public void excluir(Long id) {
        Medico medico = em.find(Medico.class, id);
        if (medico != null) {
            em.remove(medico);
        }
    }





}
