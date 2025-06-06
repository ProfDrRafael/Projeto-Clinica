/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VO;

import Persistencia.Entity.Grupo;
import Persistencia.Entity.GrupoXPaciente;
import Persistencia.Entity.GrupoXPacienteId;
import Persistencia.Entity.Paciente;
import java.time.LocalDate;

/**
 *
 * @author otnie
 */
public class GrupoXPacienteVO {
    private Integer grupoId;
    private Integer pacienteId;
    private LocalDate dataEntrada;
    private LocalDate dataSaida;

    /**
     * @return the grupoId
     */
    public Integer getGrupoId() {
        return grupoId;
    }

    /**
     * @param grupoId the grupoId to set
     */
    public void setGrupoId(Integer grupoId) {
        this.grupoId = grupoId;
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
     * @return the dataEntrada
     */
    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    /**
     * @param dataEntrada the dataEntrada to set
     */
    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    /**
     * @return the dataSaida
     */
    public LocalDate getDataSaida() {
        return dataSaida;
    }

    /**
     * @param dataSaida the dataSaida to set
     */
    public void setDataSaida(LocalDate dataSaida) {
        this.dataSaida = dataSaida;
    }

    public GrupoXPaciente toEntity() {
        GrupoXPaciente entity = new GrupoXPaciente();

        GrupoXPacienteId id = new GrupoXPacienteId();
        id.setGrupoId(this.grupoId);
        id.setPacienteId(this.pacienteId);
        entity.setId(id);

        Grupo grupo = new Grupo();
        grupo.setId(this.grupoId);
        entity.setGrupo(grupo);

        Paciente paciente = new Paciente();
        paciente.setId(this.pacienteId);
        entity.setPaciente(paciente);

        if (this.dataEntrada == null) {
            entity.setDataEntrada(LocalDate.now());
        } else {
            entity.setDataEntrada(this.dataEntrada);
        }

        entity.setDataSaida(this.dataSaida);

        return entity;
    }

    public static GrupoXPacienteVO fromEntity(GrupoXPaciente gxp) {
        GrupoXPacienteVO vo = new GrupoXPacienteVO();
        vo.setGrupoId(gxp.getGrupo().getId());
        vo.setPacienteId(gxp.getPaciente().getId());
        vo.setDataEntrada(gxp.getDataEntrada());
        vo.setDataSaida(gxp.getDataSaida());
        return vo;
    }
}
