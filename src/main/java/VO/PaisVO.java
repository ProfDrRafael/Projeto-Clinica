/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VO;

import java.util.Objects;

/**
 *
 * @author GMott
 */
public class PaisVO {

    private final int id;
    private final String nome;

    public PaisVO(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    /**
     * MÉTODO CHAVE: Informa ao JComboBox qual texto deve ser exibido na lista.
     * Graças a ele, apenas o nome do país aparecerá.
     */
    @Override
    public String toString() {
        return this.nome;
    }

    /**
     * Boa prática para que o Java saiba como comparar dois objetos PaisVO.
     * Ajuda o JComboBox a funcionar corretamente ao selecionar itens.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PaisVO paisVO = (PaisVO) o;
        return id == paisVO.id;
    }

    /**
     * Necessário quando sobrescrevemos o método equals.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
