package Visao.Slider;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;

/**
 * Classe abstrata que define o comportamento para transições de imagens entre componentes.
 * Esta classe é usada como base para implementar transições personalizadas entre dois estados de imagem (antigo e novo).
 * O método `render` coordena a renderização dessas imagens.
 * 
 * @author Raven
 */
public abstract class SliderTransition {

    /**
     * Método abstrato que define como a imagem antiga (estado anterior) deve ser renderizada.
     * Esse método será implementado pelas classes que estendem `SliderTransition`.
     * 
     * @param component Componente onde a imagem será renderizada.
     * @param g Contexto gráfico usado para desenhar.
     * @param image Imagem antiga a ser renderizada.
     * @param width Largura da área de renderização.
     * @param height Altura da área de renderização.
     * @param animate Valor de animação, variando de 0 a 1, que indica o progresso da transição.
     */
    public abstract void renderImageOld(Component component, Graphics g, Image image, int width, int height, float animate);

    /**
     * Método abstrato que define como a nova imagem (novo estado) deve ser renderizada.
     * Esse método será implementado pelas classes que estendem `SliderTransition`.
     * 
     * @param component Componente onde a imagem será renderizada.
     * @param g Contexto gráfico usado para desenhar.
     * @param image Imagem nova a ser renderizada.
     * @param width Largura da área de renderização.
     * @param height Altura da área de renderização.
     * @param animate Valor de animação, variando de 0 a 1, que indica o progresso da transição.
     */
    public abstract void renderImageNew(Component component, Graphics g, Image image, int width, int height, float animate);

    /**
     * Indica se a transição deve fechar automaticamente após a animação ser completada.
     * O valor padrão é `true`, mas pode ser sobrescrito pelas classes que estendem `SliderTransition`.
     * 
     * @return true se a animação deve fechar automaticamente, false caso contrário.
     */
    public boolean closeAfterAnimation() {
        return true;
    }

    /**
     * Método responsável por coordenar a renderização das imagens antiga e nova durante a transição.
     * Ele chama os métodos `renderImageOld` e `renderImageNew` para processar a animação e desenhar as imagens
     * em estados intermediários durante a transição.
     * 
     * @param component Componente onde as imagens serão renderizadas.
     * @param g Contexto gráfico usado para desenhar.
     * @param imageOld Imagem antiga (estado anterior).
     * @param imageNew Imagem nova (novo estado).
     * @param width Largura da área de renderização.
     * @param height Altura da área de renderização.
     * @param animate Valor de animação, variando de 0 a 1, que indica o progresso da transição.
     */
    public void render(Component component, Graphics g, Image imageOld, Image imageNew, int width, int height, float animate) {
        // Renderiza a imagem antiga
        renderImageOld(component, g.create(), imageOld, width, height, animate);
        
        // Renderiza a nova imagem
        renderImageNew(component, g.create(), imageNew, width, height, animate);
    }
}
