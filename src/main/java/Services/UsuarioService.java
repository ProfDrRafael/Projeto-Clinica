package Services;

import Persistencia.Dao.AdministradorDAO;
import Persistencia.Dao.EstagiarioDAO;
import Persistencia.Dao.OrientadorDAO;
import Persistencia.Dao.SecretariaDAO;
import Persistencia.Entity.Administrador;
import Persistencia.Entity.Estagiario;
import Persistencia.Entity.Orientador;
import Persistencia.Entity.Secretaria;
import VO.*;

public class UsuarioService {
    private final OrientadorDAO orientadorDAO;
    private final EstagiarioDAO estagiarioDAO;
    private final AdministradorDAO administradorDAO;
    private final SecretariaDAO secretariaDAO;

    public UsuarioService() {
        this.orientadorDAO = new OrientadorDAO();
        this.estagiarioDAO = new EstagiarioDAO();
        this.administradorDAO = new AdministradorDAO();
        this.secretariaDAO = new SecretariaDAO();
    }

    // Método para buscar o usuário pelo email e tipo
    public UsuarioVO buscarUsuarioPorEmail(String email, String tipoUsuario) {
        switch (tipoUsuario.toLowerCase()) {
            case "orientador" -> {
                Orientador orientador = orientadorDAO.buscarPorEmail(email);
                if (orientador != null) {
                    return OrientadorVO.fromEntity(orientador);
                }
            }
            case "estagiario" -> {
                Estagiario estagiario = estagiarioDAO.buscarPorEmail(email);
                if (estagiario != null) {
                    return EstagiarioVO.fromEntity(estagiario);
                }
            }
            case "administrador" -> {
                Administrador administrador = administradorDAO.buscarPorEmail(email);
                if (administrador != null) {
                    return AdministradorVO.fromEntity(administrador);
                }
            }
            case "secretaria" -> {
                Secretaria secretaria = secretariaDAO.buscarPorEmail(email);
                if (secretaria != null) {
                    return SecretariaVO.fromEntity(secretaria);
                }
            }
            default -> throw new RuntimeException("Tipo de usuário inválido.");
        }
        throw new RuntimeException("Usuário não encontrado.");
    }
}
