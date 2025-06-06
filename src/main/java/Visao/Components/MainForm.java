package Visao.Components;

/*
Este código define o layout principal da aplicação usando um painel deslizante (PanelSlider) para alternar entre diferentes formulários. O cabeçalho contém botões para controlar ações como abrir o menu, desfazer, refazer e atualizar o conteúdo da interface. O uso de FlatLaf ajuda a manter um visual moderno e estilizado com bordas arredondadas e ícones SVG, e MigLayout organiza o layout dos componentes com precisão.
*/

// Importações necessárias para trabalhar com temas FlatLaf, ícones SVG, layout e controle de formulários
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;
import Visao.JframeManager.FormManager;
import Visao.Slider.PanelSlider;
import Visao.Slider.SimpleTransition;
import Visao.Slider.SliderTransition;

/**
 * Componente principal que gerencia o layout da interface e o conteúdo dos formulários.
 * @author Raven
 */
public class MainForm extends JPanel {

    // Indica se a janela é undecorated (sem bordas)
    private final boolean undecorated;

    // Construtor que recebe o valor de undecorated e chama o método de inicialização
    public MainForm(boolean undecorated) {
        this.undecorated = undecorated;
        init();
    }

    // Método responsável por inicializar os componentes do formulário principal
    private void init() {
        // Se a janela for undecorated, define algumas propriedades de estilo, como borda e arredondamento
        if (undecorated) {
            putClientProperty(FlatClientProperties.STYLE, ""
                    + "border:0,5,5,5;"   // Borda de 5 pixels
                    + "arc:30");          // Cantos arredondados com raio de 30 pixels
        }

        // Define o layout como MigLayout com configurações de preenchimento e alinhamento
        setLayout(new MigLayout("wrap, fill", "[fill]", "[]0[grow,fill]0[]"));
        
        // Cria o cabeçalho da interface (barra de ferramentas)
        header = createHeader();
        
        // Cria um painel deslizante (PanelSlider) e o coloca dentro de um JScrollPane
        panelSlider = new PanelSlider();
        JScrollPane scroll = new JScrollPane(panelSlider);
        
        // Remove a borda do JScrollPane
        scroll.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:0,0,0,0");  // Sem borda
        
        // Configura o estilo da barra de rolagem vertical, incluindo o raio e a largura
        scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
                + "trackArc:999;"    // Arco máximo, tornando a barra redonda
                + "width:10");       // Define a largura da barra de rolagem como 10 pixels
        
        // Define o incremento da rolagem vertical para 10 pixels
        scroll.getVerticalScrollBar().setUnitIncrement(10);
        
        // Adiciona o cabeçalho e o painel de rolagem ao layout principal
        add(header);
        add(scroll,"grow, push");
    }

    // Método que cria o cabeçalho com botões de menu, desfazer, refazer e atualizar
    private JPanel createHeader() {
        // Cria um painel com MigLayout e define margens internas (insets)
        JPanel panel = new JPanel(new MigLayout("insets 3"));
        
        // Remove o fundo do painel do cabeçalho
        panel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:null");

        // Cria os botões com ícones SVG e associa a ações específicas
        cmdMenu = createButton("Menu", new FlatSVGIcon("Multimidia/icon/menu.svg"));
        cmdUndo = createButton("Voltar",new FlatSVGIcon("Multimidia/icon/undo.svg"));
        cmdRedo = createButton("Avançar",new FlatSVGIcon("Multimidia/icon/redo.svg"));
        cmdRefresh = createButton("Recarregar",new FlatSVGIcon("Multimidia/icon/refresh.svg"));
        
        // Define a ação de abrir o menu quando o botão "menu" é clicado
        cmdMenu.addActionListener(e -> {
            FormManager.showMenu();
        });

        // Define a ação de desfazer quando o botão "desfazer" é clicado
        cmdUndo.addActionListener(e -> {
            FormManager.undo();
        });

        // Define a ação de refazer quando o botão "refazer" é clicado
        cmdRedo.addActionListener(e -> {
            FormManager.redo();
        });

        // Define a ação de atualizar quando o botão "atualizar" é clicado
        cmdRefresh.addActionListener(e -> {
            FormManager.reloadCurrentForm();
        });

        // Adiciona os botões ao painel do cabeçalho
        panel.add(cmdMenu);
        panel.add(cmdUndo);
        panel.add(cmdRedo);
        panel.add(cmdRefresh);
        
        return panel;
    }

    // Método para criar um botão com estilo personalizado
    private JButton createButton(String tipoIcon, Icon icon) {
        // Cria o botão com o ícone fornecido
        JButton button = new JButton(tipoIcon, icon);
        
        // Define o estilo do botão usando as propriedades do FlatLaf
        button.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Button.toolbar.background;"  // Define o fundo com a cor de toolbar
                + "arc:10;"                                  // Arredonda as bordas do botão
                + "margin:3,3,3,3;"                          // Define margens internas de 3 pixels
                + "borderWidth:0;"                           // Sem borda visível
                + "focusWidth:0;"                            // Sem borda de foco
                + "innerFocusWidth:0");                      // Sem borda de foco interna
        
        return button;
    }

    // Método para exibir um formulário com uma transição deslizante personalizada
    public void showForm(Component component, SliderTransition transition) {
        // Verifica o estado dos botões antes de exibir o novo formulário
        checkButton();
        
        // Adiciona o componente ao painel deslizante com a transição fornecida
        panelSlider.addSlide(component, transition);
        
        // Revalida o painel para refletir as alterações
        revalidate();
    }

    // Método sobrecarregado para exibir um formulário com a transição padrão
    public void showForm(Component component) {
        showForm(component, SimpleTransition.getDefaultTransition(false));
    }

    // Método para definir um formulário sem transição
    public void setForm(Component component) {
        // Verifica o estado dos botões
        checkButton();
        
        // Adiciona o componente ao painel deslizante sem transição
        panelSlider.addSlide(component, null);
    }

    // Método que verifica e atualiza o estado (habilitado/desabilitado) dos botões do cabeçalho
    private void checkButton() {
        // Habilita ou desabilita o botão de desfazer com base no estado atual de formulário
        cmdUndo.setEnabled(FormManager.getForms().isUndoAble());
        
        // Habilita ou desabilita o botão de refazer com base no estado atual de formulário
        cmdRedo.setEnabled(FormManager.getForms().isRedoAble());
        
        // Habilita ou desabilita o botão de atualizar se houver um formulário atual
        cmdRefresh.setEnabled(FormManager.getForms().getCurrent() != null);
    }

    // Declaração dos componentes principais da interface
    private JPanel header;            // Cabeçalho que contém os botões
    private JButton cmdMenu;          // Botão de menu
    private JButton cmdUndo;          // Botão de desfazer
    private JButton cmdRedo;          // Botão de refazer
    private JButton cmdRefresh;       // Botão de atualizar
    private PanelSlider panelSlider;  // Painel deslizante para transições de formulários
}
