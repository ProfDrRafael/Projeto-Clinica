package Visao.Login;

import Services.AutenticacaoService;
import VO.UsuarioVO;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;

import net.miginfocom.swing.MigLayout;
import Visao.JframeManager.FormManager;
import Persistencia.modelTemp.ModelUser; // Modelo de usuário

/**
 * Classe que define o painel de login, onde o usuário insere seu nome de usuário
 * e senha para acessar o sistema.
 * Extende JPanel para usar componentes gráficos.
 * @author Raven
 */
public class Login extends JPanel {
    private AutenticacaoService autenticacaoService;

    // Construtor que inicializa os componentes do formulário de login
    public Login(AutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
        init();
    }

    // Método que inicializa e configura os componentes do painel de login
    private void init() {
        // Configura o layout do painel principal (centralizado)
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));

        // Criação dos campos de texto, senha, checkbox e botão de login
        txtUsername = new JTextField();  // Campo de texto para o nome de usuário
        txtPassword = new JPasswordField();  // Campo de senha
        chRememberMe = new JCheckBox("Remember me");  // Checkbox "Lembrar de mim"
        cmdLogin = new JButton("Login");  // Botão de login

        // Criação de um painel interno com margens definidas
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "fill,250:280"));
        panel.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:20;"  // Bordas arredondadas
                + "[light]background:darken(@background,3%);"  // Ajuste de cor do fundo no tema claro
                + "[dark]background:lighten(@background,3%)");  // Ajuste de cor do fundo no tema escuro

        // Configuração do campo de senha para exibir o botão de revelar senha
        txtPassword.putClientProperty(FlatClientProperties.STYLE, "showRevealButton:true");

        // Configuração do botão de login com estilos personalizados
        cmdLogin.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]background:darken(@background,10%);"  // Cor de fundo no tema claro
                + "[dark]background:lighten(@background,10%);"  // Cor de fundo no tema escuro
                + "borderWidth:0;"  // Sem borda
                + "focusWidth:0;"  // Sem contorno de foco
                + "innerFocusWidth:0");  // Sem contorno interno de foco

        // Definição de texto de placeholder para os campos de texto e senha
        txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your username or email");
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your password");

        // Criação e configuração de um título e uma descrição no painel
        JLabel lbTitle = new JLabel("Welcome back!");  // Título do painel
        JLabel description = new JLabel("Please sign in to access your account");  // Descrição
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");  // Fonte maior e em negrito
        description.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]foreground:lighten(@foreground,30%);"  // Ajuste da cor do texto no tema claro
                + "[dark]foreground:darken(@foreground,30%)");  // Ajuste da cor do texto no tema escuro

        // Adição dos componentes no painel, com espaçamento e organização
        panel.add(lbTitle);  // Adiciona o título
        panel.add(description);  // Adiciona a descrição
        panel.add(new JLabel("Username"), "gapy 8");  // Rótulo para o campo "Username"
        panel.add(txtUsername);  // Campo de texto para o nome de usuário
        panel.add(new JLabel("Password"), "gapy 8");  // Rótulo para o campo "Password"
        panel.add(txtPassword);  // Campo de texto para a senha
        panel.add(chRememberMe, "grow 0");  // Checkbox "Remember me"
        panel.add(cmdLogin, "gapy 10");  // Botão de login com espaçamento vertical
        add(panel);  // Adiciona o painel ao layout principal

        // Evento de clique no botão de login
        cmdLogin.addActionListener((e) -> {
            String email = txtUsername.getText().trim();  // Captura o email inserido
            String senha = new String(txtPassword.getPassword());  // Captura a senha inserida
            boolean isAdmin = false;

            try {
                // Autenticação utilizando o serviço
                UsuarioVO usuario = autenticacaoService.autenticar(email, senha);
                // Redireciona o usuário com base no tipo de usuário autenticado
                isAdmin = usuario.getTipo().equals("admin");

                FormManager.login(new ModelUser(usuario, isAdmin));
//                redirecionarUsuario(usuario);
            } catch (RuntimeException ex) {
                // Exibir mensagem de erro para o usuário
                JOptionPane.showMessageDialog(this, "Erro de autenticação: " + ex.getMessage());
            }
        });
    }

    // Declaração dos componentes de interface gráfica
    private JTextField txtUsername;  // Campo de texto para nome de usuário
    private JPasswordField txtPassword;  // Campo de texto para senha
    private JCheckBox chRememberMe;  // Checkbox "Remember me"
    private JButton cmdLogin;  // Botão de login
}
