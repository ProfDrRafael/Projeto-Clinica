package Persistencia.modelTemp;

import VO.UsuarioVO;

/**
 *
 * @author Raven
 */
public class ModelUser {

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public ModelUser(UsuarioVO usuario, boolean admin) {
        this.userName = usuario.getNomeCompleto();
        this.admin = admin;
    }

    public ModelUser() {
    }

    private String userName;
    private boolean admin;
}
