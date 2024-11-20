/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Regradenegocio;

import Persistencia.Dao.EnderecoDAO;
import Persistencia.Entity.Endereco;
import VO.EnderecoVO;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author john
 */
public class EnderecoRN {
    private EnderecoDAO enderecoDAO;
    private EnderecoVO enderecoVO;

    public EnderecoRN() {
        this.enderecoDAO = new EnderecoDAO();
        this.enderecoVO = new EnderecoVO();
    }

    // Método para salvar o estagiário
    public Endereco salvarEndereco(EnderecoVO enderecoVO) {
        try {
            Endereco enderecoEntity = enderecoVO.toEntity();

            enderecoDAO.salvarEndereco(enderecoEntity);

            return enderecoEntity;
        } catch (Exception e) {
            e.printStackTrace();
            throw new java.lang.Error("Erro ao cadastrar endereço");
        }
    }

    // Método para listar todos os estagiários
    public List<EnderecoVO> listarEnderecos() {
        var estagiarios = enderecoDAO.buscarTodos();
        return estagiarios.stream()
                .map(EnderecoVO::fromEntity)
                .collect(Collectors.toList());
    }

    // Método para buscar um estagiário por ID
    public EnderecoVO buscarEnderecoPorId(int id) {
        var estagiario = enderecoDAO.buscarPorId(id);
        return estagiario != null ? enderecoVO.fromEntity(estagiario) : null;
    }
}
