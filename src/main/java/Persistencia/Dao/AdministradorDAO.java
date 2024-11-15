package Persistencia.Dao;

import Persistencia.Entity.Administrador;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdministradorDAO extends GenericoDAO<Administrador> {

    private static final Logger logger = LoggerFactory.getLogger(AdministradorDAO.class);

    public AdministradorDAO() {
        super(Administrador.class);
    }

    public Administrador buscarPorEmail(String email) {
        EntityManager em = null;
        Administrador administrador = null;
        try {
            em = JPAUtil.getEntityManager();
            TypedQuery<Administrador> query = em.createQuery(
                    "SELECT a FROM Administrador a WHERE a.email = :email", Administrador.class);
            query.setParameter("email", email);
            administrador = query.getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Administrador não encontrado com o email: {}", email);
        } catch (Exception e) {
            logger.error("Erro ao buscar Administrador por email: ", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return administrador;
    }
}
