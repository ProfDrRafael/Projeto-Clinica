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
    private OrientadorVO orientadorVO;

    public EstagiarioVO(int id, String nomeCompleto, String email, String senha, Integer ano) {
        super(id, nomeCompleto, email, senha);
        this.ano = ano;
        this.ativo = true;
    }

    public EstagiarioVO(int id, String nomeCompleto, String email, String senha, Boolean ativo, Integer ano, String semestreFim, OrientadorVO orientadorVO) {
        super(id, nomeCompleto, email, senha);
        this.ativo = ativo;
        this.ano = ano;
        this.semestreFim = semestreFim;
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

    public static EstagiarioVO fromEntity(Estagiario entity) {
        return new EstagiarioVO(
                entity.getId(),
                entity.getNome(),
                entity.getEmail(),
                null, // Senha não é transferida
                entity.getAtivo(),
                entity.getAno(),
                entity.getSemestreFim(),
                entity.getOrientador() != null ? OrientadorVO.fromEntity(entity.getOrientador()) : null
        );
    }

    // Método de conversão de VO para Entity
    public Estagiario toEntity() {
        var estagiario = new Estagiario();
        estagiario.setId(this.getId());
        estagiario.setNome(this.getNomeCompleto());
        estagiario.setEmail(this.getEmail());
        estagiario.setSenha(this.getSenha());
        estagiario.setAtivo(this.getAtivo());
        estagiario.setAno(this.getAno());
        estagiario.setSemestreFim(this.getSemestreFim());
        if (this.getOrientadorVO() != null) {
            estagiario.setOrientador(this.getOrientadorVO().toEntity());
        }
        return estagiario;
    }

    // Método para atualizar uma entidade existente
    public void updateEntity(Estagiario entity) {
        entity.setNome(this.getNomeCompleto());
        entity.setEmail(this.getEmail());
        entity.setAtivo(this.getAtivo());
        entity.setAno(this.getAno());
        entity.setSemestreFim(this.getSemestreFim());
    }

    // Método de validação
    public boolean isValid() {
        return this.getNomeCompleto() != null && !this.getNomeCompleto().isEmpty() &&
                this.getEmail() != null && !this.getEmail().isEmpty();
    }
}
