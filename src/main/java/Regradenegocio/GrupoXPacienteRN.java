package Regradenegocio;

import Persistencia.Dao.GrupoXPacienteDAO;
import Persistencia.Entity.GrupoXPaciente;
import VO.GrupoXPacienteVO;

import java.util.List;

public class GrupoXPacienteRN {

    private final GrupoXPacienteDAO dao;

    public GrupoXPacienteRN() {
        this.dao = new GrupoXPacienteDAO();
    }

    public void salvar(GrupoXPacienteVO vo) {
        if (vo.getGrupoId() == null || vo.getPacienteId() == null) {
            throw new IllegalArgumentException("Grupo e Paciente são obrigatórios.");
        }
        dao.salvar(vo.toEntity());
    }

    public List<GrupoXPaciente> listarPorGrupo(Integer grupoId) {
        return dao.listarPorGrupo(grupoId);
    }

    public List<GrupoXPaciente> listarPorPaciente(Integer pacienteId) {
        return dao.listarPorPaciente(pacienteId);
    }
}
