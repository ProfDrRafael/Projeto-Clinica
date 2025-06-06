package VO;

import Persistencia.Entity.Estado;

public class EstadoVO {
    private int id;
    private String nome;
    private String sigla;

    public EstadoVO() {
    }

    public EstadoVO(int id, String nome, String sigla) {
        this.id = id;
        this.nome = nome;
        this.sigla = sigla;
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
     * @return the sigla
     */
    public String getSigla() {
        return sigla;
    }

    /**
     * @param sigla the sigla to set
     */
    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public static EstadoVO fromEntity(Estado entity) {
        if (entity == null) {
            return null;
        }

        EstadoVO estadoVO = new EstadoVO();
        estadoVO.setId(entity.getId());
        estadoVO.setNome(entity.getNome());
        estadoVO.setSigla(entity.getSigla());
        return estadoVO;
    }

    /**
     * Converte um objeto EstadoVO para a entidade Estado.
     *
     * @return Entidade Estado correspondente.
     */
    public Estado toEntity() {
        Estado estado = new Estado();
        estado.setId(this.id);
        estado.setNome(this.nome);
        estado.setSigla(this.sigla);
        return estado;
    }
}
