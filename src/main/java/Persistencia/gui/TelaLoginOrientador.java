package Persistencia.gui;

import Persistencia.Dao.OrientadorDAO;
import Persistencia.Entity.Orientador;
import Services.SenhaService;

import javax.swing.*;
import java.awt.*;

public class TelaLoginOrientador extends JFrame {

    private JTextField emailField;
    private JPasswordField senhaField;
    private OrientadorDAO orientadorDAO;
    private SenhaService senhaService;

    public TelaLoginOrientador() {
        orientadorDAO = new OrientadorDAO();
        senhaService = new SenhaService();

        setTitle("Login de Orientador");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        // Configura o GroupLayout
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);

        JLabel senhaLabel = new JLabel("Senha:");
        senhaField = new JPasswordField(20);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> autenticarOrientador());

        JButton limparButton = new JButton("Limpar");
        limparButton.addActionListener(e -> limparCampos());

        // Botão "Esqueci a Senha"
        JButton esqueciSenhaButton = new JButton("Esqueci a Senha");
        esqueciSenhaButton.setForeground(Color.BLUE.darker());
        esqueciSenhaButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        esqueciSenhaButton.addActionListener(e -> abrirTelaEsqueciSenha());

        // Configuração do layout horizontal
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(emailLabel)
                        .addComponent(senhaLabel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(emailField)
                        .addComponent(senhaField)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(loginButton)
                                .addComponent(limparButton)
                                .addComponent(esqueciSenhaButton))));

        // Configuração do layout vertical
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(emailLabel)
                        .addComponent(emailField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(senhaLabel)
                        .addComponent(senhaField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(loginButton)
                        .addComponent(limparButton)
                        .addComponent(esqueciSenhaButton)));

        add(panel);
    }

    private void autenticarOrientador() {
        try {
            String email = emailField.getText();
            String senha = new String(senhaField.getPassword());

            if (email.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos!");
                return;
            }

            Orientador orientador = orientadorDAO.buscarPorEmail(email);

            if (orientador == null) {
                JOptionPane.showMessageDialog(this, "Orientador não encontrado!");
                return;
            }

            // Verificar a senha usando o SenhaService
            if (senhaService.verificarSenha(senha, orientador.getSenha())) {
                JOptionPane.showMessageDialog(this, "Login bem-sucedido!");
            } else {
                JOptionPane.showMessageDialog(this, "Senha incorreta!");
            }

        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, "Erro ao autenticar: " + e.getMessage());
        }
    }

    private void limparCampos() {
        emailField.setText("");
        senhaField.setText("");
    }

    private void abrirTelaEsqueciSenha() {
        TelaEsqueciSenha telaEsqueciSenha = new TelaEsqueciSenha();
        telaEsqueciSenha.setVisible(true);
    }

    public static void main(String[] args) {
        TelaLoginOrientador loginFrame = new TelaLoginOrientador();
        loginFrame.setVisible(true);
    }
}
