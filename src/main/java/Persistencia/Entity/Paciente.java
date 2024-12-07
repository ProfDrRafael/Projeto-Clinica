package Persistencia.Entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Entity
@Table(name = "paciente")
public class Paciente {
    
    
    public enum Genero {
        HETEROSSEXUAL("Heterossexual"),
        HOMOSSEXUAL("Homossexual"),
        BISSEXUAL("Bissexual"),
        ASSEXUAL("Assexual"),
        PANSEXUAL("Pansexual"),
        QUEER("Queer"),
        OUTRO("Outro"),
        PREFIRONAOINFORMAR("Prefiro não informar");

        private final String descricao;

        Genero(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
        
        // Método para obter o enum a partir de uma string
        public static Genero fromString(String value) {
            for (Genero genero : Genero.values()) {
                if (genero.name().equalsIgnoreCase(value)) {
                    return genero;
                }
            }
            throw new IllegalArgumentException("Gênero inválido: " + value);
        }
    }
    
    public enum EstadoCivil {
        SOLTEIRO("Solteiro(a)"),
        CASADO("Casado(a)"),
        DIVORCIADO("Divorciado(a)"),
        VIUVO("Viúvo(a)");

        private final String descricao;

        EstadoCivil(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
        
        // Método para obter o enum a partir de uma string
        public static EstadoCivil fromString(String value) {
            for (EstadoCivil estadoCivil : EstadoCivil.values()) {
                if (estadoCivil.name().equalsIgnoreCase(value)) {
                    return estadoCivil;
                }
            }
            throw new IllegalArgumentException("Estado civil inválido: " + value);
        }
    }
    
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
    private Pais nacionalidade;

    @Column(name = "raca_cor_etnia", length = 50)
    private String racaCorEtnia;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero", length = 50)
    private Genero genero;

    @Column(name = "encaminhado_por", length = 100)
    private String encaminhadoPor;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_civil", length = 50)
    private EstadoCivil estadoCivil;

    @Column(name = "grau_instrucao", length = 50)
    private String grauInstrucao;

    @Column(name = "profissao", length = 100)
    private String profissao;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "responsavel_id")
//    private Responsavel responsavel;
    
    @Column(name = "responsavel_id", length = 255)
    private String responsavel;

    @Column(name = "disponibilidade", length = 255)
    private String disponibilidade;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "endereco_id", nullable = false)
    private Endereco endereco;

    @ColumnDefault("curdate()")
    @Column(name = "data_inscricao", nullable = false)
    private LocalDate dataInscricao;

    @ColumnDefault("1")
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @ColumnDefault("0")
    @Column(name = "atendido", nullable = false)
    private Boolean atendido;

    @ColumnDefault("0")
    @Column(name = "grupo")
    private Byte grupo;

    @Column(name = "orientacao_sexual", length = 50)
    private String orientacaoSexual;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estagiario_id")
    private Estagiario estagiario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orientador_id")
    private Orientador orientador;

    @Column(name = "ano")
    private Integer ano;

    @Column(name = "semestre_fim")
    private Integer semestreFim;

    @Column(name = "ano_fim")
    private Integer anoFim;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the telefone
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * @param telefone the telefone to set
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * @return the telefoneContato
     */
    public String getTelefoneContato() {
        return telefoneContato;
    }

    /**
     * @param telefoneContato the telefoneContato to set
     */
    public void setTelefoneContato(String telefoneContato) {
        this.telefoneContato = telefoneContato;
    }

    /**
     * @return the dataNascimento
     */
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    /**
     * @param dataNascimento the dataNascimento to set
     */
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    /**
     * @return the naturalidade
     */
    public String getNaturalidade() {
        return naturalidade;
    }

    /**
     * @param naturalidade the naturalidade to set
     */
    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    /**
     * @return the nacionalidade
     */
    public Pais getNacionalidade() {
        return nacionalidade;
    }

    /**
     * @param nacionalidade the nacionalidade to set
     */
    public void setNacionalidade(Pais nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    /**
     * @return the racaCorEtnia
     */
    public String getRacaCorEtnia() {
        return racaCorEtnia;
    }

    /**
     * @param racaCorEtnia the racaCorEtnia to set
     */
    public void setRacaCorEtnia(String racaCorEtnia) {
        this.racaCorEtnia = racaCorEtnia;
    }

    /**
     * @return the genero
     */
    public Genero getGenero() {
        return genero;
    }

    /**
     * @param genero the genero to set
     */
    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    /**
     * @return the encaminhadoPor
     */
    public String getEncaminhadoPor() {
        return encaminhadoPor;
    }

    /**
     * @param encaminhadoPor the encaminhadoPor to set
     */
    public void setEncaminhadoPor(String encaminhadoPor) {
        this.encaminhadoPor = encaminhadoPor;
    }

    /**
     * @return the estadoCivil
     */
    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    /**
     * @param estadoCivil the estadoCivil to set
     */
    public void setEstadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    /**
     * @return the grauInstrucao
     */
    public String getGrauInstrucao() {
        return grauInstrucao;
    }

    /**
     * @param grauInstrucao the grauInstrucao to set
     */
    public void setGrauInstrucao(String grauInstrucao) {
        this.grauInstrucao = grauInstrucao;
    }

    /**
     * @return the profissao
     */
    public String getProfissao() {
        return profissao;
    }

    /**
     * @param profissao the profissao to set
     */
    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    /**
     * @return the responsavel
     */
    public String getResponsavel() {
        return responsavel;
    }

    /**
     * @param responsavel the responsavel to set
     */
    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    /**
     * @return the disponibilidade
     */
    public String getDisponibilidade() {
        return disponibilidade;
    }

    /**
     * @param disponibilidade the disponibilidade to set
     */
    public void setDisponibilidade(String disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    /**
     * @return the endereco
     */
    public Endereco getEndereco() {
        return endereco;
    }

    /**
     * @param endereco the endereco to set
     */
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    /**
     * @return the dataInscricao
     */
    public LocalDate getDataInscricao() {
        return dataInscricao;
    }

    /**
     * @param dataInscricao the dataInscricao to set
     */
    public void setDataInscricao(LocalDate dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    /**
     * @return the ativo
     */
    public Boolean getAtivo() {
        return ativo;
    }

    /**
     * @param ativo the ativo to set
     */
    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    /**
     * @return the atendido
     */
    public Boolean getAtendido() {
        return atendido;
    }

    /**
     * @param atendido the atendido to set
     */
    public void setAtendido(Boolean atendido) {
        this.atendido = atendido;
    }

    /**
     * @return the grupo
     */
    public Byte getGrupo() {
        return grupo;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(Byte grupo) {
        this.grupo = grupo;
    }

    /**
     * @return the orientacaoSexual
     */
    public String getOrientacaoSexual() {
        return orientacaoSexual;
    }

    /**
     * @param orientacaoSexual the orientacaoSexual to set
     */
    public void setOrientacaoSexual(String orientacaoSexual) {
        this.orientacaoSexual = orientacaoSexual;
    }

    /**
     * @return the estagiario
     */
    public Estagiario getEstagiario() {
        return estagiario;
    }

    /**
     * @param estagiario the estagiario to set
     */
    public void setEstagiario(Estagiario estagiario) {
        this.estagiario = estagiario;
    }

    /**
     * @return the orientador
     */
    public Orientador getOrientador() {
        return orientador;
    }

    /**
     * @param orientador the orientador to set
     */
    public void setOrientador(Orientador orientador) {
        this.orientador = orientador;
    }

    /**
     * @return the ano
     */
    public Integer getAno() {
        return ano;
    }

    /**
     * @param ano the ano to set
     */
    public void setAno(Integer ano) {
        this.ano = ano;
    }

    /**
     * @return the semestreFim
     */
    public Integer getSemestreFim() {
        return semestreFim;
    }

    /**
     * @param semestreFim the semestreFim to set
     */
    public void setSemestreFim(Integer semestreFim) {
        this.semestreFim = semestreFim;
    }

    /**
     * @return the anoFim
     */
    public Integer getAnoFim() {
        return anoFim;
    }

    /**
     * @param anoFim the anoFim to set
     */
    public void setAnoFim(Integer anoFim) {
        this.anoFim = anoFim;
    }

    

}