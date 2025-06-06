package Regradenegocio;

import Persistencia.Dao.AtendimentoGrupoDAO;
import Persistencia.Entity.AtendimentoGrupo;
import Persistencia.Entity.Estagiario;
import Persistencia.Entity.Grupo;
import Persistencia.Entity.Orientador;
import Services.CriptografiaService;
import VO.AtendimentoGrupoVO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AtendimentoGrupoRN {

    private final AtendimentoGrupoDAO atendimentoGrupoDAO;

    public AtendimentoGrupoRN() {
        this.atendimentoGrupoDAO = new AtendimentoGrupoDAO();
    }

    public AtendimentoGrupo preencherAtendimentoGrupo(LocalDate data, LocalTime hora, Grupo grupo, Estagiario estagiario,
                                                      Orientador orientador, String relato, String observacoes) {
        AtendimentoGrupo atendimento = new AtendimentoGrupo();
        atendimento.setData(data);
        atendimento.setHora(hora);
        atendimento.setGrupo(grupo);
        atendimento.setEstagiario(estagiario);
        atendimento.setOrientador(orientador);
        atendimento.setObservacoes(observacoes);

        try {
            CriptografiaService criptografia = new CriptografiaService();
            atendimento.setRelato(criptografia.criptografarTexto(relato));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criptografar relato do grupo: " + e.getMessage(), e);
        }

        return atendimento;
    }

    public void salvarAtendimentoGrupo(AtendimentoGrupoVO vo) {
        if (vo == null || vo.getRelato() == null || vo.getRelato().trim().isEmpty()) {
            throw new IllegalArgumentException("O atendimento de grupo ou relato está vazio.");
        }

        try {
            CriptografiaService criptografia = new CriptografiaService();
            vo.setRelato(criptografia.criptografarTexto(vo.getRelato()));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criptografar o relato: " + e.getMessage(), e);
        }

        atendimentoGrupoDAO.salvar(vo.toEntity());
    }

    public List<AtendimentoGrupo> buscarPorGrupo(Integer grupoId) {
        return atendimentoGrupoDAO.buscarPorGrupo(grupoId);
    }

    public List<AtendimentoGrupo> buscarPorData(LocalDate data) {
        return atendimentoGrupoDAO.buscarPorData(data);
    }
}
