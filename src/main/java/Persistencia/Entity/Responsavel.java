package Persistencia.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "responsavel")
public class Responsavel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "vinculo", nullable = false, length = 30)
    private String vinculo;

    @Column(name = "telefone", nullable = true, length = 20)
    private String telefone;

    @Column(name = "telefone_contato",  nullable = true, length = 20)
    private String telefoneContato;

    @Column(name = "grau_instrucao", nullable = true, length = 50)
    private String grauInstrucao;

    @Column(name = "profissao", nullable = true, length = 100)
    private String profissao;

    @Column(name = "genero", nullable = true, length = 50)
    private String genero;

    @Column(name = "raca_cor_etnia", nullable = true, length = 50)
    private String racaCorEtnia;

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

    public String getVinculo() {
        return vinculo;
    }

    public void setVinculo(String vinculo) {
        this.vinculo = vinculo;
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

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getRacaCorEtnia() {
        return racaCorEtnia;
    }

    public void setRacaCorEtnia(String racaCorEtnia) {
        this.racaCorEtnia = racaCorEtnia;
    }

}