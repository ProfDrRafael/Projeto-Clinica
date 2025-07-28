/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Regradenegocio;

import Persistencia.Dao.CidadeDAO;
import Persistencia.Entity.Cidade;
import VO.CidadeVO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author GMott
 */
public class CidadeRN {

    private final CidadeDAO cidadeDAO;

    public CidadeRN() {
        this.cidadeDAO = new CidadeDAO();
    }

    /**
     * Busca as cidades de um estado específico e as converte para VOs.
     *
     * @param estadoId O ID do estado pelo qual filtrar as cidades.
     * @return Uma lista de CidadeVO para ser usada na tela.
     */
    public List<CidadeVO> listarPorEstado(int estadoId) {
        List<Cidade> cidadesEntities = cidadeDAO.buscarPorEstado(estadoId);
        List<CidadeVO> cidadesVO = new ArrayList<>();

        for (Cidade entity : cidadesEntities) {
            cidadesVO.add(CidadeVO.fromEntity(entity));
        }

        return cidadesVO;
    }

    public CidadeVO buscarPorNomeEUF(String nomeCidade, String siglaEstado) {
        Cidade cidadeEntity = cidadeDAO.buscarPorNomeEUF(nomeCidade, siglaEstado);
        // Usa o método de conversão que você já tem no seu VO
        return CidadeVO.fromEntity(cidadeEntity);
    }
}
