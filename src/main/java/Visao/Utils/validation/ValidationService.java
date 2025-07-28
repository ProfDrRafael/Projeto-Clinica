package Visao.Utils.validation;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.text.JTextComponent;

/**
 * Serviço para gerenciar e executar regras de validação em componentes Swing.
 *
 * @author GMott
 */
public class ValidationService {

    private final Map<JComponent, List<ValidationRule>> rulesMap = new HashMap<>();
    private final Map<JComponent, JLabel> errorLabelMap = new HashMap<>();
    private final Map<JComponent, String> currentErrors = new HashMap<>();
    private final Consumer<Boolean> formValidityCallback;

    public ValidationService(Consumer<Boolean> formValidityCallback) {
        this.formValidityCallback = formValidityCallback;
    }

    public void addRule(JTextComponent component, ValidationRule<String> rule, JLabel errorLabel) {
        rulesMap.computeIfAbsent(component, k -> new ArrayList<>()).add(rule);
        errorLabelMap.put(component, errorLabel);
        component.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                validateComponent(component);
            }
        });
    }

    public void addRule(JComboBox<?> component, ValidationRule<Object> rule, JLabel errorLabel) {
        rulesMap.computeIfAbsent(component, k -> new ArrayList<>()).add(rule);
        errorLabelMap.put(component, errorLabel);
        component.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                validateComponent(component);
            }
        });
        component.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                validateComponent(component);
            }
        });
    }

    private void validateComponent(JTextComponent component) {
        List<ValidationRule> componentRules = rulesMap.get(component);
        String textToValidate = component.getText();
        for (ValidationRule rule : componentRules) {
            @SuppressWarnings("unchecked")
            String errorMessage = rule.validate(textToValidate);
            if (errorMessage != null) {
                applyFeedback(component, errorMessage);
                return;
            }
        }
        clearFeedback(component);
    }

    // --- CORREÇÃO 1: De JComboBox<String> para JComboBox<?> ---
    private void validateComponent(JComboBox<?> component) {
        List<ValidationRule> componentRules = rulesMap.get(component);
        Object selectedItem = component.getSelectedItem();
        for (ValidationRule rule : componentRules) {
            @SuppressWarnings("unchecked")
            String errorMessage = rule.validate(selectedItem);
            if (errorMessage != null) {
                applyFeedback(component, errorMessage);
                return;
            }
        }
        clearFeedback(component);
    }

    private void applyFeedback(JComponent component, String message) {
        component.putClientProperty("FlatLaf.style", "outline: red;");
        JLabel errorLabel = errorLabelMap.get(component);
        if (errorLabel != null) {
            errorLabel.setText(message);
        }
        currentErrors.put(component, message);
        updateFormValidity();
    }

    private void clearFeedback(JComponent component) {
        component.putClientProperty("FlatLaf.style", "outline: null;");
        JLabel errorLabel = errorLabelMap.get(component);
        if (errorLabel != null) {
            errorLabel.setText("");
        }
        currentErrors.remove(component);
        updateFormValidity();
    }

    private void updateFormValidity() {
        formValidityCallback.accept(currentErrors.isEmpty());
    }

    public void validateAll() {
        for (JComponent component : rulesMap.keySet()) {
            if (component instanceof JTextComponent) {
                validateComponent((JTextComponent) component);
            } // --- CORREÇÃO 2: De JComboBox<String> para JComboBox<?> ---
            else if (component instanceof JComboBox) {
                validateComponent((JComboBox<?>) component);
            }
        }
    }
    
    /**
 * Verifica se o formulário está atualmente válido (sem erros registrados).
 * @return true se não houver erros, false caso contrário.
 */
public boolean isFormValid() {
    return currentErrors.isEmpty();
}

    public void validateInitialState() {
        for (JComponent component : rulesMap.keySet()) {
            List<ValidationRule> componentRules = rulesMap.get(component);
            String errorMessage = null;

            if (component instanceof JTextComponent) {
                String textToValidate = ((JTextComponent) component).getText();
                for (ValidationRule rule : componentRules) {
                    errorMessage = rule.validate(textToValidate);
                    if (errorMessage != null) {
                        break;
                    }
                }
            } else if (component instanceof JComboBox) {
                Object selectedItem = ((JComboBox) component).getSelectedItem();
                for (ValidationRule rule : componentRules) {
                    errorMessage = rule.validate(selectedItem);
                    if (errorMessage != null) {
                        break;
                    }
                }
            }

            if (errorMessage != null) {
                currentErrors.put(component, errorMessage);
            } else {
                currentErrors.remove(component);
            }
        }
        updateFormValidity();
    }
}
