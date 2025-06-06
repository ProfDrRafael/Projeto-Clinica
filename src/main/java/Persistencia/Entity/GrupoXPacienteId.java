package Persistencia.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class GrupoXPacienteId implements java.io.Serializable {
    private static final long serialVersionUID = -9212619385223470775L;
    @Column(name = "grupo_id", nullable = false)
    private Integer grupoId;

    @Column(name = "paciente_id", nullable = false)
    private Integer pacienteId;

    public Integer getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Integer grupoId) {
        this.grupoId = grupoId;
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
        GrupoXPacienteId entity = (GrupoXPacienteId) o;
        return Objects.equals(this.grupoId, entity.grupoId) &&
                Objects.equals(this.pacienteId, entity.pacienteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(grupoId, pacienteId);
    }

}