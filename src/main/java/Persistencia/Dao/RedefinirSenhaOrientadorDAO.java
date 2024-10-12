package Persistencia.Dao;

import Persistencia.Entity.RedefinirSenhaOrientador;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class RedefinirSenhaOrientadorDAO {

    private EntityManagerFactory entityManagerFactory;

    public RedefinirSenhaOrientadorDAO() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
    }

    public void salvarToken(RedefinirSenhaOrientador token) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(token);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public RedefinirSenhaOrientador buscarPorToken(String token) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<RedefinirSenhaOrientador> query = entityManager.createQuery(
                "SELECT t FROM RedefinirSenhaOrientador t WHERE t.token = :token", RedefinirSenhaOrientador.class);
        query.setParameter("token", token);

        RedefinirSenhaOrientador resetToken = null;
        try {
            resetToken = query.getSingleResult();
        } catch (Exception e) {
            System.out.println("Token não encontrado: " + token);
        } finally {
            entityManager.close();
        }
        return resetToken;
    }

    public void deletarToken(RedefinirSenhaOrientador token) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        if (!entityManager.contains(token)) {
            token = entityManager.merge(token);
        }
        entityManager.remove(token);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void fechar() {
        entityManagerFactory.close();
    }
}
