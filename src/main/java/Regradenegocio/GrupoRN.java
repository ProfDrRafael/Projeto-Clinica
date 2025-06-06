package Regradenegocio;

import Persistencia.Dao.GrupoDAO;
import Persistencia.Entity.Grupo;
import VO.GrupoVO;

import java.util.List;

public class GrupoRN {

    private final GrupoDAO grupoDAO;

    public GrupoRN() {
        this.grupoDAO = new GrupoDAO();
    }

    public void salvar(GrupoVO vo) {
        if (vo.getNome() == null || vo.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do grupo é obrigatório.");
        }

        grupoDAO.salvar(vo.toEntity());
    }

    public List<GrupoVO> listarTodos() {
        return grupoDAO.listarTodos()
                .stream()
                .map(GrupoVO::fromEntity)
                .toList();
    }

    public List<String> listarNomesDosGrupos() {
        return grupoDAO.listarTodos()
                .stream()
                .map(Grupo::getNome)
                .toList();
    }

    public GrupoVO buscarPorNome(String nome) {
        Grupo grupo = grupoDAO.buscarPorNome(nome);
        if (grupo == null) {
            throw new IllegalArgumentException("Grupo não encontrado: " + nome);
        }
        return GrupoVO.fromEntity(grupo);
    }

}
