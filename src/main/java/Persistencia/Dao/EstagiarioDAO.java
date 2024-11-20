package Persistencia.Dao;

import Persistencia.Entity.Estagiario;
import jakarta.persistence.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EstagiarioDAO extends GenericoDAO<Estagiario> {

    private static final Logger logger = LoggerFactory.getLogger(EstagiarioDAO.class);

    public EstagiarioDAO() {
        super(Estagiario.class);
    }

    // Método para buscar Estagiario por email
    public Estagiario buscarPorEmail(String email) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        Estagiario estagiario = null;
        try {
            TypedQuery<Estagiario> query = entityManager.createQuery(
                    "SELECT e FROM Estagiario e WHERE e.email = :email", Estagiario.class);
            query.setParameter("email", email);
            estagiario = query.getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Estagiario não encontrado com o email: {}", email);
        } catch (Exception e) {
            logger.error("Erro ao buscar Estagiario por email: ", e);
        } finally {
            entityManager.close();
        }
        return estagiario;
    }

    // Método para buscar Estagiario por email com o Orientador associado
    public Estagiario buscarPorEmailComOrientador(String email) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        Estagiario estagiario = null;
        try {
            TypedQuery<Estagiario> query = entityManager.createQuery(
                    "SELECT e FROM Estagiario e JOIN FETCH e.orientador WHERE e.email = :email", Estagiario.class);
            query.setParameter("email", email);
            estagiario = query.getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Estagiario não encontrado com o email: {}", email);
        } catch (Exception e) {
            logger.error("Erro ao buscar Estagiario por email com orientador: ", e);
        } finally {
            entityManager.close();
        }
        return estagiario;
    }

    // Método para buscar Estagiario por nome
    public Estagiario buscarPorNome(String nome) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        Estagiario estagiario = null;
        try {
            TypedQuery<Estagiario> query = entityManager.createQuery(
                    "SELECT e FROM Estagiario e WHERE e.nome = :nome", Estagiario.class);
            query.setParameter("nome", nome);
            estagiario = query.getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Estagiario não encontrado com o nome: {}", nome);
        } catch (Exception e) {
            logger.error("Erro ao buscar Estagiario por nome: ", e);
        } finally {
            entityManager.close();
        }
        return estagiario;
    }
    public List<Estagiario> buscarTodosEstagiarios() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Estagiario> estagiarios = null;
        try {
            TypedQuery<Estagiario> query = em.createQuery(
                    "SELECT o FROM Estagiario o", Estagiario.class);
            estagiarios = query.getResultList();  
        } catch (Exception e) {
            logger.error("Erro ao buscar orientadores: ", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return estagiarios;
    }
}