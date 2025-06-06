/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VO;

import Persistencia.Entity.Permissao;
import Persistencia.Entity.Pesquisador;
import java.util.ArrayList;

/**
 *
 * @author john
 */
public class PermissoesVO {
    private Integer id;
    private Pesquisador pesquisador;
    private String tipo;
    private String recurso;
    private boolean permitido;
    
    public PermissoesVO(int id, Pesquisador pesquisadorId, String tipo, String recurso, boolean permitido) {
        this.id = id;
        this.pesquisador = pesquisadorId;
        this.tipo = tipo;
        this.recurso = recurso;
        this.permitido = permitido;
    }


    public static PermissoesVO fromEntity(Permissao permissao) {
        return new PermissoesVO(
                permissao.getId(),
                permissao.getPesquisador(),
                permissao.getTipo(),
                permissao.getRecurso(),
                permissao.getPermitido()
        );
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the pesquisador
     */
    public Pesquisador getPesquisador() {
        return pesquisador;
    }

    /**
     * @param pesquisador
     */
    public void setPesquisador(Pesquisador pesquisador) {
        this.pesquisador = pesquisador;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the recurso
     */
    public String getRecurso() {
        return recurso;
    }

    /**
     * @param recurso the recurso to set
     */
    public void setRecurso(String recurso) {
        this.recurso = recurso;
    }

    /**
     * @return the permitido
     */
    public boolean isPermitido() {
        return permitido;
    }

    /**
     * @param permitido the permitido to set
     */
    public void setPermitido(boolean permitido) {
        this.permitido = permitido;
    }
}
