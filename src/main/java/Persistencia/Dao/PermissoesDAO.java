/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.Dao;

import Persistencia.Entity.Permissao;
import Persistencia.Entity.Pesquisador;
import jakarta.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author john
 */
public class PermissoesDAO {
    private EntityManager em;

    public PermissoesDAO(EntityManager em) {
        this.em = em;
    }

    public void salvarPermissoes(Pesquisador pesquisador, HashMap<String, List<String>> recursosSelecionados) {
        for (Map.Entry<String, List<String>> recurso : recursosSelecionados.entrySet()) {
            for (String item : recurso.getValue()) {
                Permissao permissao = new Permissao();
                permissao.setPesquisador(pesquisador);
                permissao.setTipo(recurso.getKey()); 
                permissao.setRecurso(item);          
                permissao.setPermitido(true);

                em.persist(permissao);
            }
        }
    }


    public void removerPermissoesPorPesquisador(Pesquisador pesquisador) {
        em.createQuery("DELETE FROM Permissao p WHERE p.pesquisador = :pesq")
            .setParameter("pesq", pesquisador)
            .executeUpdate();
    }

    public List<Permissao> buscarPermissoes(Pesquisador pesquisador) {
    return em.createQuery("SELECT p FROM Permissao p WHERE p.pesquisador = :pesq", Permissao.class)
             .setParameter("pesq", pesquisador)
             .getResultList();
}

}
