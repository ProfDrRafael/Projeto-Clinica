package Persistencia.Dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
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
}
