package Persistencia.Dao;

import Persistencia.Entity.Responsavel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ResponsavelDAO extends GenericoDAO<Responsavel> {
    private static final Logger logger = LoggerFactory.getLogger(ResponsavelDAO.class);

    public ResponsavelDAO() {
        super(Responsavel.class);
    }

    /**
     * Busca um responsável pelo nome.
     *
     * @param nome Nome do responsável.
     * @return Objeto Responsavel encontrado, ou null se não encontrado.
     */
    public Responsavel buscarPorNome(String nome) {
        EntityManager em = JPAUtil.getEntityManager();
        Responsavel responsavel = null;
        try {
            TypedQuery<Responsavel> query = em.createQuery(
                    "SELECT r FROM Responsavel r WHERE r.nome = :nome", Responsavel.class);
            query.setParameter("nome", nome);
            responsavel = query.getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Responsável não encontrado com o nome: {}", nome);
        } catch (Exception e) {
            logger.error("Erro ao buscar responsável por nome: ", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return responsavel;
    }

    /**
     * Busca uma lista de responsáveis pelo gênero.
     *
     * @param genero Gênero dos responsáveis.
     * @return Lista de responsáveis com o gênero especificado.
     */
    public List<Responsavel> buscarPorGenero(String genero) {
        EntityManager em = JPAUtil.getEntityManager();
        List<Responsavel> responsaveis = null;
        try {
            TypedQuery<Responsavel> query = em.createQuery(
                    "SELECT r FROM Responsavel r WHERE r.genero = :genero", Responsavel.class);
            query.setParameter("genero", genero);
            responsaveis = query.getResultList();
        } catch (Exception e) {
            logger.error("Erro ao buscar responsáveis por gênero: ", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return responsaveis;
    }

    /**
     * Busca responsáveis por vínculo.
     *
     * @param vinculo Vínculo dos responsáveis (ex.: "Pai", "Mãe").
     * @return Lista de responsáveis com o vínculo especificado.
     */
    public List<Responsavel> buscarPorVinculo(String vinculo) {
        EntityManager em = JPAUtil.getEntityManager();
        List<Responsavel> responsaveis = null;
        try {
            TypedQuery<Responsavel> query = em.createQuery(
                    "SELECT r FROM Responsavel r WHERE r.vinculo = :vinculo", Responsavel.class);
            query.setParameter("vinculo", vinculo);
            responsaveis = query.getResultList();
        } catch (Exception e) {
            logger.error("Erro ao buscar responsáveis por vínculo: ", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return responsaveis;
    }

    /**
     * Lista todos os responsáveis cadastrados no banco de dados.
     *
     * @return Lista de todos os responsáveis.
     */
    public List<Responsavel> listarTodos() {
        return buscarTodos();
    }
}
