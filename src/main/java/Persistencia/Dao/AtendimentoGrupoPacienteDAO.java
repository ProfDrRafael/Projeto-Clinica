package Persistencia.Dao;

import Persistencia.Entity.AtendimentoGrupoPaciente;
import jakarta.persistence.EntityManager;

import java.util.List;

public class AtendimentoGrupoPacienteDAO extends GenericoDAO<AtendimentoGrupoPaciente> {

    public AtendimentoGrupoPacienteDAO() {
        super(AtendimentoGrupoPaciente.class);
    }

    public List<AtendimentoGrupoPaciente> listarPorAtendimento(Integer atendimentoGrupoId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT agp FROM AtendimentoGrupoPaciente agp WHERE agp.atendimentoGrupo.id = :id", AtendimentoGrupoPaciente.class)
                    .setParameter("id", atendimentoGrupoId)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
