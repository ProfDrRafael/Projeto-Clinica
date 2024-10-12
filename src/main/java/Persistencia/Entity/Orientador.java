package Persistencia.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "orientador", schema = "clinicapsicologia")
public class Orientador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Adiciona geração automática de ID
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nome", nullable = false, length = 150)
    private String nome;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    @ColumnDefault("1")
    @Column(name = "ativo")
    private Boolean ativo;

    @Column(name = "linha_teorica", length = 100)
    private String linhaTeorica;

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getLinhaTeorica() {
        return linhaTeorica;
    }

    public void setLinhaTeorica(String linhaTeorica) {
        this.linhaTeorica = linhaTeorica;
    }
}
