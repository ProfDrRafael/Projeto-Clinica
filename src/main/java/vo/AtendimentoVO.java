

package vo;

import java.sql.Time;
import java.util.Date;


/**
 *
 * @author gani
 */
public class AtendimentoVO {
    private int id;
    private Date data;
    private PacienteVO paciente;
    private EstagiarioVO estagiarioResponsavel;
    private String descricaoAtendimento;
    private Time hora;

    // Construtor
    public AtendimentoVO(int id, Date data, PacienteVO paciente, EstagiarioVO estagiarioResponsavel, String descricaoAtendimento, Time hora) {
        this.id = id;
        this.data = data;
        this.paciente = paciente;
        this.estagiarioResponsavel = estagiarioResponsavel;
        this.descricaoAtendimento = descricaoAtendimento;
        this.hora = hora;
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
     * @return the descricaoAtendimento
     */
    public String getDescricaoAtendimento() {
        return descricaoAtendimento;
    }

    /**
     * @param descricaoAtendimento the descricaoAtendimento to set
     */
    public void setDescricaoAtendimento(String descricaoAtendimento) {
        this.descricaoAtendimento = descricaoAtendimento;
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
    
}
