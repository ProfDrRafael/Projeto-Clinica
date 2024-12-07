package Persistencia.Dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.query.sql.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JPAUtil {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("org.hibernate.clinicapsicologia.jpa");
    private static final Logger logger = LoggerFactory.getLogger(OrientadorDAO.class);
    protected EntityManager em;

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void fecharEntityManagerFactory() {
        if (emf.isOpen()) {
            emf.close();
        }
    }
    
    public static List<Object[]> buscarTabelaAnonima(String queryTable) {
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
}
