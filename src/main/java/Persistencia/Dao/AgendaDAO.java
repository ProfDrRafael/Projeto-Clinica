package Persistencia.Dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import static Persistencia.Dao.JPAUtil.getEntityManager;
import Persistencia.Entity.Agenda;
import VO.EstagiarioVO;
import VO.OrientadorVO;
import jakarta.persistence.TypedQuery;

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
     * @param estagiario
     * @return Lista de agendas associadas ao estagiário.
     */
    public List<Agenda> buscarPorEstagiario(EstagiarioVO estagiario) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT a FROM Agenda a WHERE a.estagiario.id = :estagiarioId", Agenda.class)
                    .setParameter("estagiarioId", estagiario.getId())
                    .getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Busca agendas dos alunos do orientador.
     * Ajuste a query conforme a estrutura do seu modelo.
     * @param orientadorId
     * @return 
     */
    public List<Agenda> buscarPorOrientador(OrientadorVO orientador) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                    "SELECT a FROM Agenda a WHERE a.estagiario.orientador.id = :orientadorId", Agenda.class)
                    .setParameter("orientadorId", orientador.getId())
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
    
    public List<Agenda> listarTodos() {
        EntityManager em;
        em = JPAUtil.getEntityManager();
        TypedQuery<Agenda> query = em.createQuery(
            "SELECT a FROM Agenda a ORDER BY hora", Agenda.class
        );
        return query.getResultList();
    }

    public List<Agenda> buscarPorPacienteETipoAtendimento(int pacienteId, String tipoAtendimento) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT a FROM Agenda a WHERE a.paciente.id = :pacienteId AND a.tipoAtendimento = :tipo", Agenda.class)
                    .setParameter("pacienteId", pacienteId)
                    .setParameter("tipo", tipoAtendimento)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
