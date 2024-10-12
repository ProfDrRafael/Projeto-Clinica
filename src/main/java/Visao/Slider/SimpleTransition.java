package Visao.Slider;

import com.formdev.flatlaf.ui.FlatUIUtils;
import com.formdev.flatlaf.util.UIScale;
import java.awt.AlphaComposite;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import javax.swing.UIManager;

/**
 * Classe responsável por fornecer diferentes transições visuais para o componente Slider.
 * Cada método implementa um tipo específico de transição para ser aplicado ao trocar de componentes.
 * @author Raven
 */
public class SimpleTransition {

    private static final float ZOOM_IN = 0.1f;  // Constante que define o nível de zoom para a transição

    // Método que retorna a transição padrão, movendo o novo componente para a direita ou esquerda
    public static SliderTransition getDefaultTransition(boolean toRight) {
        return new SliderTransition() {

            // Renderiza a imagem do componente antigo
            @Override
            public void renderImageOld(Component component, Graphics g, Image image, int width, int height, float animate) {
                g.drawImage(image, 0, 0, null);  // Desenha a imagem do componente antigo na tela
                g.dispose();  // Libera os recursos gráficos
            }

            // Renderiza a imagem do novo componente com uma transição horizontal
            @Override
            public void renderImageNew(Component component, Graphics g, Image image, int width, int height, float animate) {
                Graphics2D g2 = (Graphics2D) g;
                int move = UIScale.scale(200);  // Define a distância do movimento
                int x = (int) (move * (1f - animate));  // Calcula a posição x da nova imagem
                if (toRight) {
                    x = -x;  // Inverte a direção do movimento se a transição for para a direita
                }
                g2.setComposite(AlphaComposite.SrcOver.derive(animate));  // Define a transparência da nova imagem
                g2.drawImage(image, x, 0, null);  // Desenha a nova imagem na posição calculada
                g2.dispose();  // Libera os recursos gráficos
            }
        };
    }

    // Transição que revela um menu lateral (drawer), aplicando uma animação de abertura
    public static SliderTransition getShowMenuTransition(int drawerWidth, boolean undecorated) {
        return new SliderTransition() {

            @Override
            public void renderImageOld(Component component, Graphics g, Image image, int width, int height, float animate) {
                // Renderiza a imagem do componente antigo enquanto o menu se abre
                renderImage(component, g, image, width, height, drawerWidth, animate, undecorated);
            }

            @Override
            public void renderImageNew(Component component, Graphics g, Image image, int width, int height, float animate) {
                if (animate != 1) {
                    g.drawImage(image, 0, 0, null);  // Desenha a nova imagem durante a abertura
                }
                g.dispose();  // Libera os recursos gráficos
            }

            @Override
            public boolean closeAfterAnimation() {
                return false;  // O menu não fecha após a animação
            }

            @Override
            public void render(Component component, Graphics g, Image imageOld, Image imageNew, int width, int height, float animate) {
                renderImageNew(component, g.create(), imageNew, width, height, animate);  // Renderiza a nova imagem
                renderImageOld(component, g.create(), imageOld, width, height, animate);  // Renderiza a imagem antiga
            }
        };
    }

    // Transição que oculta o menu lateral (drawer), aplicando uma animação de fechamento
    public static SliderTransition getHideMenuTransition(int drawerWidth, boolean undecorated) {
        return new SliderTransition() {

            @Override
            public void renderImageOld(Component component, Graphics g, Image image, int width, int height, float animate) {
                if (animate != 1) {
                    g.drawImage(image, 0, 0, null);  // Desenha a imagem antiga enquanto o menu se fecha
                }
                g.dispose();  // Libera os recursos gráficos
            }

            @Override
            public void renderImageNew(Component component, Graphics g, Image image, int width, int height, float animate) {
                // Renderiza a nova imagem com a animação de fechamento
                renderImage(component, g, image, width, height, drawerWidth, 1f - animate, undecorated);
            }
        };
    }

    // Transição que troca o formulário visível com uma animação
    public static SliderTransition getSwitchFormTransition(Image oldFormImage, int drawerWidth) {
        return new SliderTransition() {

            @Override
            public void renderImageOld(Component component, Graphics g, Image image, int width, int height, float animate) {
                g.drawImage(image, 0, 0, null);  // Desenha a imagem do formulário antigo
                g.dispose();  // Libera os recursos gráficos
            }

            @Override
            public void renderImageNew(Component component, Graphics g, Image image, int width, int height, float animate) {
                float newAnimate = 1f - animate;  // Calcula o progresso inverso da animação
                Graphics2D g2 = (Graphics2D) g;
                FlatUIUtils.setRenderingHints(g2);  // Aplica dicas de renderização para suavizar a imagem

                // Calcula a posição e o zoom para a nova imagem
                float zoomIn = ZOOM_IN;
                boolean ltr = component.getComponentOrientation().isLeftToRight();
                int x = (int) ((ltr ? newAnimate : -newAnimate) * UIScale.scale(drawerWidth + 60));
                if (!ltr) {
                    x += width * (newAnimate * zoomIn);
                }
                int y = (height / 2);
                int space = UIScale.scale(20);
                int arc = UIScale.scale(30);
                g2.translate(x, y);  // Move a nova imagem para a posição calculada
                if (zoomIn > 0) {
                    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    float scale = 1f - newAnimate * zoomIn;  // Aplica o efeito de zoom
                    g2.scale(scale, scale);
                }
                g2.translate(0, -y);

                // Desenha uma forma arredondada atrás da nova imagem
                g2.setColor(UIManager.getColor("Panel.background"));
                g2.setComposite(AlphaComposite.SrcOver.derive(0.4f));  // Define a transparência
                g2.fill(new RoundRectangle2D.Double(((ltr ? -space : space) * 2) * newAnimate, (space * 2) * newAnimate, width, height - (space * 4) * newAnimate, arc, arc));
                g2.setComposite(AlphaComposite.SrcOver.derive(0.4f + (animate * 0.6f)));  // Ajusta a transparência da nova imagem
                g2.drawImage(image, (int) ((ltr ? -space : space) * newAnimate), (int) (space * newAnimate), width, (int) (height - (space * 2) * newAnimate), null);
                g2.setComposite(AlphaComposite.SrcOver.derive(newAnimate));  // Define a transparência da imagem antiga
                g2.drawImage(oldFormImage, 0, 0, null);  // Desenha a imagem do formulário antigo
                g2.dispose();
            }
        };
    }

    // Método auxiliar que renderiza a imagem com um efeito de transição suave
    private static void renderImage(Component component, Graphics g, Image image, int width, int height, int drawerWidth, float animate, boolean undecorated) {
        Graphics2D g2 = (Graphics2D) g;
        FlatUIUtils.setRenderingHints(g2);  // Aplica dicas de renderização

        // Calcula a posição e o zoom para a imagem
        float zoomIn = ZOOM_IN;
        boolean ltr = component.getComponentOrientation().isLeftToRight();
        int x = (int) ((ltr ? animate : -animate) * UIScale.scale(drawerWidth + 60));
        if (!ltr) {
            x += width * (animate * zoomIn);
        }
        int y = (height / 2);
        int space = UIScale.scale(20);
        int arc = UIScale.scale(30);
        g2.translate(x, y);  // Move a imagem para a nova posição
        if (zoomIn > 0) {
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            float scale = 1f - animate * zoomIn;
            g2.scale(scale, scale);  // Aplica o efeito de zoom
        }
        g2.translate(0, -y);

        // Desenha uma forma arredondada atrás da imagem
        g2.setColor(UIManager.getColor("Panel.background"));
        g2.setComposite(AlphaComposite.SrcOver.derive(0.4f));
        g2.fill(new RoundRectangle2D.Double(((ltr ? -space : space) * 2) * animate, (space * 2) * animate, width, height - (space * 4) * animate, arc, arc));
        g2.fill(new RoundRectangle2D.Double((ltr ? -space : space) * animate, space * animate, width, height - (space * 2) * animate, arc, arc));

        g2.setComposite(AlphaComposite.SrcOver);
        g2.drawImage(image, 0, 0, null);  // Desenha a nova imagem

        if (!undecorated) {
            // Desenha uma área adicional se o componente não estiver sem decoração
            g2.setColor(UIManager.getColor("Drawer.background"));
            Area area = new Area(new Rectangle.Double(0, 0, width, height));
            area.subtract(new Area(new RoundRectangle2D.Double(0, 0, width, height, arc, arc)));
            g2.fill(area);
        }
        g2.dispose();  // Libera os recursos gráficos
    }
}
