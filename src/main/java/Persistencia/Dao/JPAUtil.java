package Persistencia.Dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("org.hibernate.clinicapsicologia.jpa");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void fecharEntityManagerFactory() {
        if (emf.isOpen()) {
            emf.close();
        }
    }
}
