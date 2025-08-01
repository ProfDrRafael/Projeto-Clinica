/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Regradenegocio;

import Persistencia.Dao.PacienteDAO;
import Persistencia.Dao.ProntuarioDAO;
import Persistencia.Entity.Prontuario;
import Persistencia.Entity.Paciente;
import VO.PacienteVO;
import VO.ProntuarioEletronicoVO;
import java.time.LocalDate;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author john
 */
public class PacienteRN {
    private PacienteDAO pacienteDAO;

//    public PacienteRN() {
//        this.pacienteDAO = new PacienteDAO();
////        this.pacienteVO = new PacienteVO(entity.getId(), entity.getGenero(), entity.getTelefoneContato(), entity.getTelefone(), entity.getNome(), entity.getDataNascimento().toString(), entity.getDataInscricao(), entity.getGrauInstrucao(), entity.getProfissao(), entity.getEstadoCivil(), entity.getRacaCorEtnia(), null, entity.getNacionalidade().getId(), entity.getDisponibilidade(), null, null, entity.getEndereco(), entity.getResponsavel(), entity.getAtendido(), entity.getAtivo(), entity.getGrupo());
//    }

    public PacienteRN(){
        pacienteDAO = new PacienteDAO();
    }


    public boolean salvarPaciente(PacienteVO pacienteVO) {
        try {
            pacienteDAO.salvarPaciente(pacienteVO.toEntity());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editarPaciente(PacienteVO pacienteVO) {
        try {
            pacienteDAO.atualizarPaciente(pacienteVO.toEntity());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para listar todos os estagiários
    public List<PacienteVO> listarPacientes() {
        var estagiarios = pacienteDAO.buscarTodos();
        
        if(estagiarios != null){
            return estagiarios.stream()
                    .map(PacienteVO::fromEntity)
                    .collect(Collectors.toList());
        }
        return null;
    }

    // Método para buscar um estagiário por ID
    public PacienteVO buscarPacientePorId(int id) {
        var estagiario = pacienteDAO.buscarPorId(id);
        return estagiario != null ? PacienteVO.fromEntity(estagiario) : null;
    }

    public PacienteVO buscarPorNomePreciso(String nome) {
        return pacienteDAO.buscarPorNomePreciso(nome);
    }

    public ProntuarioEletronicoVO buscarProntuarioPorPacienteId(Integer pacienteId) {
        ProntuarioDAO prontuarioDAO = new ProntuarioDAO();
        Prontuario prontuario = prontuarioDAO.buscarPorPacienteId(pacienteId);

        if (prontuario != null) {
            return ProntuarioEletronicoVO.fromEntity(prontuario);
        }
        return null;
    }

    public Paciente salvarPacienteRetornandoEntidade(PacienteVO pacienteVO) {
        return pacienteDAO.salvarRetornandoEntidade(pacienteVO.toEntity());
    }

    public List<PacienteVO> listarPacientesSemAtendimento() {
        return pacienteDAO.listarPacientesSemAtendimento();
    }
    
    public List<Paciente> filtrarPacientes(PacienteVO filtro) {
        return pacienteDAO.buscarPorFiltros(filtro);
    }
}
