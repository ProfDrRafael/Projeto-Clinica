package Regradenegocio;

import Persistencia.Dao.OrientadorDAO;
import Persistencia.Entity.Orientador;
import VO.OrientadorVO;

import java.util.List;

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
}
