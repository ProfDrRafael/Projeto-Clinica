package Persistencia.Entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Entity
@Table(name = "paciente")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nome", nullable = false, length = 150)
    private String nome;

    @Column(name = "telefone", nullable = false, length = 20)
    private String telefone;

    @Column(name = "telefone_contato", length = 20)
    private String telefoneContato;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "naturalidade", length = 50)
    private String naturalidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nacionalidade_id")
    private Persistencia.Entity.Pais nacionalidade;

    @Column(name = "raca_cor_etnia", length = 50)
    private String racaCorEtnia;

    @ColumnDefault("'Prefiro não informar'")
    @Lob
    @Column(name = "genero")
    private String genero;
    
    @ColumnDefault("'Heterossexual'")
    @Lob
    @Column(name = "orientacao_sexual")
    private String orientacao_sexual;

    @Lob
    @Column(name = "estado_civil")
    private String estadoCivil;

    @Column(name = "grau_instrucao", length = 50)
    private String grauInstrucao;

    @Column(name = "profissao", length = 100)
    private String profissao;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "responsavel_id", nullable = true)
    private Persistencia.Entity.Responsavel responsavel;

    @Column(name = "disponibilidade")
    private String disponibilidade;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "endereco_id", nullable = false)
    private Endereco endereco;

    @ColumnDefault("curdate()")
    @Column(name = "data_inscricao", nullable = false)
    private LocalDate dataInscricao;

    @ColumnDefault("1")
    @Column(name = "ativo")
    private Boolean ativo;

    @ColumnDefault("0")
    @Column(name = "atendido")
    private Boolean atendido;

    @ColumnDefault("0")
    @Column(name = "grupo")
    private Byte grupo;

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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTelefoneContato() {
        return telefoneContato;
    }

    public void setTelefoneContato(String telefoneContato) {
        this.telefoneContato = telefoneContato;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public Persistencia.Entity.Pais getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(Persistencia.Entity.Pais nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getRacaCorEtnia() {
        return racaCorEtnia;
    }

    public void setRacaCorEtnia(String racaCorEtnia) {
        this.racaCorEtnia = racaCorEtnia;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
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

    public Persistencia.Entity.Responsavel getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
    }

    public String getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(String disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public LocalDate getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(LocalDate dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean getAtendido() {
        return atendido;
    }

    public void setAtendido(Boolean atendido) {
        this.atendido = atendido;
    }

    public Byte getGrupo() {
        return grupo;
    }

    public void setGrupo(Byte grupo) {
        this.grupo = grupo;
    }

    

    /**
     * @return the orientacao_sexual
     */
    public String getOrientacao_sexual() {
        return orientacao_sexual;
    }

    /**
     * @param orientacao_sexual the orientacao_sexual to set
     */
    public void setOrientacao_sexual(String orientacao_sexual) {
        this.orientacao_sexual = orientacao_sexual;
    }

    @Override
    public String toString() {
        return this.nome; // Retorna apenas o nome do estagiário
    }
}