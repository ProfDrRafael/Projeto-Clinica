/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vo;

/**
 *
 * @author rafael
 */
public class EstagiarioVO {
    private int id;
    private String nomeCompleto;
    private String email;
    private String senha;
    private String funcao;
    private boolean ativo;
    private int semestre;
    private int ano;
    private OrientadorVO orientadorResponsavel;
    
    public EstagiarioVO(int id, String nomeCompleto, String email, String senha,String funcao,
boolean ativo, int semestre, int ano, OrientadorVO orientadorResponsavel){
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.senha = senha;
        this.funcao = funcao;
        this.ativo = ativo;
        this.semestre = semestre;
        this.ano = ano;
        this.orientadorResponsavel = orientadorResponsavel;
        
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
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * @return the funcao
     */
    public String getFuncao() {
        return funcao;
    }

    /**
     * @param funcao the funcao to set
     */
    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    /**
     * @return the ativo
     */
    public boolean isAtivo() {
        return ativo;
    }

    /**
     * @param ativo the ativo to set
     */
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    /**
     * @return the semestre
     */
    public int getSemestre() {
        return semestre;
    }

    /**
     * @param semestre the semestre to set
     */
    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    /**
     * @return the ano
     */
    public int getAno() {
        return ano;
    }

    /**
     * @param ano the ano to set
     */
    public void setAno(int ano) {
        this.ano = ano;
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
    
}
