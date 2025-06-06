package VO;

import Persistencia.Entity.Cidade;

public class CidadeVO {
    private int id;
    private String nome;
    private EstadoVO EstadoVO;

    public CidadeVO() {
    }

    public CidadeVO(int id, String nome, EstadoVO EstadoVO) {
        this.id = id;
        this.nome = nome;
        this.EstadoVO = EstadoVO;
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
     * @return the EstadoVO
     */
    public EstadoVO getEstadoVO() {
        return EstadoVO;
    }

    /**
     * @param EstadoVO the EstadoVO to set
     */
    public void setEstadoVO(EstadoVO EstadoVO) {
        this.EstadoVO = EstadoVO;
    }

    public static CidadeVO fromEntity(Cidade cidade) {
        if (cidade == null) {
            return null; // Evita NullPointerException
        }

        // Criação de uma nova instância de CidadeVO
        CidadeVO cidadeVO = new CidadeVO();
        cidadeVO.setId(cidade.getId());
        cidadeVO.setNome(cidade.getNome());

        // Verifica se o estado da cidade não é nulo antes de converter
        if (cidade.getEstado() != null) {
            cidadeVO.setEstadoVO(VO.EstadoVO.fromEntity(cidade.getEstado()));
        }

        return cidadeVO;
    }

    public Cidade toEntity() {
        Cidade cidade = new Cidade();
        cidade.setId(this.getId());
        cidade.setNome(this.getNome());

        if (this.getEstadoVO() != null) {
            cidade.setEstado(this.getEstadoVO().toEntity());
        }

        return cidade;
    }
}
