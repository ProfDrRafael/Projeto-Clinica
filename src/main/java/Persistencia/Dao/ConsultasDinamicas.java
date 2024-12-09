/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.Dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
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
    
    public static List<Object[]> deletarRegistroConsultaAnonima(List<Object> selectedIds, String queryTable) {
        EntityManager em = JPAUtil.getEntityManager();
        List<Object[]> results = null;
        try {
            // Cria a consulta nativa com a tabela dinâmica
            String queryStr = queryTable;
            Query query = em.createNativeQuery(queryStr);
            results = query.getResultList(); 
           
            
        } catch (Exception e) {
            logger.error("Erro ao deletar registros: ", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return results;
    }
}
