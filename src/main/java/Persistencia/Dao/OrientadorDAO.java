package Persistencia.Dao;

import Persistencia.Entity.Orientador;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class OrientadorDAO {
    private EntityManagerFactory entityManagerFactory;

    public OrientadorDAO() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
    }

    public void salvarOrientador(Orientador orientador) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(orientador);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void atualizarOrientador(Orientador orientador) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(orientador);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Orientador buscarPorEmail(String email) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Orientador> query = entityManager.createQuery(
                "SELECT o FROM Orientador o WHERE o.email = :email", Orientador.class);
        query.setParameter("email", email);

        Orientador orientador = null;
        try {
            orientador = query.getSingleResult();
        } catch (Exception e) {
            System.out.println("Orientador não encontrado com o email: " + email);
        } finally {
            entityManager.close();
        }
        return orientador;
    }

    public void fechar() {
        entityManagerFactory.close();
    }
}
