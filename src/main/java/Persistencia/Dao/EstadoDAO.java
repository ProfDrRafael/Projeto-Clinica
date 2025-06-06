/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.Dao;

import Persistencia.Entity.Estado;
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
public class EstadoDAO extends GenericoDAO<Estado> {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(GenericoDAO.class);
    private EstadoDAO estadoEntity;

    public EstadoDAO() {
        super(Estado.class);
    }
    
    public static Estado buscarPorNome(String nome) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        Estado estado = null;
        try {
            TypedQuery<Estado> query = entityManager.createQuery(
                    "SELECT e FROM Estado e WHERE e.nome = :nome", Estado.class);
            query.setParameter("nome", nome);
            estado = query.getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Estado não encontrado com o nome: {}", nome);
        } catch (Exception e) {
            logger.error("Erro ao buscar Estado por nome: ", e);
        } finally {
            entityManager.close();
        }
        return estado;
    }
    
    @Override
    public List<Estado> buscarTodos() {
        EntityManager em = null;
        List<Estado> resultados = null;
        try {
            em = JPAUtil.getEntityManager();

            TypedQuery<Estado> query = em.createQuery("SELECT e FROM Estado e", Estado.class);
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
