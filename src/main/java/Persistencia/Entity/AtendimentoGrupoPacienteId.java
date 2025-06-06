package Persistencia.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class AtendimentoGrupoPacienteId implements java.io.Serializable {
    private static final long serialVersionUID = -7837560173500268000L;
    @Column(name = "atendimento_grupo_id", nullable = false)
    private Integer atendimentoGrupoId;

    @Column(name = "paciente_id", nullable = false)
    private Integer pacienteId;

    public Integer getAtendimentoGrupoId() {
        return atendimentoGrupoId;
    }

    public void setAtendimentoGrupoId(Integer atendimentoGrupoId) {
        this.atendimentoGrupoId = atendimentoGrupoId;
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
        AtendimentoGrupoPacienteId entity = (AtendimentoGrupoPacienteId) o;
        return Objects.equals(this.atendimentoGrupoId, entity.atendimentoGrupoId) &&
                Objects.equals(this.pacienteId, entity.pacienteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(atendimentoGrupoId, pacienteId);
    }

}