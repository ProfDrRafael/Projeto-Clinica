package Regradenegocio;

import Persistencia.Dao.EstagiarioDAO;
import Persistencia.Dao.OrientadorDAO;
import Persistencia.Dao.PesquisadorDAO;
import Persistencia.Dao.SecretariaDAO;
import Persistencia.Entity.Estagiario;
import Persistencia.Entity.Orientador;
import Persistencia.Entity.Pesquisador;
import Persistencia.Entity.Secretaria;
import VO.UsuarioVO;
import VO.EstagiarioVO;
import VO.OrientadorVO;
import VO.SecretariaVO;
import Services.SenhaService;
import VO.PesquisadorVO;

public class UsuarioRN {
    private SenhaService senhaService;

    public UsuarioRN() {
        this.senhaService = new SenhaService();
    }

    // Método para salvar o usuário no banco de dados
    public boolean salvarUsuario(UsuarioVO usuarioVO) {
        if (!validarDadosUsuario(usuarioVO)) {
            return false;
        }

        try {
            if (usuarioVO instanceof EstagiarioVO) {
                EstagiarioDAO estagiarioDAO = new EstagiarioDAO();
                estagiarioDAO.salvar(converterVOParaEstagiario((EstagiarioVO) usuarioVO));
            } else if (usuarioVO instanceof OrientadorVO) {
                OrientadorDAO orientadorDAO = new OrientadorDAO();
                orientadorDAO.salvar(converterVOParaOrientador((OrientadorVO) usuarioVO));
            } else if (usuarioVO instanceof SecretariaVO) {
                SecretariaDAO secretariaDAO = new SecretariaDAO();
                secretariaDAO.salvar(converterVOParaSecretaria((SecretariaVO) usuarioVO));
            } else if (usuarioVO instanceof PesquisadorVO) {
                PesquisadorDAO pesquisadorDAO = new PesquisadorDAO();
                pesquisadorDAO.salvar(converterVOParaPesquisador((PesquisadorVO) usuarioVO));
            } else {
                throw new IllegalArgumentException("Tipo de usuário inválido.");
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean validarDadosUsuario(UsuarioVO usuarioVO) {
        if (usuarioVO.getNomeCompleto() == null || usuarioVO.getNomeCompleto().isEmpty()) {
            throw new IllegalArgumentException("O nome é obrigatório.");
        }
        if (usuarioVO.getEmail() == null || usuarioVO.getEmail().isEmpty()) {
            throw new IllegalArgumentException("O e-mail é obrigatório.");
        }
        if (usuarioVO.getSenha() == null || usuarioVO.getSenha().isEmpty()) {
            throw new IllegalArgumentException("A senha é obrigatória.");
        }
        return true;
    }

    private Estagiario converterVOParaEstagiario(EstagiarioVO vo) {
        Estagiario estagiario = new Estagiario();
        estagiario.setNome(vo.getNomeCompleto());
        estagiario.setEmail(vo.getEmail());
        estagiario.setSenha(senhaService.criptografarSenha(vo.getSenha())); // Criptografar a senha se necessário
        estagiario.setAno(vo.getAno());
        estagiario.setAtivo(vo.getAtivo());

        return estagiario;
    }

    private Orientador converterVOParaOrientador(OrientadorVO vo) {
        Orientador orientador = new Orientador();
        orientador.setNome(vo.getNomeCompleto());
        orientador.setEmail(vo.getEmail());
        orientador.setSenha(senhaService.criptografarSenha(vo.getSenha())); // Criptografar a senha se necessário
        // Outros atributos específicos de Orientador
        return orientador;
    }

    private Secretaria converterVOParaSecretaria(SecretariaVO vo) {
        Secretaria secretaria = new Secretaria();
        secretaria.setNome(vo.getNomeCompleto());
        secretaria.setEmail(vo.getEmail());
        secretaria.setSenha(senhaService.criptografarSenha(vo.getSenha())); // Criptografar a senha se necessário
        // Outros atributos específicos de Secretaria
        return secretaria;
    }
    
    private Pesquisador converterVOParaPesquisador(PesquisadorVO vo) {
        Pesquisador pesquisador = new Pesquisador();
        pesquisador.setNome(vo.getNomeCompleto());
        pesquisador.setEmail(vo.getEmail());
        pesquisador.setSenha(senhaService.criptografarSenha(vo.getSenha())); // Criptografar a senha se necessário
        pesquisador.setAtivo(vo.getAtivo());
        // Outros atributos específicos de Pesquisador
        return pesquisador;
    }
}
