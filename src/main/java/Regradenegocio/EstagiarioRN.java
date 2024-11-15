package Regradenegocio;

import Persistencia.Dao.EstagiarioDAO;
import Persistencia.Dao.OrientadorDAO;
import VO.EstagiarioVO;
import VO.OrientadorVO;

import java.util.List;
import java.util.stream.Collectors;

public class EstagiarioRN {

    private EstagiarioDAO estagiarioDAO;
    private OrientadorDAO orientadorDAO;

    public EstagiarioRN() {
        this.estagiarioDAO = new EstagiarioDAO();
        this.orientadorDAO = new OrientadorDAO();
    }

    // Método de conversão de EstagiarioVO para Estagiario (Entity)
    private Estagiario converterVOParaEntity(EstagiarioVO vo) {
        Estagiario estagiario = new Estagiario();
        estagiario.setNome(vo.getNomeCompleto());
        estagiario.setEmail(vo.getEmail());
        estagiario.setAtivo(vo.getAtivo());
        estagiario.setAno(vo.getAno());
        estagiario.setSemestreFim(vo.getSemestreFim());
        estagiario.setAnoFim(vo.getAnoFim());

        if (vo.getOrientadorVO() != null) {
            estagiario.setOrientador(converterOrientadorVOParaEntity(vo.getOrientadorVO()));
        }

        return estagiario;
    }

    // Método de conversão de Estagiario (Entity) para EstagiarioVO
    private EstagiarioVO converterEntityParaVO(Estagiario entity) {
        EstagiarioVO vo = new EstagiarioVO(
                entity.getId(),
                entity.getNome(),
                entity.getEmail(),
                null, // Não transferimos a senha para o VO
                entity.getAtivo(),
                entity.getAno(),
                entity.getSemestreFim(),
                entity.getAnoFim(),
                entity.getOrientador() != null ? converterOrientadorEntityParaVO(entity.getOrientador()) : null
        );
        return vo;
    }

    private Orientador converterOrientadorVOParaEntity(OrientadorVO vo) {
        Orientador orientador = new Orientador();
        orientador.setNome(vo.getNomeCompleto());
        return orientador;
    }

    private OrientadorVO converterOrientadorEntityParaVO(Orientador entity) {
        return new OrientadorVO(entity.getId(), entity.getNome(), entity.getEmail(), null, true, entity.getLinhaTeorica());
    }

    // Método para salvar o estagiário
    public boolean salvarEstagiario(EstagiarioVO estagiarioVO) {
        Estagiario estagiario = converterVOParaEntity(estagiarioVO);
        try {
            estagiarioDAO.salvar(estagiario);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean atualizarEstagiario(EstagiarioVO estagiarioVO) {
        try {
            Estagiario estagiarioExistente = estagiarioDAO.buscarPorId(estagiarioVO.getId());

            if (estagiarioExistente == null) {
                return false;
            }

            estagiarioExistente.setNome(estagiarioVO.getNomeCompleto());
            estagiarioExistente.setEmail(estagiarioVO.getEmail());
            estagiarioExistente.setAtivo(estagiarioVO.getAtivo());
            estagiarioExistente.setAno(estagiarioVO.getAno());
            estagiarioExistente.setSemestreFim(estagiarioVO.getSemestreFim());
            estagiarioExistente.setAnoFim(estagiarioVO.getAnoFim());

            if (estagiarioVO.getOrientadorVO() != null) {
                Orientador orientador = orientadorDAO.buscarPorId(estagiarioVO.getOrientadorVO().getId());

                if (orientador == null) {
                    orientador = converterOrientadorVOParaEntity(estagiarioVO.getOrientadorVO());
                    orientadorDAO.salvar(orientador);
                }

                estagiarioExistente.setOrientador(orientador);
            }

            estagiarioDAO.atualizar(estagiarioExistente);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    // Método para listar todos os estagiários
    public List<EstagiarioVO> listarEstagiarios() {
        List<Estagiario> estagiarios = estagiarioDAO.buscarTodos();
        return estagiarios.stream()
                .map(this::converterEntityParaVO)
                .collect(Collectors.toList());
    }

    // Método para buscar um estagiário por ID
    public EstagiarioVO buscarEstagiarioPorId(int id) {
        Estagiario estagiario = estagiarioDAO.buscarPorId(id);
        return estagiario != null ? converterEntityParaVO(estagiario) : null;
    }

    // Método para buscar um estagiário por nome
    public EstagiarioVO buscarEstagiarioPorNome(String nome) {
        Estagiario estagiario = estagiarioDAO.buscarPorNome(nome);
        return estagiario != null ? converterEntityParaVO(estagiario) : null;
    }

    // Método para buscar um orientador por nome
    public OrientadorVO buscarOrientadorPorNome(String nome) {
        Orientador orientador = orientadorDAO.buscarPorNome(nome);
        return orientador != null ? converterOrientadorEntityParaVO(orientador) : null;
    }
}
