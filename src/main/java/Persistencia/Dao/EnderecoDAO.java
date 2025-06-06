/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.Dao;

import Persistencia.Entity.Endereco;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author john
 */
public class EnderecoDAO extends GenericoDAO<Endereco> {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(GenericoDAO.class);
    private Endereco enderecoDAO;

    public EnderecoDAO() {
        super(Endereco.class);
    }


    public void salvarEndereco(Endereco entity) {
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
            logger.error("Erro ao salvar a endereço: ", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public void atualizarEndereco(Endereco entity) {
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
            logger.error("Erro ao atualizar a endereço: ", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public Endereco buscarPorId(Object id) {
        EntityManager em = null;
        Endereco entity = null;
        try {
            em = JPAUtil.getEntityManager();
            entity = em.find(entityClass, id);
        } catch (Exception e) {
            logger.error("Erro ao buscar o Endereço por ID: ", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return entity;
    }

    public List<Endereco> buscarTodos() {
        EntityManager em = null;
        List<Endereco> resultados = null;
        try {
            em = JPAUtil.getEntityManager();
            TypedQuery<Endereco> query = em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass);
            resultados = query.getResultList();
        } catch (Exception e) {
            logger.error("Erro ao buscar todas os Endereços: ", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return resultados;
    }

    public void deletarEndereco(Endereco entity) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = JPAUtil.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            Endereco managedEntity = em.contains(entity) ? entity : em.merge(entity);
            em.remove(managedEntity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            logger.error("Erro ao deletar a endereço: ", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
