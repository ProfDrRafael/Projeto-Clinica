/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.Dao;

import Persistencia.Entity.Pesquisador;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author john
 */
public class PesquisadorDAO extends GenericoDAO<Pesquisador> {

    private static final Logger logger = LoggerFactory.getLogger(PesquisadorDAO.class);

    public PesquisadorDAO() {
        super(Pesquisador.class);
    }

    public Pesquisador buscarPorEmail(String email) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        Pesquisador pesquisador = null;
        try {
            TypedQuery<Pesquisador> query = entityManager.createQuery(
                    "SELECT s FROM Pesquisador s WHERE s.email = :email", Pesquisador.class);
            query.setParameter("email", email);
            pesquisador = query.getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Pesquisador não encontrada com o email: {}", email);
        } catch (Exception e) {
            logger.error("Erro ao buscar Pesquisador por email: ", e);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return pesquisador;
    }
}
