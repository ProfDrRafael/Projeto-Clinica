/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.Dao;

import Persistencia.Entity.Paciente;
import Persistencia.Entity.Responsavel;
import VO.PacienteVO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author john
 */
public class PacienteDAO extends GenericoDAO<Paciente> {

    private static final Logger logger = LoggerFactory.getLogger(GenericoDAO.class);
    private Paciente pacienteDAO;

    public PacienteDAO() {
        super(Paciente.class);
    }

    public void salvarPaciente(Paciente entity) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = JPAUtil.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            em.persist(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            logger.error("Erro ao salvar a paciente: ", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public void atualizarPaciente(Paciente entity) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = JPAUtil.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            em.merge(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            logger.error("Erro ao atualizar a entidade: ", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public Paciente buscarPorId(Object id) {
        EntityManager em = null;
        Paciente entity = null;
        try {
            em = JPAUtil.getEntityManager();

            entity = em.createQuery(
                    "SELECT p FROM Paciente p LEFT JOIN FETCH p.endereco e LEFT JOIN FETCH e.cidade WHERE p.id = :id",
                    Paciente.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            logger.error("Paciente não encontrado para o ID: " + id);
        } catch (Exception e) {
            logger.error("Erro ao buscar o Paciente por ID: ", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return entity;
    }

    /**
     * Busca pacientes pelo nome.
     *
     * @param nome Nome ou parte do nome do paciente a ser buscado.
     * @return Lista de PacienteVO correspondentes ao nome.
     */
    public List<PacienteVO> buscarPorNome(String nome) {
        EntityManager em = null;
        List<PacienteVO> pacientesVO = new ArrayList<>();

        try {
            em = JPAUtil.getEntityManager();
            TypedQuery<Paciente> query = em.createQuery(
                    "SELECT p FROM Paciente p WHERE LOWER(p.nome) LIKE LOWER(:nome)", Paciente.class);
            query.setParameter("nome", "%" + nome + "%");

            List<Paciente> pacientes = query.getResultList();
            for (Paciente paciente : pacientes) {
                pacientesVO.add(PacienteVO.fromEntity(paciente));
            }
        } catch (Exception e) {
            logger.error("Erro ao buscar pacientes por nome: ", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        return pacientesVO;
    }

    public void editarPaciente(PacienteVO pacienteVO) {
        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = JPAUtil.getEntityManager();
            tx = em.getTransaction();
            tx.begin();

            Paciente pacienteEntity = em.find(Paciente.class, pacienteVO.getId());
            if (pacienteEntity == null) {
                throw new IllegalArgumentException("Paciente não encontrado para o ID: " + pacienteVO.getId());
            }

            // Atualizar os dados do paciente
            pacienteEntity.setNome(pacienteVO.getNome());
            pacienteEntity.setGenero(pacienteVO.getGenero());
            pacienteEntity.setTelefoneContato(pacienteVO.getCelularContato());
            pacienteEntity.setTelefone(pacienteVO.getCelular());
            pacienteEntity.setDataNascimento(LocalDate.parse(pacienteVO.getDataNascimento()));
            pacienteEntity.setGrauInstrucao(pacienteVO.getInstrucao());
            pacienteEntity.setProfissao(pacienteVO.getProfissao());
            pacienteEntity.setEstadoCivil(pacienteVO.getEstadoCivil());
            pacienteEntity.setRacaCorEtnia(pacienteVO.getRaca_cor_etnia());
            pacienteEntity.setDisponibilidade(pacienteVO.getDisponibilidade());
            pacienteEntity.setEndereco(pacienteVO.getEndereco());
            pacienteEntity.setAtendido(pacienteVO.isAtendido());
            pacienteEntity.setAtivo(pacienteVO.isAtivo());
            pacienteEntity.setGrupo(pacienteVO.getGrupo());

            // Atualizar o responsável, se necessário
            if (pacienteVO.getResponsavel() != null) {
                Responsavel responsavelEntity = em.find(Responsavel.class, pacienteVO.getResponsavel().getId());
                if (responsavelEntity == null) {
                    responsavelEntity = pacienteVO.getResponsavel(); // Assumindo que já seja uma entidade gerenciada
                    em.persist(responsavelEntity);
                }
                pacienteEntity.setResponsavel(responsavelEntity);
            }

            em.merge(pacienteEntity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            logger.error("Erro ao editar o paciente: ", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    /**
     * Busca pacientes pelo nome exato.
     *
     * @param nome Nome exato do paciente a ser buscado.
     * @return PacienteVO correspondente ao nome exato, ou null se não
     * encontrado.
     */
    public PacienteVO buscarPorNomePreciso(String nome) {
        EntityManager em = null;
        PacienteVO pacienteVO = null;

        try {
            em = JPAUtil.getEntityManager();
            TypedQuery<Paciente> query = em.createQuery(
                    "SELECT p FROM Paciente p WHERE LOWER(p.nome) = LOWER(:nome)", Paciente.class);
            query.setParameter("nome", nome);

            // Obtém o resultado único
            Paciente paciente = query.getSingleResult();
            if (paciente != null) {
                pacienteVO = PacienteVO.fromEntity(paciente);
            }
        } catch (Exception e) {
            logger.error("Erro ao buscar paciente pelo nome exato: ", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        return pacienteVO;
    }

    public List<Paciente> buscarTodos() {
        EntityManager em = null;
        List<Paciente> resultados = null;
        try {
            em = JPAUtil.getEntityManager();
            TypedQuery<Paciente> query = em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e",
                    entityClass);
            resultados = query.getResultList();
        } catch (Exception e) {
            logger.error("Erro ao buscar todas os Pacientes: ", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return resultados;
    }

    public List<PacienteVO> buscarPorEstagiarioId(Integer estagiarioId) {
        List<PacienteVO> pacientesVO = new ArrayList<>();
        ProntuarioDAO prontuarioDAO = new ProntuarioDAO();

        List<Paciente> pacientes = prontuarioDAO.buscarPacientesPorEstagiarioId(estagiarioId);

        for (Paciente paciente : pacientes) {
            pacientesVO.add(PacienteVO.fromEntity(paciente));
        }
        return pacientesVO;
    }

    public void deletarPaciente(Paciente entity) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = JPAUtil.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            Paciente managedEntity = em.contains(entity) ? entity : em.merge(entity);
            em.remove(managedEntity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            logger.error("Erro ao deletar a entidade: ", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
