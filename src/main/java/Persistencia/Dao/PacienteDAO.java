/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.Dao;

import Persistencia.Entity.Agenda;
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

import static Persistencia.Dao.JPAUtil.getEntityManager;
import Persistencia.Entity.Cidade;
import Persistencia.Entity.Endereco;
import Persistencia.Entity.Estado;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

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
            em = getEntityManager();
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
            em = getEntityManager();
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
            em = getEntityManager();

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
            em = getEntityManager();
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
            em = getEntityManager();
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
            em = getEntityManager();
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
            em = getEntityManager();
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
            em = getEntityManager();
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

    public List<PacienteVO> listarPacientesSemAtendimento() {
        EntityManager em = null;
        List<PacienteVO> pacientesVO = new ArrayList<>();

        try {
            em = getEntityManager();

            String jpql = """
            SELECT p FROM Paciente p
            WHERE p.id NOT IN (
                SELECT a.paciente.id FROM Agenda a
                WHERE a.tipoAtendimento != 'Emergencial' AND a.tipoAtendimento IS NOT NULL
            )
            AND p.ativo = true
        """;

            List<Paciente> pacientes = em.createQuery(jpql, Paciente.class).getResultList();

            for (Paciente paciente : pacientes) {
                pacientesVO.add(PacienteVO.fromEntity(paciente));
            }
        } catch (Exception e) {
            logger.error("Erro ao listar pacientes sem atendimento: ", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        return pacientesVO;
    }

    public List<Paciente> buscarPorFiltros(PacienteVO filtro) {
        StringBuilder jpql = new StringBuilder(
                "SELECT p FROM Paciente p "
                + "JOIN FETCH p.endereco e "
                + "JOIN FETCH e.cidade c "
                + "JOIN FETCH c.estado est "
                + "LEFT JOIN FETCH p.responsavel r "
                + "LEFT JOIN FETCH p.nacionalidade n "
                + "WHERE 1=1"
        );
        Map<String, Object> params = new HashMap<>();

        if (filtro.getNome() != null && !filtro.getNome().isEmpty() && !"null".equals(filtro.getNome())) {
            jpql.append(" AND p.nome LIKE :nome");
            params.put("nome", "%" + filtro.getNome() + "%");
        }
        if (filtro.getProfissao() != null && !filtro.getProfissao().isEmpty() && !"null".equals(filtro.getProfissao())) {
            jpql.append(" AND p.profissao LIKE :profissao");
            params.put("profissao", "%" + filtro.getProfissao() + "%");
        }
        if (filtro.getResponsavel() != null && filtro.getResponsavel().getNome() != null && !filtro.getResponsavel().getNome().isEmpty() && !"null".equals(filtro.getResponsavel().getNome())) {
            jpql.append(" AND r.nome LIKE :responsavel");
            params.put("responsavel", "%" + filtro.getResponsavel().getNome() + "%");
        }

        Endereco endereco = filtro.getEndereco();
        if (endereco != null) {
            if (endereco.getRua() != null && !endereco.getRua().isEmpty() && !"null".equals(endereco.getRua())) {
                jpql.append(" AND e.rua LIKE :rua");
                params.put("rua", "%" + endereco.getRua() + "%");
            }
            if (endereco.getNumero() != null) {
                jpql.append(" AND e.numero = :numero");
                params.put("numero", endereco.getNumero());
            }
            if (endereco.getComplemento() != null && !endereco.getComplemento().isEmpty() && !"null".equals(endereco.getComplemento())) {
                jpql.append(" AND e.complemento LIKE :complemento");
                params.put("complemento", "%" + endereco.getComplemento() + "%");
            }
            if (endereco.getBairro() != null && !endereco.getBairro().isEmpty() && !"null".equals(endereco.getBairro())) {
                jpql.append(" AND e.bairro LIKE :bairro");
                params.put("bairro", "%" + endereco.getBairro() + "%");
            }
            if (endereco.getCep() != null && !endereco.getCep().isEmpty() && !"null".equals(endereco.getCep())) {
                jpql.append(" AND e.cep = :cep");
                params.put("cep", formatarCep(endereco.getCep()));
            }
            if (endereco.getCidade() != null) {
                Cidade cidadeFiltro = filtro.getEndereco().getCidade();
                if (cidadeFiltro != null) {
                    if (cidadeFiltro.getId() != null) {
                        jpql.append(" AND c.id = :cidadeId");
                        params.put("cidadeId", cidadeFiltro.getId());
                    }

                    Estado estadoFiltro = cidadeFiltro.getEstado();
                    if (estadoFiltro != null && estadoFiltro.getId() != null) {
                        jpql.append(" AND est.id = :estadoId");
                        params.put("estadoId", estadoFiltro.getId());
                    }
                }
            }
        }

        if (filtro.getEstadoCivil() != null && !filtro.getEstadoCivil().isEmpty() && !"null".equals(filtro.getEstadoCivil())) {
            jpql.append(" AND p.estadoCivil = :estadoCivil");
            params.put("estadoCivil", filtro.getEstadoCivil());
        }
        if (filtro.getEstagiario() != null) {
            jpql.append(" AND p.estagiario.id = :estagiario");
            params.put("estagiario", filtro.getEstagiario());
        }
        if (filtro.getGenero() != null && !filtro.getGenero().isEmpty() && !"null".equals(filtro.getGenero())) {
            jpql.append(" AND p.genero = :genero");
            params.put("genero", filtro.getGenero());
        }
        if (filtro.getInstrucao() != null && !filtro.getInstrucao().isEmpty() && !"null".equals(filtro.getInstrucao())) {
            jpql.append(" AND p.grauInstrucao = :instrucao");
            params.put("instrucao", filtro.getInstrucao());
        }
        if (filtro.getNacionalidade() != null) {
            jpql.append(" AND p.nacionalidade.id = :nacionalidade");
            params.put("nacionalidade", filtro.getNacionalidade());
        }
        if (filtro.getOrientacao() != null && !filtro.getOrientacao().isEmpty() && !"null".equals(filtro.getOrientacao())) {
            jpql.append(" AND p.orientacaoSexual = :orientacao");
            params.put("orientacao", filtro.getOrientacao());
        }
        if (filtro.getRaca_cor_etnia() != null && !filtro.getRaca_cor_etnia().isEmpty() && !"null".equals(filtro.getRaca_cor_etnia())) {
            jpql.append(" AND p.racaCorEtnia = :racaCorEtnia");
            params.put("racaCorEtnia", filtro.getRaca_cor_etnia());
        }
        if (filtro.getDataNascimento() != null && !filtro.getDataNascimento().isEmpty() && !"null".equals(filtro.getDataNascimento())) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate dataNascimento = LocalDate.parse(filtro.getDataNascimento(), formatter);

                jpql.append(" AND p.dataNascimento = :dataNascimento");
                params.put("dataNascimento", dataNascimento);
            } catch (DateTimeParseException e) {
                e.printStackTrace();
            }
        }
        if (filtro.getDataInscricao() != null) {
            jpql.append(" AND p.dataInscricao = :dataInscricao");
            params.put("dataInscricao", filtro.getDataInscricao());
        }
        jpql.append(" AND p.atendido = :atendido");
        params.put("atendido", filtro.isAtendido());

        jpql.append(" AND p.ativo = :ativo");
        params.put("ativo", filtro.isAtivo());

        // Imprime antes de criar a query
        System.out.println("JPQL gerada:");
        System.out.println(jpql.toString());

        System.out.println("Parâmetros:");
        params.forEach((chave, valor) -> System.out.println(chave + " = " + valor));

        TypedQuery<Paciente> query = getEntityManager().createQuery(jpql.toString(), Paciente.class);
        params.forEach(query::setParameter);
        return query.getResultList();
    }

    public static String formatarCep(String cep) {
        if (cep == null || cep.length() != 8) {
            return cep;
        }
        return cep.substring(0, 2) + "." + cep.substring(2, 5) + "-" + cep.substring(5);
    }

}
