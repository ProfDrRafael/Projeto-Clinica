package Persistencia.Dao;

import Persistencia.Entity.Atendimento;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

public class AtendimentoDAO extends GenericoDAO<Atendimento> {
    private static final Logger logger = LoggerFactory.getLogger(AtendimentoDAO.class);

    public AtendimentoDAO() {
        super(Atendimento.class);
    }

    @Override
    public void salvar(Atendimento atendimento) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (atendimento.getId() == null) {
                em.persist(atendimento); // Para novos registros
            } else {
                atendimento = em.merge(atendimento); // Para atualizar registros existentes
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao salvar atendimento: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }


    // Exemplo de método específico para buscar atendimentos por prontuário
    public List<Atendimento> buscarPorProntuario(Integer prontuarioId) {
        EntityManager em = null;
        List<Atendimento> resultados = null;
        try {
            em = JPAUtil.getEntityManager();
            resultados = em.createQuery("SELECT a FROM Atendimento a WHERE a.prontuario.id = :prontuarioId", Atendimento.class)
                    .setParameter("prontuarioId", prontuarioId)
                    .getResultList();
        } catch (Exception e) {
            logger.error("Erro ao buscar atendimentos por prontuário: ", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return resultados;
    }

    // Exemplo de método específico para buscar atendimentos por data
    public List<Atendimento> buscarPorData(LocalDate data) {
        EntityManager em = null;
        List<Atendimento> resultados = null;
        try {
            em = JPAUtil.getEntityManager();
            resultados = em.createQuery("SELECT a FROM Atendimento a WHERE a.data = :data", Atendimento.class)
                    .setParameter("data", data)
                    .getResultList();
        } catch (Exception e) {
            logger.error("Erro ao buscar atendimentos por data: ", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return resultados;
    }


}
