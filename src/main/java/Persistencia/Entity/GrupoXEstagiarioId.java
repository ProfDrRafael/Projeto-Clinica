package Persistencia.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class GrupoXEstagiarioId implements java.io.Serializable {
    private static final long serialVersionUID = 8723539040595386513L;
    @Column(name = "grupo_id", nullable = false)
    private Integer grupoId;

    @Column(name = "estagiario_id", nullable = false)
    private Integer estagiarioId;

    public Integer getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Integer grupoId) {
        this.grupoId = grupoId;
    }

    public Integer getEstagiarioId() {
        return estagiarioId;
    }

    public void setEstagiarioId(Integer estagiarioId) {
        this.estagiarioId = estagiarioId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GrupoXEstagiarioId entity = (GrupoXEstagiarioId) o;
        return Objects.equals(this.estagiarioId, entity.estagiarioId) &&
                Objects.equals(this.grupoId, entity.grupoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(estagiarioId, grupoId);
    }

}