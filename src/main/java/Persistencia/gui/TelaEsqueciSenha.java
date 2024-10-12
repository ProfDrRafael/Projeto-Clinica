package Persistencia.gui;

import Services.RedefinirSenhaOrientadorService;
import java.awt.HeadlessException;

import javax.swing.*;

public class TelaEsqueciSenha extends JFrame {

    private JTextField emailField;
    private JTextField tokenField;
    private JPasswordField novaSenhaField;

    private RedefinirSenhaOrientadorService redefinirSenhaService;

    public TelaEsqueciSenha() {
        redefinirSenhaService = new RedefinirSenhaOrientadorService();

        setTitle("Esqueci a Senha");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JLabel emailLabel = new JLabel("Digite seu e-mail:");
        emailField = new JTextField(20);

        JButton enviarTokenButton = new JButton("Enviar Token");
        enviarTokenButton.addActionListener(e -> enviarToken());

        JLabel tokenLabel = new JLabel("Digite o Token:");
        tokenField = new JTextField(20);

        JLabel novaSenhaLabel = new JLabel("Digite sua nova senha:");
        novaSenhaField = new JPasswordField(20);

        JButton redefinirSenhaButton = new JButton("Redefinir Senha");
        redefinirSenhaButton.addActionListener(e -> redefinirSenha());

        // Layout horizontal
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(emailLabel)
                        .addComponent(tokenLabel)
                        .addComponent(novaSenhaLabel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(emailField)
                        .addComponent(tokenField)
                        .addComponent(novaSenhaField)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(enviarTokenButton)
                                .addComponent(redefinirSenhaButton)))
        );

        // Layout vertical
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(emailLabel)
                        .addComponent(emailField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(tokenLabel)
                        .addComponent(tokenField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(novaSenhaLabel)
                        .addComponent(novaSenhaField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(enviarTokenButton)
                        .addComponent(redefinirSenhaButton))
        );

        add(panel);
    }

    private void enviarToken() {
        String email = emailField.getText();
        try {
            redefinirSenhaService.gerarTokenRedefinicao(email);
            JOptionPane.showMessageDialog(this, "Token enviado para o e-mail: " + email);
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, "Erro ao enviar token: " + e.getMessage());
        }
    }

    private void redefinirSenha() {
        String token = tokenField.getText();
        String novaSenha = new String(novaSenhaField.getPassword());

        try {
            redefinirSenhaService.redefinirSenha(token, novaSenha);
            JOptionPane.showMessageDialog(this, "Senha redefinida com sucesso!");
            this.dispose();
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, "Erro ao redefinir senha: " + e.getMessage());
        }
    }
}
