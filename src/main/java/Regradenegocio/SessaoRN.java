package Regradenegocio;

import Persistencia.Dao.SessaoDAO;
import Persistencia.Entity.Sessao;
import VO.SessaoVO;

import java.util.List;
import java.util.stream.Collectors;

public class SessaoRN {
    private final SessaoDAO sessaoDAO;

    public SessaoRN() {
        this.sessaoDAO = new SessaoDAO();
    }

    // Regras de negócio para salvar uma Sessão
    public void salvarSessao(SessaoVO sessaoVO) {
        validarSessao(sessaoVO);
        Sessao sessao = sessaoVO.toEntity();
        sessao.setId(null);
        sessaoDAO.salvar(sessao);
    }

    // Buscar todas as Sessões
    public List<SessaoVO> buscarTodasSessoes() {
        return sessaoDAO.buscarTodos()
                .stream()
                .map(SessaoVO::fromEntity)
                .collect(Collectors.toList());
    }

    // Buscar a última Sessão adicionada
    public SessaoVO buscarUltimaSessao() {
        Sessao ultimaSessao = sessaoDAO.buscarUltimaAdicionada();
        if (ultimaSessao == null) {
            throw new RuntimeException("Nenhuma sessão encontrada.");
        }
        return SessaoVO.fromEntity(ultimaSessao);
    }

    // Validações de negócio
    private void validarSessao(SessaoVO sessaoVO) {
        if (sessaoVO.getNome() == null || sessaoVO.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da sessão é obrigatório.");
        }
        if (sessaoVO.getEmail() == null || !sessaoVO.getEmail().contains("@")) {
            throw new IllegalArgumentException("O e-mail da sessão é inválido.");
        }
        if (sessaoVO.getTipo() == null || sessaoVO.getTipo().trim().isEmpty()) {
            throw new IllegalArgumentException("O tipo da sessão é obrigatório.");
        }
    }
}
