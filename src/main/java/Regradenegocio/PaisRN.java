/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Regradenegocio;

/**
 *
 * @author GMott
 */
import Persistencia.Dao.PaisDAO;
import Persistencia.Entity.Pais;
import VO.PaisVO;
import java.util.ArrayList;
import java.util.List;

public class PaisRN {

    private final PaisDAO paisDAO;

    public PaisRN() {
        this.paisDAO = new PaisDAO();
    }

    /**
     * Busca todos os países da camada de persistência e os converte para uma
     * lista de PaisVO.
     *
     * @return Uma lista de PaisVO para ser usada na camada de Visão (telas).
     */
    public List<PaisVO> listarTodos() {

        List<Pais> paisesEntities = paisDAO.buscarTodos();

        List<PaisVO> paisesVO = new ArrayList<>();

        for (Pais entity : paisesEntities) {
            paisesVO.add(new PaisVO(entity.getId(), entity.getNome()));
        }

        return paisesVO;
    }
}
