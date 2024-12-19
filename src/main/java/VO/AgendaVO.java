/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VO;

import Persistencia.Entity.Agenda;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author rafael
 */
public class AgendaVO {
    private Integer id;
    private LocalDate data; // Alterado para LocalDate
    private LocalTime hora;
    private Byte sala;

    private PacienteVO pacienteVO;
    private EstagiarioVO estagiarioVO;

    private Integer atendimentoId; // Pode ser nulo

    // Construtor completo
    public AgendaVO(Integer id, LocalDate data, LocalTime hora, Byte sala, PacienteVO pacienteVO, EstagiarioVO estagiarioVO, Integer atendimentoId) {
        this.id = id;
        this.data = data;
        this.hora = hora;
        this.sala = sala;
        this.pacienteVO = pacienteVO;
        this.estagiarioVO = estagiarioVO;
        this.atendimentoId = atendimentoId;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the data
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Date data) {
        this.data = data.toLocalDate(); // Convertendo de Date para LocalDate
    }

    /**
     * @return the hora
     */
    public LocalTime getHora() {
        return hora;
    }

    /**
     * @param hora the hora to set
     */
    public void setHora(Time hora) {
        this.hora = hora.toLocalTime(); // Convertendo de Time para LocalTime
    }

    /**
     * @return the sala
     */
    public Byte getSala() {
        return sala;
    }

    /**
     * @param sala the sala to set
     */
    public void setSala(Byte sala) {
        this.sala = sala;
    }

    public PacienteVO getPacienteVO() {
        return pacienteVO;
    }

    public void setPacienteVO(PacienteVO pacienteVO) {
        this.pacienteVO = pacienteVO;
    }

    public EstagiarioVO getEstagiarioVO() {
        return estagiarioVO;
    }

    public void setEstagiarioVO(EstagiarioVO estagiarioVO) {
        this.estagiarioVO = estagiarioVO;
    }

    public Integer getAtendimentoId() {
        return atendimentoId;
    }

    public void setAtendimentoId(Integer atendimentoId) {
        this.atendimentoId = atendimentoId;
    }

    // Método de conversão de VO para Entity
    public Agenda toEntity() {
        var agenda = new Agenda();
        agenda.setId(this.getId());
        agenda.setData(this.getData());
        agenda.setHora(this.getHora());
        agenda.setSala(this.getSala());

        // Converte PacienteVO para Paciente
        if (this.getPacienteVO() != null) {
            agenda.setPaciente(this.getPacienteVO().toEntity());
        }

        // Converte EstagiarioVO para Estagiario
        if (this.getEstagiarioVO() != null) {
            agenda.setEstagiario(this.getEstagiarioVO().toEntity());
        }

        // Associa atendimento por ID, se existir
        if (this.getAtendimentoId() != null && this.getAtendimentoId() > 0) {
            var atendimento = new Persistencia.Entity.Atendimento();
            atendimento.setId(this.getAtendimentoId());
            agenda.setAtendimento(atendimento);
        }

        return agenda;
    }

    // Método para atualizar uma entidade existente
    public void updateEntity(Agenda agenda) {
        agenda.setData(this.getData());
        agenda.setHora(this.getHora());
        agenda.setSala(this.getSala());

        // Atualiza o Paciente, se necessário
        if (this.getPacienteVO() != null) {
            agenda.setPaciente(this.getPacienteVO().toEntity());
        }

        // Atualiza o Estagiário, se necessário
        if (this.getEstagiarioVO() != null) {
            agenda.setEstagiario(this.getEstagiarioVO().toEntity());
        }

        // Atualiza o atendimento por ID, se existir
        if (this.getAtendimentoId() != null && this.getAtendimentoId() > 0) {
            var atendimento = new Persistencia.Entity.Atendimento();
            atendimento.setId(this.getAtendimentoId());
            agenda.setAtendimento(atendimento);
        }
    }

    @Override
    public String toString() {
        return "AgendaVO{" +
                "id=" + id +
                ", data=" + data +
                ", hora=" + hora +
                ", sala=" + sala +
                ", pacienteVO=" + (pacienteVO != null ? pacienteVO.getPaciente() : null) +
                ", estagiarioVO=" + (estagiarioVO != null ? estagiarioVO.getNomeCompleto() : null) +
                ", atendimentoId=" + atendimentoId +
                '}';
    }
}
