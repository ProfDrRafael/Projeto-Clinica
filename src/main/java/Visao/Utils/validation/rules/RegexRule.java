/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Visao.Utils.validation.rules;

import Visao.Utils.validation.ValidationRule;
import java.util.regex.Pattern;


public class RegexRule implements ValidationRule<String> {
    private final Pattern pattern;
    private final String errorMessage;

    public RegexRule(String regex, String errorMessage) {
        this.pattern = Pattern.compile(regex);
        this.errorMessage = errorMessage;
    }

    @Override
    public String validate(String value) {
        if (value == null || !pattern.matcher(value).matches()) {
            return errorMessage;
        }
        return null;
    }
}