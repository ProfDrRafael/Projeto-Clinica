package Persistencia.Dao;

import Persistencia.Entity.Secretaria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecretariaDAO extends GenericoDAO<Secretaria> {

    private static final Logger logger = LoggerFactory.getLogger(SecretariaDAO.class);

    public SecretariaDAO() {
        super(Secretaria.class);
    }

    public Secretaria buscarPorEmail(String email) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        Secretaria secretaria = null;
        try {
            TypedQuery<Secretaria> query = entityManager.createQuery(
                    "SELECT s FROM Secretaria s WHERE s.email = :email", Secretaria.class);
            query.setParameter("email", email);
            secretaria = query.getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Secretaria não encontrada com o email: {}", email);
        } catch (Exception e) {
            logger.error("Erro ao buscar Secretaria por email: ", e);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return secretaria;
    }
}
