/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VO;

import Persistencia.Entity.Secretaria;

/**
 *
 * @author rafael
 */
public class SecretariaVO extends UsuarioVO {

    public SecretariaVO(int id, String nomeCompleto, String email, String senha) {
        super(id, nomeCompleto, email, senha);
    }

    @Override
    public String getTipo() {
        return "Secretaria";
    }

    public static SecretariaVO fromEntity(Secretaria secretaria) {
        return new SecretariaVO(
                secretaria.getId(),
                secretaria.getNome(),
                secretaria.getEmail(),
                null // senha é null
        );
    }
}
