package Visao.Utils.validation.rules;

import Visao.Utils.validation.ValidationRule;


/**
 * Regra de validação para garantir que um campo de texto contenha apenas caracteres numéricos.
 */
public class NumericFilterRule implements ValidationRule<String> {

    @Override
    public String validate(String value) {
        // Se o valor for nulo ou vazio, esta regra específica passa.
        // A regra NotEmptyRule deve ser usada para checar se o campo está vazio.
        if (value == null || value.trim().isEmpty()) {
            return null; // Retorna null para indicar sucesso
        }

        // Verifica cada caractere da string
        for (char c : value.toCharArray()) {
            if (!Character.isDigit(c)) {
                // Retorna a mensagem de erro diretamente
                return "Deve conter apenas números."; 
            }
        }

        return null; // Todos os caracteres são dígitos, então retorna null (sucesso)
    }
}