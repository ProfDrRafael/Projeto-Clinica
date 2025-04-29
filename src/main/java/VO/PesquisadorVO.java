/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VO;

import Persistencia.Entity.Pesquisador;

/**
 *
 * @author john
 */
public class PesquisadorVO extends UsuarioVO {
    private Boolean ativo;
    
    public PesquisadorVO(int id, String nomeCompleto, String email, String senha) {
        super(id, nomeCompleto, email, senha);
        this.ativo = true;
    }

    @Override
    public String getTipo() {
        return "Pesquisador";
    }

    public static PesquisadorVO fromEntity(Pesquisador pesquisador) {
        return new PesquisadorVO(
                pesquisador.getId(),
                pesquisador.getNome(),
                pesquisador.getEmail(),
                null // senha é null
        );
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
}
