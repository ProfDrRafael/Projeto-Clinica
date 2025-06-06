package Persistencia.Dao;

import Persistencia.Entity.Paciente;
import Persistencia.Entity.Prontuario;
import VO.ProntuarioEletronicoVO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static Persistencia.Dao.JPAUtil.getEntityManager;

public class ProntuarioDAO extends GenericoDAO<Prontuario> {
    private static final Logger logger = LoggerFactory.getLogger(ProntuarioDAO.class);
    public ProntuarioDAO() {
        super(Prontuario.class);
    }

    public Prontuario buscarPorPacienteId(Integer pacienteId) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Prontuario> query = em.createQuery(
                    "SELECT p FROM Prontuario p WHERE p.paciente.id = :pacienteId", Prontuario.class
            );
            query.setParameter("pacienteId", pacienteId);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    /**
     * Obtém um prontuário associado a um estagiário específico.
     *
     * @param estagiarioId ID do estagiário.
     * @return Prontuário associado ao estagiário, ou null se não encontrado.
     */
    public Prontuario buscarPorEstagiarioId(Integer estagiarioId) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Prontuario> query = em.createQuery(
                    "SELECT p FROM Prontuario p WHERE p.estagiario.id = :estagiarioId", Prontuario.class
            );
            query.setParameter("estagiarioId", estagiarioId);

            return query.getSingleResult();
        } catch (NoResultException e) {
            // Retorna null se nenhum prontuário for encontrado
            return null;
        } finally {
            em.close();
        }
    }

    /**
     * Lista todos os prontuários associados a um orientador específico.
     *
     * @param orientadorId ID do orientador.
     * @return Lista de prontuários.
     */
    public List<Prontuario> listarPorOrientadorId(Integer orientadorId) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Prontuario> query = em.createQuery(
                    "SELECT p FROM Prontuario p WHERE p.orientador.id = :orientadorId", Prontuario.class
            );
            query.setParameter("orientadorId", orientadorId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Lista todos os prontuários no sistema.
     *
     * @return Lista de todos os prontuários.
     */
    public List<Prontuario> listarTodos() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Prontuario> query = em.createQuery("SELECT p FROM Prontuario p", Prontuario.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Paciente> buscarPacientesPorEstagiarioId(Integer estagiarioId) {
        EntityManager em = getEntityManager();
        List<Paciente> pacientes = null;
        try {
            TypedQuery<Paciente> query = em.createQuery(
                    "SELECT DISTINCT p.paciente FROM Prontuario p WHERE p.estagiario.id = :estagiarioId", Paciente.class
            );
            query.setParameter("estagiarioId", estagiarioId);
            pacientes = query.getResultList();
        } catch (Exception e) {
            logger.error("Erro ao buscar pacientes por estagiário ID: ", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return pacientes;
    }

}
