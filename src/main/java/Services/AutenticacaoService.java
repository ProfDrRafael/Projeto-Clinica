package Services;

import Persistencia.Dao.*;
import Persistencia.Entity.*;
import VO.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutenticacaoService {
    private static final Logger logger = LoggerFactory.getLogger(AutenticacaoService.class);

    private final OrientadorDAO orientadorDAO;
    private final EstagiarioDAO estagiarioDAO;
    private final AdministradorDAO administradorDAO;
    private final SecretariaDAO secretariaDAO;
    private final SenhaService senhaService;

    public AutenticacaoService() {
        this.orientadorDAO = new OrientadorDAO();
        this.estagiarioDAO = new EstagiarioDAO();
        this.administradorDAO = new AdministradorDAO();
        this.secretariaDAO = new SecretariaDAO();
        this.senhaService = new SenhaService();
    }

    public UsuarioVO autenticar(String email, String senha) {
        Orientador orientador = orientadorDAO.buscarPorEmail(email);
        if (orientador != null && senhaService.verificarSenha(senha, orientador.getSenha())) {
            return criarOrientadorVO(orientador);
        }

        Estagiario estagiario = estagiarioDAO.buscarPorEmailComOrientador(email);
        if (estagiario != null && senhaService.verificarSenha(senha, estagiario.getSenha())) {
            return criarEstagiarioVO(estagiario);
        }

        Administrador administrador = administradorDAO.buscarPorEmail(email);
        if (administrador != null && senhaService.verificarSenha(senha, administrador.getSenha())) {
            return criarAdministradorVO(administrador);
        }

        Secretaria secretaria = secretariaDAO.buscarPorEmail(email);
        if (secretaria != null && senhaService.verificarSenha(senha, secretaria.getSenha())) {
            return criarSecretariaVO(secretaria);
        }

        throw new RuntimeException("Usuário ou senha inválidos.");
    }

    private OrientadorVO criarOrientadorVO(Orientador orientador) {
        return new OrientadorVO(
                orientador.getId(),
                orientador.getNome(),
                orientador.getEmail(),
                orientador.getSenha(),
                orientador.getAtivo(),
                orientador.getLinhaTeorica()
        );
    }

    private EstagiarioVO criarEstagiarioVO(Estagiario estagiario) {
        OrientadorVO orientadorVO = criarOrientadorVO(estagiario.getOrientador());
        return new EstagiarioVO(
                estagiario.getId(),
                estagiario.getNome(),
                estagiario.getEmail(),
                estagiario.getSenha(),
                estagiario.getAtivo(),
                estagiario.getAno(),
                estagiario.getSemestreFim(),
                estagiario.getAnoFim(),
                orientadorVO
        );
    }

    private AdministradorVO criarAdministradorVO(Administrador administrador) {
        return new AdministradorVO(
                administrador.getId(),
                administrador.getNome(),
                administrador.getEmail(),
                administrador.getSenha()
        );
    }

    private SecretariaVO criarSecretariaVO(Secretaria secretaria) {
        return new SecretariaVO(
                secretaria.getId(),
                secretaria.getNome(),
                secretaria.getEmail(),
                secretaria.getSenha()
        );
    }
}