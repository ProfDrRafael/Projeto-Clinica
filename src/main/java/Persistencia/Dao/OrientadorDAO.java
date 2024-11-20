package Persistencia.Dao;

import Persistencia.Entity.Orientador;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrientadorDAO extends GenericoDAO<Orientador> {

    private static final Logger logger = LoggerFactory.getLogger(OrientadorDAO.class);

    public OrientadorDAO() {
        super(Orientador.class);
    }

    public Orientador buscarPorEmail(String email) {
        EntityManager em = JPAUtil.getEntityManager();
        Orientador orientador = null;
        try {
            TypedQuery<Orientador> query = em.createQuery(
                    "SELECT o FROM Orientador o WHERE o.email = :email", Orientador.class);
            query.setParameter("email", email);
            orientador = query.getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Orientador não encontrado com o email: {}", email);
        } catch (Exception e) {
            logger.error("Erro ao buscar Orientador por email: ", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return orientador;
    }

    public Orientador buscarPorNome(String nome) {
        EntityManager em = JPAUtil.getEntityManager();
        Orientador orientador = null;
        try {
            TypedQuery<Orientador> query = em.createQuery(
                    "SELECT o FROM Orientador o WHERE o.nome = :nome", Orientador.class);
            query.setParameter("nome", nome);
            orientador = query.getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Orientador não encontrado com o nome: {}", nome);
        } catch (Exception e) {
            logger.error("Erro ao buscar Orientador por nome: ", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return orientador;
    }
    
    public List<Orientador> buscarTodosOrientadores() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Orientador> orientadores = null;
        try {
            TypedQuery<Orientador> query = em.createQuery(
                    "SELECT o FROM Orientador o", Orientador.class);
            orientadores = query.getResultList(); 
        } catch (Exception e) {
            logger.error("Erro ao buscar orientadores: ", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return orientadores;
    }
}
