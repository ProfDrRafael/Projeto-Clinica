/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Regradenegocio;

import Persistencia.Dao.PacienteDAO;
import VO.PacienteVO;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author john
 */
public class PacienteRN {
    private PacienteDAO pacienteDAO;
    private PacienteVO pacienteVO;

    public PacienteRN() {
        this.pacienteDAO = new PacienteDAO();
        this.pacienteVO = new PacienteVO();
    }

    // Método para salvar o estagiário
    public boolean salvarPaciente(PacienteVO pacienteVO) {
        try {
            pacienteDAO.salvarPaciente(pacienteVO.toEntity());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para listar todos os estagiários
    public List<PacienteVO> listarPacientes() {
        var estagiarios = pacienteDAO.buscarTodos();
        return estagiarios.stream()
                .map(PacienteVO::fromEntity)
                .collect(Collectors.toList());
    }

    // Método para buscar um estagiário por ID
    public PacienteVO buscarPacientePorId(int id) {
        var estagiario = pacienteDAO.buscarPorId(id);
        return estagiario != null ? PacienteVO.fromEntity(estagiario) : null;
    }
}
