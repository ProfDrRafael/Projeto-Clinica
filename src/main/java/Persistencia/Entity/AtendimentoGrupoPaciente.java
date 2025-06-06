package Persistencia.Entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "atendimento_grupo_paciente")
public class AtendimentoGrupoPaciente {
    @EmbeddedId
    private AtendimentoGrupoPacienteId id;

    @MapsId("atendimentoGrupoId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "atendimento_grupo_id", nullable = false)
    private AtendimentoGrupo atendimentoGrupo;

    @MapsId("pacienteId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ColumnDefault("0")
    @Column(name = "compareceu")
    private Boolean compareceu;

    @Column(name = "justificativa")
    private String justificativa;

    public AtendimentoGrupoPacienteId getId() {
        return id;
    }

    public void setId(AtendimentoGrupoPacienteId id) {
        this.id = id;
    }

    public AtendimentoGrupo getAtendimentoGrupo() {
        return atendimentoGrupo;
    }

    public void setAtendimentoGrupo(AtendimentoGrupo atendimentoGrupo) {
        this.atendimentoGrupo = atendimentoGrupo;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Boolean getCompareceu() {
        return compareceu;
    }

    public void setCompareceu(Boolean compareceu) {
        this.compareceu = compareceu;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

}