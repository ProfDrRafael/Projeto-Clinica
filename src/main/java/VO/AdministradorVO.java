package VO;

import Persistencia.Entity.Administrador;

public class AdministradorVO extends UsuarioVO {

    public AdministradorVO(int id, String nomeCompleto, String email, String senha) {
        super(id, nomeCompleto, email, senha);
    }

    public static AdministradorVO fromEntity(Administrador administrador) {
        return new AdministradorVO(
                administrador.getId(),
                administrador.getNome(),
                administrador.getEmail(),
                null
        );
    }

    public Administrador toEntity() {
        var admin = new Administrador();
        admin.setId(this.getId());
        admin.setNome(this.getNomeCompleto());
        admin.setEmail(this.getEmail());
        admin.setSenha(this.getSenha());
        return admin;
    }

    @Override
    public String getTipo() {
        return "Administrador";
    }
}
