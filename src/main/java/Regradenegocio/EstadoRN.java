/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Regradenegocio;

import Persistencia.Dao.EstadoDAO;
import Persistencia.Entity.Estado;
import VO.EstadoVO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author GMott
 */
public class EstadoRN {

    private final EstadoDAO estadoDAO;

    public EstadoRN() {
        this.estadoDAO = new EstadoDAO();
    }

    /**
     * Busca todos os estados da camada de persistência e os converte em uma
     * lista de EstadoVO.
     *
     * @return Uma lista de EstadoVO para ser usada na tela.
     */
    public List<EstadoVO> listarTodos() {
        List<Estado> estadosEntities = estadoDAO.buscarTodos();
        List<EstadoVO> estadosVO = new ArrayList<>();

        for (Estado entity : estadosEntities) {
            // Utilizando o método estático para a conversão.
            estadosVO.add(EstadoVO.fromEntity(entity));
        }

        return estadosVO;
    }
}
