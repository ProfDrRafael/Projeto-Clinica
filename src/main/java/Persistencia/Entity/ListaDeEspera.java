package Persistencia.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "lista_de_espera")
public class ListaDeEspera {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Persistencia.Entity.Paciente paciente;

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

}