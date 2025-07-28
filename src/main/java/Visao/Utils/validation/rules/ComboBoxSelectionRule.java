package Visao.Utils.validation.rules;

import Visao.Utils.validation.ValidationRule;
import javax.swing.JComboBox;

/**
 * Regra para validar se uma opção válida foi selecionada em um JComboBox,
 * ignorando um item de placeholder. Pode, opcionalmente, depender de outro JComboBox.
 */
public class ComboBoxSelectionRule implements ValidationRule<Object> {

    private final String placeholder;
    private final String fieldName;
    
    // --- NOVO: Campos para a dependência ---
    private JComboBox<?> parentComboBox;
    private String dependencyErrorMessage;
    // ------------------------------------

    /**
     * Construtor simples para validação sem dependência.
     */
    public ComboBoxSelectionRule(String placeholder, String fieldName) {
        this.placeholder = placeholder;
        this.fieldName = fieldName;
    }

    /**
     * --- NOVO: Construtor para validação com dependência ---
     * @param placeholder O texto do placeholder deste ComboBox.
     * @param fieldName O nome do campo para a mensagem de erro padrão.
     * @param parent O ComboBox "pai" do qual este depende.
     * @param dependencyMsg A mensagem de erro para mostrar se o pai não foi selecionado.
     */
    public ComboBoxSelectionRule(String placeholder, String fieldName, JComboBox<?> parent, String dependencyMsg) {
        this.placeholder = placeholder;
        this.fieldName = fieldName;
        this.parentComboBox = parent;
        this.dependencyErrorMessage = dependencyMsg;
    }

    @Override
    public String validate(Object value) {
        // --- LÓGICA DE DEPENDÊNCIA MODIFICADA ---
        // 1. Se existe um ComboBox pai, verifique-o primeiro.
        if (parentComboBox != null) {
            Object parentValue = parentComboBox.getSelectedItem();
            if (parentValue == null || parentValue.toString().startsWith("Escolha")) {
                return dependencyErrorMessage; // Retorna a mensagem de erro da dependência
            }
        }
        // ------------------------------------------

        // 2. Se a verificação do pai passou (ou não existe), continua a validação normal.
        if (value == null || placeholder.equals(value.toString())) {
            return "Por favor, selecione uma opção para " + fieldName + ".";
        }

        return null; // Sucesso
    }
}