package visao;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author rafael
 */
public class TelaAgenda extends javax.swing.JFrame {

    /**
     * Creates new form TelaAgenda
     */
    public TelaAgenda() {
        initComponents();
        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jColorChooser1 = new javax.swing.JColorChooser();
        pCentro = new javax.swing.JPanel();
        lbData = new javax.swing.JLabel();
        lbHora = new javax.swing.JLabel();
        lbPaciente = new javax.swing.JLabel();
        tfPaciente = new javax.swing.JTextField();
        lbCelular = new javax.swing.JLabel();
        lbContato2 = new javax.swing.JLabel();
        lbEmail = new javax.swing.JLabel();
        tfEmail = new javax.swing.JTextField();
        lbObservacoes = new javax.swing.JLabel();
        btSalvar = new javax.swing.JButton();
        btCancelar = new javax.swing.JButton();
        cbHora = new javax.swing.JComboBox<>();
        lbSala = new javax.swing.JLabel();
        cbSala = new javax.swing.JComboBox<>();
        lbEstagiario = new javax.swing.JLabel();
        tfEstagiario = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        taObservacoes = new javax.swing.JTextArea();
        ftfData = new javax.swing.JFormattedTextField();
        ftfCelular = new javax.swing.JFormattedTextField();
        ftfCelular2 = new javax.swing.JFormattedTextField();
        pNorte = new javax.swing.JPanel();
        lbAgenda = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Agenda");
        setPreferredSize(new java.awt.Dimension(1024, 768));
        setResizable(false);

        pCentro.setBackground(new java.awt.Color(255, 255, 255));
        pCentro.setPreferredSize(new java.awt.Dimension(1024, 768));

        lbData.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbData.setText("*Data:");

        lbHora.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbHora.setText("*Hora:");

        lbPaciente.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbPaciente.setText("*Paciente:");

        tfPaciente.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        tfPaciente.setText("Maria Aparecida da Silva");

        lbCelular.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbCelular.setText("*Celular:");

        lbContato2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbContato2.setText("*Celular (Contato)");

        lbEmail.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbEmail.setText("*Email:");

        tfEmail.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        tfEmail.setText("email@email.com");
        tfEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfEmailActionPerformed(evt);
            }
        });

        lbObservacoes.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbObservacoes.setText("Observações:");

        btSalvar.setBackground(new java.awt.Color(51, 153, 255));
        btSalvar.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btSalvar.setText("Salvar");

        btCancelar.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btCancelar.setText("Cancelar");

        cbHora.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        cbHora.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "8:00", "9:00", "10:00", "14:00", "15:00", "16:00", "17:00" }));

        lbSala.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbSala.setText("*Sala:");

        cbSala.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        cbSala.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01", "02", "03", "04", "05", "06" }));

        lbEstagiario.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbEstagiario.setText("*Estágiario:");

        tfEstagiario.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        tfEstagiario.setText("Pedro");

        taObservacoes.setColumns(20);
        taObservacoes.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        taObservacoes.setRows(5);
        jScrollPane1.setViewportView(taObservacoes);

        try {
            ftfData.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("  /   /    ")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        ftfData.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        ftfData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ftfDataActionPerformed(evt);
            }
        });

        try {
            ftfCelular.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        ftfCelular.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        try {
            ftfCelular2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        ftfCelular2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        javax.swing.GroupLayout pCentroLayout = new javax.swing.GroupLayout(pCentro);
        pCentro.setLayout(pCentroLayout);
        pCentroLayout.setHorizontalGroup(
            pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pCentroLayout.createSequentialGroup()
                .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pCentroLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pCentroLayout.createSequentialGroup()
                                .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pCentroLayout.createSequentialGroup()
                                        .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pCentroLayout.createSequentialGroup()
                                                .addComponent(lbPaciente)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(pCentroLayout.createSequentialGroup()
                                                .addComponent(ftfData, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(cbHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(28, 28, 28))
                                            .addGroup(pCentroLayout.createSequentialGroup()
                                                .addComponent(lbData)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 129, Short.MAX_VALUE)
                                                .addComponent(lbHora)
                                                .addGap(57, 57, 57)))
                                        .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cbSala, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lbSala)
                                            .addComponent(lbCelular))
                                        .addGap(116, 116, 116))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pCentroLayout.createSequentialGroup()
                                        .addComponent(tfPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                                        .addComponent(ftfCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(28, 28, 28)))
                                .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lbContato2)
                                    .addGroup(pCentroLayout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(ftfCelular2))))
                            .addComponent(lbEmail)
                            .addComponent(tfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbObservacoes)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pCentroLayout.createSequentialGroup()
                        .addGap(289, 289, 289)
                        .addComponent(btCancelar)
                        .addGap(18, 18, 18)
                        .addComponent(btSalvar))
                    .addGroup(pCentroLayout.createSequentialGroup()
                        .addGap(494, 494, 494)
                        .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbEstagiario)
                            .addComponent(tfEstagiario, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(120, Short.MAX_VALUE))
        );
        pCentroLayout.setVerticalGroup(
            pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pCentroLayout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbData)
                    .addComponent(lbHora)
                    .addComponent(lbSala)
                    .addComponent(lbEstagiario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbSala, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfEstagiario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ftfData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbPaciente)
                    .addComponent(lbCelular)
                    .addComponent(lbContato2))
                .addGap(2, 2, 2)
                .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ftfCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ftfCelular2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(lbEmail)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(lbObservacoes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btCancelar)
                    .addComponent(btSalvar))
                .addGap(17, 17, 17))
        );

        getContentPane().add(pCentro, java.awt.BorderLayout.CENTER);

        pNorte.setBackground(new java.awt.Color(255, 255, 255));

        lbAgenda.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbAgenda.setText("Agenda");
        pNorte.add(lbAgenda);

        getContentPane().add(pNorte, java.awt.BorderLayout.NORTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfEmailActionPerformed

    private void ftfDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ftfDataActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ftfDataActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaAgenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaAgenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaAgenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaAgenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaAgenda().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCancelar;
    private javax.swing.JButton btSalvar;
    private javax.swing.JComboBox<String> cbHora;
    private javax.swing.JComboBox<String> cbSala;
    private javax.swing.JFormattedTextField ftfCelular;
    private javax.swing.JFormattedTextField ftfCelular2;
    private javax.swing.JFormattedTextField ftfData;
    private javax.swing.JColorChooser jColorChooser1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbAgenda;
    private javax.swing.JLabel lbCelular;
    private javax.swing.JLabel lbContato2;
    private javax.swing.JLabel lbData;
    private javax.swing.JLabel lbEmail;
    private javax.swing.JLabel lbEstagiario;
    private javax.swing.JLabel lbHora;
    private javax.swing.JLabel lbObservacoes;
    private javax.swing.JLabel lbPaciente;
    private javax.swing.JLabel lbSala;
    private javax.swing.JPanel pCentro;
    private javax.swing.JPanel pNorte;
    private javax.swing.JTextArea taObservacoes;
    private javax.swing.JTextField tfEmail;
    private javax.swing.JTextField tfEstagiario;
    private javax.swing.JTextField tfPaciente;
    // End of variables declaration//GEN-END:variables
}
