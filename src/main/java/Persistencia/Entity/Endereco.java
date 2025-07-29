package Persistencia.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "rua", nullable = false, length = 100)
    private String rua;

    @Column(name = "numero", nullable = false)
    private Integer numero;

    @Column(name = "bairro", nullable = false, length = 50)
    private String bairro;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cidade_id", nullable = false)
    private Cidade cidade;

    @Column(name = "cep", nullable = false, length = 20)
    private String cep;

    @Column(name = "complemento", length = 100)
    private String complemento;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    @Override
    public String toString() {
        return "Endereco{"
                + "id=" + id
                + ", rua='" + rua + '\''
                + ", numero=" + numero
                + ", bairro='" + bairro + '\''
                + ", cidade=" + (cidade != null ? cidade.getNome() : "null")
                + ", cep='" + cep + '\''
                + ", complemento='" + complemento + '\''
                + '}';
    }

}
