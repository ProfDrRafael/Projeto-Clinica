package Regradenegocio;

import Persistencia.Dao.EstagiarioDAO;
import Persistencia.Dao.OrientadorDAO;
import VO.EstagiarioVO;
import VO.OrientadorVO;
import Services.SenhaService;

import java.util.List;
import java.util.stream.Collectors;

public class EstagiarioRN {

    private EstagiarioDAO estagiarioDAO;
    private OrientadorDAO orientadorDAO;
    private SenhaService senhaService;

    public EstagiarioRN() {
        this.estagiarioDAO = new EstagiarioDAO();
        this.orientadorDAO = new OrientadorDAO();
        this.senhaService = new SenhaService();
    }

    // Método para salvar o estagiário
    public boolean salvarEstagiario(EstagiarioVO estagiarioVO) {
        if (!estagiarioVO.isValid()) {
            throw new IllegalArgumentException("Dados do estagiário inválidos.");
        }

        try {
            estagiarioVO.setSenha(senhaService.criptografarSenha(estagiarioVO.getSenha())); // Criptografa a senha
            estagiarioDAO.salvar(estagiarioVO.toEntity());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para atualizar o estagiário
    public boolean atualizarEstagiario(EstagiarioVO estagiarioVO) {
        if (!estagiarioVO.isValid()) {
            throw new IllegalArgumentException("Dados do estagiário inválidos.");
        }

        try {
            var estagiarioExistente = estagiarioDAO.buscarPorId(estagiarioVO.getId());

            if (estagiarioExistente == null) {
                return false; // Estagiário não encontrado
            }

            // Atualiza os campos do estagiário existente
            estagiarioVO.updateEntity(estagiarioExistente);

            // Atualiza o orientador, se necessário
            if (estagiarioVO.getOrientadorVO() != null) {
                var orientador = orientadorDAO.buscarPorId(estagiarioVO.getOrientadorVO().getId());

                if (orientador == null) {
                    orientador = estagiarioVO.getOrientadorVO().toEntity();
                    orientadorDAO.salvar(orientador);
                }

                estagiarioExistente.setOrientador(orientador);
            }

            estagiarioDAO.atualizar(estagiarioExistente);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para listar todos os estagiários
    public List<EstagiarioVO> listarEstagiarios() {
        var estagiarios = estagiarioDAO.buscarTodos();
        return estagiarios.stream()
                .map(EstagiarioVO::fromEntity)
                .collect(Collectors.toList());
    }

    // Método para buscar um estagiário por ID
    public EstagiarioVO buscarEstagiarioPorId(int id) {
        var estagiario = estagiarioDAO.buscarPorId(id);
        return estagiario != null ? EstagiarioVO.fromEntity(estagiario) : null;
    }

    // Método para buscar um estagiário por nome
    public EstagiarioVO buscarEstagiarioPorNome(String nome) {
        var estagiario = estagiarioDAO.buscarPorNome(nome);
        return estagiario != null ? EstagiarioVO.fromEntity(estagiario) : null;
    }

    // Método para buscar um orientador por nome
    public OrientadorVO buscarOrientadorPorNome(String nome) {
        var orientador = orientadorDAO.buscarPorNome(nome);
        return orientador != null ? OrientadorVO.fromEntity(orientador) : null;
    }
}
