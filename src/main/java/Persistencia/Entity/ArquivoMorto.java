package Persistencia.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "arquivo_morto")
public class ArquivoMorto {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Persistencia.Entity.Paciente paciente;

    @Column(name = "data_arquivamento", nullable = false)
    private LocalDate dataArquivamento;

    @Column(name = "motivo", nullable = false)
    private String motivo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Persistencia.Entity.Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Persistencia.Entity.Paciente paciente) {
        this.paciente = paciente;
    }

    public LocalDate getDataArquivamento() {
        return dataArquivamento;
    }

    public void setDataArquivamento(LocalDate dataArquivamento) {
        this.dataArquivamento = dataArquivamento;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

}