/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.Dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author john
 */
public class TabelaEstatisticaDAO {

    private static final Logger logger = LoggerFactory.getLogger(TabelaEstatisticaDAO.class);

    public static List<Object[]> buscarAtendimentosPorUsuario(Integer estagiarioId) {
        EntityManager em = JPAUtil.getEntityManager();
        List<Object[]> result = new ArrayList<>();
        try {
            String hql = "SELECT a FROM Atendimento a";

            if (estagiarioId != null) {
                hql += " WHERE a.estagiario.id = :estagiarioId";
            }

            TypedQuery<Object[]> query = em.createQuery(hql, Object[].class);

            if (estagiarioId != null) {
                query.setParameter("estagiarioId", estagiarioId);
            }

            result = query.getResultList();
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao buscar atendimentos: ", e);
            return null;
        } finally {
            em.close();
        }
        return result;
    }

    public static List<Object[]> buscarAtendimentosAgendadosVersusRealizados(Integer estagiarioId) {
        EntityManager em = JPAUtil.getEntityManager();
        List<Object[]> result = new ArrayList<>();
        try {
            String hql = "SELECT "
                    + "a.data AS data, "
                    + "COUNT(CASE WHEN a.comparecimento = false THEN 1 END) AS agendados, "
                    + "COUNT(CASE WHEN a.comparecimento = true THEN 1 END) AS realizados "
                    + "FROM Atendimento a ";

            if (estagiarioId != null) {
                hql += "WHERE a.estagiario.id = :estagiarioId ";
            }

            hql += "GROUP BY a.data "
                    + "ORDER BY a.data";

            TypedQuery<Object[]> query = em.createQuery(hql, Object[].class);

            if (estagiarioId != null) {
                query.setParameter("estagiarioId", estagiarioId);
            }

            result = query.getResultList();
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao buscar atendimentos: ", e);
            return null;
        } finally {
            em.close();
        }
        return result;
    }

    public static Map<String, Long> getDistribuicaoPacientes(String atributo) {
        EntityManager em = JPAUtil.getEntityManager();
        Map<String, Long> distribuicao = new HashMap<>();
        try {
            String hql = "SELECT p." + atributo + ", COUNT(p) FROM Paciente p GROUP BY p." + atributo;
            List<Object[]> result = em.createQuery(hql, Object[].class).getResultList();

            for (Object[] row : result) {
                distribuicao.put((String) row[0], (Long) row[1]);
            }
        } finally {
            em.close();
        }
        return distribuicao;
    }

    public static List<Object[]> getPacientesPorFaixaEtaria() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Object[]> result = new ArrayList<>();
        try {
            String hql = "SELECT "
                    + "CASE "
                    + "WHEN TIMESTAMPDIFF(YEAR, p.dataNascimento, CURRENT_DATE()) BETWEEN 0 AND 18 THEN '0-18' "
                    + "WHEN TIMESTAMPDIFF(YEAR, p.dataNascimento, CURRENT_DATE()) BETWEEN 19 AND 35 THEN '19-35' "
                    + "WHEN TIMESTAMPDIFF(YEAR, p.dataNascimento, CURRENT_DATE()) BETWEEN 36 AND 60 THEN '36-60' "
                    + "ELSE '60+' END, "
                    + "COUNT(p) FROM Paciente p GROUP BY 1";

            result = em.createQuery(hql, Object[].class).getResultList();
        } finally {
            em.close();
        }
        return result;
    }

    public static List<Object[]> getTaxaComparecimentoPorPeriodo(String periodo) {
        EntityManager em = JPAUtil.getEntityManager();
        List<Object[]> result = new ArrayList<>();
        try {
            String groupBy;
            switch (periodo.toLowerCase()) {
                case "dia":
                    groupBy = "DATE(a.data)";
                    break;
                case "semana":
                    groupBy = "YEAR(a.data), WEEK(a.data)";
                    break;
                case "mes":
                    groupBy = "YEAR(a.data), MONTH(a.data)";
                    break;
                default:
                    throw new IllegalArgumentException("Período inválido. Escolha entre: 'dia', 'semana', ou 'mes'.");
            }
            String hql = "SELECT " + groupBy + ", "
                    + "COUNT(a) AS totalAtendimentos, "
                    + "SUM(CASE WHEN a.comparecimento = true THEN 1 ELSE 0 END) AS atendidos, "
                    + "ROUND(((SUM(CASE WHEN a.comparecimento = true THEN 1 ELSE 0 END) * 1.0 / COUNT(a)) * 100), 2) AS taxaComparecimento "
                    + "FROM Atendimento a GROUP BY " + groupBy + " ORDER BY " + groupBy;
            result = em.createQuery(hql, Object[].class).getResultList();
        } finally {
            em.close();
        }
        return result;
    }

    public static List<Object[]> getEvolucaoListaEspera() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Object[]> result = new ArrayList<>();
        try {
            String hql = "SELECT YEAR(p.dataInscricao), MONTH(p.dataInscricao), COUNT(le) "
                    + "FROM ListaDeEspera le "
                    + "JOIN le.paciente p "
                    + "GROUP BY YEAR(p.dataInscricao), MONTH(p.dataInscricao) "
                    + "ORDER BY YEAR(p.dataInscricao), MONTH(p.dataInscricao)";

            result = em.createQuery(hql, Object[].class).getResultList();
        } finally {
            em.close();
        }
        return result;
    }

    public static List<Object[]> getTempoMedioEspera() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Object[]> result = null;
        try {
            String hql = "SELECT a.data, AVG(DATEDIFF(a.data, p.dataInscricao)) "
                    + "FROM Atendimento a "
                    + "JOIN a.prontuario pac "
                    + "JOIN Paciente p ON pac.id = p.id "
                    + "WHERE a.data = (SELECT MIN(a2.data) FROM Atendimento a2 WHERE a2.prontuario.id = pac.id) "
                    + "GROUP BY a.data "
                    + "ORDER BY a.data";

            TypedQuery<Object[]> query = em.createQuery(hql, Object[].class);
            result = query.getResultList();
        } finally {
            em.close();
        }
        return result;
    }

    public static List<Object[]> getOcupacaoSalas() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Object[]> result = new ArrayList<>();
        try {
            String hql = "SELECT a.sala, COUNT(a) FROM Agenda a GROUP BY a.sala ORDER BY COUNT(a) DESC";

            result = em.createQuery(hql, Object[].class).getResultList();
        } finally {
            em.close();
        }
        return result;
    }

    public static List<Object[]> getDistribuicaoAtendimentosPorSala() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Object[]> result = new ArrayList<>();
        try {
            String hql = "SELECT ag.sala, COUNT(a) "
                    + "FROM Atendimento a "
                    + "JOIN Agenda ag ON a.id = ag.atendimento.id "
                    + "GROUP BY ag.sala "
                    + "ORDER BY ag.sala";

            result = em.createQuery(hql, Object[].class).getResultList();
        } finally {
            em.close();
        }
        return result;
    }

    public static long getTotalPacientesAtivos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(p) FROM Paciente p WHERE p.ativo = true", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }

    public static double getTaxaMediaComparecimento() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT AVG(CASE WHEN a.comparecimento = TRUE THEN 1 ELSE 0 END) FROM Atendimento a", Double.class).getSingleResult();
        } finally {
            em.close();
        }
    }

    public static long getPacientesListaEspera() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(le) FROM ListaDeEspera le", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }

    public static long getTotalAtendimentosMesAtual() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(a) FROM Atendimento a WHERE MONTH(a.data) = MONTH(CURRENT_DATE())", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }

    public static long getEstagiariosAtivos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(e) FROM Estagiario e WHERE e.ativo = true", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }

}
