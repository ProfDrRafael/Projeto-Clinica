package Regradenegocio;

import Persistencia.Dao.OrientadorDAO;
import Persistencia.Entity.Orientador;
import VO.OrientadorVO;

import java.util.List;
import java.util.stream.Collectors;

public class OrientadorRN {
    private OrientadorDAO orientadorDAO;

    public OrientadorRN() {
        orientadorDAO = new OrientadorDAO();
    }

    public List<OrientadorVO> listar() {
        List<Orientador> orientadores = orientadorDAO.buscarTodos();

        return orientadores.stream()
                .map(OrientadorVO::fromEntity)
                .toList();
    }
    // Método para listar todos os estagiários
    public List<OrientadorVO> listarOrientadores() {
        var orientadores = orientadorDAO.buscarTodos(); 
        return orientadores.stream()
                .map(OrientadorVO::fromEntity)
                .collect(Collectors.toList());
    }

    // Método para buscar um estagiário por ID
    public OrientadorVO buscarOrientadorPorId(int id) {
        var estagiario = orientadorDAO.buscarPorId(id);
        return estagiario != null ? OrientadorVO.fromEntity(estagiario) : null;
    }

    // Método para buscar um orientador por nome
    public OrientadorVO buscarOrientadorPorNome(String nome) {
        var orientador = orientadorDAO.buscarPorNome(nome);
        return orientador != null ? OrientadorVO.fromEntity(orientador) : null;
    }
}
