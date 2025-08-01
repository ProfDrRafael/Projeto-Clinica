/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Visao.Telas;
import Visao.Components.Table;
import Visao.Components.PanelTemplate;
import Visao.JframeManager.FormManager;
import java.awt.BorderLayout;

/**
 *
 * @author john
 */
public class TableListaEsperaGeral extends PanelTemplate {
    
    /**
     * Creates new form listaEsperaTable
     */
    public TableListaEsperaGeral() {
        initComponents();
        
        String[] tableColumns = new String[]{"#", "ID", "Nome", "Telefone", "Data de Nascimento", "Gênero","Estado Civil", "Data Inscrição", "Disponiblidade"};
        String queryTable = "SELECT p.id as paciente_id, p.nome, p.telefone, p.data_nascimento, p.genero, p.estado_civil, p.data_inscricao, p.disponibilidade "
                + "FROM paciente p "
                + "LEFT JOIN prontuario pr ON pr.paciente_id = p.id " 
                + "LEFT JOIN atendimento a ON a.prontuario_id = pr.id "
                + "WHERE ativo = 1 "
                    + "AND a.id IS NULL;";
 

        boolean acao_ativar_ou_inativar = false;
        
        
        Table customTable = new Table(queryTable, tableColumns, "Lista de Espera Geral", "Paciente", acao_ativar_ou_inativar, "Inativar", "/Multimidia/imagens/cadeado.png");

        // Set up the painel_lista_espera layout
        painel_lista_espera.setLayout(new BorderLayout()); // Set layout to BorderLayout

        // Add the custom table to the center of the panel
        painel_lista_espera.add(customTable.createCustomTable(queryTable, tableColumns, "Paciente", null), BorderLayout.CENTER);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painel_lista_espera = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbListaEsperaNaoAtendidos = new javax.swing.JTable();
        pNorth = new javax.swing.JPanel();
        lbClinica = new javax.swing.JLabel();
        lbOrientador = new javax.swing.JLabel();
        lbLogoGeral = new javax.swing.JLabel();
        btnTrocar = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(950, 650));
        setMinimumSize(new java.awt.Dimension(950, 650));
        setPreferredSize(new java.awt.Dimension(950, 650));
        setLayout(new java.awt.BorderLayout());

        painel_lista_espera.setBackground(new java.awt.Color(255, 255, 255));
        painel_lista_espera.setForeground(new java.awt.Color(255, 255, 255));
        painel_lista_espera.setMaximumSize(new java.awt.Dimension(950, 650));
        painel_lista_espera.setMinimumSize(new java.awt.Dimension(950, 650));
        painel_lista_espera.setPreferredSize(new java.awt.Dimension(950, 650));

        tbListaEsperaNaoAtendidos.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        tbListaEsperaNaoAtendidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", "Fulano de Tal", "19", "14/05/2023", "Masculino", "Adulto"},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Posição", "Nome", "Idade", "Data Cadastro", "Sexo", "Faixa Etária"
            }
        ));
        jScrollPane1.setViewportView(tbListaEsperaNaoAtendidos);

        javax.swing.GroupLayout painel_lista_esperaLayout = new javax.swing.GroupLayout(painel_lista_espera);
        painel_lista_espera.setLayout(painel_lista_esperaLayout);
        painel_lista_esperaLayout.setHorizontalGroup(
            painel_lista_esperaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painel_lista_esperaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1005, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(3, Short.MAX_VALUE))
        );
        painel_lista_esperaLayout.setVerticalGroup(
            painel_lista_esperaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painel_lista_esperaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(213, Short.MAX_VALUE))
        );

        add(painel_lista_espera, java.awt.BorderLayout.CENTER);

        pNorth.setBackground(new java.awt.Color(0, 102, 102));
        pNorth.setPreferredSize(new java.awt.Dimension(638, 183));

        lbClinica.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lbClinica.setForeground(new java.awt.Color(255, 255, 255));
        lbClinica.setText("Clínica de Psicologia");

        lbOrientador.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        lbOrientador.setForeground(new java.awt.Color(255, 255, 255));
        lbOrientador.setText("Lista de Espera Não Atendidos");

        lbLogoGeral.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Multimidia/imagens/listaEsperaGeral.png"))); // NOI18N

        btnTrocar.setText("Ir para Lista Especifica");
        btnTrocar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrocarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pNorthLayout = new javax.swing.GroupLayout(pNorth);
        pNorth.setLayout(pNorthLayout);
        pNorthLayout.setHorizontalGroup(
            pNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pNorthLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(lbLogoGeral, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pNorthLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbOrientador)
                            .addGroup(pNorthLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(lbClinica)))
                        .addGap(0, 358, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pNorthLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnTrocar)
                        .addContainerGap())))
        );
        pNorthLayout.setVerticalGroup(
            pNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pNorthLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lbOrientador)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbClinica)
                .addGap(58, 58, 58))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pNorthLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pNorthLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnTrocar))
                    .addComponent(lbLogoGeral, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE))
                .addContainerGap())
        );

        add(pNorth, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents

    private void btnTrocarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrocarActionPerformed
      FormManager.showForm(new TableListaEsperaEspecifica());
    }//GEN-LAST:event_btnTrocarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTrocar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbClinica;
    private javax.swing.JLabel lbLogoGeral;
    private javax.swing.JLabel lbOrientador;
    private javax.swing.JPanel pNorth;
    private javax.swing.JPanel painel_lista_espera;
    private javax.swing.JTable tbListaEsperaNaoAtendidos;
    // End of variables declaration//GEN-END:variables


}
