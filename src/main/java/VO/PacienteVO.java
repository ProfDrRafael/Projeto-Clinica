/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VO;

import java.util.Date;

/**
 *
 * @author rafael
 */
public class PacienteVO {
    private int id;
    private String nomeCompleto;
    private Date dataNascimento;
    private String genero;
    private String telefone;
    private EstagiarioVO estagiarioResponsavel; 
    private String endereco;
    private String telefoneContato;
    private String adultoResponsavel;
    private OrientadorVO orientadorResponsavel;
    private String disponibilidade;
    private boolean atendido;
    


public PacienteVO(int id, String nomeCompleto, Date dataNascimento, String genero, String telefone, 
                      EstagiarioVO estagiarioResponsavel, String endereco, String telefoneContato, 
                      String adultoResponsavel, OrientadorVO orientadorResponsavel, String disponibilidade, 
                      boolean atendido){
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.telefone = telefone;
        this.estagiarioResponsavel = estagiarioResponsavel;
        this.endereco = endereco;
        this.telefoneContato = telefoneContato;
        this.adultoResponsavel = adultoResponsavel;
        this.orientadorResponsavel = orientadorResponsavel;
        this.disponibilidade = disponibilidade;
        this.atendido = atendido;
    
        
}

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nomeCompleto
     */
    public String getNomeCompleto() {
        return nomeCompleto;
    }

    /**
     * @param nomeCompleto the nomeCompleto to set
     */
    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    /**
     * @return the dataNascimento
     */
    public Date getDataNascimento() {
        return dataNascimento;
    }

    /**
     * @param dataNascimento the dataNascimento to set
     */
    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
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
     * @return the estagiarioResponsavel
     */
    public EstagiarioVO getEstagiarioResponsavel() {
        return estagiarioResponsavel;
    }

    /**
     * @param estagiarioResponsavel the estagiarioResponsavel to set
     */
    public void setEstagiarioResponsavel(EstagiarioVO estagiarioResponsavel) {
        this.estagiarioResponsavel = estagiarioResponsavel;
    }

    /**
     * @return the endereco
     */
    public String getEndereco() {
        return endereco;
    }

    /**
     * @param endereco the endereco to set
     */
    public void setEndereco(String endereco) {
        this.endereco = endereco;
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
     * @return the adultoResponsavel
     */
    public String getAdultoResponsavel() {
        return adultoResponsavel;
    }

    /**
     * @param adultoResponsavel the adultoResponsavel to set
     */
    public void setAdultoResponsavel(String adultoResponsavel) {
        this.adultoResponsavel = adultoResponsavel;
    }

    /**
     * @return the orientadorResponsavel
     */
    public OrientadorVO getOrientadorResponsavel() {
        return orientadorResponsavel;
    }

    /**
     * @param orientadorResponsavel the orientadorResponsavel to set
     */
    public void setOrientadorResponsavel(OrientadorVO orientadorResponsavel) {
        this.orientadorResponsavel = orientadorResponsavel;
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
}


