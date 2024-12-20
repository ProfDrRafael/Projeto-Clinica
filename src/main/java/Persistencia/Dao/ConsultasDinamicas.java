/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.Dao;

import Persistencia.Entity.Administrador;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author john
 */
public class ConsultasDinamicas {
    private static final Logger logger = LoggerFactory.getLogger(OrientadorDAO.class);
    
    public static List<Object[]> buscarTabelaConsultaAnonima(String queryTable) {
        EntityManager em = JPAUtil.getEntityManager();
        List<Object[]> results = null;
        try {
            // Cria a consulta nativa com a tabela dinâmica
            String queryStr = queryTable;
            Query query = em.createNativeQuery(queryStr);
            results = query.getResultList(); 
           
            
        } catch (Exception e) {
            logger.error("Erro ao buscar dados da tabela: ", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return results;
    }
    
    public static boolean deletarRegistroConsultaAnonima(List<Object> selectedIds, String tableName) {
        EntityManager em = JPAUtil.getEntityManager();
    try {
        em.getTransaction().begin();

        for (Object id : selectedIds) {
            Query query = em.createQuery(
                "DELETE FROM " + tableName + " t WHERE t.id = :id"
            );
            query.setParameter("id", id);
            query.executeUpdate();
        }

        em.getTransaction().commit();
            
        } catch (Exception e) {
            logger.error("Erro ao deletar registros: ", e);
            return false;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return true;
    }
}
