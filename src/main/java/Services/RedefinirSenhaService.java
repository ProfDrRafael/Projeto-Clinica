package Services;

import Persistencia.Dao.*;
import Persistencia.Entity.RedefinirSenha;

import java.time.Instant;
import java.util.UUID;

public class RedefinirSenhaService {
    private final AdministradorDAO administradorDAO = new AdministradorDAO();
    private final OrientadorDAO orientadorDAO = new OrientadorDAO();
    private final EstagiarioDAO estagiarioDAO = new EstagiarioDAO();
    private final SecretariaDAO secretariaDAO = new SecretariaDAO();

    private final RedefinirSenhaDAO redefinirSenhaDAO = new RedefinirSenhaDAO();
    private final SenhaService senhaService = new SenhaService();
    private final EmailService emailService = new EmailService();

    public String descobrirTipoUsuario(String email) {
        if (administradorDAO.buscarPorEmail(email) != null) return "administrador";
        if (orientadorDAO.buscarPorEmail(email) != null) return "orientador";
        if (estagiarioDAO.buscarPorEmail(email) != null) return "estagiario";
        if (secretariaDAO.buscarPorEmail(email) != null) return "secretaria";
        throw new RuntimeException("Usuário não encontrado para o email fornecido.");
    }

    public void gerarTokenRedefinicao(String email) {
        String token = UUID.randomUUID().toString();
        Instant expiracao = Instant.now().plusSeconds(24 * 3600);

        RedefinirSenha redefinirSenha = new RedefinirSenha();
        redefinirSenha.setEmail(email);
        redefinirSenha.setToken(token);
        redefinirSenha.setValidadeToken(expiracao);

        redefinirSenhaDAO.salvarTokenRedefinicao(redefinirSenha);
        emailService.enviarEmailComToken(email, token);
    }

    public void redefinirSenha(String token, String novaSenha) {
        RedefinirSenha redefinirSenha = redefinirSenhaDAO.buscarPorToken(token);
        if (redefinirSenha == null || redefinirSenha.getValidadeToken().isBefore(Instant.now())) {
            throw new RuntimeException("Token inválido ou expirado.");
        }

        String email = redefinirSenha.getEmail();
        String tipoUsuario = descobrirTipoUsuario(email);

        switch (tipoUsuario) {
            case "administrador" -> {
                var administrador = administradorDAO.buscarPorEmail(email);
                administrador.setSenha(senhaService.criptografarSenha(novaSenha));
                administradorDAO.atualizar(administrador);
            }
            case "orientador" -> {
                var orientador = orientadorDAO.buscarPorEmail(email);
                orientador.setSenha(senhaService.criptografarSenha(novaSenha));
                orientadorDAO.atualizar(orientador);
            }
            case "estagiario" -> {
                var estagiario = estagiarioDAO.buscarPorEmail(email);
                estagiario.setSenha(senhaService.criptografarSenha(novaSenha));
                estagiarioDAO.atualizar(estagiario);
            }
            case "secretaria" -> {
                var secretaria = secretariaDAO.buscarPorEmail(email);
                secretaria.setSenha(senhaService.criptografarSenha(novaSenha));
                secretariaDAO.atualizar(secretaria);
            }
            default -> throw new RuntimeException("Tipo de usuário inválido.");
        }

        redefinirSenhaDAO.deletarToken(redefinirSenha);
    }
}
