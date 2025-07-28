package VO;

import Persistencia.Entity.Cidade;
import java.util.Objects;

public class CidadeVO {

    private int id;
    private String nome;
    private EstadoVO EstadoVO;

    public CidadeVO() {
    }

    // Adicionei este construtor para o placeholder, se necessário
    public CidadeVO(int id, String nome) {
        this.id = id;
        this.nome = nome;
        this.EstadoVO = null; // Estado é nulo para o placeholder
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

    @Override
    public String toString() {
        return this.nome;

    }

    // --- MÉTODOS ADICIONADOS PARA CONSISTÊNCIA ---
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CidadeVO cidadeVO = (CidadeVO) o;
        return id == cidadeVO.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
