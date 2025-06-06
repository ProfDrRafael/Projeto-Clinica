/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VO;

import Persistencia.Entity.Grupo;

/**
 *
 * @author otnie
 */
public class GrupoVO {
    private Integer id;
    private String nome;
    private String descricao;

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
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Grupo toEntity() {
        Grupo grupo = new Grupo();
        grupo.setId(this.id);
        grupo.setNome(this.nome);
        grupo.setDescricao(this.descricao);
        return grupo;
    }

    public static GrupoVO fromEntity(Grupo grupo) {
        GrupoVO vo = new GrupoVO();
        vo.setId(grupo.getId());
        vo.setNome(grupo.getNome());
        vo.setDescricao(grupo.getDescricao());
        return vo;
    }
    
    @Override
    public String toString() {
        return this.nome;
    }
}
