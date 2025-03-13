package VO;

import Persistencia.Entity.Agenda;
import Persistencia.Entity.Atendimento;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class AgendaVO {
    private Integer id;
    private LocalDate data; // Alterado para LocalDate
    private LocalTime hora;
    private Byte sala;

    private PacienteVO pacienteVO;
    private EstagiarioVO estagiarioVO;

    private AtendimentoVO atendimentoVO; // Pode ser nulo

    // Construtor completo
    public AgendaVO(Integer id, LocalDate data, LocalTime hora, Byte sala, PacienteVO pacienteVO, EstagiarioVO estagiarioVO, AtendimentoVO atendimentoVO) {
        this.id = id;
        this.data = data;
        this.hora = hora;
        this.sala = sala;
        this.pacienteVO = pacienteVO;
        this.estagiarioVO = estagiarioVO;
        this.atendimentoVO = atendimentoVO;
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

    public AtendimentoVO getAtendimentoId() {
        return atendimentoVO;
    }

    public void setAtendimentoId(AtendimentoVO atendimentoVO) {
        this.atendimentoVO = atendimentoVO;
    }
    
    // Método de conversão de Entity para VO
    public static AgendaVO fromEntity(Agenda entity) {
        return new AgendaVO(
            entity.getId(),
            entity.getData(),
            entity.getHora(),
            entity.getSala(),
            entity.getPaciente() != null ? PacienteVO.fromEntity(entity.getPaciente()) : null,
            entity.getEstagiario() != null ? EstagiarioVO.fromEntity(entity.getEstagiario()) : null,
            entity.getAtendimento() != null ? AtendimentoVO.fromEntity(entity.getAtendimento()) : null
        );
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
        if (this.getAtendimentoId() != null 
                && this.getAtendimentoId().getId() != null 
                && this.getAtendimentoId().getId() > 0) {
            Atendimento atendimento = new Atendimento();
            atendimento.setId(this.getAtendimentoId().getId());
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
        if (this.getAtendimentoId() != null 
                && this.getAtendimentoId().getId() != null 
                && this.getAtendimentoId().getId() > 0) {
            Atendimento atendimento = new Atendimento();
            atendimento.setId(this.getAtendimentoId().getId());
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
                ", atendimentoVO=" + (atendimentoVO != null ? atendimentoVO.getId() : null) +
                '}';
    }
}
