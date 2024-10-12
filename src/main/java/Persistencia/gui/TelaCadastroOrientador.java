package Persistencia.gui;

import Persistencia.Dao.OrientadorDAO;
import Persistencia.Entity.Orientador;
import Services.SenhaService;
import java.awt.HeadlessException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import javax.swing.*;

public class TelaCadastroOrientador extends JFrame {

    private JTextField nomeField;
    private JTextField emailField;
    private JPasswordField senhaField;
    private JTextField linhaTeoricaField;
    private OrientadorDAO orientadorDAO;

    public TelaCadastroOrientador() {
        orientadorDAO = new OrientadorDAO();

        setTitle("Cadastro de Orientador");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JLabel nomeLabel = new JLabel("Nome:");
        nomeField = new JTextField(20);

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);

        JLabel senhaLabel = new JLabel("Senha:");
        senhaField = new JPasswordField(20);

        JLabel linhaTeoricaLabel = new JLabel("Linha Teórica:");
        linhaTeoricaField = new JTextField(20);

        JButton salvarButton = new JButton("Salvar");
        salvarButton.addActionListener(e -> salvarOrientador());

        JButton limparButton = new JButton("Limpar");
        limparButton.addActionListener(e -> limparCampos());

        // Configuração do layout horizontal
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(nomeLabel)
                        .addComponent(emailLabel)
                        .addComponent(senhaLabel)
                        .addComponent(linhaTeoricaLabel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(nomeField)
                        .addComponent(emailField)
                        .addComponent(senhaField)
                        .addComponent(linhaTeoricaField)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(salvarButton)
                                .addComponent(limparButton))));

        // Configuração do layout vertical
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(nomeLabel)
                        .addComponent(nomeField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(emailLabel)
                        .addComponent(emailField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(senhaLabel)
                        .addComponent(senhaField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(linhaTeoricaLabel)
                        .addComponent(linhaTeoricaField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(salvarButton)
                        .addComponent(limparButton)));

        add(panel);
    }

    private void salvarOrientador() {
        try {
            String nome = nomeField.getText();
            String email = emailField.getText();
            String senha = new String(senhaField.getPassword());
            String linhaTeorica = linhaTeoricaField.getText();

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos obrigatórios!");
                return;
            }

            // Criptografar a senha
            String senhaCriptografada = criptografarSenhaArgon2(senha);

            Orientador orientador = new Orientador();
            orientador.setNome(nome);
            orientador.setEmail(email);
            orientador.setSenha(senhaCriptografada);
            orientador.setLinhaTeorica(linhaTeorica);
            orientador.setAtivo(true);

            orientadorDAO.salvarOrientador(orientador);
            JOptionPane.showMessageDialog(this, "Orientador cadastrado com sucesso!");
            limparCampos();
        } catch (HeadlessException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar orientador: " + ex.getMessage());
        }
    }

    private void limparCampos() {
        nomeField.setText("");
        emailField.setText("");
        senhaField.setText("");
        linhaTeoricaField.setText("");
    }

    private String criptografarSenhaArgon2(String senha) {
        SenhaService argon2 = new SenhaService();
        return argon2.criptografarSenha(senha);
    }

    public void fecharRecursos() {
        orientadorDAO.fechar();
    }

    public static void main(String[] args) {
        TelaCadastroOrientador frame = new TelaCadastroOrientador();
        frame.setVisible(true);
    }
}
