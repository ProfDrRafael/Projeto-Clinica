package Persistencia.Model;

import VO.UsuarioVO;

/**
 *
 * @author Raven
 */
public class ModelUser {
    private String name;
    private String email;
    private String tipo;

    public ModelUser() {
    }

    public ModelUser(UsuarioVO usuario, String tipo) {
        if (usuario != null) {
            this.name = usuario.getNomeCompleto();
            this.setEmail(usuario.getEmail());
        }
        this.tipo = tipo;
    }

    public ModelUser(String name, String email, String tipo) {
        this.name = name;
        this.email = email;
        this.tipo = tipo;
    }

    public String getName() {
        return name;
    }

    public void setUserName(String name) {
        this.name = name;
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

    public boolean isAdmin() {
        return "Administrador".equalsIgnoreCase(tipo);
    }

    public boolean isOrientador() {
        return "Orientador".equalsIgnoreCase(tipo);
    }

    public boolean isSecretaria() {
        return "Secretária".equalsIgnoreCase(tipo);
    }

    public boolean isEstagiario() {
        return "Estagiário".equalsIgnoreCase(tipo);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
