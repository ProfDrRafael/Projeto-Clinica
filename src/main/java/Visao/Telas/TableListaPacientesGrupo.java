/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Visao.Telas;

import Persistencia.Entity.Grupo;
import Regradenegocio.GrupoRN;
import Visao.Components.Table;
import Visao.Components.PanelTemplate;

import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.util.List;

/**
 *
 * @author john
 */
public class TableListaPacientesGrupo extends PanelTemplate {

    /**
     * Creates new form listaEsperaTable
     */
    public TableListaPacientesGrupo(int grupoId, String nomeGrupo) {
        initComponents();

        /* 1. Colunas que você quer exibir */
        String[] tableColumns = {
                "#",
                "ID Paciente",
                "Nome",
                "Data Entrada",
//                "Data Saída"
        };

        String queryTable =
                "SELECT p.id, p.nome, gxp.data_entrada, gxp.data_saida " +
                        "FROM paciente p " +
                        "JOIN grupo_x_paciente gxp ON p.id = gxp.paciente_id " +
                        "WHERE gxp.grupo_id = " + grupoId;

        boolean acaoRemoverPaciente = true;

        Table customTable = new Table(
                queryTable,
                tableColumns,
                "Pacientes do Grupo",    
                "grupo_x_paciente",              
                acaoRemoverPaciente,
                "Remover",               
                "/Multimidia/imagens/excluir.png" 
        );

        customTable.setForeignKeyId(grupoId);

        painel_lista_grupos.setLayout(new BorderLayout());
        painel_lista_grupos.add(
                customTable.createCustomTable(
                        queryTable, tableColumns,
                        "Paciente", null
                ),
                BorderLayout.CENTER
        );

        lbTitulo.setText("Pacientes do Grupo: " + nomeGrupo);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painel_lista_grupos = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbListaEsperaEspecifica = new javax.swing.JTable();
        pNorth = new javax.swing.JPanel();
        lbClinica = new javax.swing.JLabel();
        lbTitulo = new javax.swing.JLabel();
        lbLogoGrupos = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(1000, 768));
        setMinimumSize(new java.awt.Dimension(1000, 768));
        setPreferredSize(new java.awt.Dimension(1000, 768));
        setLayout(new java.awt.BorderLayout());

        painel_lista_grupos.setBackground(new java.awt.Color(255, 255, 255));
        painel_lista_grupos.setForeground(new java.awt.Color(255, 255, 255));
        painel_lista_grupos.setMaximumSize(new java.awt.Dimension(99999, 99999999));
        painel_lista_grupos.setMinimumSize(new java.awt.Dimension(0, 0));
        painel_lista_grupos.setPreferredSize(new java.awt.Dimension(1200, 768));

        tbListaEsperaEspecifica.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        tbListaEsperaEspecifica.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Nome", "Descrição"
            }
        ));
        jScrollPane1.setViewportView(tbListaEsperaEspecifica);

        javax.swing.GroupLayout painel_lista_gruposLayout = new javax.swing.GroupLayout(painel_lista_grupos);
        painel_lista_grupos.setLayout(painel_lista_gruposLayout);
        painel_lista_gruposLayout.setHorizontalGroup(
            painel_lista_gruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painel_lista_gruposLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 944, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        painel_lista_gruposLayout.setVerticalGroup(
            painel_lista_gruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painel_lista_gruposLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(125, 125, 125))
        );

        add(painel_lista_grupos, java.awt.BorderLayout.CENTER);

        pNorth.setBackground(new java.awt.Color(0, 102, 102));
        pNorth.setPreferredSize(new java.awt.Dimension(638, 183));

        lbClinica.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lbClinica.setForeground(new java.awt.Color(255, 255, 255));
        lbClinica.setText("Clínica de Psicologia");

        lbTitulo.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        lbTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lbTitulo.setText("Lista de Grupos");

        lbLogoGrupos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Multimidia/imagens/logoGrupos.png"))); // NOI18N

        javax.swing.GroupLayout pNorthLayout = new javax.swing.GroupLayout(pNorth);
        pNorth.setLayout(pNorthLayout);
        pNorthLayout.setHorizontalGroup(
            pNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pNorthLayout.createSequentialGroup()
                .addComponent(lbLogoGrupos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbClinica)
                    .addComponent(lbTitulo))
                .addContainerGap(510, Short.MAX_VALUE))
        );
        pNorthLayout.setVerticalGroup(
            pNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pNorthLayout.createSequentialGroup()
                .addGroup(pNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pNorthLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbLogoGrupos))
                    .addGroup(pNorthLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(lbTitulo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbClinica)))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        add(pNorth, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbClinica;
    private javax.swing.JLabel lbLogoGrupos;
    private javax.swing.JLabel lbTitulo;
    private javax.swing.JPanel pNorth;
    private javax.swing.JPanel painel_lista_grupos;
    private javax.swing.JTable tbListaEsperaEspecifica;
    // End of variables declaration//GEN-END:variables

}
