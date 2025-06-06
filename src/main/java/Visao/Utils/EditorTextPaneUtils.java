package Visao.Utils;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.rtf.RTFEditorKit;
import javax.swing.undo.UndoManager;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.function.BiConsumer;

/**
 * Utilitário para configurar JTextPane com RTF, estilização e undo/redo.
 */
public class EditorTextPaneUtils {

    /**
     * Inicializa o JTextPane para trabalhar com RTF.
     * Deve ser chamado antes do usuário digitar ou carregar conteúdo.
     */
    public static void initRTFEditor(JTextPane textPane) {
        RTFEditorKit rtfKit = new RTFEditorKit();
        textPane.setEditorKit(rtfKit);
        textPane.setContentType("text/rtf");
    }

    /**
     * Configura undo/redo (Ctrl+Z / Ctrl+Y) no JTextPane.
     */
    public static void configureUndoRedo(JTextPane textPane) {
        UndoManager undoManager = new UndoManager();
        textPane.getDocument().addUndoableEditListener(undoManager);

        InputMap im = textPane.getInputMap(JComponent.WHEN_FOCUSED);
        ActionMap am = textPane.getActionMap();

        im.put(KeyStroke.getKeyStroke("control Z"), "Undo");
        am.put("Undo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (undoManager.canUndo()) {
                    undoManager.undo();
                }
            }
        });

        im.put(KeyStroke.getKeyStroke("control Y"), "Redo");
        am.put("Redo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (undoManager.canRedo()) {
                    undoManager.redo();
                }
            }
        });
    }

    /**
     * Configura botões para aplicar negrito, itálico e sublinhado na seleção.
     */
    public static void configureStyling(
            JTextPane textPane,
            JButton btNegrito,
            JButton btItalico,
            JButton btSublinhado) {

        // Redimensiona ícones (classe auxiliar imaginada)
        RedimencionarIcones red = new RedimencionarIcones();
        red.redimensionarIcones(btNegrito, "/Multimidia/icon/bold_icon.png", 35);
        red.redimensionarIcones(btItalico, "/Multimidia/icon/italico_icon.png", 35);
        red.redimensionarIcones(btSublinhado, "/Multimidia/icon/sublinhado_icon.png", 35);

        StyledDocument doc = textPane.getStyledDocument();
        BiConsumer<String, Void> toggleStyle = (style, dummy) -> {
            int start = textPane.getSelectionStart();
            int end = textPane.getSelectionEnd();
            if (start == end) return;

            Element element = doc.getCharacterElement(start);
            AttributeSet current = element.getAttributes();
            SimpleAttributeSet attrs = new SimpleAttributeSet(current);

            switch (style) {
                case "bold":
                    StyleConstants.setBold(attrs, !StyleConstants.isBold(current));
                    break;
                case "italic":
                    StyleConstants.setItalic(attrs, !StyleConstants.isItalic(current));
                    break;
                case "underline":
                    StyleConstants.setUnderline(attrs, !StyleConstants.isUnderline(current));
                    break;
            }
            doc.setCharacterAttributes(start, end - start, attrs, true);
        };

        btNegrito.addActionListener(e -> toggleStyle.accept("bold", null));
        btItalico.addActionListener(e -> toggleStyle.accept("italic", null));
        btSublinhado.addActionListener(e -> toggleStyle.accept("underline", null));
    }

    /**
     * Retorna o texto puro (sem marcação) contido no JTextPane.
     */
    public static String getPlainText(JTextPane textPane) throws BadLocationException {
        Document doc = textPane.getDocument();
        return doc.getText(0, doc.getLength());
    }

    /**
     * Exporta o conteúdo RTF do JTextPane como String.
     */
    public static String exportRTF(JTextPane textPane) throws IOException, BadLocationException {
        EditorKit kit = textPane.getEditorKit();
        if (!(kit instanceof RTFEditorKit)) {
            throw new IllegalStateException("EditorKit não é RTFEditorKit");
        }
        RTFEditorKit rtfKit = (RTFEditorKit) kit;
        StyledDocument doc = (StyledDocument) textPane.getDocument();
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            rtfKit.write(baos, doc, 0, doc.getLength());
            return baos.toString("UTF-8");
        }
    }

    /**
     * Carrega marcação RTF de String e exibe no JTextPane.
     */
    public static void importRTF(JTextPane textPane, String rtf) throws IOException, BadLocationException {
        RTFEditorKit rtfKit = new RTFEditorKit();
        StyledDocument doc = (StyledDocument) rtfKit.createDefaultDocument();
        try (StringReader reader = new StringReader(rtf)) {
            rtfKit.read(reader, doc, 0);
        }
        textPane.setDocument(doc);
    }
}