/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.Dao;

import Persistencia.Entity.Paciente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public List<Paciente> buscarTodos() {
        EntityManager em = null;
        List<Paciente> resultados = null;
        try {
            em = JPAUtil.getEntityManager();
            TypedQuery<Paciente> query = em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass);
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
