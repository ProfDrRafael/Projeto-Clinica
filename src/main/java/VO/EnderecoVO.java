/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VO;

import Persistencia.Dao.CidadeDAO;
import Persistencia.Entity.Cidade;
import Persistencia.Entity.Endereco;

/**
 *
 * @author john
 */
public class EnderecoVO {

    private Integer id;
    private String rua;
    private Integer numero;
    private String bairro;
    private Integer cidadeId;
    private String cep;
    private String complemento;

    // Construtor vazio
    public EnderecoVO() {
    }

    // Construtor sem id
    public EnderecoVO(String rua, Integer numero, String bairro, Integer cidadeId, String cep, String complemento) {
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidadeId = cidadeId;
        this.cep = cep;
        this.complemento = complemento;
    }

    // Construtor completo
    public EnderecoVO(Integer id, String rua, Integer numero, String bairro, Integer cidadeId, String cep, String complemento) {
        this.id = id;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidadeId = cidadeId;
        this.cep = cep;
        this.complemento = complemento;
    }

    @Override
    public String toString() {
        return String.format("Endereço [Rua: %s, Número: %s, Bairro: %s, CidadeID: %s, CEP: %s, Complemento: %s]",
                rua != null ? rua : "-",
                numero != null ? numero : "-",
                bairro != null ? bairro : "-",
                cidadeId != null ? cidadeId : "-",
                cep != null ? cep : "-",
                complemento != null ? complemento : "-"
        );
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
     * @return the rua
     */
    public String getRua() {
        return rua;
    }

    /**
     * @param rua the rua to set
     */
    public void setRua(String rua) {
        this.rua = rua;
    }

    /**
     * @return the numero
     */
    public Integer getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    /**
     * @return the bairro
     */
    public String getBairro() {
        return bairro;
    }

    /**
     * @param bairro the bairro to set
     */
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    /**
     * @return the cidadeId
     */
    public Integer getCidadeNome() {
        return cidadeId;
    }

    /**
     * @param cidadeId the cidadeId to set
     */
    public void setCidadeNome(Integer cidadeId) {
        this.cidadeId = cidadeId;
    }

    /**
     * @return the cep
     */
    public String getCep() {
        return cep;
    }

    /**
     * @param cep the cep to set
     */
    public void setCep(String cep) {
        this.cep = cep;
    }

    /**
     * @return the complemento
     */
    public String getComplemento() {
        return complemento;
    }

    /**
     * @param complemento the complemento to set
     */
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    // Método de conversão de Entity para VO
    public static EnderecoVO fromEntity(Endereco entity) {
        return new EnderecoVO(
                entity.getId(),
                entity.getRua(),
                entity.getNumero(),
                entity.getBairro(),
                entity.getCidade().getId(),
                entity.getCep(),
                entity.getComplemento()
        );
    }

    // Método de conversão de VO para Entity
    public Endereco toEntity() {
        Cidade cidadeBusca = CidadeDAO.buscarPorNome(cidadeId);

        var endereco = new Endereco();
        endereco.setId(id);
        endereco.setRua(rua);
        endereco.setNumero(numero);
        endereco.setBairro(bairro);
        endereco.setCidade(cidadeBusca);
        endereco.setCep(cep);
        endereco.setComplemento(complemento);
        return endereco;
    }

    // Método para atualizar uma entidade existente
    public void updateEntity(Endereco entity) {
        Cidade cidadeBusca = CidadeDAO.buscarPorNome(cidadeId);

        entity.setId(id);
        entity.setRua(rua);
        entity.setNumero(numero);
        entity.setBairro(bairro);
        entity.setCidade(cidadeBusca);
        entity.setCep(cep);
        entity.setComplemento(complemento);
    }
}
