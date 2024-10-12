package Persistencia;

import Persistencia.Dao.TextoDAO;
import Persistencia.gui.TelaCadastroOrientador;
import Persistencia.gui.TelaCifrador;
import Persistencia.gui.TelaLoginOrientador;
import Services.CriptografiaService;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {

    public App() {
        setTitle("Escolha uma Opção");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        JButton cadastroOrientadorButton = new JButton("Cadastro de Orientador");
        cadastroOrientadorButton.addActionListener(e -> abrirCadastroOrientador());

        JButton cifradorButton = new JButton("Abrir Cifrador");
        cifradorButton.addActionListener(e -> {
            try {
                abrirCifrador();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        JButton loginButton = new JButton("Login de Orientador");
        loginButton.addActionListener(e -> abrirLoginOrientador());

        add(cadastroOrientadorButton);
        add(cifradorButton);
        add(loginButton);
    }

    // Metodo para abrir o painel de cadastro de orientador
    private void abrirCadastroOrientador() {
        TelaCadastroOrientador telaCadastroOrientador = new TelaCadastroOrientador();
        telaCadastroOrientador.setVisible(true);
        dispose();
    }

    // Metodo para abrir o cifrador
    private void abrirCifrador() throws Exception {
        CriptografiaService criptografiaService = new CriptografiaService(); 
        TextoDAO textoDAO = new TextoDAO(); 

        TelaCifrador telaCifrador = new TelaCifrador(criptografiaService, textoDAO); 
        telaCifrador.setVisible(true);
        dispose();
    }

    // Metodo para abrir o painel de login de orientador
    private void abrirLoginOrientador() {
        TelaLoginOrientador telaLoginOrientador = new TelaLoginOrientador();
        telaLoginOrientador.setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        App mainApp = new App();
        mainApp.setVisible(true);
    }
}
