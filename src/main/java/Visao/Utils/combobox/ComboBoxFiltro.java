/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Visao.Utils.combobox;

/**
 *
 * @author GMott
 *
 */
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ComboBoxFiltro<E> {

    private final JComboBox<E> comboBox;
    private final List<E> itensOriginais;

    public ComboBoxFiltro(JComboBox<E> comboBox) {
        this.comboBox = comboBox;
        this.itensOriginais = new ArrayList<>();
        // Armazena os itens originais do ComboBox
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            itensOriginais.add(comboBox.getItemAt(i));
        }

        // Torna o ComboBox "pesquisável" no FlatLaf, o que habilita a digitação
        // sem mudar a aparência de "não editável".
        this.comboBox.putClientProperty("JTextField.variant", "search");

        // Obtém o componente de editor (que é um JTextField) para adicionar o listener
        JTextField editor = (JTextField) comboBox.getEditor().getEditorComponent();
        editor.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                // Executa a lógica de filtro em uma thread separada para não travar a UI
                SwingUtilities.invokeLater(() -> {
                    filtrarItens(editor.getText());
                });
            }
        });
    }

    /**
     * Filtra os itens do ComboBox com base no texto digitado.
     *
     * @param texto O texto usado para o filtro.
     */
    private void filtrarItens(String texto) {
        DefaultComboBoxModel<E> model = (DefaultComboBoxModel<E>) comboBox.getModel();
        model.removeAllElements();

        // Se o texto estiver vazio, mostra todos os itens originais
        if (texto.isEmpty()) {
            for (E item : itensOriginais) {
                model.addElement(item);
            }
            return;
        }

        // Adiciona ao modelo apenas os itens que contêm o texto digitado (ignorando maiúsculas/minúsculas)
        String textoLower = texto.toLowerCase();
        for (E item : itensOriginais) {
            if (item.toString().toLowerCase().contains(textoLower)) {
                model.addElement(item);
            }
        }

        // Mantém o popup do ComboBox visível para mostrar os resultados do filtro
        if (model.getSize() > 0) {
            comboBox.showPopup();
        } else {
            comboBox.hidePopup();
        }
    }
}
