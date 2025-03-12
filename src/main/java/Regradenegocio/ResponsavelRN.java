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

    public Responsavel salvar(ResponsavelVO responsavelVO) {
        if (responsavelVO.getNome() == null || responsavelVO.getNome().isEmpty()) {
            throw new IllegalArgumentException("O nome do responsável é obrigatório.");
        }

        Responsavel responsavelEntity = responsavelVO.toEntity();
        responsavelDAO.salvar(responsavelEntity);

        return responsavelEntity;
    }

    public Responsavel atualizar(ResponsavelVO responsavelVO) {
        Responsavel responsavelEntity = responsavelDAO.buscarPorId(responsavelVO.getId());
        if (responsavelEntity == null) {
            throw new IllegalArgumentException("Responsável não encontrado para atualização.");
        }

        responsavelEntity = responsavelVO.toEntity(); 
        responsavelEntity.setId(responsavelVO.getId()); 

        responsavelDAO.atualizar(responsavelEntity);

        return responsavelEntity;
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
