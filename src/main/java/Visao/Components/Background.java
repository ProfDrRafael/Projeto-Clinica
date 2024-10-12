package Visao.Components;

/*
Este código define um painel personalizado (Background) que pode ser usado como fundo para a interface gráfica. Ele tem duas variações: uma com bordas arredondadas e transparência (quando udecorated é true) e outra sem essas características (quando udecorated é false).

O painel usa o sistema de layout BorderLayout, permitindo organizar outros componentes facilmente. Ele também utiliza o sistema de estilo do FlatLaf para aplicar propriedades visuais como bordas e cores de fundo.
*/

// Importações necessárias para manipulação de temas FlatLaf, renderização de UI e layout de componentes
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.ui.FlatUIUtils;
import com.formdev.flatlaf.util.UIScale;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 * Componente de fundo personalizado para a interface gráfica.
 * @author Raven
 */
public class Background extends JPanel {

    // Variável que indica se a janela é "undecorated" (sem bordas)
    private final boolean udecorated;
    
    // Construtor que recebe o valor de 'udecorated' e chama o método de inicialização
    public Background(boolean udecorated) {
        this.udecorated = udecorated;
        init();
    }

    // Método de inicialização do painel
    private void init() {
        // Define se o painel será opaco ou não, dependendo do estado de 'udecorated'
        setOpaque(!udecorated);
        
        // Define o layout do painel como BorderLayout, que organiza os componentes em cinco regiões (Norte, Sul, Leste, Oeste e Centro)
        setLayout(new BorderLayout());
        
        // Define propriedades de estilo usando FlatLaf, como borda e cor de fundo
        putClientProperty(FlatClientProperties.STYLE, ""
                + "border:5,5,5,5;"  // Define uma borda com espessura de 5 pixels em todos os lados
                + "background:$Drawer.background");  // Define a cor de fundo como a cor padrão para gavetas (Drawer)
    }

    // Método para desenhar o componente no painel
    @Override
    protected void paintComponent(Graphics g) {
        // Se o painel for "undecorated" (sem bordas), desenha um fundo com bordas arredondadas
        if (udecorated) {
            // Cria uma cópia do contexto gráfico atual para manipulação
            Graphics2D g2 = (Graphics2D) g.create();
            
            // Aplica dicas de renderização para suavizar o desenho (antialiasing, etc.)
            FlatUIUtils.setRenderingHints(g2);
            
            // Define o raio de arredondamento das bordas (escala 30 pixels usando UIScale)
            int arc = UIScale.scale(30);
            
            // Define a cor de fundo com base no background atual do painel
            g2.setColor(getBackground());
            
            // Desenha o fundo do componente com cantos arredondados
            FlatUIUtils.paintComponentBackground(g2, 0, 0, getWidth(), getHeight(), 0, arc);
            
            // Libera o contexto gráfico criado
            g2.dispose();
        }
        
        // Chama o método da superclasse para garantir que outros elementos sejam pintados corretamente
        super.paintComponent(g);
    }
}
