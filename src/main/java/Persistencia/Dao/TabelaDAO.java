/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.Dao;

import Persistencia.Entity.Administrador;
import com.formdev.flatlaf.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author john
 */
public class TabelaDAO {

    private static final Logger logger = LoggerFactory.getLogger(OrientadorDAO.class);

    public static List<Object[]> buscarTabelaConsultaAnonima(String queryTable) {
        EntityManager em = JPAUtil.getEntityManager();
        List<Object[]> results = null;
        try {
            // Cria a consulta nativa com a tabela dinâmica
            String queryStr = queryTable;
            Query query = em.createNativeQuery(queryStr);
            results = query.getResultList();

        } catch (Exception e) {
            logger.error("Erro ao buscar dados da tabela: ", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return results;
    }

    public static boolean deletarRegistroConsultaAnonima(List<Object> selectedIds, String tableName) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            for (Object id : selectedIds) {
                Query query = em.createQuery(
                        "DELETE FROM " + tableName + " t WHERE t.id = :id"
                );
                query.setParameter("id", id);
                query.executeUpdate();
            }

            em.getTransaction().commit();

        } catch (Exception e) {
            logger.error("Erro ao deletar registros: ", e);
            return false;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return true;
    }

    public static boolean inativarRegistroConsultaAnonima(List<Object> selectedIds, String tableName, boolean acao_inativar, String tipoUsuario) {
        EntityManager em = JPAUtil.getEntityManager();
        String tableNameLowerCase = tableName.toLowerCase(); // Garantir que seja minúsculo
        String tipoUsuarioLowerCase = (tipoUsuario != null && !tipoUsuario.isEmpty()) ? tipoUsuario.toLowerCase() : null;

        if (selectedIds == null || selectedIds.isEmpty()) {
            throw new IllegalArgumentException("Nenhum id para inativação.");
        }

        try {

            em.getTransaction().begin();
            String sql = "";

            if ("usuarios".equals(tableNameLowerCase)) {
                sql = "UPDATE " + tipoUsuarioLowerCase + " SET ativo = ? WHERE id = ?";

            } else {
                sql = "UPDATE " + tableNameLowerCase + " SET ativo = ? WHERE id = ?";

            }

            for (Object id : selectedIds) {
                Query query = em.createNativeQuery(sql);
                query.setParameter(1, acao_inativar);
                query.setParameter(2, id);
                query.executeUpdate();
            }

            em.getTransaction().commit();

        } catch (Exception e) {
            logger.error("Erro ao inativar registros: ", e);
            return false;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return true;
    }

    public static boolean inativarPacienteArquivoMorto(List<Object> selectedIds) {
        EntityManager em = JPAUtil.getEntityManager();
        if (selectedIds == null || selectedIds.isEmpty()) {
            throw new IllegalArgumentException("Nenhum id para inativação.");
        }
        try {
            em.getTransaction().begin();

            for (Object id : selectedIds) {

                String sqlInsert = "INSERT INTO arquivo_morto (paciente_id, data_arquivamento, motivo) VALUES (?, ?, ?)";
                
                java.sql.Date dataArquivamento = new java.sql.Date(System.currentTimeMillis());

                Query query = em.createNativeQuery(sqlInsert);
                query.setParameter(1, id);
                query.setParameter(2, dataArquivamento); 
                query.setParameter(3, "Inativação por tabela.");
                query.executeUpdate();
            }
            em.getTransaction().commit();
            return true;
            
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            logger.error("Erro ao inativar registros: ", e);
            return false;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
