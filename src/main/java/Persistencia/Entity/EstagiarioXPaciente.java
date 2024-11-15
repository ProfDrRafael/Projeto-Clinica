package Persistencia.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "estagiario_x_paciente")
public class EstagiarioXPaciente {
    @EmbeddedId
    private EstagiarioXPacienteId id;

    @MapsId("estagiarioId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "estagiario_id", nullable = false)
    private Estagiario estagiario;

    @MapsId("pacienteId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Persistencia.Entity.Paciente paciente;

    public EstagiarioXPacienteId getId() {
        return id;
    }

    public void setId(EstagiarioXPacienteId id) {
        this.id = id;
    }

    public Estagiario getEstagiario() {
        return estagiario;
    }

    public void setEstagiario(Estagiario estagiario) {
        this.estagiario = estagiario;
    }

    public Persistencia.Entity.Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Persistencia.Entity.Paciente paciente) {
        this.paciente = paciente;
    }

}