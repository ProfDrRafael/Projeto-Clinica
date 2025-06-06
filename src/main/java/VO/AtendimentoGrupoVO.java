/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VO;

import java.time.LocalDate;
import java.time.LocalTime;

import Persistencia.Entity.AtendimentoGrupo;
import Persistencia.Entity.Estagiario;
import Persistencia.Entity.Grupo;
import Persistencia.Entity.Orientador;

/**
 *
 * @author otnie
 */
public class AtendimentoGrupoVO {
    private Integer id;
    private Integer grupoId;
    private LocalDate data;
    private LocalTime hora;
    private Integer estagiarioId;
    private Integer orientadorId;
    private String relato;
    private String observacoes;

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
     * @return the grupoId
     */
    public Integer getGrupoId() {
        return grupoId;
    }

    /**
     * @param grupoId the grupoId to set
     */
    public void setGrupoId(Integer grupoId) {
        this.grupoId = grupoId;
    }

    /**
     * @return the data
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(LocalDate data) {
        this.data = data;
    }

    /**
     * @return the hora
     */
    public LocalTime getHora() {
        return hora;
    }

    /**
     * @param hora the hora to set
     */
    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    /**
     * @return the estagiarioId
     */
    public Integer getEstagiarioId() {
        return estagiarioId;
    }

    /**
     * @param estagiarioId the estagiarioId to set
     */
    public void setEstagiarioId(Integer estagiarioId) {
        this.estagiarioId = estagiarioId;
    }

    /**
     * @return the orientadorId
     */
    public Integer getOrientadorId() {
        return orientadorId;
    }

    /**
     * @param orientadorId the orientadorId to set
     */
    public void setOrientadorId(Integer orientadorId) {
        this.orientadorId = orientadorId;
    }

    /**
     * @return the relato
     */
    public String getRelato() {
        return relato;
    }

    /**
     * @param relato the relato to set
     */
    public void setRelato(String relato) {
        this.relato = relato;
    }

    /**
     * @return the observacoes
     */
    public String getObservacoes() {
        return observacoes;
    }

    /**
     * @param observacoes the observacoes to set
     */
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public AtendimentoGrupo toEntity() {
        AtendimentoGrupo ag = new AtendimentoGrupo();
        ag.setId(this.id);

        Grupo grupo = new Grupo();
        grupo.setId(this.grupoId);
        ag.setGrupo(grupo);

        Estagiario estagiario = new Estagiario();
        estagiario.setId(this.estagiarioId);
        ag.setEstagiario(estagiario);

        Orientador orientador = new Orientador();
        orientador.setId(this.orientadorId);
        ag.setOrientador(orientador);

        ag.setData(this.data);
        ag.setHora(this.hora);
        ag.setRelato(this.relato);
        ag.setObservacoes(this.observacoes);
        return ag;
    }

    public static AtendimentoGrupoVO fromEntity(AtendimentoGrupo ag) {
        AtendimentoGrupoVO vo = new AtendimentoGrupoVO();
        vo.setId(ag.getId());
        vo.setGrupoId(ag.getGrupo().getId());
        vo.setEstagiarioId(ag.getEstagiario().getId());
        vo.setOrientadorId(ag.getOrientador().getId());
        vo.setData(ag.getData());
        vo.setHora(ag.getHora());
        vo.setRelato(ag.getRelato());
        vo.setObservacoes(ag.getObservacoes());
        return vo;
    }
}
