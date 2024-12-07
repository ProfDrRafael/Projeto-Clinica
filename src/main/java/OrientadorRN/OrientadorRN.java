/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package OrientadorRN;

import Persistencia.Dao.EstagiarioDAO;
import Persistencia.Dao.OrientadorDAO;
import Services.SenhaService;
import VO.OrientadorVO;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author john
 */
public class OrientadorRN {
    private OrientadorDAO orientadorDAO;

    public OrientadorRN() {
        this.orientadorDAO = new OrientadorDAO();
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
