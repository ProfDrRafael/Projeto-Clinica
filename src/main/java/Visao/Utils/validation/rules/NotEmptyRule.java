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
public class NotEmptyRule implements ValidationRule<String> {

    private final String fieldName;

    public NotEmptyRule(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String validate(String value) {
        if (value == null || value.trim().isEmpty()) {
            return "O campo '" + fieldName + "' é obrigatório.";
        }
        return null;
    }
}
