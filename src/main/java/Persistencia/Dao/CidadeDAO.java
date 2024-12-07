/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.Dao;

import Persistencia.Entity.Cidade;
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
public class CidadeDAO extends GenericoDAO<Cidade> {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(GenericoDAO.class);
    private Cidade cidadeEntity;

    public CidadeDAO() {
        super(Cidade.class);
    }
    
    public static Cidade buscarPorNome(Integer id) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        Cidade cidade = null;
        try {
            TypedQuery<Cidade> query = entityManager.createQuery(
                    "SELECT e FROM Cidade e WHERE e.id = :id", Cidade.class);
            query.setParameter("id", id);
            cidade = query.getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Cidade não encontrado com o nome: {}", id);
        } catch (Exception e) {
            logger.error("Erro ao buscar Cidade por nome: ", e);
        } finally {
            entityManager.close();
        }
        return cidade;
    }
   
    public List<Cidade> buscarPorEstado(int estadoId) {
        EntityManager em = null;
        List<Cidade> resultados = null;
        try {
            em = JPAUtil.getEntityManager();

            TypedQuery<Cidade> query = em.createQuery("SELECT e FROM Cidade e WHERE e.estado.id = :estado_id", Cidade.class);
            query.setParameter("estado_id", estadoId);
            resultados = query.getResultList();

        } catch (Exception e) {
            logger.error("Erro ao buscar todas as Cidades para o Estado: ", e);

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return resultados;
    }
    
    
    public List<Cidade> buscarTodos() {
        EntityManager em = null;
        List<Cidade> resultados = null;
        try {
            em = JPAUtil.getEntityManager();

            TypedQuery<Cidade> query = em.createQuery("SELECT e FROM Cidade e", Cidade.class);
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
