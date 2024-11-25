package Persistencia.Dao;

import Persistencia.Entity.Sessao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static Persistencia.Dao.JPAUtil.getEntityManager;

public class SessaoDAO extends GenericoDAO<Sessao> {

    private static final Logger logger = LoggerFactory.getLogger(SessaoDAO.class);

    public SessaoDAO() {
        super(Sessao.class);
    }

    public Sessao buscarUltimaAdicionada() {
        EntityManager em = null;
        Sessao sessao = null;
        try {
            em = getEntityManager();
            TypedQuery<Sessao> query = em.createQuery(
                    "SELECT s FROM Sessao s ORDER BY s.id DESC", Sessao.class
            );
            query.setMaxResults(1); // Limita o resultado a apenas 1
            sessao = query.getSingleResult();
        } catch (Exception e) {
            logger.error("Erro ao buscar a última sessão adicionada: ", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return sessao;
    }
}