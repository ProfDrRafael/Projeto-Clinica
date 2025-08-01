/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.Dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
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
public class GraficosDAO {

    private static final Logger logger = LoggerFactory.getLogger(GraficosDAO.class);

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

    public static List<Object[]> buscarEvolucaoAtendimentos(LocalDate inicio, LocalDate fim, String periodo) {
        EntityManager em = JPAUtil.getEntityManager();
        List<Object[]> result = new ArrayList<>();

        try {
            String groupBy;
            switch (periodo) {
                case "dia" ->
                    groupBy = "FUNCTION('DATE', a.data)";
                case "semana" ->
                    groupBy = "YEAR(a.data), WEEK(a.data)";
                case "mês" ->
                    groupBy = "YEAR(a.data), MONTH(a.data)";
                case "ano" ->
                    groupBy = "YEAR(a.data)";
                default ->
                    throw new IllegalArgumentException("Período inválido.");
            }

            String hql = "SELECT " + groupBy + ", COUNT(a) "
                    + "FROM Atendimento a "
                    + "WHERE a.data BETWEEN :inicio AND :fim "
                    + "GROUP BY " + groupBy + " "
                    + "ORDER BY " + groupBy;

            Query query = em.createQuery(hql);
            query.setParameter("inicio", inicio);
            query.setParameter("fim", fim);

            result = query.getResultList();
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

    public static List<Object[]> buscarAtendimentosAgendadosVersusRealizados(
            LocalDate inicio,
            LocalDate fim,
            String periodo,
            String tipoAtendimento,
            Integer estagiarioId
    ) {
        EntityManager em = JPAUtil.getEntityManager();
        List<Object[]> result = new ArrayList<>();
        try {
            StringBuilder hql = new StringBuilder("SELECT ");

            switch (periodo) {
                case "dia" ->
                    hql.append("a.data, ");
                case "semana" ->
                    hql.append("YEAR(a.data), WEEK(a.data), ");
                case "mês" ->
                    hql.append("YEAR(a.data), MONTH(a.data), ");
                case "ano" ->
                    hql.append("YEAR(a.data), ");
                default ->
                    throw new IllegalArgumentException("Período inválido: " + periodo);
            }

            hql.append("COUNT(CASE WHEN a.comparecimento = false THEN 1 END), ")
                    .append("COUNT(CASE WHEN a.comparecimento = true THEN 1 END) ")
                    .append("FROM Atendimento a WHERE 1=1 ");

            if (inicio != null) {
                hql.append("AND a.data >= :inicio ");
            }
            if (fim != null) {
                hql.append("AND a.data <= :fim ");
            }
            if (tipoAtendimento != null && !tipoAtendimento.isEmpty()) {
                hql.append("AND a.tipoAtendimento = :tipoAtendimento ");
            }
            if (estagiarioId != null) {
                hql.append("AND a.estagiario.id = :estagiarioId ");
            }

            switch (periodo) {
                case "dia" ->
                    hql.append("GROUP BY a.data ORDER BY a.data");
                case "mês" ->
                    hql.append("GROUP BY YEAR(a.data), MONTH(a.data) ORDER BY YEAR(a.data), MONTH(a.data)");
                case "ano" ->
                    hql.append("GROUP BY YEAR(a.data) ORDER BY YEAR(a.data)");
            }

            TypedQuery<Object[]> query = em.createQuery(hql.toString(), Object[].class);

            if (inicio != null) {
                query.setParameter("inicio", inicio);
            }
            if (fim != null) {
                query.setParameter("fim", fim);
            }
            if (tipoAtendimento != null && !tipoAtendimento.isEmpty()) {
                query.setParameter("tipoAtendimento", tipoAtendimento);
            }
            if (estagiarioId != null) {
                query.setParameter("estagiarioId", estagiarioId);
            }

            result = query.getResultList();
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao buscar atendimentos agendados vs realizados: ", e);
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

    public static List<Object[]> getPacientesPorFaixaEtaria(List<int[]> faixasEtarias) {
        EntityManager em = JPAUtil.getEntityManager();
        List<Object[]> result = new ArrayList<>();
        try {
            StringBuilder hql = new StringBuilder("SELECT ");
            System.out.println(faixasEtarias);
            for (int i = 0; i < faixasEtarias.size(); i++) {
                int[] faixa = faixasEtarias.get(i);
                System.out.println("FAIXA ETARIAAAA VALOR 1: " + faixa[0] + " FAIXA ETARIAAAA VALOR 2: " + faixa[1]);
                hql.append("SUM(CASE WHEN TIMESTAMPDIFF(YEAR, p.dataNascimento, CURRENT_DATE()) BETWEEN ")
                        .append(faixa[0]).append(" AND ").append(faixa[1])
                        .append(" THEN 1 ELSE 0 END), ");
            }
            hql.setLength(hql.length() - 2);
            hql.append(" FROM Paciente p");

            Query query = em.createQuery(hql.toString());
            Object[] queryResult = (Object[]) query.getSingleResult();

            for (int i = 0; i < queryResult.length; i++) {
                result.add(new Object[]{
                    faixasEtarias.get(i)[0] + "-" + faixasEtarias.get(i)[1],
                    queryResult[i]
                });
            }
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

    public static List<Object[]> getTaxaComparecimentoPorPeriodo(LocalDate inicio, LocalDate fim, String periodo) {
        System.out.println("INICIO: " + inicio);
        System.out.println("FIM: " + fim);

        EntityManager em = JPAUtil.getEntityManager();
        List<Object[]> result = new ArrayList<>();
        try {
            String groupBy;
            switch (periodo.toLowerCase()) {
                case "dia" ->
                    groupBy = "FUNCTION('DATE', a.data)";
                case "semana" ->
                    groupBy = "YEAR(a.data), WEEK(a.data)";
                case "mês" ->
                    groupBy = "YEAR(a.data), MONTH(a.data)";
                case "ano" ->
                    groupBy = "YEAR(a.data)";
                default ->
                    throw new IllegalArgumentException("Período inválido.");
            }

            String hql = "SELECT " + groupBy + ", COUNT(a), "
                    + "SUM(CASE WHEN a.comparecimento = true THEN 1 ELSE 0 END) "
                    + "FROM Atendimento a "
                    + "WHERE a.data BETWEEN :inicio AND :fim "
                    + "GROUP BY " + groupBy + " ORDER BY " + groupBy;

            Query query = em.createQuery(hql);
            query.setParameter("inicio", inicio);
            query.setParameter("fim", fim);
            System.out.println("HQL: " + hql + " QUERY: " + query);
            result = query.getResultList();
        } finally {
            em.close();
        }
        return result;
    }

    public static List<Object[]> getEvolucaoListaEspera(LocalDate inicio, LocalDate fim, String periodo) {
        EntityManager em = JPAUtil.getEntityManager();
        List<Object[]> result = new ArrayList<>();
        try {
            String groupBy;
            switch (periodo.toLowerCase()) {
                case "dia" ->
                    groupBy = "FUNCTION('DATE', p.dataInscricao)";
                case "semana" ->
                    groupBy = "YEAR(p.dataInscricao), WEEK(p.dataInscricao)";
                case "mês" ->
                    groupBy = "YEAR(p.dataInscricao), MONTH(p.dataInscricao)";
                case "ano" ->
                    groupBy = "YEAR(p.dataInscricao)";
                default ->
                    throw new IllegalArgumentException("Período inválido.");
            }

            String hql = "SELECT " + groupBy + ", COUNT(le) "
                    + "FROM ListaDeEspera le "
                    + "JOIN le.paciente p "
                    + "WHERE p.dataInscricao BETWEEN :inicio AND :fim "
                    + "GROUP BY " + groupBy + " ORDER BY " + groupBy;

            Query query = em.createQuery(hql);
            query.setParameter("inicio", inicio);
            query.setParameter("fim", fim);

            result = query.getResultList();
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

    public static List<Object[]> buscarTempoMedioEspera(LocalDate inicio,
            LocalDate fim,
            String periodo,
            String tipoAtendimento) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            StringBuilder hql = new StringBuilder();

            // SELECT dinâmico
            hql.append("SELECT ");
            switch (periodo) {
                case "dia" ->
                    hql.append("a.data, ");
                case "semana" ->
                    hql.append("YEAR(a.data), FUNCTION('week', a.data), ");
                case "mês" ->
                    hql.append("YEAR(a.data), MONTH(a.data), ");
                case "ano" ->
                    hql.append("YEAR(a.data), ");
                default ->
                    throw new IllegalArgumentException("Período inválido: " + periodo);
            }

            hql.append("AVG(CAST(FUNCTION('DATEDIFF', a.data, p.dataInscricao) AS integer)) ");

            // FROM e JOINs
            hql.append("FROM Atendimento a ")
                    .append("JOIN a.prontuario pr ")
                    .append("JOIN pr.paciente p ")
                    .append("WHERE a.data BETWEEN :inicio AND :fim ");

            if (tipoAtendimento != null && !tipoAtendimento.isBlank()) {
                hql.append("AND a.tipoAtendimento = :tipoAtendimento ");
            }

            // primeiro atendimento de todos os tempos
            hql.append("AND a.data = (")
                    .append("  SELECT MIN(a2.data) ")
                    .append("  FROM Atendimento a2 ")
                    .append("  WHERE a2.prontuario = pr")
                    .append(") ");

            // GROUP BY dinâmico
            switch (periodo) {
                case "dia" ->
                    hql.append("GROUP BY a.data ");
                case "semana" ->
                    hql.append("GROUP BY YEAR(a.data), FUNCTION('week', a.data) ");
                case "mês", "mes" ->
                    hql.append("GROUP BY YEAR(a.data), MONTH(a.data) ");
                case "ano" ->
                    hql.append("GROUP BY YEAR(a.data) ");
            }

            hql.append("ORDER BY 1");

            // criação da query
            TypedQuery<Object[]> query = em.createQuery(hql.toString(), Object[].class)
                    .setParameter("inicio", inicio)
                    .setParameter("fim", fim);

            if (tipoAtendimento != null && !tipoAtendimento.isBlank()) {
                query.setParameter("tipoAtendimento", tipoAtendimento);
            }

            List<Object[]> result = query.getResultList();
            System.err.println(">> rows: " + result.size());
            return result;

        } finally {
            em.close();
        }
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

    public static List<Object[]> getDistribuicaoAtendimentosPorSala(
            LocalDate inicio, LocalDate fim,
            int horaInicio, int horaFim
    ) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String hql = "SELECT a.sala, COUNT(a) "
                    + "FROM Agenda a "
                    + "WHERE a.data BETWEEN :inicio AND :fim "
                    + "AND HOUR(a.hora) BETWEEN :hIni AND :hFim "
                    + "GROUP BY a.sala ORDER BY a.sala";
            TypedQuery<Object[]> q = em.createQuery(hql, Object[].class)
                    .setParameter("inicio", inicio)
                    .setParameter("fim", fim)
                    .setParameter("hIni", horaInicio)
                    .setParameter("hFim", horaFim);
            return q.getResultList();
        } finally {
            em.close();
        }
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
