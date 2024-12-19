package Regradenegocio;

import Persistencia.Dao.ResponsavelDAO;
import Persistencia.Entity.Responsavel;
import VO.ResponsavelVO;

import java.util.List;
import java.util.stream.Collectors;

public class ResponsavelRN {
    private final ResponsavelDAO responsavelDAO;

    public ResponsavelRN() {
        this.responsavelDAO = new ResponsavelDAO();
    }

    public void salvar(ResponsavelVO responsavelVO) {
        if (responsavelVO.getNome() == null || responsavelVO.getNome().isEmpty()) {
            throw new IllegalArgumentException("O nome do responsável é obrigatório.");
        }

        Responsavel responsavel = responsavelVO.toEntity();
        responsavelDAO.salvar(responsavel);
    }

    public void atualizar(ResponsavelVO responsavelVO) {
        Responsavel responsavel = responsavelDAO.buscarPorId(responsavelVO.getId());
        if (responsavel == null) {
            throw new IllegalArgumentException("Responsável não encontrado para atualização.");
        }

        responsavelVO.toEntity().setId(responsavel.getId());
        responsavelDAO.salvar(responsavelVO.toEntity());
    }

    public void deletar(int id) {
        Responsavel responsavel = responsavelDAO.buscarPorId(id);
        if (responsavel == null) {
            throw new IllegalArgumentException("Responsável não encontrado para exclusão.");
        }
        responsavelDAO.deletar(responsavel);
    }

    public ResponsavelVO buscarPorId(int id) {
        Responsavel responsavel = responsavelDAO.buscarPorId(id);
        return responsavel != null ? ResponsavelVO.fromEntity(responsavel) : null;
    }

    public ResponsavelVO buscarPorNome(String nome) {
        Responsavel responsavel = responsavelDAO.buscarPorNome(nome);
        return responsavel != null ? ResponsavelVO.fromEntity(responsavel) : null;
    }

    public List<ResponsavelVO> listarTodos() {
        List<Responsavel> responsaveis = responsavelDAO.listarTodos();
        return responsaveis.stream().map(ResponsavelVO::fromEntity).collect(Collectors.toList());
    }
}
