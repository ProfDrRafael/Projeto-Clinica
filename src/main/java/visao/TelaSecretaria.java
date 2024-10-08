package visao;

import java.awt.Image;
import javax.swing.ImageIcon;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author rafael
 */
public class TelaSecretaria extends javax.swing.JFrame {

    /**
     * Creates new form TelaSecretaria
     */
    public TelaSecretaria() {
        initComponents();
        setLocationRelativeTo(null);
        redimensionarIcones();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pNorte = new javax.swing.JPanel();
        jlSecretaria = new javax.swing.JLabel();
        pCentro = new javax.swing.JPanel();
        btCadastrarPaciente = new javax.swing.JButton();
        btCadastrarEstagiario = new javax.swing.JButton();
        btCadastrarOrientador = new javax.swing.JButton();
        btVisualizarAgenda = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Secretária");
        setPreferredSize(new java.awt.Dimension(1024, 768));
        setResizable(false);

        pNorte.setBackground(new java.awt.Color(255, 255, 255));

        jlSecretaria.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        jlSecretaria.setText("Secretária");
        pNorte.add(jlSecretaria);

        getContentPane().add(pNorte, java.awt.BorderLayout.NORTH);

        pCentro.setBackground(new java.awt.Color(255, 255, 255));

        btCadastrarPaciente.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btCadastrarPaciente.setText("Cadastrar Paciente");

        btCadastrarEstagiario.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btCadastrarEstagiario.setText("Cadastrar Estagiário");

        btCadastrarOrientador.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btCadastrarOrientador.setText("Cadastrar Orientador");

        btVisualizarAgenda.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btVisualizarAgenda.setText("Visualizar Agenda");

        javax.swing.GroupLayout pCentroLayout = new javax.swing.GroupLayout(pCentro);
        pCentro.setLayout(pCentroLayout);
        pCentroLayout.setHorizontalGroup(
            pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pCentroLayout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btCadastrarOrientador, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btCadastrarPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btCadastrarEstagiario, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btVisualizarAgenda, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(105, 105, 105))
        );
        pCentroLayout.setVerticalGroup(
            pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pCentroLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btCadastrarPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btCadastrarEstagiario, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(69, 69, 69)
                .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btCadastrarOrientador, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btVisualizarAgenda, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(70, Short.MAX_VALUE))
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
            java.util.logging.Logger.getLogger(TelaSecretaria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaSecretaria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaSecretaria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaSecretaria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaSecretaria().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCadastrarEstagiario;
    private javax.swing.JButton btCadastrarOrientador;
    private javax.swing.JButton btCadastrarPaciente;
    private javax.swing.JButton btVisualizarAgenda;
    private javax.swing.JLabel jlSecretaria;
    private javax.swing.JPanel pCentro;
    private javax.swing.JPanel pNorte;
    // End of variables declaration//GEN-END:variables

private void redimensionarIcones() {
        //Cadastrar Paciente
        ImageIcon iconeOriginalPaciente = new ImageIcon(getClass().getResource("/imagens/imagem1.png"));
        Image iconeEmEscalaPaciente = iconeOriginalPaciente.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
        btCadastrarPaciente.setIcon(new ImageIcon(iconeEmEscalaPaciente));
        
        //Cadastrar Estagiario
        ImageIcon iconeOriginalEstagiario = new ImageIcon(getClass().getResource("/imagens/estagiario.png"));
        Image iconeEmEscalaEstagiario = iconeOriginalEstagiario.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
        btCadastrarEstagiario.setIcon(new ImageIcon(iconeEmEscalaEstagiario));
        
        //Cadastrar Orientador
        ImageIcon iconeOriginalOrientador = new ImageIcon(getClass().getResource("/imagens/cadastrar.png"));
        Image iconeEmEscalaOrientador = iconeOriginalOrientador.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
        btCadastrarOrientador.setIcon(new ImageIcon(iconeEmEscalaOrientador));

        //Visualizar Agenda
        ImageIcon iconeOriginalAgenda = new ImageIcon(getClass().getResource("/imagens/agenda.png"));
        Image iconeEmEscalaAgenda = iconeOriginalAgenda.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
        btVisualizarAgenda.setIcon(new ImageIcon(iconeEmEscalaAgenda));
    }


}
