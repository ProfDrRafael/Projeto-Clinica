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
    public static void EstilizeEditorTextPane(JTextPane textPane) {
        textPane.setEditorKit(new RTFEditorKit());
        textPane.setContentType("text/rtf");
        
    }
    public static void JTextComponentUndoRedo(JTextPane textPane){
        // Gerenciador de Undo e Redo
        UndoManager undoManager = new UndoManager();
        textPane.getDocument().addUndoableEditListener(undoManager);
        
        // Adicionar atalhos de teclado
        textPane.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("control Z"), "Undo");
        textPane.getActionMap().put("Undo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (undoManager.canUndo()) {
                    undoManager.undo();
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
    
    public static void JTextComponentStylization(JTextPane textPane, JButton btNegrito, JButton btItalico, JButton btSublinhado){
        // Configurar StyledDocument
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet bold = new SimpleAttributeSet();
        StyleConstants.setBold(bold, true);

        SimpleAttributeSet italic = new SimpleAttributeSet();
        StyleConstants.setItalic(italic, true);

        SimpleAttributeSet underline = new SimpleAttributeSet();
        StyleConstants.setUnderline(underline, true);

        // Adicionar ações aos botões
        btNegrito.addActionListener(e -> {
            int start = textPane.getSelectionStart();
            int end = textPane.getSelectionEnd();
            doc.setCharacterAttributes(start, end - start, bold, false);
        });

        btItalico.addActionListener(e -> {
            int start = textPane.getSelectionStart();
            int end = textPane.getSelectionEnd();
            doc.setCharacterAttributes(start, end - start, italic, false);
        });

        btSublinhado.addActionListener(e -> {
            int start = textPane.getSelectionStart();
            int end = textPane.getSelectionEnd();
            doc.setCharacterAttributes(start, end - start, underline, false);
        });

    }
}
