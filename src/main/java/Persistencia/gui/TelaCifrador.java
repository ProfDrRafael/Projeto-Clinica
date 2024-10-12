package Persistencia.gui;

import Persistencia.Dao.TextoDAO;
import Services.CriptografiaService;

import javax.swing.*;
import java.awt.*;

public class TelaCifrador extends JFrame {

    private JEditorPane editorPane;
    private JEditorPane decryptedEditorPane;
    private CriptografiaService criptografiaService;
    private TextoDAO textoDAO;

    public TelaCifrador(CriptografiaService criptografiaService, TextoDAO textoDAO) throws Exception {
        this.criptografiaService = criptografiaService;
        this.textoDAO = textoDAO;

        setTitle("Entrada e Decifração de Texto");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        // Editor para entrada de texto
        editorPane = new JEditorPane();
        editorPane.setContentType("text/html"); // Para aceitar HTML
        editorPane.setPreferredSize(new Dimension(780, 300)); // Define o tamanho fixo
        JScrollPane scrollPane = new JScrollPane(editorPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Scroll sempre ativo
        panel.add(scrollPane, BorderLayout.NORTH);

        // Botões
        JButton submitButton = new JButton("Criptografar e Salvar");
        submitButton.addActionListener(e -> criptografarESalvar());

        JButton fetchButton = new JButton("Buscar e Decifrar Texto");
        fetchButton.addActionListener(e -> buscarEDecifrar());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);
        buttonPanel.add(fetchButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Editor para texto decifrado
        decryptedEditorPane = new JEditorPane();
        decryptedEditorPane.setContentType("text/html"); // Para aceitar HTML
        decryptedEditorPane.setPreferredSize(new Dimension(780, 200)); // Define o tamanho fixo
        JScrollPane decryptedScrollPane = new JScrollPane(decryptedEditorPane);
        decryptedScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Scroll sempre ativo
        panel.add(decryptedScrollPane, BorderLayout.CENTER);

        add(panel);
    }

    private void criptografarESalvar() {
        try {
            String texto = editorPane.getText();
            if (!texto.isEmpty()) {
                String textoCriptografado = criptografiaService.criptografarTexto(texto);
                textoDAO.salvarTexto(textoCriptografado);
                // Exibir texto criptografado no terminal
                System.out.println("Texto criptografado: " + textoCriptografado);
                JOptionPane.showMessageDialog(this, "Texto criptografado e salvo com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "O campo de texto está vazio.");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void buscarEDecifrar() {
        try {
            var textos = textoDAO.buscarTodosTextos();
            if (!textos.isEmpty()) {
                var textoCriptografado = textos.get(0).getTextoCifrado();
                String textoDecifrado = criptografiaService.decifrarTexto(textoCriptografado);
                decryptedEditorPane.setText(textoDecifrado);
            } else {
                JOptionPane.showMessageDialog(this, "Nenhum texto encontrado.");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void fecharRecursos() {
        textoDAO.fechar();
    }
}
