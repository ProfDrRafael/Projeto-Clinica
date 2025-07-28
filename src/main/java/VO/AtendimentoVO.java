package VO;

import Persistencia.Entity.Atendimento;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author rafael
 */
public class AtendimentoVO {
    private Integer id; // Alterado para Integer para permitir valores nulos
    private Date data;
    private Time hora;
    private Boolean preenchido;
    private Boolean comparecimento;
    private String justificativa;
    private String relatoAtendimento;
    private PacienteVO paciente;
    private ProntuarioEletronicoVO prontuario;
    private EstagiarioVO estagiarioResponsavel;
    private String tipoAtendimento;

    // Construtor padrão
    public AtendimentoVO() {
    }

    // Construtor completo
    public AtendimentoVO(Integer id, Date data, Time hora, Boolean preenchido, Boolean comparecimento,
                         String justificativa, String relatoAtendimento, PacienteVO paciente,
                         EstagiarioVO estagiarioResponsavel, String tipoAtendimento) {
        this.id = id;
        this.data = data;
        this.hora = hora;
        this.preenchido = preenchido;
        this.comparecimento = comparecimento;
        this.justificativa = justificativa;
        this.relatoAtendimento = relatoAtendimento;
        this.paciente = paciente;
        this.estagiarioResponsavel = estagiarioResponsavel;
        this.tipoAtendimento = tipoAtendimento;
    }

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
     * @return the data
     */
    public Date getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Date data) {
        this.data = data;
    }

    /**
     * @return the hora
     */
    public Time getHora() {
        return hora;
    }

    /**
     * @param hora the hora to set
     */
    public void setHora(Time hora) {
        this.hora = hora;
    }

    /**
     * @return the preenchido
     */
    public Boolean getPreenchido() {
        return preenchido;
    }

    /**
     * @param preenchido the preenchido to set
     */
    public void setPreenchido(Boolean preenchido) {
        this.preenchido = preenchido;
    }

    /**
     * @return the comparecimento
     */
    public Boolean getComparecimento() {
        return comparecimento;
    }

    /**
     * @param comparecimento the comparecimento to set
     */
    public void setComparecimento(Boolean comparecimento) {
        this.comparecimento = comparecimento;
    }

    /**
     * @return the justificativa
     */
    public String getJustificativa() {
        return justificativa;
    }

    /**
     * @param justificativa the justificativa to set
     */
    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    /**
     * @return the relatoAtendimento
     */
    public String getRelatoAtendimento() {
        return relatoAtendimento;
    }

    /**
     * @param relatoAtendimento the relatoAtendimento to set
     */
    public void setRelatoAtendimento(String relatoAtendimento) {
        this.relatoAtendimento = relatoAtendimento;
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
     * @return the prontuario
     */
    public ProntuarioEletronicoVO getProntuario() {
        return prontuario;
    }

    /**
     * @param prontuario the prontuario to set
     */
    public void setProntuario(ProntuarioEletronicoVO prontuario) {
        this.prontuario = prontuario;
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
     * @return the tipoAtendimento
     */
    public String getTipoAtendimento() {
        return tipoAtendimento;
    }

    /**
     * @param tipoAtendimento the tipoAtendimento to set
     */
    public void setTipoAtendimento(String tipoAtendimento) {
        this.tipoAtendimento = tipoAtendimento;
    }

    // Método para converter de Entity para VO
    public static AtendimentoVO fromEntity(Atendimento entity) {
        AtendimentoVO vo = new AtendimentoVO();
        vo.setId(entity.getId());
        vo.setData(Date.valueOf(entity.getData()));
        vo.setHora(Time.valueOf(entity.getHora()));
        vo.setPreenchido(entity.getPreenchido());
        vo.setComparecimento(entity.getComparecimento());
        vo.setJustificativa(entity.getJustificativa());
        vo.setRelatoAtendimento(entity.getRelatoAtendimento());
        vo.setPaciente(entity.getProntuario() != null ? PacienteVO.fromEntity(entity.getProntuario().getPaciente()) : null);
        vo.setProntuario(entity.getProntuario() != null ? ProntuarioEletronicoVO.fromEntity(entity.getProntuario()) : null);
        vo.setEstagiarioResponsavel(entity.getEstagiario() != null ? EstagiarioVO.fromEntity(entity.getEstagiario()) : null);
        vo.setTipoAtendimento(entity.getTipoAtendimento());
        return vo;
    }

    public Atendimento toEntity() {
        Atendimento entity = new Atendimento();
        entity.setId(this.getId());

        if (this.getData() != null) {
            entity.setData(this.getData().toLocalDate());
        }
        if (this.getHora() != null) {
            entity.setHora(this.getHora().toLocalTime());
        }

        entity.setPreenchido(this.getPreenchido());
        entity.setComparecimento(this.getComparecimento());
        entity.setJustificativa(this.getJustificativa());
        entity.setRelatoAtendimento(this.getRelatoAtendimento());

        if (this.getProntuario() != null) {
            entity.setProntuario(this.getProntuario().toEntity());
        }
        if (this.getEstagiarioResponsavel() != null) {
            entity.setEstagiario(this.getEstagiarioResponsavel().toEntity());
        }

        entity.setTipoAtendimento(this.getTipoAtendimento());

        return entity;
    }

    // @Override
    // public String toString() {
    //     return "AtendimentoVO{" +
    //             "id=" + id +
    //             ", data=" + data +
    //             ", hora=" + hora +
    //             ", preenchido=" + preenchido +
    //             ", comparecimento=" + comparecimento +
    //             ", justificativa='" + justificativa + '\'' +
    //             ", relatoAtendimento='" + relatoAtendimento + '\'' +
    //             ", prontuario=" + (prontuario != null ? prontuario.getId() : "null") +
    //             ", estagiarioResponsavel=" + (estagiarioResponsavel != null ? estagiarioResponsavel.getNomeCompleto() : "null") +
    //             ", paciente=" + (paciente != null ? paciente.getPaciente() : "null") +
    //             '}';
    // }

    @Override
    public String toString() {
        return id + "-" + data + "-" + hora;
    }
}
