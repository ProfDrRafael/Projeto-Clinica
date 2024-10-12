/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Visao.Telas;
import Persistencia.modelTemp.ModelUser;
import Visao.Components.SimpleForm;
import Visao.JframeManager.FormManager;


/**
 *
 * @author john
 */
public class FormEsqueciSenha3 extends SimpleForm {

    /**
     * Creates new form formLogin
     */
    public FormEsqueciSenha3() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pLogin = new javax.swing.JPanel();
        pEsquerda = new javax.swing.JPanel();
        lbLogin = new javax.swing.JLabel();
        btEntrar = new javax.swing.JButton();
        lbTitulo = new javax.swing.JLabel();
        pfSenha = new javax.swing.JPasswordField();
        pDireita = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(800, 400));
        setMinimumSize(new java.awt.Dimension(800, 400));
        setPreferredSize(new java.awt.Dimension(800, 400));
        setLayout(new java.awt.BorderLayout());

        pLogin.setBackground(java.awt.SystemColor.controlHighlight);
        pLogin.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 102), 1, true));
        pLogin.setMaximumSize(new java.awt.Dimension(800, 400));
        pLogin.setMinimumSize(new java.awt.Dimension(800, 400));
        pLogin.setPreferredSize(new java.awt.Dimension(800, 400));
        pLogin.setLayout(new java.awt.GridLayout(1, 2));

        pEsquerda.setBackground(new java.awt.Color(255, 255, 255));
        pEsquerda.setMaximumSize(new java.awt.Dimension(400, 400));
        pEsquerda.setMinimumSize(new java.awt.Dimension(400, 400));
        pEsquerda.setPreferredSize(new java.awt.Dimension(400, 400));

        lbLogin.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lbLogin.setText("Digite a nova senha:");

        btEntrar.setBackground(new java.awt.Color(0, 102, 102));
        btEntrar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btEntrar.setForeground(new java.awt.Color(255, 255, 255));
        btEntrar.setText("Próximo");
        btEntrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEntrarActionPerformed(evt);
            }
        });

        lbTitulo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbTitulo.setForeground(new java.awt.Color(0, 102, 102));
        lbTitulo.setText("Esqueci a Senha");

        pfSenha.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        pfSenha.setText("jPasswordField1");

        javax.swing.GroupLayout pEsquerdaLayout = new javax.swing.GroupLayout(pEsquerda);
        pEsquerda.setLayout(pEsquerdaLayout);
        pEsquerdaLayout.setHorizontalGroup(
            pEsquerdaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pEsquerdaLayout.createSequentialGroup()
                .addGroup(pEsquerdaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pEsquerdaLayout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(lbTitulo))
                    .addGroup(pEsquerdaLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(pEsquerdaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbLogin)
                            .addGroup(pEsquerdaLayout.createSequentialGroup()
                                .addGap(235, 235, 235)
                                .addComponent(btEntrar))
                            .addComponent(pfSenha))))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        pEsquerdaLayout.setVerticalGroup(
            pEsquerdaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pEsquerdaLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(lbTitulo)
                .addGap(67, 67, 67)
                .addComponent(lbLogin)
                .addGap(18, 18, 18)
                .addComponent(pfSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
                .addComponent(btEntrar)
                .addGap(39, 39, 39))
        );

        pLogin.add(pEsquerda);

        pDireita.setBackground(new java.awt.Color(0, 102, 102));
        pDireita.setMaximumSize(new java.awt.Dimension(400, 400));
        pDireita.setMinimumSize(new java.awt.Dimension(400, 400));
        pDireita.setPreferredSize(new java.awt.Dimension(400, 400));

        jLabel2.setFont(new java.awt.Font("Noto Sans CJK HK", 0, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Copyright©Clínica Cpan. Todos os direitos reservados.");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Multimidia/imagens/cpan-small.png"))); // NOI18N

        javax.swing.GroupLayout pDireitaLayout = new javax.swing.GroupLayout(pDireita);
        pDireita.setLayout(pDireitaLayout);
        pDireitaLayout.setHorizontalGroup(
            pDireitaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pDireitaLayout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
            .addGroup(pDireitaLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pDireitaLayout.setVerticalGroup(
            pDireitaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pDireitaLayout.createSequentialGroup()
                .addContainerGap(120, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(111, 111, 111)
                .addComponent(jLabel2)
                .addGap(16, 16, 16))
        );

        pLogin.add(pDireita);

        add(pLogin, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btEntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEntrarActionPerformed
        FormManager.logout();
    }//GEN-LAST:event_btEntrarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btEntrar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lbLogin;
    private javax.swing.JLabel lbTitulo;
    private javax.swing.JPanel pDireita;
    private javax.swing.JPanel pEsquerda;
    private javax.swing.JPanel pLogin;
    private javax.swing.JPasswordField pfSenha;
    // End of variables declaration//GEN-END:variables
}
