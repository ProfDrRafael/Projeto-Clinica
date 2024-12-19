/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.Dao;

import Persistencia.Entity.Paciente;
import VO.PacienteVO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author john
 */
public class PacienteDAO extends GenericoDAO<Paciente> {
    private static final Logger logger = LoggerFactory.getLogger(GenericoDAO.class);
    private Paciente pacienteDAO;

    public PacienteDAO() {
        super(Paciente.class);
    }

    public void salvarPaciente(Paciente entity) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = JPAUtil.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            em.persist(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            logger.error("Erro ao salvar a paciente: ", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public void atualizarPaciente(Paciente entity) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = JPAUtil.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            em.merge(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            logger.error("Erro ao atualizar a entidade: ", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public Paciente buscarPorId(Object id) {
        EntityManager em = null;
        Paciente entity = null;
        try {
            em = JPAUtil.getEntityManager();
            entity = em.find(entityClass, id);
        } catch (Exception e) {
            logger.error("Erro ao buscar o Paciente por ID: ", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return entity;
    }

    /**
     * Busca pacientes pelo nome.
     *
     * @param nome Nome ou parte do nome do paciente a ser buscado.
     * @return Lista de PacienteVO correspondentes ao nome.
     */
    public List<PacienteVO> buscarPorNome(String nome) {
        EntityManager em = null;
        List<PacienteVO> pacientesVO = new ArrayList<>();

        try {
            em = JPAUtil.getEntityManager();
            TypedQuery<Paciente> query = em.createQuery(
                    "SELECT p FROM Paciente p WHERE LOWER(p.nome) LIKE LOWER(:nome)", Paciente.class);
            query.setParameter("nome", "%" + nome + "%");

            List<Paciente> pacientes = query.getResultList();
            for (Paciente paciente : pacientes) {
                pacientesVO.add(PacienteVO.fromEntity(paciente));
            }
        } catch (Exception e) {
            logger.error("Erro ao buscar pacientes por nome: ", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        return pacientesVO;
    }

    /**
     * Busca pacientes pelo nome exato.
     *
     * @param nome Nome exato do paciente a ser buscado.
     * @return PacienteVO correspondente ao nome exato, ou null se não encontrado.
     */
    public PacienteVO buscarPorNomePreciso(String nome) {
        EntityManager em = null;
        PacienteVO pacienteVO = null;

        try {
            em = JPAUtil.getEntityManager();
            TypedQuery<Paciente> query = em.createQuery(
                    "SELECT p FROM Paciente p WHERE LOWER(p.nome) = LOWER(:nome)", Paciente.class);
            query.setParameter("nome", nome);

            // Obtém o resultado único
            Paciente paciente = query.getSingleResult();
            if (paciente != null) {
                pacienteVO = PacienteVO.fromEntity(paciente);
            }
        } catch (Exception e) {
            logger.error("Erro ao buscar paciente pelo nome exato: ", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        return pacienteVO;
    }

    public List<Paciente> buscarTodos() {
        EntityManager em = null;
        List<Paciente> resultados = null;
        try {
            em = JPAUtil.getEntityManager();
            TypedQuery<Paciente> query = em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e",
                    entityClass);
            resultados = query.getResultList();
        } catch (Exception e) {
            logger.error("Erro ao buscar todas os Pacientes: ", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return resultados;
    }

    public List<PacienteVO> buscarPorEstagiarioId(Integer estagiarioId) {
        List<PacienteVO> pacientesVO = new ArrayList<>();
        ProntuarioDAO prontuarioDAO = new ProntuarioDAO();

        List<Paciente> pacientes = prontuarioDAO.buscarPacientesPorEstagiarioId(estagiarioId);

        for (Paciente paciente : pacientes) {
            pacientesVO.add(PacienteVO.fromEntity(paciente));
        }
        return pacientesVO;
    }

    public void deletarPaciente(Paciente entity) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = JPAUtil.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            Paciente managedEntity = em.contains(entity) ? entity : em.merge(entity);
            em.remove(managedEntity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            logger.error("Erro ao deletar a entidade: ", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
