/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VO;

import Persistencia.Entity.AtendimentoGrupo;
import Persistencia.Entity.AtendimentoGrupoPaciente;
import Persistencia.Entity.Paciente;

/**
 *
 * @author otnie
 */
public class AtendimentoGrupoPacienteVO {
    private Integer atendimentoGrupoId;
    private Integer pacienteId;
    private Boolean compareceu;
    private String justificativa;

    /**
     * @return the atendimentoGrupoId
     */
    public Integer getAtendimentoGrupoId() {
        return atendimentoGrupoId;
    }

    /**
     * @param atendimentoGrupoId the atendimentoGrupoId to set
     */
    public void setAtendimentoGrupoId(Integer atendimentoGrupoId) {
        this.atendimentoGrupoId = atendimentoGrupoId;
    }

    /**
     * @return the pacienteId
     */
    public Integer getPacienteId() {
        return pacienteId;
    }

    /**
     * @param pacienteId the pacienteId to set
     */
    public void setPacienteId(Integer pacienteId) {
        this.pacienteId = pacienteId;
    }

    /**
     * @return the compareceu
     */
    public Boolean getCompareceu() {
        return compareceu;
    }

    /**
     * @param compareceu the compareceu to set
     */
    public void setCompareceu(Boolean compareceu) {
        this.compareceu = compareceu;
    }

    /**
     * @return the justificativa
     */
    public String getJustificativa() {
        return justificativa;
    }

    /**
     * @param justificativa the justificativa to set
     */
    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public AtendimentoGrupoPaciente toEntity() {
        AtendimentoGrupoPaciente agp = new AtendimentoGrupoPaciente();

        AtendimentoGrupo ag = new AtendimentoGrupo();
        ag.setId(this.atendimentoGrupoId);
        agp.setAtendimentoGrupo(ag);

        Paciente paciente = new Paciente();
        paciente.setId(this.pacienteId);
        agp.setPaciente(paciente);

        agp.setCompareceu(this.compareceu != null && this.compareceu);
        agp.setJustificativa(this.justificativa);
        return agp;
    }

    public static AtendimentoGrupoPacienteVO fromEntity(AtendimentoGrupoPaciente agp) {
        AtendimentoGrupoPacienteVO vo = new AtendimentoGrupoPacienteVO();
        vo.setAtendimentoGrupoId(agp.getAtendimentoGrupo().getId());
        vo.setPacienteId(agp.getPaciente().getId());
        vo.setCompareceu(agp.getCompareceu());
        vo.setJustificativa(agp.getJustificativa());
        return vo;
    }
}
