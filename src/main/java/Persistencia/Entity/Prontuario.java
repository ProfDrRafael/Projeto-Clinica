package Persistencia.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "prontuario", schema = "clinicapsicologia")
public class Prontuario {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "observacoes", nullable = false, length = 150)
    private String observacoes;

    @Column(name = "queixa_inicial", nullable = false, length = 1500)
    private String queixaInicial;

    @Column(name = "grau_instrucao", length = 50)
    private String grauInstrucao;

    @Column(name = "profissao", length = 100)
    private String profissao;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "estagiario_id", nullable = false)
    private Estagiario estagiario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orientador_id", nullable = false)
    private Orientador orientador;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getQueixaInicial() {
        return queixaInicial;
    }

    public void setQueixaInicial(String queixaInicial) {
        this.queixaInicial = queixaInicial;
    }

    public String getGrauInstrucao() {
        return grauInstrucao;
    }

    public void setGrauInstrucao(String grauInstrucao) {
        this.grauInstrucao = grauInstrucao;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Estagiario getEstagiario() {
        return estagiario;
    }

    public void setEstagiario(Estagiario estagiario) {
        this.estagiario = estagiario;
    }

    public Orientador getOrientador() {
        return orientador;
    }

    public void setOrientador(Orientador orientador) {
        this.orientador = orientador;
    }

}