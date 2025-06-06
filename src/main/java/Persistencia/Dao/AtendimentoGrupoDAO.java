package Persistencia.Dao;

import Persistencia.Entity.AtendimentoGrupo;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

public class AtendimentoGrupoDAO extends GenericoDAO<AtendimentoGrupo> {
    private static final Logger logger = LoggerFactory.getLogger(AtendimentoGrupoDAO.class);

    public AtendimentoGrupoDAO() {
        super(AtendimentoGrupo.class);
    }

    public List<AtendimentoGrupo> buscarPorGrupo(Integer grupoId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT ag FROM AtendimentoGrupo ag WHERE ag.grupo.id = :grupoId", AtendimentoGrupo.class)
                    .setParameter("grupoId", grupoId)
                    .getResultList();
        } catch (Exception e) {
            logger.error("Erro ao buscar atendimentos de grupo por grupoId", e);
            return null;
        } finally {
            em.close();
        }
    }

    public List<AtendimentoGrupo> buscarPorData(LocalDate data) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT ag FROM AtendimentoGrupo ag WHERE ag.data = :data", AtendimentoGrupo.class)
                    .setParameter("data", data)
                    .getResultList();
        } catch (Exception e) {
            logger.error("Erro ao buscar atendimentos de grupo por data", e);
            return null;
        } finally {
            em.close();
        }
    }
}
