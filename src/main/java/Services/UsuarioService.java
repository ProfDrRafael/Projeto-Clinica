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

    /**
     * Busca um usuário no sistema com base no e-mail e tipo informado.
     *
     * @param email O e-mail do usuário a ser buscado.
     * @param tipoUsuario O tipo do usuário: "orientador", "estagiario", "administrador" ou "secretaria".
     * @param incluirSenha Se true, inclui a senha do usuário no objeto VO retornado. Use isso apenas em cenários de autenticação.
     * @return Um objeto UsuarioVO correspondente ao usuário encontrado.
     * @throws RuntimeException Se o tipo for inválido ou se o usuário não for encontrado.
     */
    public UsuarioVO buscarUsuario(String email, String tipoUsuario, boolean incluirSenha) {
        switch (tipoUsuario.toLowerCase()) {
            case "orientador" -> {
                Orientador orientador = orientadorDAO.buscarPorEmail(email);
                if (orientador != null) {
                    OrientadorVO vo = OrientadorVO.fromEntity(orientador);
                    if (incluirSenha) vo.setSenha(orientador.getSenha());
                    return vo;
                }
            }
            case "estagiario" -> {
                Estagiario estagiario = estagiarioDAO.buscarPorEmail(email);
                if (estagiario != null) {
                    EstagiarioVO vo = EstagiarioVO.fromEntity(estagiario);
                    if (incluirSenha) vo.setSenha(estagiario.getSenha());
                    return vo;
                }
            }
            case "administrador" -> {
                Administrador administrador = administradorDAO.buscarPorEmail(email);
                if (administrador != null) {
                    AdministradorVO vo = AdministradorVO.fromEntity(administrador);
                    if (incluirSenha) vo.setSenha(administrador.getSenha());
                    return vo;
                }
            }
            case "secretaria" -> {
                Secretaria secretaria = secretariaDAO.buscarPorEmail(email);
                if (secretaria != null) {
                    SecretariaVO vo = SecretariaVO.fromEntity(secretaria);
                    if (incluirSenha) vo.setSenha(secretaria.getSenha());
                    return vo;
                }
            }
            default -> throw new RuntimeException("Tipo de usuário inválido.");
        }
        throw new RuntimeException("Usuário não encontrado.");
    }

    /**
     * Busca um usuário no sistema com base no e-mail e tipo informado.
     * Não inclui a senha no objeto retornado.
     *
     * @param email O e-mail do usuário.
     * @param tipoUsuario O tipo do usuário.
     * @return O usuário representado como UsuarioVO, sem a senha.
     */
    public UsuarioVO buscarUsuario(String email, String tipoUsuario) {
        return buscarUsuario(email, tipoUsuario, false);
    }

    public void atualizarUsuario(UsuarioVO vo, String tipo) {
        switch (tipo.toLowerCase()) {
            case "estagiario" -> estagiarioDAO.atualizar(((EstagiarioVO) vo).toEntity());
            case "orientador" -> orientadorDAO.atualizar(((OrientadorVO) vo).toEntity());
            case "secretaria" -> secretariaDAO.atualizar(((SecretariaVO) vo).toEntity());
            case "administrador" -> administradorDAO.atualizar(((AdministradorVO) vo).toEntity());
            default -> throw new RuntimeException("Tipo de usuário inválido.");
        }
    }

    public void alterarEmail(String emailAtual, String novoEmail, String senhaAtual, String tipo) {
        UsuarioVO usuario = buscarUsuario(emailAtual, tipo, true);
        SenhaService senhaService = new SenhaService();
        if (!senhaService.verificarSenha(senhaAtual, usuario.getSenha())) {
            throw new RuntimeException("Senha incorreta.");
        }
        usuario.setEmail(novoEmail);
        atualizarUsuario(usuario, tipo);
    }

    public void alterarSenha(String email, String senhaAtual, String novaSenha, String tipo) {
        UsuarioVO usuario = buscarUsuario(email, tipo, true);
        SenhaService senhaService = new SenhaService();
        if (!senhaService.verificarSenha(senhaAtual, usuario.getSenha())) {
            throw new RuntimeException("Senha incorreta.");
        }
        usuario.setSenha(senhaService.criptografarSenha(novaSenha));
        atualizarUsuario(usuario, tipo);
    }
}
