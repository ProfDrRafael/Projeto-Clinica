package visao;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author rafael
 */
public class TelaCadastroEstagiario extends javax.swing.JFrame {

    /**
     * Creates new form TelaEstagiario
     */
    public TelaCadastroEstagiario() {
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        pNorte = new javax.swing.JPanel();
        lbEstagiario = new javax.swing.JLabel();
        pCentro = new javax.swing.JPanel();
        lbAtivo = new javax.swing.JLabel();
        lbSemestre = new javax.swing.JLabel();
        lbAno = new javax.swing.JLabel();
        lbOrientador = new javax.swing.JLabel();
        lbUsuarioEstagiario = new javax.swing.JLabel();
        tfEstagiario = new javax.swing.JTextField();
        btEditar = new javax.swing.JButton();
        btSalvar = new javax.swing.JButton();
        rbNono = new javax.swing.JRadioButton();
        rbDecimo = new javax.swing.JRadioButton();
        cbAtivo = new javax.swing.JCheckBox();
        spData = new javax.swing.JSpinner();
        cbOrientador = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Estagiário");
        setPreferredSize(new java.awt.Dimension(1024, 768));
        setResizable(false);

        pNorte.setBackground(new java.awt.Color(255, 255, 255));

        lbEstagiario.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbEstagiario.setText("Usuário - Estágiario");
        pNorte.add(lbEstagiario);

        getContentPane().add(pNorte, java.awt.BorderLayout.NORTH);

        pCentro.setBackground(new java.awt.Color(255, 255, 255));

        lbAtivo.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbAtivo.setText("*Ativo:");

        lbSemestre.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbSemestre.setText("*Semestre:");

        lbAno.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbAno.setText("*Ano:");

        lbOrientador.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbOrientador.setText("*Orientador:");

        lbUsuarioEstagiario.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbUsuarioEstagiario.setText("*Estágiario:");

        tfEstagiario.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        tfEstagiario.setText("Isabella");

        btEditar.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btEditar.setText("Editar");

        btSalvar.setBackground(new java.awt.Color(51, 153, 255));
        btSalvar.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btSalvar.setText("Salvar");

        rbNono.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        rbNono.setText("9º");

        rbDecimo.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        rbDecimo.setText("10º");

        cbAtivo.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        spData.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        spData.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(1597764060000L), null, new java.util.Date(1724340106233L), java.util.Calendar.YEAR));

        cbOrientador.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        cbOrientador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Caroline", "João", "Marcelo" }));

        javax.swing.GroupLayout pCentroLayout = new javax.swing.GroupLayout(pCentro);
        pCentro.setLayout(pCentroLayout);
        pCentroLayout.setHorizontalGroup(
            pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pCentroLayout.createSequentialGroup()
                .addComponent(lbUsuarioEstagiario)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pCentroLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbOrientador, 0, 315, Short.MAX_VALUE)
                    .addComponent(lbOrientador)
                    .addComponent(lbAno)
                    .addComponent(spData, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbSemestre)
                    .addGroup(pCentroLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbDecimo)
                            .addComponent(rbNono))))
                .addGap(150, 150, 150))
            .addGroup(pCentroLayout.createSequentialGroup()
                .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pCentroLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbAtivo)
                            .addGroup(pCentroLayout.createSequentialGroup()
                                .addComponent(tfEstagiario, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(151, 151, 151)
                                .addComponent(cbAtivo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(16, 16, 16))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pCentroLayout.createSequentialGroup()
                        .addComponent(btEditar)
                        .addGap(32, 32, 32)))
                .addComponent(btSalvar)
                .addContainerGap(382, Short.MAX_VALUE))
        );
        pCentroLayout.setVerticalGroup(
            pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pCentroLayout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbUsuarioEstagiario)
                        .addComponent(lbSemestre))
                    .addComponent(lbAtivo, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pCentroLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfEstagiario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbAtivo, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbOrientador)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbOrientador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbAno)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
                        .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btEditar)
                            .addComponent(btSalvar))
                        .addGap(15, 15, 15))
                    .addGroup(pCentroLayout.createSequentialGroup()
                        .addComponent(rbNono)
                        .addGap(18, 18, 18)
                        .addComponent(rbDecimo)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        getContentPane().add(pCentro, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(TelaCadastroEstagiario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroEstagiario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroEstagiario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroEstagiario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaCadastroEstagiario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btEditar;
    private javax.swing.JButton btSalvar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox cbAtivo;
    private javax.swing.JComboBox<String> cbOrientador;
    private javax.swing.JLabel lbAno;
    private javax.swing.JLabel lbAtivo;
    private javax.swing.JLabel lbEstagiario;
    private javax.swing.JLabel lbOrientador;
    private javax.swing.JLabel lbSemestre;
    private javax.swing.JLabel lbUsuarioEstagiario;
    private javax.swing.JPanel pCentro;
    private javax.swing.JPanel pNorte;
    private javax.swing.JRadioButton rbDecimo;
    private javax.swing.JRadioButton rbNono;
    private javax.swing.JSpinner spData;
    private javax.swing.JTextField tfEstagiario;
    // End of variables declaration//GEN-END:variables
}
