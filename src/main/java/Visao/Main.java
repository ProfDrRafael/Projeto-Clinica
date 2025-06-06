package Visao;

/*
Este código define uma janela principal (Main) que usa o framework FlatLaf para gerenciar temas e fontes, além de utilizar um gerenciador de formulários e componentes personalizados como Background e GlassPanePopup. O código configura uma interface moderna, possivelmente com um tema escuro e utiliza componentes adicionais para melhorar a experiência visual, como popups e efeitos de transparência.
*/

// Importações necessárias para trabalhar com o tema FlatLaf, fontes, escalas e componentes da interface gráfica
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.formdev.flatlaf.util.UIScale;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.UIManager;
import Visao.Components.Background;
import Visao.Telas.TableEstatisticas;
import Visao.JframeManager.FormManager;
import com.formdev.flatlaf.FlatIntelliJLaf;
import raven.popup.GlassPanePopup;

/**
 * Classe principal da aplicação que herda de JFrame para criar a interface gráfica
 */
public class Main extends JFrame {

    // Constante para definir se a janela será sem bordas (undecorated)
    private final boolean UNDECORATED = !true;

    // Construtor que chama o método de inicialização
    public Main() {
        init();
    }

    // Método responsável por inicializar a janela e os componentes
    private void init() {
        // Define o comportamento de fechar a janela ao clicar no botão de fechar
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Define o tamanho da janela utilizando o método de escala do FlatLaf
        setSize(UIScale.scale(new Dimension(1024, 768)));
        //setSize(UIScale.scale(new Dimension(1366, 768)));
        
        setResizable(false);
        
        // Centraliza a janela na tela
        setLocationRelativeTo(null);
        
        // Se a janela for sem borda, define-a como undecorated e define um fundo transparente
        if (UNDECORATED) {
            setUndecorated(UNDECORATED);
            setBackground(new Color(0, 0, 0, 0));  // Fundo transparente
        } else {
            // Se tiver bordas, habilita o conteúdo da janela em tela cheia
            getRootPane().putClientProperty(FlatClientProperties.FULL_WINDOW_CONTENT, true);
        }
        
        // Define o painel de fundo da janela, passando o valor de UNDECORATED
        setContentPane(new Background(UNDECORATED));
        
        // Instala um popup de efeito de vidro (transparência)
        GlassPanePopup.install(this);
        
        // Instala o gerenciador de formulários na janela
        FormManager.install(this, UNDECORATED);
        
        FormManager.showMenu();
        
        // Mostra o formulário de dashboard (painel principal)
        //FormManager.showForm(new TableEstatisticas());
                
        // Chama o método de logout, que provavelmente limpa a sessão ou volta para uma tela de login
        FormManager.logout();
        
        // Comentário de código que pode ser utilizado para alterar a orientação dos componentes para direita-esquerda (opcional)
        // applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    }

    // Método principal da aplicação
    public static void main(String[] args) {
        // Instala a fonte Roboto como padrão
        FlatRobotoFont.install();
        
        // Registra as fontes e temas personalizados na aplicação
        FlatLaf.registerCustomDefaultsSource("Visao.ThemesProperties");
        
        // Define a fonte padrão para a interface como Roboto tamanho 13
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));

        // Configura o tema FlatMacDarkLaf (tema escuro estilo Mac)
        //FlatMacDarkLaf.setup();
        FlatMacLightLaf.setup();
        //FlatIntelliJLaf.setup();
        
        // Inicia a aplicação na EventQueue para garantir que o código de interface gráfica rode na thread correta
        EventQueue.invokeLater(() -> new Main().setVisible(true));
    }
}
