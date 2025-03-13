package Persistencia.Entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "estagiario")
public class Estagiario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "ano", nullable = false)
    private Integer ano;

    @Lob
    @Column(name = "semestre_fim")
    private String semestreFim;

    @ManyToOne(fetch = FetchType.EAGER)
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

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getSemestreFim() {
        return semestreFim;
    }

    public void setSemestreFim(String semestreFim) {
        this.semestreFim = semestreFim;
    }

    public Persistencia.Entity.Orientador getOrientador() {
        return orientador;
    }

    public void setOrientador(Persistencia.Entity.Orientador orientador) {
        this.orientador = orientador;
    }

    @Override
    public String toString() {
        return this.nome; // Retorna apenas o nome do estagiário
    }

}