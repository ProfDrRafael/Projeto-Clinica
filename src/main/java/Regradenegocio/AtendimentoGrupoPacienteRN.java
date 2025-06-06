package Regradenegocio;

import Persistencia.Dao.AtendimentoGrupoPacienteDAO;
import Persistencia.Entity.AtendimentoGrupoPaciente;
import VO.AtendimentoGrupoPacienteVO;

import java.util.List;

public class AtendimentoGrupoPacienteRN {

    private final AtendimentoGrupoPacienteDAO dao;

    public AtendimentoGrupoPacienteRN() {
        this.dao = new AtendimentoGrupoPacienteDAO();
    }

    public void salvar(AtendimentoGrupoPacienteVO vo) {
        if (vo.getAtendimentoGrupoId() == null || vo.getPacienteId() == null) {
            throw new IllegalArgumentException("Atendimento de grupo e paciente são obrigatórios.");
        }

        dao.salvar(vo.toEntity());
    }

    public List<AtendimentoGrupoPaciente> listarPorAtendimento(Integer atendimentoGrupoId) {
        return dao.listarPorAtendimento(atendimentoGrupoId);
    }
}
