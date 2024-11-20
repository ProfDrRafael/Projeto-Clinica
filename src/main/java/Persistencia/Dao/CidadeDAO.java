/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.Dao;

import Persistencia.Entity.Cidade;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author john
 */
public class CidadeDAO extends GenericoDAO<Cidade> {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(GenericoDAO.class);
    private Cidade cidadeEntity;

    public CidadeDAO() {
        super(Cidade.class);
    }
    
    // Método para buscar Estagiario por nome
    public static Cidade buscarPorNome(String nome) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        Cidade cidade = null;
        try {
            TypedQuery<Cidade> query = entityManager.createQuery(
                    "SELECT e FROM Cidade e WHERE e.nome = :nome", Cidade.class);
            query.setParameter("nome", nome);
            cidade = query.getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Cidade não encontrado com o nome: {}", nome);
        } catch (Exception e) {
            logger.error("Erro ao buscar Cidade por nome: ", e);
        } finally {
            entityManager.close();
        }
        return cidade;
    }
}
