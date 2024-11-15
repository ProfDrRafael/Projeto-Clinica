package Persistencia.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity
@Table(name = "redefinir_senha")
public class RedefinirSenha {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "token")
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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