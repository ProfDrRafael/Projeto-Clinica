/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Visao.Utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
/**
 *
 * @author john
 */
public class RedimencionarIcones {
    
  // Versão padrão para ícones 
    public void redimensionarIcones(JButton botao, String caminho) {
        redimensionarIcones(botao, caminho, 92);
    }

    // Redimensiona o ícone para o tamanho especificado (em pixels)
    public void redimensionarIcones(JButton botao, String caminho, int tamanho) {
        try {
            // Carrega a imagem original do recurso
            ImageIcon iconeOriginal = new ImageIcon(getClass().getResource(caminho));
            Image imagemOriginal = iconeOriginal.getImage();

            // Cria um BufferedImage com canal alfa para suportar transparências (se houver)
            BufferedImage imagemRedimensionada = new BufferedImage(tamanho, tamanho, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = imagemRedimensionada.createGraphics();

            // Define pistas de renderização para alta qualidade
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Desenha a imagem escalada no BufferedImage
            g2d.drawImage(imagemOriginal, 0, 0, tamanho, tamanho, null);
            g2d.dispose();

            // Atualiza o botão com o ícone redimensionado
            botao.setIcon(new ImageIcon(imagemRedimensionada));
            botao.setIconTextGap(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
