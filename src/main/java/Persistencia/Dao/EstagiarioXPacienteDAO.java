/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.Dao;

import VO.EstagiarioXPacienteVO;
import Persistencia.Entity.EstagiarioXPaciente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author otniel
 */
public class EstagiarioXPacienteDAO extends GenericoDAO<EstagiarioXPaciente> {
    public EstagiarioXPacienteDAO() {
        super(EstagiarioXPaciente.class);
    }

    public List<EstagiarioXPacienteVO> buscarPorEstagiario(Integer estagiarioId) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            TypedQuery<EstagiarioXPaciente> query = em.createQuery(
                "SELECT e FROM EstagiarioXPaciente e WHERE e.id.estagiarioId = :estagiarioId", 
                EstagiarioXPaciente.class
            );
            query.setParameter("estagiarioId", estagiarioId);
            return query.getResultList().stream()
                .map(EstagiarioXPacienteVO::fromEntity)
                .collect(Collectors.toList());
        }
    }

    public List<EstagiarioXPacienteVO> buscarPorPaciente(Integer pacienteId) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            TypedQuery<EstagiarioXPaciente> query = em.createQuery(
                "SELECT e FROM EstagiarioXPaciente e WHERE e.id.pacienteId = :pacienteId", 
                EstagiarioXPaciente.class
            );
            query.setParameter("pacienteId", pacienteId);
            return query.getResultList().stream()
                .map(EstagiarioXPacienteVO::fromEntity)
                .collect(Collectors.toList());
        }
    }
}
