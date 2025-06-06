package Persistencia.Dao;

import Persistencia.Entity.GrupoXPaciente;
import jakarta.persistence.EntityManager;

import java.util.List;

public class GrupoXPacienteDAO extends GenericoDAO<GrupoXPaciente> {

    public GrupoXPacienteDAO() {
        super(GrupoXPaciente.class);
    }

    public List<GrupoXPaciente> listarPorGrupo(Integer grupoId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT gxp FROM GrupoXPaciente gxp WHERE gxp.grupo.id = :grupoId", GrupoXPaciente.class)
                    .setParameter("grupoId", grupoId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<GrupoXPaciente> listarPorPaciente(Integer pacienteId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT gxp FROM GrupoXPaciente gxp WHERE gxp.paciente.id = :pacienteId", GrupoXPaciente.class)
                    .setParameter("pacienteId", pacienteId)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
