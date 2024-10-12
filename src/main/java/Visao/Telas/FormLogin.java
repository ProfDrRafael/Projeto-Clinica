/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Visao.Telas;
import Persistencia.modelTemp.ModelUser;
import Visao.Components.SimpleForm;
import Visao.JframeManager.FormManager;
import java.awt.Color;


/**
 *
 * @author john
 */
public class FormLogin extends SimpleForm {

    /**
     * Creates new form formLogin
     */
    public FormLogin() {
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
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        pDireita = new javax.swing.JPanel();
        tfLogin = new javax.swing.JTextField();
        lbLogin = new javax.swing.JLabel();
        pfSenha = new javax.swing.JPasswordField();
        jlSenha = new javax.swing.JLabel();
        lbEsqueceuSenha = new javax.swing.JLabel();
        btEntrar = new javax.swing.JButton();
        lbTitulo = new javax.swing.JLabel();

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

        pEsquerda.setBackground(new java.awt.Color(0, 102, 102));
        pEsquerda.setMaximumSize(new java.awt.Dimension(400, 400));
        pEsquerda.setMinimumSize(new java.awt.Dimension(400, 400));
        pEsquerda.setPreferredSize(new java.awt.Dimension(400, 400));

        jLabel2.setFont(new java.awt.Font("Noto Sans CJK HK", 0, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Copyright©Clínica Cpan. Todos os direitos reservados.");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Multimidia/imagens/cpan-small.png"))); // NOI18N

        javax.swing.GroupLayout pEsquerdaLayout = new javax.swing.GroupLayout(pEsquerda);
        pEsquerda.setLayout(pEsquerdaLayout);
        pEsquerdaLayout.setHorizontalGroup(
            pEsquerdaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pEsquerdaLayout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
            .addGroup(pEsquerdaLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pEsquerdaLayout.setVerticalGroup(
            pEsquerdaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pEsquerdaLayout.createSequentialGroup()
                .addContainerGap(120, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(111, 111, 111)
                .addComponent(jLabel2)
                .addGap(16, 16, 16))
        );

        pLogin.add(pEsquerda);

        pDireita.setBackground(new java.awt.Color(255, 255, 255));
        pDireita.setMaximumSize(new java.awt.Dimension(400, 400));
        pDireita.setMinimumSize(new java.awt.Dimension(400, 400));
        pDireita.setPreferredSize(new java.awt.Dimension(400, 400));

        tfLogin.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        lbLogin.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lbLogin.setText("Login:");

        pfSenha.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        pfSenha.setText("jPasswordField1");

        jlSenha.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jlSenha.setText("Senha:");

        lbEsqueceuSenha.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lbEsqueceuSenha.setForeground(new java.awt.Color(51, 51, 255));
        lbEsqueceuSenha.setText("Esqueceu sua senha?");
        lbEsqueceuSenha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbEsqueceuSenhaMouseClicked(evt);
            }
        });

        btEntrar.setBackground(new java.awt.Color(0, 102, 102));
        btEntrar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btEntrar.setForeground(new java.awt.Color(255, 255, 255));
        btEntrar.setText("Entrar");
        btEntrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEntrarActionPerformed(evt);
            }
        });

        lbTitulo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbTitulo.setForeground(new java.awt.Color(0, 102, 102));
        lbTitulo.setText("Login");

        javax.swing.GroupLayout pDireitaLayout = new javax.swing.GroupLayout(pDireita);
        pDireita.setLayout(pDireitaLayout);
        pDireitaLayout.setHorizontalGroup(
            pDireitaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pDireitaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbTitulo)
                .addGap(159, 159, 159))
            .addGroup(pDireitaLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(pDireitaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btEntrar)
                    .addGroup(pDireitaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(tfLogin)
                        .addComponent(lbLogin)
                        .addComponent(jlSenha)
                        .addComponent(lbEsqueceuSenha)
                        .addComponent(pfSenha, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pDireitaLayout.setVerticalGroup(
            pDireitaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pDireitaLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lbTitulo)
                .addGap(28, 28, 28)
                .addComponent(lbLogin)
                .addGap(18, 18, 18)
                .addComponent(tfLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jlSenha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pfSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(lbEsqueceuSenha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btEntrar)
                .addGap(49, 49, 49))
        );

        pLogin.add(pDireita);

        add(pLogin, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btEntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEntrarActionPerformed
        String userName = tfLogin.getText().trim();  // Obtém o texto do campo de usuário e remove espaços em branco
        // Verificação simples para exemplo: se o nome de usuário for "admin"
        boolean isAdmin = userName.equals("admin");
        // Chama o método de login com o modelo do usuário (nome de usuário e flag se é admin)
        FormManager.login(new ModelUser(userName, isAdmin));
    }//GEN-LAST:event_btEntrarActionPerformed

    private void lbEsqueceuSenhaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbEsqueceuSenhaMouseClicked
        lbEsqueceuSenha.setBackground(Color.black);
        
        FormEsqueciSenha esqueciSenha = new FormEsqueciSenha();
        
        FormManager.EsqueciSenha(esqueciSenha);
    }//GEN-LAST:event_lbEsqueceuSenhaMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btEntrar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jlSenha;
    private javax.swing.JLabel lbEsqueceuSenha;
    private javax.swing.JLabel lbLogin;
    private javax.swing.JLabel lbTitulo;
    private javax.swing.JPanel pDireita;
    private javax.swing.JPanel pEsquerda;
    private javax.swing.JPanel pLogin;
    private javax.swing.JPasswordField pfSenha;
    private javax.swing.JTextField tfLogin;
    // End of variables declaration//GEN-END:variables
}
