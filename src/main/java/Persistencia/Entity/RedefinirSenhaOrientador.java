package Persistencia.Entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Entity
@Table(name = "redefinir_senha_orientador", schema = "clinicapsicologia")
public class RedefinirSenhaOrientador {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "orientador_id", nullable = false)
    private Orientador orientador;

    @Column(name = "token", nullable = false)
    private String token;

    @ColumnDefault("current_timestamp()")
    @Column(name = "validade_token", nullable = false)
    private Instant validadeToken;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Orientador getOrientador() {
        return orientador;
    }

    public void setOrientador(Orientador orientador) {
        this.orientador = orientador;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getValidadeToken() {
        return validadeToken;
    }

    public void setValidadeToken(Instant validadeToken) {
        this.validadeToken = validadeToken;
    }

}