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
public class MinLengthRule implements ValidationRule<String> {

    private final int minLength;

    public MinLengthRule(int minLength) {
        this.minLength = minLength;
    }

    @Override
    public String validate(String value) {
        // Remove caracteres não numéricos para validar CEP, celular, etc.
        String cleanedValue = value.replaceAll("[^0-9]", "");
        if (cleanedValue.length() < minLength) {
            return "Deve conter no mínimo " + minLength + " dígitos.";
        }
        return null;
    }
}
