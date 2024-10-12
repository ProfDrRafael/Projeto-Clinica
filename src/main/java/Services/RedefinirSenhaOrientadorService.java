package Services;

import Persistencia.Dao.OrientadorDAO;
import Persistencia.Dao.RedefinirSenhaOrientadorDAO;
import Persistencia.Entity.Orientador;
import Persistencia.Entity.RedefinirSenhaOrientador;

import java.time.Instant;
import java.util.UUID;

public class RedefinirSenhaOrientadorService {

    private final RedefinirSenhaOrientadorDAO tokenDAO = new RedefinirSenhaOrientadorDAO();
    private final OrientadorDAO orientadorDAO = new OrientadorDAO();
    private final EmailService emailService = new EmailService();  // Instanciando o serviço de e-mails

    public void gerarTokenRedefinicao(String email) {
        Orientador orientador = orientadorDAO.buscarPorEmail(email);

        if (orientador == null) {
            throw new RuntimeException("Orientador não encontrado com o e-mail: " + email);
        }

        // Gera um token UUID e define um tempo de expiração
        String token = UUID.randomUUID().toString();
        Instant expiracao = calcularExpiracaoToken(24);

        
        RedefinirSenhaOrientador resetToken = new RedefinirSenhaOrientador();
        resetToken.setOrientador(orientador);
        resetToken.setToken(token);
        resetToken.setValidadeToken(expiracao);

        tokenDAO.salvarToken(resetToken);

        emailService.enviarEmailComToken(email, token);
    }

    private Instant calcularExpiracaoToken(int horas) {
        return Instant.now().plusSeconds(horas * 3600);
    }

    public boolean validarToken(String token) {
        RedefinirSenhaOrientador resetToken = tokenDAO.buscarPorToken(token);
        // Verificar o token existente e a data de validade
        return !(resetToken == null || resetToken.getValidadeToken().isBefore(Instant.now()));  
    }

    public void redefinirSenha(String token, String novaSenha) {
        RedefinirSenhaOrientador resetToken = tokenDAO.buscarPorToken(token);
        if (!validarToken(token)) {
            throw new RuntimeException("Token inválido ou expirado.");
        }

        // Criptografar a nova senha e atualizar no orientador
        SenhaService senhaService = new SenhaService();
        String senhaCriptografada = senhaService.criptografarSenha(novaSenha);

        Orientador orientador = resetToken.getOrientador();
        orientador.setSenha(senhaCriptografada);

        orientadorDAO.atualizarOrientador(orientador);

//        tokenDAO.deletarToken(resetToken);;
    }
}
