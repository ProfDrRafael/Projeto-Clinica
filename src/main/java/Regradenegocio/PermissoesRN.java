package Regradenegocio;


import Persistencia.Dao.PermissoesDAO;
import Persistencia.Dao.JPAUtil;
import Persistencia.Entity.Pesquisador;
import jakarta.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author john
 */
public class PermissoesRN {
    public void salvarPermissoes(Pesquisador pesquisador, HashMap<String, java.util.List<String>> recursosSelecionados) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            PermissoesDAO permissaoDAO = new PermissoesDAO(em);
            permissaoDAO.removerPermissoesPorPesquisador(pesquisador); 
            permissaoDAO.salvarPermissoes(pesquisador, recursosSelecionados);

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
