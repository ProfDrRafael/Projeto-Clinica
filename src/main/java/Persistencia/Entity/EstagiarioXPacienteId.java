package Persistencia.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class EstagiarioXPacienteId implements java.io.Serializable {
    private static final long serialVersionUID = 2833444078215048243L;
    @Column(name = "estagiario_id", nullable = false)
    private Integer estagiarioId;

    @Column(name = "paciente_id", nullable = false)
    private Integer pacienteId;

    public Integer getEstagiarioId() {
        return estagiarioId;
    }

    public void setEstagiarioId(Integer estagiarioId) {
        this.estagiarioId = estagiarioId;
    }

    public Integer getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Integer pacienteId) {
        this.pacienteId = pacienteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EstagiarioXPacienteId entity = (EstagiarioXPacienteId) o;
        return Objects.equals(this.estagiarioId, entity.estagiarioId) &&
                Objects.equals(this.pacienteId, entity.pacienteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(estagiarioId, pacienteId);
    }

}