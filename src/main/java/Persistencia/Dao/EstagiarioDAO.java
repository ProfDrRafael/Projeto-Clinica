package Persistencia.Dao;

import Persistencia.Entity.Estagiario;
import jakarta.persistence.*;
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
}