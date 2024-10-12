package Persistencia.Entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "estagiario", schema = "clinicapsicologia")
public class Estagiario {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nome", nullable = false, length = 150)
    private String nome;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    @ColumnDefault("1")
    @Column(name = "ativo")
    private Boolean ativo;

    @Lob
    @Column(name = "semestre_inicio", nullable = false)
    private String semestreInicio;

    @Column(name = "ano_inicio", nullable = false)
    private Integer anoInicio;

    @Lob
    @Column(name = "semestre_fim")
    private String semestreFim;

    @Column(name = "ano_fim")
    private Integer anoFim;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orientador_id")
    private Persistencia.Entity.Orientador orientador;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getSemestreInicio() {
        return semestreInicio;
    }

    public void setSemestreInicio(String semestreInicio) {
        this.semestreInicio = semestreInicio;
    }

    public Integer getAnoInicio() {
        return anoInicio;
    }

    public void setAnoInicio(Integer anoInicio) {
        this.anoInicio = anoInicio;
    }

    public String getSemestreFim() {
        return semestreFim;
    }

    public void setSemestreFim(String semestreFim) {
        this.semestreFim = semestreFim;
    }

    public Integer getAnoFim() {
        return anoFim;
    }

    public void setAnoFim(Integer anoFim) {
        this.anoFim = anoFim;
    }

    public Persistencia.Entity.Orientador getOrientador() {
        return orientador;
    }

    public void setOrientador(Persistencia.Entity.Orientador orientador) {
        this.orientador = orientador;
    }

}