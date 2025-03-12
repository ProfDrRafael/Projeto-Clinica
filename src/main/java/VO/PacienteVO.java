/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VO;

import Persistencia.Dao.EstagiarioDAO;
import Persistencia.Dao.OrientadorDAO;
import Persistencia.Dao.PaisDAO;
import Persistencia.Entity.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author rafael
 */
public class PacienteVO {

    OrientadorDAO orientadorDAO = new OrientadorDAO();
    EstagiarioDAO estagiarioDAO = new EstagiarioDAO();

    private Integer id;
    private String nome;
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
    private Responsavel responsavel;
    private boolean atendido;
    private boolean ativo;
    private Byte grupo; // Campo adicional para grupo

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
            Responsavel responsavel,
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

    public PacienteVO(
            Integer id,
            String nome,
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
            Responsavel responsavel,
            boolean atendido,
            boolean ativo,
            Byte grupo
    ) {
        this.id = id;
        this.nome = nome;
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
        this.grupo = grupo;
    }

    public Integer getId() {
        return id;
    }

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

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCelularContato() {
        return celularContato;
    }

    public void setCelularContato(String celularContato) {
        this.celularContato = celularContato;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getInstrucao() {
        return instrucao;
    }

    public void setInstrucao(String instrucao) {
        this.instrucao = instrucao;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getRaca_cor_etnia() {
        return raca_cor_etnia;
    }

    public void setRaca_cor_etnia(String raca_cor_etnia) {
        this.raca_cor_etnia = raca_cor_etnia;
    }

    public String getOrientacao() {
        return orientacao;
    }

    public void setOrientacao(String orientacao) {
        this.orientacao = orientacao;
    }

    public Integer getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(Integer nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(String disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public Integer getEstagiario() {
        return estagiario;
    }

    public void setEstagiario(Integer estagiario) {
        this.estagiario = estagiario;
    }

    public Integer getOrientador() {
        return orientador;
    }

    public void setOrientador(Integer orientador) {
        this.orientador = orientador;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Responsavel getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
    }

    public boolean isAtendido() {
        return atendido;
    }

    public void setAtendido(boolean atendido) {
        this.atendido = atendido;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Byte getGrupo() {
        return grupo;
    }

    public void setGrupo(Byte grupo) {
        this.grupo = grupo;
    }

    // Método de conversão de Entity para VO
    public static PacienteVO fromEntity(Paciente entity) {
        return new PacienteVO(
                entity.getId(),
                entity.getNome(),
                entity.getGenero(),
                entity.getTelefoneContato(),
                entity.getTelefone(),
                entity.getNome(),
                entity.getDataNascimento().toString(),
                entity.getDataInscricao(),
                entity.getGrauInstrucao(),
                entity.getProfissao(),
                entity.getEstadoCivil(),
                entity.getRacaCorEtnia(),
                null, // Orientação Sexual não definida no Entity original
                entity.getNacionalidade().getId(),
                entity.getDisponibilidade(),
                null, // Estagiário não definido no Entity original
                null, // Orientador não definido no Entity original
                entity.getEndereco(),
                entity.getResponsavel(),
                entity.getAtendido(),
                entity.getAtivo(),
                entity.getGrupo()
        );
    }

    // Método de conversão de VO para Entity
    public Paciente toEntity() {
        LocalDate dataNascimento = parseDate(this.dataNascimento);

        Pais paisResultado = PaisDAO.buscarPorId(this.nacionalidade);

        Paciente paciente = new Paciente();
        paciente.setId(this.id);
        paciente.setNome(this.nome);
        paciente.setTelefoneContato(this.celularContato);
        paciente.setTelefone(this.celular);
        paciente.setNome(this.paciente);
        paciente.setDataNascimento(dataNascimento);
        paciente.setDataInscricao(LocalDate.now());
        paciente.setGrauInstrucao(this.instrucao);
        paciente.setGenero(genero);
        paciente.setProfissao(this.profissao);
        paciente.setEstadoCivil(this.estadoCivil);
        paciente.setRacaCorEtnia(this.raca_cor_etnia);
        paciente.setNacionalidade(paisResultado);
        paciente.setDisponibilidade(disponibilidade);
        paciente.setEndereco(endereco);
        paciente.setResponsavel(responsavel);
        paciente.setOrientacao_sexual(orientacao);
        paciente.setAtendido(this.atendido);
        paciente.setAtivo(this.ativo);
        paciente.setGrupo(this.grupo);

        return paciente;
    }

    public LocalDate parseDate(String dateStr) {
        List<DateTimeFormatter> formatters = Arrays.asList(
                DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
        );

        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDate.parse(dateStr, formatter);
            } catch (DateTimeParseException e) {
                // Tenta o próximo formato
            }
        }

        throw new IllegalArgumentException("Data inválida: " + dateStr + ". Formatos aceitos: dd-MM-yyyy, yyyy-MM-dd, dd/MM/yyyy");
    }

    @Override
    public String toString() {
        return this.nome;
    }

}
