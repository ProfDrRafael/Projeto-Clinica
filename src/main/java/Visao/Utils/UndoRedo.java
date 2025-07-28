package Visao.Utils;

import java.util.Iterator;
import java.util.Stack;

/**
 * Classe utilitária que fornece funcionalidade de desfazer/refazer utilizando duas pilhas internas
 * com um limite de capacidade para o histórico de desfazer, evitando sobrecarga do sistema.
 * <p>
 * Cada vez que um novo estado é adicionado via {@link #add(Object)}, o histórico de refazer é limpo.
 * Se o histórico de desfazer atingir a capacidade máxima, o estado mais antigo é removido.
 * Ao chamar {@link #undo()}, volta-se um estado (quando possível) e habilita-se o refazer via {@link #redo()}.
 * </p>
 *
 * @param <E> o tipo de estado ou ação a ser rastreado
 */
public class UndoRedo<E> implements Iterable<E> {

    /** O estado inicial (raiz), que nunca é removido. */
    private E rootState;
    /** Pilha que armazena o histórico de estados posteriores ao raiz. */
    private final Stack<E> undoStack;
    /** Pilha que armazena o histórico de refazer. */
    private final Stack<E> redoStack;
    /** A capacidade máxima do histórico de desfazer (não inclui o estado raiz). */
    private final int capacity;

    /** Capacidade padrão se nenhuma for especificada. */
    private static final int DEFAULT_CAPACITY = 5;

    /**
     * Constrói um novo gerenciador {@code UndoRedo} com uma capacidade padrão.
     */
    public UndoRedo() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Constrói um novo gerenciador {@code UndoRedo} com uma capacidade máxima especificada.
     *
     * @param capacity a capacidade máxima do histórico de desfazer (alterações após o estado inicial).
     */
    public UndoRedo(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("A capacidade deve ser um número positivo.");
        }
        this.capacity = capacity;
        this.rootState = null;
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }

    /**
     * Adiciona um novo estado. O primeiro estado se torna a raiz.
     * Os estados subsequentes são adicionados ao histórico de desfazer, que tem capacidade limitada.
     *
     * @param item o novo estado ou ação a ser registrado
     */
    public void add(E item) {
        if (rootState == null) {
            rootState = item;
        } else {
            if (undoStack.size() >= this.capacity) {
                undoStack.remove(0); // Remove a alteração mais antiga
            }
            undoStack.push(item);
        }
        redoStack.clear();
    }

    /**
     * Desfaz a última ação. É possível desfazer até retornar ao estado raiz.
     *
     * @return o estado após desfazer, ou {@code null} se já estiver no estado raiz.
     */
    public E undo() {
        if (isUndoAble()) {
            redoStack.push(undoStack.pop());
            return getCurrent(); // Retorna o novo estado atual
        }
        return null;
    }

    /**
     * Refaz a última ação desfeita.
     *
     * @return o estado que foi refeito, ou {@code null} se não for possível refazer.
     */
    public E redo() {
        if (isRedoAble()) {
            E item = redoStack.pop();
            undoStack.push(item);
            return item;
        }
        return null;
    }

    /**
     * Retorna o estado atual, que pode ser o último da pilha de desfazer ou o estado raiz.
     *
     * @return o estado atual, ou {@code null} se nenhum estado foi adicionado.
     */
    public E getCurrent() {
        if (!undoStack.isEmpty()) {
            return undoStack.peek();
        }
        return rootState;
    }
    
    /**
     * Retorna diretamente ao estado raiz, movendo todo o histórico de alterações para a pilha de refazer.
     *
     * @return o estado raiz.
     */
    public E resetToRoot() {
        if (rootState != null) {
            // Move todos os itens da pilha de desfazer para a de refazer, na ordem inversa.
            while (!undoStack.isEmpty()) {
                redoStack.push(undoStack.pop());
            }
        }
        return rootState;
    }

    /**
     * Verifica se é possível desfazer uma ação.
     * Só é possível desfazer se houver alterações além do estado raiz.
     *
     * @return {@code true} se a pilha de desfazer não estiver vazia.
     */
    public boolean isUndoAble() {
        return !undoStack.isEmpty();
    }

    /**
     * Verifica se é possível refazer uma ação.
     *
     * @return {@code true} se houver pelo menos um estado no histórico de refazer.
     */
    public boolean isRedoAble() {
        return !redoStack.isEmpty();
    }

    /**
     * Limpa completamente os históricos e o estado raiz.
     */
    public void clear() {
        rootState = null;
        undoStack.clear();
        redoStack.clear();
    }

    /**
     * Retorna a capacidade máxima do histórico de alterações.
     *
     * @return a capacidade configurada.
     */
    public int getCapacity() {
        return this.capacity;
    }

    // O Iterator e a classe MyIterator permanecem os mesmos, mas sua lógica
    // agora reflete a separação entre rootState e undoStack implicitamente.
    // Para uma implementação mais robusta, o iterador também poderia ser ajustado.
    @Override
    public Iterator<E> iterator() {
        // Esta implementação simples pode ser ajustada para incluir a raiz explicitamente se necessário.
        // Por agora, ela itera sobre as pilhas como antes.
        return new MyIterator(); 
    }


    /**
     * Iterador interno que combina as pilhas de desfazer e refazer.
     * A iteração não é segura contra modificações concorrentes.
     */
    private class MyIterator implements Iterator<E> {
        private int index = 0;
        private final int totalSize;
        private final int undoSize;

        MyIterator() {
            // Armazena os tamanhos no momento da criação para evitar inconsistências
            this.undoSize = undoStack.size();
            this.totalSize = this.undoSize + redoStack.size();
        }
        
        @Override
        public boolean hasNext() {
            return index < totalSize;
        }

        @Override
        public E next() {
            if (index < undoSize) {
                return undoStack.elementAt(index++);
            } else {
                // O índice para a pilha de refazer é o índice geral menos o tamanho da pilha de desfazer
                return redoStack.elementAt((index++) - undoSize);
            }
        }
    }
}