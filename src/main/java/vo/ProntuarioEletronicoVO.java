/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vo;

/**
 *
 * @author rafael
 */
public class ProntuarioEletronicoVO {
    private int id;
    private String paciente;
    private EstagiarioVO estagiarioResponsavel;
    private String queixaInicial;
    private String observacoes;
    private OrientadorVO orientadorResponsavel;
    
    public ProntuarioEletronicoVO(int id, String paciente, EstagiarioVO estagiarioResponsavel, String queixaInicial,
            String observacoes, OrientadorVO orientadorResponsavel){
        this.id = id;
        this.paciente = paciente;
        this.estagiarioResponsavel = estagiarioResponsavel;
        this.queixaInicial = queixaInicial;
        this.observacoes = observacoes;
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
     * @return the queixaInicial
     */
    public String getQueixaInicial() {
        return queixaInicial;
    }

    /**
     * @param queixaInicial the queixaInicial to set
     */
    public void setQueixaInicial(String queixaInicial) {
        this.queixaInicial = queixaInicial;
    }

    /**
     * @return the observacoes
     */
    public String getObservacoes() {
        return observacoes;
    }

    /**
     * @param observacoes the observacoes to set
     */
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
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
