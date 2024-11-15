/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Visao.Telas;
import Visao.Components.SimpleForm;
import Visao.Utils.EditorTextPaneEstilization;
import Visao.Utils.redimencionarIcones;

/**
 *
 * @author john
 */
public class FormProduzirRelatorio extends SimpleForm {

    /**
     * Creates new form atendimentoForm
     */
    public FormProduzirRelatorio() {
        initComponents();
        //redimensionarIcones();
        
        EditorTextPaneEstilization.EstilizeEditorTextPane(tpTextoRelatorio);

        
        redimencionarIcones redimencionarIcone = new redimencionarIcones();
        redimencionarIcone.redimensionarIcones(btSalvar, "/Multimidia/imagens/salvar-btn.png");
        redimencionarIcone.redimensionarIcones(btCancelar, "/Multimidia/imagens/excluir.png");

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pCentro = new javax.swing.JPanel();
        ldDescricao = new javax.swing.JLabel();
        btSalvar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane4 = new javax.swing.JScrollPane();
        tpTextoRelatorio = new javax.swing.JTextPane();
        btCancelar = new javax.swing.JButton();
        pNorth = new javax.swing.JPanel();
        lbClinica = new javax.swing.JLabel();
        lbProntuario = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(1000, 680));
        setMinimumSize(new java.awt.Dimension(1000, 680));
        setPreferredSize(new java.awt.Dimension(1000, 680));
        setLayout(new java.awt.BorderLayout());

        pCentro.setBackground(java.awt.SystemColor.controlHighlight);
        pCentro.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        pCentro.setPreferredSize(new java.awt.Dimension(1024, 768));

        ldDescricao.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        ldDescricao.setForeground(new java.awt.Color(0, 102, 102));
        ldDescricao.setText("Texto do Relatório:");

        btSalvar.setBackground(new java.awt.Color(102, 255, 102));
        btSalvar.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btSalvar.setForeground(new java.awt.Color(51, 51, 51));
        btSalvar.setText("Submeter");

        tpTextoRelatorio.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jScrollPane4.setViewportView(tpTextoRelatorio);

        btCancelar.setBackground(new java.awt.Color(255, 102, 102));
        btCancelar.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btCancelar.setForeground(new java.awt.Color(51, 51, 51));
        btCancelar.setText("Cancelar");
        btCancelar.setMaximumSize(new java.awt.Dimension(108, 39));
        btCancelar.setMinimumSize(new java.awt.Dimension(108, 39));
        btCancelar.setPreferredSize(new java.awt.Dimension(108, 39));
        btCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pCentroLayout = new javax.swing.GroupLayout(pCentro);
        pCentro.setLayout(pCentroLayout);
        pCentroLayout.setHorizontalGroup(
            pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pCentroLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ldDescricao)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 947, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(43, Short.MAX_VALUE))
            .addGroup(pCentroLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pCentroLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btSalvar)
                .addGap(44, 44, 44))
        );
        pCentroLayout.setVerticalGroup(
            pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pCentroLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(ldDescricao)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btSalvar)
                    .addComponent(btCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        add(pCentro, java.awt.BorderLayout.CENTER);

        pNorth.setBackground(new java.awt.Color(0, 102, 102));
        pNorth.setPreferredSize(new java.awt.Dimension(638, 183));

        lbClinica.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lbClinica.setForeground(new java.awt.Color(255, 255, 255));
        lbClinica.setText("Clínica de Psicologia");

        lbProntuario.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        lbProntuario.setForeground(new java.awt.Color(255, 255, 255));
        lbProntuario.setText("Produzir Relatório");

        javax.swing.GroupLayout pNorthLayout = new javax.swing.GroupLayout(pNorth);
        pNorth.setLayout(pNorthLayout);
        pNorthLayout.setHorizontalGroup(
            pNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pNorthLayout.createSequentialGroup()
                .addContainerGap(213, Short.MAX_VALUE)
                .addGroup(pNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbClinica)
                    .addComponent(lbProntuario))
                .addGap(0, 558, Short.MAX_VALUE))
        );
        pNorthLayout.setVerticalGroup(
            pNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pNorthLayout.createSequentialGroup()
                .addGap(0, 72, Short.MAX_VALUE)
                .addComponent(lbProntuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbClinica)
                .addGap(0, 63, Short.MAX_VALUE))
        );

        add(pNorth, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents

    private void btCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btCancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCancelar;
    private javax.swing.JButton btSalvar;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbClinica;
    private javax.swing.JLabel lbProntuario;
    private javax.swing.JLabel ldDescricao;
    private javax.swing.JPanel pCentro;
    private javax.swing.JPanel pNorth;
    private javax.swing.JTextPane tpTextoRelatorio;
    // End of variables declaration//GEN-END:variables
 
}
