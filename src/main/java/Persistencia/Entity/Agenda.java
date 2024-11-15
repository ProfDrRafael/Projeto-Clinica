package Persistencia.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "agenda")
public class Agenda {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Column(name = "hora", nullable = false)
    private LocalTime hora;

    @Column(name = "sala", nullable = false)
    private Byte sala;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "atendimento_id", nullable = false)
    private Persistencia.Entity.Atendimento atendimento;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Persistencia.Entity.Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "estagiario_id", nullable = false)
    private Persistencia.Entity.Estagiario estagiario;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public Byte getSala() {
        return sala;
    }

    public void setSala(Byte sala) {
        this.sala = sala;
    }

    public Persistencia.Entity.Atendimento getAtendimento() {
        return atendimento;
    }

    public void setAtendimento(Persistencia.Entity.Atendimento atendimento) {
        this.atendimento = atendimento;
    }

    public Persistencia.Entity.Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Persistencia.Entity.Paciente paciente) {
        this.paciente = paciente;
    }

    public Persistencia.Entity.Estagiario getEstagiario() {
        return estagiario;
    }

    public void setEstagiario(Persistencia.Entity.Estagiario estagiario) {
        this.estagiario = estagiario;
    }

}