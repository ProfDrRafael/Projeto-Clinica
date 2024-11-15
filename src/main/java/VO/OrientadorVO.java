/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VO;

import Persistencia.Entity.Orientador;

/**
 *
 * @author rafael
 */
public class OrientadorVO extends UsuarioVO {
    private Boolean ativo;
    private String linhaTeorica;

    public OrientadorVO(int id, String nomeCompleto, String email, String senha) {
        super(id, nomeCompleto, email, senha);
        this.ativo = true;
    }

    public OrientadorVO(int id, String nomeCompleto, String email, String senha, Boolean ativo, String linhaTeorica) {
        super(id, nomeCompleto, email, senha);
        this.ativo = ativo;
        this.linhaTeorica = linhaTeorica;
    }
    @Override
    public String getTipo() {
        return "Orientador";
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
     * @return the linhaTeorica
     */
    public String getLinhaTeorica() {
        return linhaTeorica;
    }

    /**
     * @param linhaTeorica the linhaTeorica to set
     */
    public void setLinhaTeorica(String linhaTeorica) {
        this.linhaTeorica = linhaTeorica;
    }

    public static OrientadorVO fromEntity(Orientador orientador) {
        return new OrientadorVO(
                orientador.getId(),
                orientador.getNome(),
                orientador.getEmail(),
                null,
                orientador.getAtivo(),
                orientador.getLinhaTeorica()
        );
    }
}
