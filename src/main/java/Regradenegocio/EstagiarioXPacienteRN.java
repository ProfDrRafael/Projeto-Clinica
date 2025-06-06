/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Regradenegocio;

import VO.EstagiarioXPacienteVO;
import Persistencia.Dao.EstagiarioXPacienteDAO;
import java.util.List;

/**
 *
 * @author otniel
 */
public class EstagiarioXPacienteRN {
    private final EstagiarioXPacienteDAO dao = new EstagiarioXPacienteDAO();

    public void associarEstagiarioPaciente(EstagiarioXPacienteVO vo) {
        dao.salvar(vo.toEntity());
    }

    public void removerAssociacao(EstagiarioXPacienteVO vo) {
        dao.deletar(vo.toEntity());
    }

    public List<EstagiarioXPacienteVO> buscarPorEstagiario(Integer estagiarioId) {
        return dao.buscarPorEstagiario(estagiarioId);
    }

    public List<EstagiarioXPacienteVO> buscarPorPaciente(Integer pacienteId) {
        return dao.buscarPorPaciente(pacienteId);
    }
}
