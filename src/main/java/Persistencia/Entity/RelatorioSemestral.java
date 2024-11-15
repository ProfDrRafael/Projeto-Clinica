package Persistencia.Entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity
@Table(name = "relatorio_semestral")
public class RelatorioSemestral {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Lob
    @Column(name = "conteudo", nullable = false)
    private String conteudo;

    @Lob
    @Column(name = "semestre", nullable = false)
    private String semestre;

    @Column(name = "ano", nullable = false)
    private Integer ano;

    @Column(name = "estagiario_id", nullable = false)
    private Integer estagiarioId;

    @Column(name = "orientador_id", nullable = false)
    private Integer orientadorId;

    @ColumnDefault("current_timestamp()")
    @Column(name = "data_criacao", nullable = false)
    private Instant dataCriacao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getEstagiarioId() {
        return estagiarioId;
    }

    public void setEstagiarioId(Integer estagiarioId) {
        this.estagiarioId = estagiarioId;
    }

    public Integer getOrientadorId() {
        return orientadorId;
    }

    public void setOrientadorId(Integer orientadorId) {
        this.orientadorId = orientadorId;
    }

    public Instant getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Instant dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

}