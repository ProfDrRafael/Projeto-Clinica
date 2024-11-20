/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Visao.Telas;
import Visao.Components.SimpleForm;
import Visao.JframeManager.FormManager;
import Visao.Utils.MessagesAlert;
import Visao.Utils.RedimencionarIcones;

/**
 *
 * @author john
 */
public class MenuAdministrador extends SimpleForm {

    /**
     * Creates new form administradorMenu
     */
    public MenuAdministrador() {
        initComponents();

        RedimencionarIcones redimencionarIcone = new RedimencionarIcones();
        redimencionarIcone.redimensionarIcones(btAcessarListaEspera, "/Multimidia/imagens/listaEspera.png");
        redimencionarIcone.redimensionarIcones(btRelatorioClinica, "/Multimidia/imagens/secretaria.png");
        redimencionarIcone.redimensionarIcones(btGerenciarListas, "/Multimidia/imagens/cadastrar.png");
        redimencionarIcone.redimensionarIcones(btGerenciarAgenda, "/Multimidia/imagens/agenda.png");
        redimencionarIcone.redimensionarIcones(btGerenciarUsuarios, "/Multimidia/imagens/cadastrar.png");
        redimencionarIcone.redimensionarIcones(btProntuarioPesquisa, "/Multimidia/imagens/editar-btn.png");
        redimencionarIcone.redimensionarIcones(btDesconectar, "/Multimidia/imagens/desconectar.png");
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        pNorth = new javax.swing.JPanel();
        lbClinica = new javax.swing.JLabel();
        lbProntuario = new javax.swing.JLabel();
        pCentro = new javax.swing.JPanel();
        btRelatorioClinica = new javax.swing.JButton();
        btGerenciarListas = new javax.swing.JButton();
        btProntuarioPesquisa = new javax.swing.JButton();
        btGerenciarAgenda = new javax.swing.JButton();
        btAcessarListaEspera = new javax.swing.JButton();
        btGerenciarUsuarios = new javax.swing.JButton();
        btDesconectar = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(975, 630));
        setMinimumSize(new java.awt.Dimension(975, 630));
        setPreferredSize(new java.awt.Dimension(975, 630));
        setLayout(new java.awt.BorderLayout());

        pNorth.setBackground(new java.awt.Color(0, 102, 102));
        pNorth.setPreferredSize(new java.awt.Dimension(638, 183));

        lbClinica.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lbClinica.setForeground(new java.awt.Color(255, 255, 255));
        lbClinica.setText("Clínica de Psicologia");

        lbProntuario.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        lbProntuario.setForeground(new java.awt.Color(255, 255, 255));
        lbProntuario.setText("Administrador");

        javax.swing.GroupLayout pNorthLayout = new javax.swing.GroupLayout(pNorth);
        pNorth.setLayout(pNorthLayout);
        pNorthLayout.setHorizontalGroup(
            pNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pNorthLayout.createSequentialGroup()
                .addGap(215, 215, 215)
                .addGroup(pNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbClinica)
                    .addComponent(lbProntuario))
                .addContainerGap(683, Short.MAX_VALUE))
        );
        pNorthLayout.setVerticalGroup(
            pNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pNorthLayout.createSequentialGroup()
                .addGap(0, 60, Short.MAX_VALUE)
                .addComponent(lbProntuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbClinica)
                .addGap(0, 69, Short.MAX_VALUE))
        );

        add(pNorth, java.awt.BorderLayout.NORTH);

        pCentro.setBackground(java.awt.SystemColor.controlHighlight);
        pCentro.setPreferredSize(new java.awt.Dimension(1024, 768));
        pCentro.setLayout(new java.awt.GridBagLayout());

        btRelatorioClinica.setBackground(new java.awt.Color(64, 61, 88));
        btRelatorioClinica.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btRelatorioClinica.setForeground(new java.awt.Color(255, 255, 255));
        btRelatorioClinica.setText("Relatórios da Clínica");
        btRelatorioClinica.setMaximumSize(new java.awt.Dimension(200, 40));
        btRelatorioClinica.setMinimumSize(new java.awt.Dimension(200, 40));
        btRelatorioClinica.setPreferredSize(new java.awt.Dimension(200, 40));
        btRelatorioClinica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRelatorioClinicaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 60;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 6, 20, 6);
        pCentro.add(btRelatorioClinica, gridBagConstraints);

        btGerenciarListas.setBackground(new java.awt.Color(91, 46, 72));
        btGerenciarListas.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btGerenciarListas.setForeground(new java.awt.Color(255, 255, 255));
        btGerenciarListas.setText("Gerenciar Listas");
        btGerenciarListas.setMaximumSize(new java.awt.Dimension(200, 40));
        btGerenciarListas.setMinimumSize(new java.awt.Dimension(200, 40));
        btGerenciarListas.setPreferredSize(new java.awt.Dimension(200, 40));
        btGerenciarListas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btGerenciarListasActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 60;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 6, 20, 6);
        pCentro.add(btGerenciarListas, gridBagConstraints);

        btProntuarioPesquisa.setBackground(new java.awt.Color(30, 27, 24));
        btProntuarioPesquisa.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btProntuarioPesquisa.setForeground(new java.awt.Color(255, 255, 255));
        btProntuarioPesquisa.setText("Prontuários p/ Pesquisa");
        btProntuarioPesquisa.setMaximumSize(new java.awt.Dimension(200, 40));
        btProntuarioPesquisa.setMinimumSize(new java.awt.Dimension(200, 40));
        btProntuarioPesquisa.setPreferredSize(new java.awt.Dimension(200, 40));
        btProntuarioPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btProntuarioPesquisaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 60;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(20, 6, 20, 6);
        pCentro.add(btProntuarioPesquisa, gridBagConstraints);

        btGerenciarAgenda.setBackground(new java.awt.Color(34, 56, 67));
        btGerenciarAgenda.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btGerenciarAgenda.setForeground(new java.awt.Color(255, 255, 255));
        btGerenciarAgenda.setText("Gerenciar Agendas");
        btGerenciarAgenda.setMaximumSize(new java.awt.Dimension(200, 40));
        btGerenciarAgenda.setMinimumSize(new java.awt.Dimension(200, 40));
        btGerenciarAgenda.setPreferredSize(new java.awt.Dimension(200, 40));
        btGerenciarAgenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btGerenciarAgendaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 60;
        gridBagConstraints.insets = new java.awt.Insets(20, 6, 20, 6);
        pCentro.add(btGerenciarAgenda, gridBagConstraints);

        btAcessarListaEspera.setBackground(new java.awt.Color(28, 16, 24));
        btAcessarListaEspera.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btAcessarListaEspera.setForeground(new java.awt.Color(255, 255, 255));
        btAcessarListaEspera.setText("Lista de Espera");
        btAcessarListaEspera.setMaximumSize(new java.awt.Dimension(200, 40));
        btAcessarListaEspera.setMinimumSize(new java.awt.Dimension(200, 40));
        btAcessarListaEspera.setPreferredSize(new java.awt.Dimension(200, 40));
        btAcessarListaEspera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAcessarListaEsperaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 60;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.insets = new java.awt.Insets(20, 6, 20, 6);
        pCentro.add(btAcessarListaEspera, gridBagConstraints);

        btGerenciarUsuarios.setBackground(new java.awt.Color(88, 100, 29));
        btGerenciarUsuarios.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btGerenciarUsuarios.setForeground(new java.awt.Color(255, 255, 255));
        btGerenciarUsuarios.setText("Gerenciar Usuários");
        btGerenciarUsuarios.setMaximumSize(new java.awt.Dimension(200, 40));
        btGerenciarUsuarios.setMinimumSize(new java.awt.Dimension(200, 40));
        btGerenciarUsuarios.setPreferredSize(new java.awt.Dimension(200, 40));
        btGerenciarUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btGerenciarUsuariosActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 60;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(20, 6, 20, 6);
        pCentro.add(btGerenciarUsuarios, gridBagConstraints);

        btDesconectar.setBackground(new java.awt.Color(255, 51, 51));
        btDesconectar.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btDesconectar.setForeground(new java.awt.Color(255, 255, 255));
        btDesconectar.setText("Desconectar");
        btDesconectar.setMaximumSize(new java.awt.Dimension(200, 40));
        btDesconectar.setMinimumSize(new java.awt.Dimension(200, 40));
        btDesconectar.setPreferredSize(new java.awt.Dimension(200, 40));
        btDesconectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDesconectarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 60;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.insets = new java.awt.Insets(20, 6, 20, 6);
        pCentro.add(btDesconectar, gridBagConstraints);

        add(pCentro, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btDesconectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDesconectarActionPerformed
        MessagesAlert logout = new MessagesAlert();
        logout.MessageAlertDesconectarOpcoes();
    }//GEN-LAST:event_btDesconectarActionPerformed

    private void btRelatorioClinicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRelatorioClinicaActionPerformed
        
    }//GEN-LAST:event_btRelatorioClinicaActionPerformed

    private void btProntuarioPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btProntuarioPesquisaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btProntuarioPesquisaActionPerformed

    private void btGerenciarUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btGerenciarUsuariosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btGerenciarUsuariosActionPerformed

    private void btGerenciarListasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btGerenciarListasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btGerenciarListasActionPerformed

    private void btGerenciarAgendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btGerenciarAgendaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btGerenciarAgendaActionPerformed

    private void btAcessarListaEsperaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAcessarListaEsperaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btAcessarListaEsperaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAcessarListaEspera;
    private javax.swing.JButton btDesconectar;
    private javax.swing.JButton btGerenciarAgenda;
    private javax.swing.JButton btGerenciarListas;
    private javax.swing.JButton btGerenciarUsuarios;
    private javax.swing.JButton btProntuarioPesquisa;
    private javax.swing.JButton btRelatorioClinica;
    private javax.swing.JLabel lbClinica;
    private javax.swing.JLabel lbProntuario;
    private javax.swing.JPanel pCentro;
    private javax.swing.JPanel pNorth;
    // End of variables declaration//GEN-END:variables
 
}
