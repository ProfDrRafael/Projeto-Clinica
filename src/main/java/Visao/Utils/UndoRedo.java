/**
 * Classe utilitária que fornece funcionalidade de desfazer/refazer utilizando duas pilhas internas.
 * <p>
 * Cada vez que um novo estado é adicionado via {@link #add(Object)}, o histórico de refazer é limpo.
 * Ao chamar {@link #undo()}, volta-se um estado (quando possível) e habilita-se o refazer via {@link #redo()}.
 * </p>
 *
 * @param <E> o tipo de estado ou ação a ser rastreado
 */
package Visao.Utils;

import java.util.Iterator;
import java.util.Stack;

public class UndoRedo<E> implements Iterable<E> {

    /** Pilha que armazena o histórico de estados (pilha de desfazer). */
    private final Stack<E> stack1;
    /** Pilha que armazena o histórico de refazer quando operações de desfazer ocorrem. */
    private final Stack<E> stack2;

    /**
     * Constrói um novo gerenciador {@code UndoRedo} com pilhas de desfazer e refazer vazias.
     */
    public UndoRedo() {
        stack1 = new Stack<>();
        stack2 = new Stack<>();
    }

    /**
     * Adiciona um novo estado ao histórico de desfazer e limpa o histórico de refazer.
     *
     * @param item o novo estado ou ação a ser registrado
     */
    public void add(E item) {
        stack1.push(item);
        stack2.clear();
    }

    /**
     * Desfaz a última ação e retorna o estado atual após o desfazer.
     * O estado desfeito é empilhado na pilha de refazer.
     *
     * @return o estado após desfazer, ou {@code null} se não for possível desfazer
     */
    public E undo() {
        if (stack1.size() > 1) {
            stack2.push(stack1.pop());
            return stack1.get(stack1.size() - 1);
        } else {
            return null;
        }
    }

    /**
     * Refaz a última ação desfeita e retorna o estado refeito.
     * O estado refeito é empilhado de volta na pilha de desfazer.
     *
     * @return o estado que foi refeito, ou {@code null} se não for possível refazer
     */
    public E redo() {
        if (!stack2.isEmpty()) {
            E item = stack2.pop();
            stack1.push(item);
            return item;
        } else {
            return null;
        }
    }

    /**
     * Retorna o estado atual sem modificar os históricos.
     *
     * @return o estado atual, ou {@code null} se não houver estados registrados
     */
    public E getCurrent() {
        if (stack1.isEmpty()) {
            return null;
        } else {
            return stack1.get(stack1.size() - 1);
        }
    }

    /**
     * Verifica se é possível desfazer uma ação.
     *
     * @return {@code true} se existir pelo menos um estado anterior para retornar
     */
    public boolean isUndoAble() {
        return stack1.size() > 1;
    }

    /**
     * Verifica se é possível refazer uma ação.
     *
     * @return {@code true} se houver pelo menos um estado no histórico de refazer
     */
    public boolean isRedoAble() {
        return !stack2.empty();
    }

    /**
     * Limpa tanto o histórico de desfazer quanto o de refazer.
     */
    public void clear() {
        stack1.clear();
        stack2.clear();
    }

    /**
     * Limpa apenas o histórico de refazer, preservando o histórico de desfazer.
     */
    public void clearRedo() {
        stack2.clear();
    }

    /**
     * Retorna um iterador sobre todos os estados registrados: primeiro o histórico de desfazer,
     * depois o histórico de refazer.
     *
     * @return um {@link Iterator} sobre estados na ordem cronológica de registro
     */
    @Override
    public Iterator<E> iterator() {
        return new MyIterator();
    }

    /**
     * Iterador interno que combina as pilhas de desfazer e refazer.
     */
    private class MyIterator implements Iterator<E> {

        /** Índice atual que percorre ambas as pilhas. */
        private int index = 0;

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            return index < stack1.size() + stack2.size();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E next() {
            if (index < stack1.size()) {
                return stack1.elementAt(index++);
            } else {
                return stack2.elementAt((index++) - stack1.size());
            }
        }
    }
}
