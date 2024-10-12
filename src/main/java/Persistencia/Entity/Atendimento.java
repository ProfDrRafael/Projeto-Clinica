package Persistencia.Entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "atendimento", schema = "clinicapsicologia")
public class Atendimento {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Column(name = "hora", nullable = false)
    private LocalTime hora;

    @ColumnDefault("0")
    @Column(name = "preenchido")
    private Boolean preenchido;

    @ColumnDefault("0")
    @Column(name = "comparecimento")
    private Boolean comparecimento;

    @Column(name = "justificativa")
    private String justificativa;

    @Lob
    @Column(name = "relato_atendimento")
    private String relatoAtendimento;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "prontuario_eletronico_id", nullable = false)
    private Persistencia.Entity.Prontuario prontuarioEletronico;

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

    public Boolean getPreenchido() {
        return preenchido;
    }

    public void setPreenchido(Boolean preenchido) {
        this.preenchido = preenchido;
    }

    public Boolean getComparecimento() {
        return comparecimento;
    }

    public void setComparecimento(Boolean comparecimento) {
        this.comparecimento = comparecimento;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public String getRelatoAtendimento() {
        return relatoAtendimento;
    }

    public void setRelatoAtendimento(String relatoAtendimento) {
        this.relatoAtendimento = relatoAtendimento;
    }

    public Persistencia.Entity.Prontuario getProntuarioEletronico() {
        return prontuarioEletronico;
    }

    public void setProntuarioEletronico(Persistencia.Entity.Prontuario prontuarioEletronico) {
        this.prontuarioEletronico = prontuarioEletronico;
    }

    public Persistencia.Entity.Estagiario getEstagiario() {
        return estagiario;
    }

    public void setEstagiario(Persistencia.Entity.Estagiario estagiario) {
        this.estagiario = estagiario;
    }

}