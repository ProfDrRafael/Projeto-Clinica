/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Visao.Utils;

import javax.swing.JTextPane;
import javax.swing.text.rtf.RTFEditorKit;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.*;
import javax.swing.undo.*;

/**
 *
 * @author john
 */
public class EditorTextPaneEstilization {
   // Configura o editor para RTF
    public static void EstilizeEditorTextPane(JTextPane textPane) {
        textPane.setEditorKit(new javax.swing.text.rtf.RTFEditorKit());
        textPane.setContentType("text/rtf");
    }

    // Configura os atalhos de Undo/Redo para o JTextPane
    public static void JTextComponentUndoRedo(JTextPane textPane) {
        UndoManager undoManager = new UndoManager();
        textPane.getDocument().addUndoableEditListener(undoManager);

        // Atalho para Undo (Ctrl+Z)
        textPane.getInputMap(JComponent.WHEN_FOCUSED)
                .put(KeyStroke.getKeyStroke("control Z"), "Undo");
        textPane.getActionMap().put("Undo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (undoManager.canUndo()) {
                    undoManager.undo();
                }
            }
        });

        // Atalho para Redo (Ctrl+Y)
        textPane.getInputMap(JComponent.WHEN_FOCUSED)
                .put(KeyStroke.getKeyStroke("control Y"), "Redo");
        textPane.getActionMap().put("Redo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (undoManager.canRedo()) {
                    undoManager.redo();
                }
            }
        });
        

        textPane.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("control Y"), "Redo");
        textPane.getActionMap().put("Redo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (undoManager.canRedo()) {
                    undoManager.redo();
                }
            }
        });
    }
    
      public static void JTextComponentStylization(JTextPane textPane, 
                                                 JButton btNegrito, 
                                                 JButton btItalico, 
                                                 JButton btSublinhado) {
        // Adiciona ícones redimensionados aos botões (utilize sua classe de redimensionamento)
        RedimencionarIcones redimencionarIcone = new RedimencionarIcones();
        redimencionarIcone.redimensionarIcones(btNegrito, "/Multimidia/icon/bold_icon.png", 35);
        redimencionarIcone.redimensionarIcones(btItalico, "/Multimidia/icon/italico_icon.png", 35);
        redimencionarIcone.redimensionarIcones(btSublinhado, "/Multimidia/icon/sublinhado_icon.png", 35);
        
        // Obter o documento do JTextPane
        StyledDocument doc = textPane.getStyledDocument();
        
        // Método auxiliar que alterna um atributo (ex: negrito, itálico ou sublinhado)
        java.util.function.BiConsumer<String, Runnable> toggleStyle = (style, dummy) -> {
            int start = textPane.getSelectionStart();
            int end = textPane.getSelectionEnd();
            if (start == end) {
                return; // Se não há seleção, não faz nada
            }
            // Obtém os atributos atuais do primeiro caractere da seleção
            Element element = doc.getCharacterElement(start);
            AttributeSet currentAttrs = element.getAttributes();
            // Cria um novo conjunto de atributos baseado nos atuais para mesclar as mudanças
            SimpleAttributeSet newAttrs = new SimpleAttributeSet(currentAttrs);
            switch (style) {
                case "bold":
                    // Alterna o atributo Bold
                    boolean isBold = StyleConstants.isBold(currentAttrs);
                    StyleConstants.setBold(newAttrs, !isBold);
                    break;
                case "italic":
                    // Alterna o atributo Italic
                    boolean isItalic = StyleConstants.isItalic(currentAttrs);
                    StyleConstants.setItalic(newAttrs, !isItalic);
                    break;
                case "underline":
                    // Alterna o atributo Underline
                    boolean isUnderline = StyleConstants.isUnderline(currentAttrs);
                    StyleConstants.setUnderline(newAttrs, !isUnderline);
                    break;
            }
            // Aplica os novos atributos somente para a faixa selecionada, mesclando os existentes
            doc.setCharacterAttributes(start, end - start, newAttrs, true);
        };
        
        // Configura as ações dos botões para chamar o toggleStyle com o respectivo estilo

        btNegrito.addActionListener(e -> {
            toggleStyle.accept("bold", () -> {});
        });
        
        btItalico.addActionListener(e -> {
            toggleStyle.accept("italic", () -> {});
        });
        
        btSublinhado.addActionListener(e -> {
            toggleStyle.accept("underline", () -> {});
        });
    }
}
