package Persistencia.Entity;

import Persistencia.Entity.Endereco;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Entity
@Table(name = "paciente", schema = "clinicapsicologia")
public class Paciente {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "email", nullable = false, length = 150)
    private String email;

    @Column(name = "nome", nullable = false, length = 150)
    private String nome;

    @Column(name = "telefone", nullable = false, length = 20)
    private String telefone;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "naturalidade", length = 50)
    private String naturalidade;

    @Column(name = "nacionalidade", length = 50)
    private String nacionalidade;

    @Column(name = "genero", length = 20)
    private String genero;

    @Column(name = "estado_civil", length = 20)
    private String estadoCivil;

    @Column(name = "nome_responsavel", length = 100)
    private String nomeResponsavel;

    @Column(name = "vinculo_responsavel", length = 30)
    private String vinculoResponsavel;

    @Column(name = "telefone_responsavel", length = 20)
    private String telefoneResponsavel;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
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

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public String getVinculoResponsavel() {
        return vinculoResponsavel;
    }

    public void setVinculoResponsavel(String vinculoResponsavel) {
        this.vinculoResponsavel = vinculoResponsavel;
    }

    public String getTelefoneResponsavel() {
        return telefoneResponsavel;
    }

    public void setTelefoneResponsavel(String telefoneResponsavel) {
        this.telefoneResponsavel = telefoneResponsavel;
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

}