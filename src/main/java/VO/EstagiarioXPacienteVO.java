/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VO;

import Persistencia.Entity.EstagiarioXPaciente;
import Persistencia.Entity.EstagiarioXPacienteId;

/**
 * VO para EstagiarioXPaciente
 * 
 * Utiliza os VO correspondentes para as associações: EstagiarioVO e PacienteVO.
 * 
 * @author 
 */
public class EstagiarioXPacienteVO {

    private EstagiarioVO estagiario;
    private PacienteVO paciente;

    // Construtor padrão
    public EstagiarioXPacienteVO() {
    }

    // Construtor completo
    public EstagiarioXPacienteVO(EstagiarioVO estagiario, PacienteVO paciente) {
        this.estagiario = estagiario;
        this.paciente = paciente;
    }

    /**
     * Converte a entidade EstagiarioXPaciente para VO.
     * 
     * @param entity a entidade EstagiarioXPaciente
     * @return um VO preenchido com os objetos EstagiarioVO e PacienteVO
     */
    public static EstagiarioXPacienteVO fromEntity(EstagiarioXPaciente entity) {
        EstagiarioXPacienteVO vo = new EstagiarioXPacienteVO();
        
        // Converte e seta o Estagiario se presente
        if (entity.getEstagiario() != null) {
            vo.setEstagiario(EstagiarioVO.fromEntity(entity.getEstagiario()));
        }
        // Converte e seta o Paciente se presente
        if (entity.getPaciente() != null) {
            vo.setPaciente(PacienteVO.fromEntity(entity.getPaciente()));
        }
        
        return vo;
    }

    /**
     * Converte este VO para a entidade EstagiarioXPaciente.
     * 
     * Cria e seta o ID composto (utilizando os IDs dos VO) e as associações.
     *
     * @return uma instância de EstagiarioXPaciente com os dados do VO.
     */
    public EstagiarioXPaciente toEntity() {
        EstagiarioXPaciente entity = new EstagiarioXPaciente();
        EstagiarioXPacienteId id = new EstagiarioXPacienteId();

        if (this.estagiario != null) {
            // Assume que EstagiarioVO possui um método getId() e toEntity()
            id.setEstagiarioId(this.estagiario.getId());
            entity.setEstagiario(this.estagiario.toEntity());
        }
        if (this.paciente != null) {
            // Assume que PacienteVO possui um método getId() e toEntity()
            id.setPacienteId(this.paciente.getId());
            entity.setPaciente(this.paciente.toEntity());
        }
        entity.setId(id);
        
        return entity;
    }

    // Getters e Setters
    public EstagiarioVO getEstagiario() {
        return estagiario;
    }

    public void setEstagiario(EstagiarioVO estagiario) {
        this.estagiario = estagiario;
    }

    public PacienteVO getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteVO paciente) {
        this.paciente = paciente;
    }

    @Override
    public String toString() {
        return "EstagiarioXPacienteVO{" +
                "estagiario=" + (estagiario != null ? estagiario.toString() : "null") +
                ", paciente=" + (paciente != null ? paciente.toString() : "null") +
                '}';
    }
}
