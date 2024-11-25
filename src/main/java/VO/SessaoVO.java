package VO;

import Persistencia.Entity.Sessao;

/**
 *
 * @author otniel
 */
public class SessaoVO {
    private Integer id; // Alterado de int para Integer
    private String nome;
    private String email;
    private String tipo;

    public SessaoVO() {}

    public SessaoVO(Integer id, String nome, String email, String tipo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.tipo = tipo;
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
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    // Método de conversão de Entity para VO
    public static SessaoVO fromEntity(Sessao entity) {
        return new SessaoVO(
                entity.getId(),
                entity.getNome(),
                entity.getEmail(),
                entity.getTipo()
        );
    }

    // Método de conversão de VO para Entity
    public Sessao toEntity() {
        var sessao = new Sessao();
        sessao.setId(this.getId());
        sessao.setNome(this.getNome());
        sessao.setEmail(this.getEmail());
        sessao.setTipo(this.getTipo());
        return sessao;
    }
}
