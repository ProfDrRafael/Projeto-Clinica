package VO;

import Persistencia.Entity.Responsavel;

public class ResponsavelVO {
    private Integer id;
    private String nome;
    private String vinculo;
    private String telefone;
    private String telefoneContato;
    private String grauInstrucao;
    private String profissao;
    private String genero;
    private String racaCorEtnia;

    // Construtor padrão
    public ResponsavelVO() {
    }

    // Construtor completo
    public ResponsavelVO(Integer id, String nome, String vinculo, String telefone, String telefoneContato,
                         String grauInstrucao, String profissao, String genero, String racaCorEtnia) {
        this.id = id;
        this.nome = nome;
        this.vinculo = vinculo;
        this.telefone = telefone;
        this.telefoneContato = telefoneContato;
        this.grauInstrucao = grauInstrucao;
        this.profissao = profissao;
        this.genero = genero;
        this.racaCorEtnia = racaCorEtnia;
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
     * @return the vinculo
     */
    public String getVinculo() {
        return vinculo;
    }

    /**
     * @param vinculo the vinculo to set
     */
    public void setVinculo(String vinculo) {
        this.vinculo = vinculo;
    }

    /**
     * @return the telefone
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * @param telefone the telefone to set
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * @return the telefoneContato
     */
    public String getTelefoneContato() {
        return telefoneContato;
    }

    /**
     * @param telefoneContato the telefoneContato to set
     */
    public void setTelefoneContato(String telefoneContato) {
        this.telefoneContato = telefoneContato;
    }

    /**
     * @return the grauInstrucao
     */
    public String getGrauInstrucao() {
        return grauInstrucao;
    }

    /**
     * @param grauInstrucao the grauInstrucao to set
     */
    public void setGrauInstrucao(String grauInstrucao) {
        this.grauInstrucao = grauInstrucao;
    }

    /**
     * @return the profissao
     */
    public String getProfissao() {
        return profissao;
    }

    /**
     * @param profissao the profissao to set
     */
    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    /**
     * @return the genero
     */
    public String getGenero() {
        return genero;
    }

    /**
     * @param genero the genero to set
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }

    /**
     * @return the racaCorEtnia
     */
    public String getRacaCorEtnia() {
        return racaCorEtnia;
    }

    /**
     * @param racaCorEtnia the racaCorEtnia to set
     */
    public void setRacaCorEtnia(String racaCorEtnia) {
        this.racaCorEtnia = racaCorEtnia;
    }

    public static ResponsavelVO fromEntity(Persistencia.Entity.Responsavel entity) {
        if (entity == null) {
            return null; // Retorna null caso o responsável seja inexistente
        }

        ResponsavelVO vo = new ResponsavelVO();
        vo.setId(entity.getId());
        vo.setNome(entity.getNome());
        vo.setVinculo(entity.getVinculo());
        vo.setTelefone(entity.getTelefone());
        vo.setTelefoneContato(entity.getTelefoneContato());
        vo.setGrauInstrucao(entity.getGrauInstrucao());
        vo.setProfissao(entity.getProfissao());
        vo.setGenero(entity.getGenero());
        vo.setRacaCorEtnia(entity.getRacaCorEtnia());
        return vo;
    }

    public Responsavel toEntity() {
        Responsavel entity = new Responsavel();
        entity.setId(this.getId());
        entity.setNome(this.getNome());
        entity.setVinculo(this.getVinculo());
        entity.setTelefone(this.getTelefone());
        entity.setTelefoneContato(this.getTelefoneContato());
        entity.setGrauInstrucao(this.getGrauInstrucao());
        entity.setProfissao(this.getProfissao());
        entity.setGenero(this.getGenero());
        entity.setRacaCorEtnia(this.getRacaCorEtnia());
        return entity;
    }

    @Override
    public String toString() {
        return this.nome; // Útil para exibição em componentes como combobox
    }
}
