package Persistencia.Dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import static Persistencia.Dao.JPAUtil.getEntityManager;
import Persistencia.Entity.Agenda;

import java.util.List;

public class AgendaDAO extends GenericoDAO<Agenda> {

    public AgendaDAO() {
        super(Agenda.class);
    }

    @Override
    public void salvar(Agenda agenda) {
        EntityManager em = getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            if (agenda.getId() == null || agenda.getId() == 0) {
                em.persist(agenda); // Insere nova entidade
            } else {
                em.merge(agenda); // Atualiza entidade existente
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Busca uma lista de agendas por um atributo específico (exemplo: ID do paciente).
     *
     * @param pacienteId ID do paciente.
     * @return Lista de agendas associadas ao paciente.
     */
    public List<Agenda> buscarPorPacienteId(int pacienteId) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT a FROM Agenda a WHERE a.paciente.id = :pacienteId", Agenda.class)
                    .setParameter("pacienteId", pacienteId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Busca uma lista de agendas por estagiário.
     *
     * @param estagiarioId ID do estagiário.
     * @return Lista de agendas associadas ao estagiário.
     */
    public List<Agenda> buscarPorEstagiarioId(int estagiarioId) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT a FROM Agenda a WHERE a.estagiario.id = :estagiarioId", Agenda.class)
                    .setParameter("estagiarioId", estagiarioId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Remove uma agenda por ID.
     *
     * @param id ID da agenda a ser removida.
     */
    public void removerPorId(int id) {
        EntityManager em = getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            Agenda agenda = em.find(Agenda.class, id);
            if (agenda != null) {
                em.remove(agenda);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}
