/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.Dao;

import Persistencia.Entity.Pais;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author john
 */
public class PaisDAO extends GenericoDAO<Pais>{
    private static final Logger logger = (Logger) LoggerFactory.getLogger(GenericoDAO.class);
    private Pais paiEntity;

    public PaisDAO() {
        super(Pais.class);
    }
    
    public static Pais buscarPorId(Integer id) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        Pais pais = null;
        try {
            TypedQuery<Pais> query = entityManager.createQuery(
                    "SELECT e FROM Pais e WHERE e.id = :id", Pais.class);
            query.setParameter("id", id);
            pais = query.getSingleResult();
        } catch (NoResultException e) {
            logger.warn("País não encontrado com o nome: {}", id);
        } catch (Exception e) {
            logger.error("Erro ao buscar País por nome: ", e);
        } finally {
            entityManager.close();
        }
        return pais;
    }
    
    public List<Pais> buscarTodos() {
        EntityManager em = null;
        List<Pais> resultados = null;
        try {
            em = JPAUtil.getEntityManager();

            TypedQuery<Pais> query = em.createQuery("SELECT e FROM Pais e", Pais.class);
            resultados = query.getResultList();
            
        } catch (Exception e) {
            logger.error("Erro ao buscar todas as entidades: ", e);
            
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return resultados;
    }
}
