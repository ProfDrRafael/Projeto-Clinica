/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VO;

/**
 *
 * @author rafael
 */
public class ProntuarioEletronicoVO {
    private int id;
    private PacienteVO paciente;
    private EstagiarioVO estagiarioResponsavel;
    private String queixaInicial;
    private String observacoes;
    private OrientadorVO orientadorResponsavel;

    public ProntuarioEletronicoVO() {
    }

    public ProntuarioEletronicoVO(int id, PacienteVO paciente, EstagiarioVO estagiarioResponsavel,
            String queixaInicial, String observacoes, OrientadorVO orientadorResponsavel) {
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
    public PacienteVO getPaciente() {
        return paciente;
    }

    /**
     * @param paciente the paciente to set
     */
    public void setPaciente(PacienteVO paciente) {
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

    public static ProntuarioEletronicoVO fromEntity(Persistencia.Entity.Prontuario prontuario) {
        if (prontuario == null) {
            return null; // Evita NullPointerException
        }

        return new ProntuarioEletronicoVO(
                prontuario.getId(),
                PacienteVO.fromEntity(prontuario.getPaciente()), // Converte Paciente para PacienteVO
                EstagiarioVO.fromEntity(prontuario.getEstagiario()), // Converte Estagiario para EstagiarioVO
                prontuario.getQueixaInicial(),
                prontuario.getObservacoes(),
                OrientadorVO.fromEntity(prontuario.getOrientador()) // Converte Orientador para OrientadorVO
        );
    }

    public Persistencia.Entity.Prontuario toEntity() {
        Persistencia.Entity.Prontuario prontuario = new Persistencia.Entity.Prontuario();
        prontuario.setId(this.id);
        prontuario.setPaciente(this.paciente != null ? this.paciente.toEntity() : null); // Converte PacienteVO para
                                                                                         // Paciente
        prontuario.setEstagiario(this.estagiarioResponsavel != null ? this.estagiarioResponsavel.toEntity() : null); // Converte
                                                                                                                     // EstagiarioVO
                                                                                                                     // para
                                                                                                                     // Estagiario
        prontuario.setQueixaInicial(this.queixaInicial);
        prontuario.setObservacoes(this.observacoes);
        prontuario.setOrientador(this.orientadorResponsavel != null ? this.orientadorResponsavel.toEntity() : null); // Converte
                                                                                                                     // OrientadorVO
                                                                                                                     // para
                                                                                                                     // Orientador
        return prontuario;
    }
}
