/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VO;

import Persistencia.Entity.Estagiario;

/**
 *
 * @author rafael
 */
public class EstagiarioVO extends UsuarioVO {
    private Boolean ativo;
    private Integer ano;
    private String semestreFim;
    private Integer anoFim;
    private OrientadorVO orientadorVO;

    public EstagiarioVO(int id, String nomeCompleto, String email, String senha, Integer ano) {
        super(id, nomeCompleto, email, senha);
        this.ano = ano;
        this.ativo = true;
    }

    public EstagiarioVO(int id, String nomeCompleto, String email, String senha, Boolean ativo, Integer ano, String semestreFim, Integer anoFim, OrientadorVO orientadorVO) {
        super(id, nomeCompleto, email, senha);
        this.ativo = ativo;
        this.ano = ano;
        this.semestreFim = semestreFim;
        this.anoFim = anoFim;
        this.orientadorVO = orientadorVO;
    }

    @Override
    public String getTipo() {
        return "Estagiario";
    }

    /**
     * @return the ativo
     */
    public Boolean getAtivo() {
        return ativo;
    }

    /**
     * @param ativo the ativo to set
     */
    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    /**
     * @return the ano
     */
    public Integer getAno() {
        return ano;
    }

    /**
     * @param ano the ano to set
     */
    public void setAno(Integer ano) {
        this.ano = ano;
    }

    /**
     * @return the semestreFim
     */
    public String getSemestreFim() {
        return semestreFim;
    }

    /**
     * @param semestreFim the semestreFim to set
     */
    public void setSemestreFim(String semestreFim) {
        this.semestreFim = semestreFim;
    }

    /**
     * @return the anoFim
     */
    public Integer getAnoFim() {
        return anoFim;
    }

    /**
     * @param anoFim the anoFim to set
     */
    public void setAnoFim(Integer anoFim) {
        this.anoFim = anoFim;
    }

    /**
     * @return the orientadorVO
     */
    public OrientadorVO getOrientadorVO() {
        return orientadorVO;
    }

    /**
     * @param orientadorVO the orientadorVO to set
     */
    public void setOrientadorVO(OrientadorVO orientadorVO) {
        this.orientadorVO = orientadorVO;
    }

    public static EstagiarioVO fromEntity(Estagiario estagiario) {
        return new EstagiarioVO(
                estagiario.getId(),
                estagiario.getNome(),
                estagiario.getEmail(),
                null,
                estagiario.getAtivo(),
                estagiario.getAno(),
                estagiario.getSemestreFim(),
                estagiario.getAnoFim(),
                OrientadorVO.fromEntity(estagiario.getOrientador())
        );
    }
}
