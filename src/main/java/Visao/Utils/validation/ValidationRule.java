/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Visao.Utils.validation;

/**
 * Interface funcional para uma regra de validação genérica.
 * @param <T> O tipo do dado a ser validado (ex: String para um JTextField).
 */
@FunctionalInterface
public interface ValidationRule<T> {
    /**
     * Valida um valor de entrada.
     * @param value O valor a ser validado.
     * @return Uma String com a mensagem de erro se a validação falhar, ou null se for bem-sucedida.
     */
    String validate(T value);
}