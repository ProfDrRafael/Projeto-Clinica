package Persistencia.Dao;

import Persistencia.Entity.Grupo;
import jakarta.persistence.EntityManager;
import java.util.List;

public class GrupoDAO extends GenericoDAO<Grupo> {

    public GrupoDAO() {
        super(Grupo.class);
    }

    public List<Grupo> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT g FROM Grupo g", Grupo.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Grupo buscarPorNome(String nome) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT g FROM Grupo g WHERE g.nome = :nome", Grupo.class)
                    .setParameter("nome", nome)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public List<Grupo> listarGruposDoEstagiario(int estagiarioId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT g FROM Grupo g JOIN g.estagiarios e WHERE e.id = :estagiarioId", Grupo.class)
                    .setParameter("estagiarioId", estagiarioId)
                    .getResultList();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Grupo> listarGruposDoOrientador(int orientadorId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT g FROM Grupo g JOIN g.orientadores o WHERE o.id = :orientadorId", Grupo.class)
                    .setParameter("orientadorId", orientadorId)
                    .getResultList();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
}
