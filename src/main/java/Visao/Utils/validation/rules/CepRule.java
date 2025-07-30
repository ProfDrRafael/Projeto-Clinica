/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Visao.Utils.validation.rules;

import Visao.Utils.validation.ValidationRule;

/**
 *
 * @author GMott
 */
public class CepRule implements ValidationRule<String> {

    @Override
    public String validate(String value) {
        if (value == null) {
            return "CEP é obrigatório.";
        }

        // 1. Remove todos os caracteres que não são dígitos (pontos, hífens, etc.)
        String cepApenasNumeros = value.replaceAll("[^0-9]", "");

        // 2. Verifica se o campo está vazio após remover a máscara
        if (cepApenasNumeros.isBlank()) {
            return "CEP é obrigatório.";
        }

        // 3. Verifica se tem exatamente 8 dígitos
        if (cepApenasNumeros.length() != 8) {
            return "CEP deve conter 8 dígitos.";
        }

        return null; // Sucesso, o CEP tem 8 dígitos.
    }
}
