package visao;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author gani
 */
public class TelaCadastroUsuario extends javax.swing.JFrame {

    /**
     * Creates new form TelaCadastro
     */
    public TelaCadastroUsuario() {
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

        pCentro = new javax.swing.JPanel();
        tfNome = new javax.swing.JTextField();
        lbNome = new javax.swing.JLabel();
        tfEmail = new javax.swing.JTextField();
        lbEmail = new javax.swing.JLabel();
        cbFuncao = new javax.swing.JComboBox<>();
        lbFuncao = new javax.swing.JLabel();
        pfSenha = new javax.swing.JPasswordField();
        lbSenha = new javax.swing.JLabel();
        pfConfirmarSenha = new javax.swing.JPasswordField();
        lbConfirmarSenha = new javax.swing.JLabel();
        btCadastrar = new javax.swing.JButton();
        pNorte = new javax.swing.JPanel();
        lbCadastro = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cadastro de Usuário");
        setBackground(new java.awt.Color(0, 0, 0));
        setPreferredSize(new java.awt.Dimension(1024, 768));
        setResizable(false);

        pCentro.setBackground(new java.awt.Color(255, 255, 255));
        pCentro.setPreferredSize(new java.awt.Dimension(1024, 768));

        tfNome.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        lbNome.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbNome.setText("*Nome:");

        tfEmail.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        tfEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfEmailActionPerformed(evt);
            }
        });

        lbEmail.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbEmail.setText("*Email:");

        cbFuncao.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        cbFuncao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Orientador", "Estagiário", "Secretária" }));

        lbFuncao.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbFuncao.setText("*Função:");

        pfSenha.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        pfSenha.setText("jPasswordField1");

        lbSenha.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbSenha.setText("*Senha:");

        pfConfirmarSenha.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        pfConfirmarSenha.setText("jPasswordField1");
        pfConfirmarSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pfConfirmarSenhaActionPerformed(evt);
            }
        });

        lbConfirmarSenha.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbConfirmarSenha.setText("*Confirmar senha:");

        btCadastrar.setBackground(new java.awt.Color(51, 153, 255));
        btCadastrar.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btCadastrar.setText("Cadastrar");

        javax.swing.GroupLayout pCentroLayout = new javax.swing.GroupLayout(pCentro);
        pCentro.setLayout(pCentroLayout);
        pCentroLayout.setHorizontalGroup(
            pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pCentroLayout.createSequentialGroup()
                .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pCentroLayout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pCentroLayout.createSequentialGroup()
                            .addGap(22, 22, 22)
                            .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(pCentroLayout.createSequentialGroup()
                                    .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lbNome)
                                        .addComponent(lbEmail))
                                    .addGap(113, 113, 113)
                                    .addComponent(tfEmail))
                                .addComponent(lbSenha)
                                .addComponent(lbFuncao)
                                .addGroup(pCentroLayout.createSequentialGroup()
                                    .addComponent(lbConfirmarSenha)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(pfConfirmarSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pCentroLayout.createSequentialGroup()
                        .addGap(211, 211, 211)
                        .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pfSenha, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbFuncao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(585, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pCentroLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btCadastrar)
                .addGap(441, 441, 441))
        );
        pCentroLayout.setVerticalGroup(
            pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pCentroLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbNome))
                .addGap(25, 25, 25)
                .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbEmail))
                .addGap(18, 18, 18)
                .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbFuncao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbFuncao))
                .addGap(16, 16, 16)
                .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbSenha)
                    .addComponent(pfSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pfConfirmarSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbConfirmarSenha))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(btCadastrar)
                .addGap(17, 17, 17))
        );

        getContentPane().add(pCentro, java.awt.BorderLayout.CENTER);

        pNorte.setBackground(new java.awt.Color(255, 255, 255));

        lbCadastro.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbCadastro.setText("Cadastro de Usuário");
        pNorte.add(lbCadastro);

        getContentPane().add(pNorte, java.awt.BorderLayout.NORTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfEmailActionPerformed

    private void pfConfirmarSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pfConfirmarSenhaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pfConfirmarSenhaActionPerformed

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
            java.util.logging.Logger.getLogger(TelaCadastroUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaCadastroUsuario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCadastrar;
    private javax.swing.JComboBox<String> cbFuncao;
    private javax.swing.JLabel lbCadastro;
    private javax.swing.JLabel lbConfirmarSenha;
    private javax.swing.JLabel lbEmail;
    private javax.swing.JLabel lbFuncao;
    private javax.swing.JLabel lbNome;
    private javax.swing.JLabel lbSenha;
    private javax.swing.JPanel pCentro;
    private javax.swing.JPanel pNorte;
    private javax.swing.JPasswordField pfConfirmarSenha;
    private javax.swing.JPasswordField pfSenha;
    private javax.swing.JTextField tfEmail;
    private javax.swing.JTextField tfNome;
    // End of variables declaration//GEN-END:variables
}