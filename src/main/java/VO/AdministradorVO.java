package VO;

import Persistencia.Entity.Administrador;

public class AdministradorVO extends UsuarioVO {

    public AdministradorVO(int id, String nomeCompleto, String email, String senha) {
        super(id, nomeCompleto, email, senha);
    }

    @Override
    public String getTipo() {
        return "Administrador";
    }

    public static AdministradorVO fromEntity(Administrador administrador) {
        return new AdministradorVO(
                administrador.getId(),
                administrador.getNome(),
                administrador.getEmail(),
                null
        );
    }
}
