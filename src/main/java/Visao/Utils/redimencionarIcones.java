/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Visao.Utils;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author john
 */
public class redimencionarIcones {
    
    public void redimensionarIcones(JButton botao, String caminho) {
        // Chama a versão com tamanho padrão
        redimensionarIcones(botao, caminho, 30);
    }

    public void redimensionarIcones(JButton botao, String caminho, int tamanho) {
        ImageIcon iconeOriginalBotao = new ImageIcon(getClass().getResource(caminho));
        Image iconeEmEscalaBotao = iconeOriginalBotao.getImage().getScaledInstance(tamanho, tamanho, java.awt.Image.SCALE_SMOOTH);
        botao.setIcon(new ImageIcon(iconeEmEscalaBotao));
        botao.setIconTextGap(10);
    }
    
}
