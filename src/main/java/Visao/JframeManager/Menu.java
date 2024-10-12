package Visao.JframeManager;

import com.formdev.flatlaf.FlatClientProperties; // Propriedades do cliente para o Flat Look and Feel
import java.awt.event.MouseAdapter; // Classe para adaptação de eventos de mouse
import java.awt.event.MouseEvent; // Classe para eventos de mouse
import javax.swing.JPanel; // Classe base para a criação de painéis em Swing
import javax.swing.SwingUtilities; // Utilitário para manipulações na interface gráfica
import net.miginfocom.swing.MigLayout; // Layout de gerenciamento de posição baseado em regras
import raven.drawer.component.DrawerBuilder; // Construtor para o painel do menu lateral
import raven.drawer.component.DrawerPanel; // Painel que representa o conteúdo do menu lateral

/**
 * Classe que representa o menu lateral da aplicação, gerenciando a exibição e a interação do usuário.
 * Contém um painel de desenho (DrawerPanel) que é construído a partir de um DrawerBuilder.
 * 
 * @author Raven
 */
public class Menu extends JPanel {

    private final DrawerBuilder drawerBuilder; // Construtor do painel de menu

    /**
     * Retorna o construtor do painel de menu.
     * 
     * @return O objeto DrawerBuilder associado ao menu.
     */
    public DrawerBuilder getDrawerBuilder() {
        return drawerBuilder; // Retorna o construtor do menu
    }

    /**
     * Construtor da classe Menu, que inicializa o painel de menu com o construtor fornecido.
     * 
     * @param drawerBuilder O construtor para o painel lateral do menu
     */
    public Menu(DrawerBuilder drawerBuilder) {
        this.drawerBuilder = drawerBuilder; // Inicializa o construtor do menu
        init(); // Chama o método de inicialização
    }

    /**
     * Método que realiza a configuração inicial do menu, incluindo propriedades e layout.
     */
    private void init() {
        putClientProperty(FlatClientProperties.STYLE, "" // Define a propriedade de estilo do cliente
                + "background:$Drawer.background"); // Define a cor de fundo do menu a partir das propriedades do Flat LAF

        // Configura o layout utilizando MigLayout
        setLayout(new MigLayout("wrap,fill", "[fill," + drawerBuilder.getDrawerWidth() + "!]", "[fill]"));
        
        // Adiciona um listener para eventos de mouse ao painel
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) { // Verifica se o botão do mouse pressionado é o esquerdo
                    FormManager.hideMenu(); // Oculta o menu ao clicar
                }
            }
        });

        // Cria um painel de desenho com base no construtor fornecido
        DrawerPanel drawerPanel = new DrawerPanel(drawerBuilder);
        
        // Adiciona um listener de mouse ao painel de desenho (atualmente vazio)
        drawerPanel.addMouseListener(new MouseAdapter() {
        });

        // Constrói o painel de desenho usando o construtor
        drawerBuilder.build(drawerPanel);
        
        // Adiciona o painel de desenho ao menu
        add(drawerPanel);
    }
}
