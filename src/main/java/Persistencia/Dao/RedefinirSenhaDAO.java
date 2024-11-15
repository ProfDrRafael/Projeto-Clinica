package Persistencia.Dao;

import Persistencia.Entity.RedefinirSenha;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.NoResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedefinirSenhaDAO {

    private static final Logger logger = LoggerFactory.getLogger(RedefinirSenhaDAO.class);
    private Class<RedefinirSenha> entityType = RedefinirSenha.class;

    public RedefinirSenhaDAO() {
    }

    // Método para salvar o token de redefinição de senha
    public void salvarTokenRedefinicao(RedefinirSenha redefinirSenha) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(redefinirSenha);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            logger.error("Erro ao salvar token de redefinição de senha: ", e);
        } finally {
            if (entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    // Método para buscar por token
    public RedefinirSenha buscarPorToken(String token) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        RedefinirSenha redefinirSenha = null;
        try {
            TypedQuery<RedefinirSenha> query = entityManager.createQuery(
                    "SELECT r FROM " + entityType.getSimpleName() + " r WHERE r.token = :token", entityType);
            query.setParameter("token", token);
            redefinirSenha = query.getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Token não encontrado: {}", token);
        } catch (Exception e) {
            logger.error("Erro ao buscar redefinição de senha por token: ", e);
        } finally {
            if (entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return redefinirSenha;
    }

    // Método para deletar o token de redefinição de senha
    public void deletarToken(RedefinirSenha redefinirSenha) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            redefinirSenha = entityManager.merge(redefinirSenha);
            entityManager.remove(redefinirSenha);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            logger.error("Erro ao deletar token de redefinição de senha: ", e);
        } finally {
            if (entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }
}
