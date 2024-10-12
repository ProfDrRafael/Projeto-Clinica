package Visao.Slider;

import com.formdev.flatlaf.util.Animator;
import com.formdev.flatlaf.util.CubicBezierEasing;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.VolatileImage;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;

/**
 * Classe responsável por criar um painel com efeito de transição entre componentes (sliders).
 * O painel usa o JLayeredPane para empilhar os componentes e aplicar transições suaves.
 * @author Raven
 */
public class PanelSlider extends JLayeredPane {

    private PanelSnapshot panelSnapshot;  // Componente para capturar e exibir imagens durante as animações
    private Component component;  // Componente atual visível
    private Component oldComponent;  // Componente anterior, usado para a transição

    // Construtor que inicializa o painel de slider
    public PanelSlider() {
        init();
    }

    // Método que inicializa os componentes e configurações do painel
    private void init() {
        setOpaque(true);  // Define o painel como opaco
        panelSnapshot = new PanelSnapshot();  // Inicializa o painel de snapshot para capturar imagens
        setLayout(new CardLayout());  // Define o layout como CardLayout para empilhar os componentes
        setLayer(panelSnapshot, JLayeredPane.DRAG_LAYER);  // Define o nível de profundidade do painel de snapshot
        add(panelSnapshot);  // Adiciona o painel de snapshot ao layout
        panelSnapshot.setVisible(false);  // Inicialmente invisível até que uma animação seja executada
    }

    // Método para adicionar um novo componente com uma transição de slide
    public void addSlide(Component component, SliderTransition transition) {
        component.applyComponentOrientation(getComponentOrientation());  // Aplica a orientação do componente

        // Se já houver um componente visível, ele é definido como o antigo
        if (this.component != null) {
            this.oldComponent = this.component;
        }

        this.component = component;  // Define o novo componente atual

        // Se não houver um componente antigo, adiciona o novo diretamente
        if (oldComponent == null) {
            add(component);
            repaint();
            revalidate();
            component.setVisible(true);
        } else {
            add(component);  // Adiciona o novo componente à interface
            if (transition != null) {
                doLayout();  // Reorganiza o layout para ajustar os componentes
                SwingUtilities.invokeLater(() -> {
                    // Cria imagens do componente antigo e do novo para a animação
                    Image oldImage = createImage(oldComponent);
                    Image newImage = createImage(component);
                    remove(oldComponent);  // Remove o componente antigo
                    panelSnapshot.animate(transition, oldImage, newImage);  // Executa a animação de transição
                });
            } else {
                component.setVisible(true);
                remove(oldComponent);  // Se não houver transição, apenas troca os componentes
                revalidate();
                repaint();
            }
        }
    }

    // Método para criar uma imagem de um componente para a transição
    public Image createImage(Component component) {
        boolean check = false;
        for (Component com : getComponents()) {
            if (com == component) {
                check = true;
                break;
            }
        }
        if (!check) {
            add(component);  // Adiciona o componente se ele não estiver na lista
        }
        VolatileImage snapshot = component.createVolatileImage(getWidth(), getHeight());  // Cria uma imagem do componente
        if (snapshot == null) {
            return null;  // Retorna nulo se a criação da imagem falhar
        }
        component.paint(snapshot.getGraphics());  // Renderiza o componente na imagem
        if (!check) {
            remove(component);  // Remove o componente se ele não estava na lista originalmente
        }
        return snapshot;
    }

    // Método para criar uma imagem do componente antigo
    public Image createOldImage() {
        if (oldComponent != null) {
            return createImage(oldComponent);
        }
        return null;
    }

    // Classe interna responsável pela captura e exibição das imagens durante a transição
    private class PanelSnapshot extends JComponent {

        @Override
        public void updateUI() {
            super.updateUI();
            if (sliderTransition != null && !sliderTransition.closeAfterAnimation()) {
                if (oldComponent != null) {
                    SwingUtilities.updateComponentTreeUI(oldComponent);  // Atualiza a árvore de componentes
                    oldImage = PanelSlider.this.createImage(oldComponent);  // Cria a imagem do componente antigo
                }
            }
        }

        private final Animator animator;  // Controla a animação da transição
        private float animate;  // Controla o progresso da animação

        private SliderTransition sliderTransition;  // Define a transição usada
        private Image oldImage;  // Imagem do componente antigo
        private Image newImage;  // Imagem do novo componente

        // Construtor que inicializa o animador e suas configurações
        public PanelSnapshot() {
            animator = new Animator(400, new Animator.TimingTarget() {
                @Override
                public void timingEvent(float v) {
                    animate = v;  // Atualiza o progresso da animação
                    repaint();  // Re-renderiza o painel
                }

                @Override
                public void end() {
                    if (sliderTransition.closeAfterAnimation()) {
                        setVisible(false);  // Esconde o painel de snapshot ao final da animação
                        if (oldImage != null) {
                            oldImage.flush();  // Libera recursos da imagem antiga
                        }
                        if (newImage != null) {
                            newImage.flush();  // Libera recursos da imagem nova
                        }
                    }
                    component.setVisible(true);  // Mostra o novo componente ao final da animação
                }
            });
            animator.setInterpolator(CubicBezierEasing.EASE);  // Define a suavidade da animação
        }

        // Método que inicia a animação da transição entre as imagens
        protected void animate(SliderTransition sliderTransition, Image oldImage, Image newImage) {
            if (animator.isRunning()) {
                animator.stop();  // Para a animação atual se já estiver rodando
            }
            this.oldImage = oldImage;
            this.newImage = newImage;
            this.sliderTransition = sliderTransition;
            this.animate = 0f;
            repaint();  // Re-renderiza o painel com as novas imagens
            setVisible(true);  // Torna o painel de snapshot visível
            animator.start();  // Inicia a animação
        }

        @Override
        public void paint(Graphics g) {
            if (sliderTransition != null) {
                int width = getWidth();
                int height = getHeight();
                sliderTransition.render(this, g, oldImage, newImage, width, height, animate);  // Renderiza a transição
            }
        }
    }
}
