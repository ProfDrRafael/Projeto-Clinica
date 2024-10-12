package Persistencia.Dao;

import Persistencia.Entity.Texto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class TextoDAO {

    private EntityManagerFactory entityManagerFactory;

    public TextoDAO() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
    }

    public void salvarTexto(String textoCifrado) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Texto textoEntity = new Texto();
        textoEntity.setTextoCifrado(textoCifrado);
        entityManager.persist(textoEntity);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<Texto> buscarTodosTextos() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Texto> textos = entityManager.createQuery("SELECT t FROM Texto t", Texto.class).getResultList();
        entityManager.close();
        System.out.println(textos);
        return textos;
    }

    public void fechar() {
        entityManagerFactory.close();
    }
}
