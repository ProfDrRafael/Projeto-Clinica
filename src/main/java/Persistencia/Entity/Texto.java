package Persistencia.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "texto", schema = "texto_teste")
public class Texto {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    @Column(name = "texto_cifrado", nullable = false)
    private String textoCifrado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTextoCifrado() {
        return textoCifrado;
    }

    public void setTextoCifrado(String textoCifrado) {
        this.textoCifrado = textoCifrado;
    }

}