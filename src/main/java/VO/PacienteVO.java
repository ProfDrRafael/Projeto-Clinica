/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VO;

import Persistencia.Dao.EstagiarioDAO;
import Persistencia.Dao.OrientadorDAO;
import Persistencia.Dao.PaisDAO;
import Persistencia.Entity.Endereco;
import Persistencia.Entity.Estagiario;
import Persistencia.Entity.Orientador;
import Persistencia.Entity.Paciente;
import Persistencia.Entity.Pais;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author rafael
 */
public class PacienteVO {
    OrientadorDAO orientadorDAO = new OrientadorDAO();
    EstagiarioDAO estagiarioDAO = new EstagiarioDAO();
    
    private String genero;
    private String celularContato;
    private String celular;
    private String paciente;
    private String dataNascimento;
    private LocalDate dataInscricao;
    private String instrucao;
    private String profissao;
    private String estadoCivil;
    private String raca_cor_etnia;
    private String orientacao;
    private Integer nacionalidade;
    private String disponibilidade;
    private Integer estagiario;
    private Integer orientador;
    private Endereco endereco;
    private String responsavel;
    private boolean atendido;
    private boolean ativo;

    // Construtor com todos os campos como argumentos
    public PacienteVO(
        String genero,
        String celularContato,
        String celular,
        String paciente,
        String dataNascimento,
        LocalDate dataInscricao,
        String instrucao,
        String profissao,
        String estadoCivil,
        String raca_cor_etnia,
        String orientacao,
        Integer nacionalidade,
        String disponibilidade,
        Integer estagiario,
        Integer orientador,
        Endereco endereco,
        String responsavel,
        boolean atendido,
        boolean ativo
    ) {
        this.genero = genero;
        this.celularContato = celularContato;
        this.celular = celular;
        this.paciente = paciente;
        this.dataNascimento = dataNascimento;
        this.dataInscricao = dataInscricao;
        this.instrucao = instrucao;
        this.profissao = profissao;
        this.estadoCivil = estadoCivil;
        this.raca_cor_etnia = raca_cor_etnia;
        this.orientacao = orientacao;
        this.nacionalidade = nacionalidade;
        this.disponibilidade = disponibilidade;
        this.estagiario = estagiario;
        this.orientador = orientador;
        this.endereco = endereco;
        this.responsavel = responsavel;
        this.atendido = atendido;
        this.ativo = ativo;
    }

    public PacienteVO() {
        System.out.println("VO.PacienteVO.<init>()");
    }

    /**
     * @return the genero
     */
    public String getGenero() {
        return genero;
    }

    /**
     * @param genero the genero to set
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }

    /**
     * @return the celularContato
     */
    public String gettelefone_contatoContato() {
        return celularContato;
    }

    /**
     * @param celularContato the celularContato to set
     */
    public void settelefone_contatoContato(String celularContato) {
        this.celularContato = celularContato;
    }

    /**
     * @return the celular
     */
    public String gettelefone_contato() {
        return celular;
    }

    /**
     * @param celular the celular to set
     */
    public void settelefone_contato(String celular) {
        this.celular = celular;
    }

    /**
     * @return the paciente
     */
    public String getPaciente() {
        return paciente;
    }

    /**
     * @param paciente the paciente to set
     */
    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    /**
     * @return the dataNascimento
     */
    public String getDataNascimento() {
        return dataNascimento;
    }

    /**
     * @param dataNascimento the dataNascimento to set
     */
    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    /**
     * @return the instrucao
     */
    public String getInstrucao() {
        return instrucao;
    }

    /**
     * @param instrucao the instrucao to set
     */
    public void setInstrucao(String instrucao) {
        this.instrucao = instrucao;
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
     * @return the estadoCivil
     */
    public String getEstadoCivil() {
        return estadoCivil;
    }

    /**
     * @param estadoCivil the estadoCivil to set
     */
    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    /**
     * @return the raca_cor_etnia
     */
    public String getRaca_cor_etnia() {
        return raca_cor_etnia;
    }

    /**
     * @param raca_cor_etnia the raca_cor_etnia to set
     */
    public void setRaca_cor_etnia(String raca_cor_etnia) {
        this.raca_cor_etnia = raca_cor_etnia;
    }

    /**
     * @return the orientacao
     */
    public String getOrientacao() {
        return orientacao;
    }

    /**
     * @param orientacao the orientacao to set
     */
    public void setOrientacao(String orientacao) {
        this.orientacao = orientacao;
    }

    /**
     * @return the nacionalidade
     */
    public Integer getNacionalidade() {
        return nacionalidade;
    }

    /**
     * @param nacionalidade the nacionalidade to set
     */
    public void setNacionalidade(Integer nacionalidade) {
        this.nacionalidade = nacionalidade;
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
     * @return the estagiario
     */
    public Integer getEstagiario() {
        return estagiario;
    }

    /**
     * @param estagiario the estagiario to set
     */
    public void setEstagiario(Integer estagiario) {
        this.estagiario = estagiario;
    }

    /**
     * @return the orientador
     */
    public Integer getOrientador() {
        return orientador;
    }

    /**
     * @param orientador the orientador to set
     */
    public void setOrientador(Integer orientador) {
        this.orientador = orientador;
    }
    
    /**
     * @return the endereco_id
     */
    public Endereco getEnderco() {
        return endereco;
    }

    /**
     * @param responsavel the responsavel to set
     */
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
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
     * @return the atendido
     */
    public boolean isAtendido() {
        return atendido;
    }

    /**
     * @param atendido the atendido to set
     */
    public void setAtendido(boolean atendido) {
        this.atendido = atendido;
    }
    
    // Método de conversão de Entity para VO
    public static PacienteVO fromEntity(Paciente entity) {
        return new PacienteVO(
            entity.getGenero().toString(),
            entity.getTelefoneContato(),
            entity.getTelefone(),
            entity.getNome(),
            entity.getDataNascimento().toString(),
            entity.getDataInscricao(),
            entity.getGrauInstrucao(),
            entity.getProfissao(),
            entity.getEstadoCivil().toString(),
            entity.getRacaCorEtnia(),
            entity.getOrientacaoSexual(),
            entity.getNacionalidade().getId(),
            entity.getDisponibilidade(),
            entity.getEstagiario().getId(),
            entity.getOrientador().getId(),
            entity.getEndereco(),
            entity.getResponsavel(),
            entity.getAtendido(),
            entity.getAtivo()
        );
    }

    // Método de conversão de VO para Entity
    public Paciente toEntity() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dataNascimento = LocalDate.parse(this.dataNascimento, formatter);
        
        Pais paisResultado = PaisDAO.buscarPorId(this.nacionalidade);
                
        Orientador orientadorResultado = orientadorDAO.buscarPorId(this.orientador);
        
        Estagiario estagiarioResultado = estagiarioDAO.buscarPorId(this.estagiario);
        
        
        var paciente = new Paciente();
        paciente.setTelefoneContato(this.celularContato);
        paciente.setTelefone(this.celular);
        paciente.setNome(this.paciente);
        paciente.setDataNascimento(dataNascimento);
        paciente.setDataInscricao(dataInscricao);
        paciente.setGrauInstrucao(this.instrucao);
        paciente.setProfissao(this.profissao);
        paciente.setRacaCorEtnia(this.raca_cor_etnia);
        paciente.setOrientacaoSexual(this.orientacao);
        paciente.setNacionalidade(paisResultado);
        paciente.setDisponibilidade(this.disponibilidade);
        paciente.setEstagiario(estagiarioResultado);
        paciente.setOrientador(orientadorResultado);
        paciente.setEndereco(endereco);
        paciente.setResponsavel(this.responsavel);
        paciente.setAtendido(this.atendido);
        paciente.setAtivo(true);
        
        // Conversão da string para enum
        try {
            paciente.setGenero(Paciente.Genero.fromString(this.genero));
        } catch (IllegalArgumentException e) {
            System.err.println("Erro: " + e.getMessage());
            // Você pode lançar a exceção ou definir um valor padrão, se necessário
            paciente.setGenero(Paciente.Genero.PREFIRONAOINFORMAR); // ou  por exemplo
        }
        
        // Conversão da string para enum
        try {
            paciente.setEstadoCivil(Paciente.EstadoCivil.fromString(this.estadoCivil));
        } catch (IllegalArgumentException e) {
            System.err.println("Erro: " + e.getMessage());
            // Você pode lançar a exceção ou definir um valor padrão, se necessário
            paciente.setEstadoCivil(Paciente.EstadoCivil.SOLTEIRO); // ou  por exemplo
        }
        
        return paciente;
    }

    // Método para atualizar uma entidade existente
    public void updateEntity(Paciente entity) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dataNascimento = LocalDate.parse(this.dataNascimento, formatter);
        
        Pais paisResultado = PaisDAO.buscarPorId(this.nacionalidade);
        
        Orientador orientador = new Orientador();
        orientador.setId(this.orientador);
        
        Estagiario estagiario = new Estagiario();
        estagiario.setId(this.estagiario);
        
        entity.setTelefoneContato(this.celularContato);
        entity.setTelefone(this.celular);
        entity.setNome(this.paciente);
        entity.setDataNascimento(dataNascimento);
        entity.setGrauInstrucao(this.instrucao);
        entity.setProfissao(this.profissao);
        entity.setRacaCorEtnia(this.raca_cor_etnia);
        entity.setOrientacaoSexual(this.orientacao);
        entity.setNacionalidade(paisResultado);
        entity.setDisponibilidade(this.disponibilidade);
        entity.setEstagiario(estagiario);
        entity.setOrientador(orientador);
        entity.setResponsavel(this.responsavel);
        entity.setAtendido(this.atendido);
        entity.setAtivo(true);
        
        
        // Conversão da string para enum
        try {
            entity.setGenero(Paciente.Genero.fromString(this.genero));
        } catch (IllegalArgumentException e) {
            System.err.println("Erro: " + e.getMessage());
            // Você pode lançar a exceção ou definir um valor padrão, se necessário
            entity.setGenero(Paciente.Genero.PREFIRONAOINFORMAR); // ou  por exemplo
        }
        
        // Conversão da string para enum
        try {
            entity.setEstadoCivil(Paciente.EstadoCivil.fromString(this.estadoCivil));
        } catch (IllegalArgumentException e) {
            System.err.println("Erro: " + e.getMessage());
            // Você pode lançar a exceção ou definir um valor padrão, se necessário
            entity.setEstadoCivil(Paciente.EstadoCivil.SOLTEIRO); // ou  por exemplo
        }
    }
    
}


