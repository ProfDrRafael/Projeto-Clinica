package Visao.JframeManager;

import Regradenegocio.SessaoRN;
import Services.AutenticacaoService;
import VO.SessaoVO;
import VO.UsuarioVO;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange; // Utilitário para mudanças de temas com animações

import java.awt.*;
import javax.swing.*;

import Visao.Components.MainForm; // Componente principal do formulário
import Visao.Components.SimpleForm; // Classe base para formulários simples
import Visao.Slider.PanelSlider; // Painel que implementa transições deslizantes
import Visao.Slider.SimpleTransition; // Transições simples para o PanelSlider
import Visao.Utils.UndoRedo;
import Visao.Telas.FormLogin;
import Visao.Telas.PageWelcome;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;

/**
 * Classe responsável por gerenciar a exibição de formulários, transições e o menu lateral.
 * Implementa funcionalidades de login, logout e transições entre formulários.
 * Também lida com as ações de desfazer/refazer, e atualização da interface gráfica.
 * 
 * @author Raven
 */
public class FormManager {

    private static FormManager instance; // Instância única da classe (Singleton)
    private final JFrame frame; // Janela principal da aplicação

    private final UndoRedo<SimpleForm> forms = new UndoRedo<>(); // Controle de formulários com funções de desfazer/refazer

    private boolean menuShowing = true; // Estado atual do menu (se está visível ou não)
    private final PanelSlider panelSlider; // Painel que implementa as transições entre menu e formulários
    private final MainForm mainForm; // Formulário principal da aplicação
    private final Menu menu; // Menu lateral da aplicação
    private final boolean undecorated; // Define se a janela está sem decoração (barra de título, bordas)

    /**
     * Método para inicializar o FormManager. Cria uma instância da classe associada à janela principal.
     * 
     * @param frame Janela principal
     * @param undecorated Se a janela terá bordas e barra de título ou não
     */
    public static void install(JFrame frame, boolean undecorated) {
        instance = new FormManager(frame, undecorated); // Inicializa a instância
    }

    /**
     * Construtor privado da classe, usado para inicializar os componentes e o menu.
     * 
     * @param frame Janela principal
     * @param undecorated Se a janela será decorada ou não
     */
    private FormManager(JFrame frame, boolean undecorated) {
        this.frame = frame; // Associa a janela principal
        panelSlider = new PanelSlider(); // Inicializa o painel deslizante
        mainForm = new MainForm(undecorated); // Inicializa o formulário principal
        menu = new Menu(new MyDrawerBuilder()); // Inicializa o menu com um Drawer personalizado
        this.undecorated = undecorated; // Define se a janela é decorada
    }

    /**
     * Exibe o menu lateral com uma animação deslizante.
     */
    public static void showMenu() {
        instance.menuShowing = true; // Atualiza o estado do menu como visível
        instance.panelSlider.addSlide(
            instance.menu, 
            SimpleTransition.getShowMenuTransition(instance.menu.getDrawerBuilder().getDrawerWidth(), instance.undecorated)
        ); // Adiciona o menu com uma transição suave
    }

    /**
     * Exibe um novo formulário no painel principal, com transição animada.
     * 
     * @param component Formulário a ser exibido
     */
    public static void showForm(SimpleForm component) {
        if (isNewFormAble()) { // Verifica se pode exibir um novo formulário
            instance.forms.add(component); // Adiciona o formulário à pilha de formulários
            if (instance.menuShowing == true) { // Se o menu está visível
                instance.menuShowing = false; // Oculta o menu
                Image oldImage = instance.panelSlider.createOldImage(); // Captura a imagem do estado anterior
                instance.mainForm.setForm(component); // Define o novo formulário no mainForm
                instance.panelSlider.addSlide(
                    instance.mainForm, 
                    SimpleTransition.getSwitchFormTransition(oldImage, instance.menu.getDrawerBuilder().getDrawerWidth())
                ); // Transição suave entre o menu e o formulário
            } else {
                instance.mainForm.showForm(component); // Exibe o formulário diretamente se o menu já está oculto
            }
            instance.forms.getCurrent().formInitAndOpen(); // Inicializa e abre o formulário atual
        }
    }

    /**
     * 
     * @param esqueciSenha
     */
    public static void EsqueciSenha(SimpleForm esqueciSenha) {
        FlatAnimatedLafChange.showSnapshot();

        // Esconde a tela atual com fade-out
        instance.frame.getContentPane().setVisible(false);

        // Usar um SwingUtilities.invokeLater para garantir que a remoção e a adição de componentes ocorram de forma fluida
        SwingUtilities.invokeLater(() -> {
            // Remove todos os componentes do frame
            instance.frame.getContentPane().removeAll();

            // Configura MigLayout no container principal (mantém a consistência de layout)
            instance.frame.getContentPane().setLayout(new MigLayout("fill", "[]", "[]"));

            // Adiciona o FormLogin centralizado tanto verticalmente quanto horizontalmente
            instance.frame.getContentPane().add(esqueciSenha, "align center center, grow");

            // Revalida e repinta a tela antes de exibi-la
            instance.frame.revalidate();
            instance.frame.repaint();

            // Exibe o conteúdo novamente com fade-in
            instance.frame.getContentPane().setVisible(true);
            FlatAnimatedLafChange.hideSnapshotWithAnimation(); // Exibe a animação de transição
        });
    }
    
    /**
     * Realiza o logout, removendo os componentes atuais e exibindo a tela de login.
     */
    public static void logout() {
        // Captura o estado atual da interface para a animação
        FlatAnimatedLafChange.showSnapshot();

        // Esconde a tela atual com fade-out
        instance.frame.getContentPane().setVisible(false);

        // Usar um SwingUtilities.invokeLater para garantir que a remoção e a adição de componentes ocorram de forma fluida
        SwingUtilities.invokeLater(() -> {
            // Remove todos os componentes do frame
            instance.frame.getContentPane().removeAll();

            // Configura MigLayout no container principal (mantém a consistência de layout)
            instance.frame.getContentPane().setLayout(new MigLayout("fill", "[]", "[]"));

            // Adiciona o FormLogin centralizado tanto verticalmente quanto horizontalmente
            FormLogin formLogin = new FormLogin(new AutenticacaoService());
            instance.frame.getContentPane().add(formLogin, "align center center, grow");

            // Revalida e repinta a tela antes de exibi-la
            instance.frame.revalidate();
            instance.frame.repaint();

            // Exibe o conteúdo novamente com fade-in
            instance.frame.getContentPane().setVisible(true);
            FlatAnimatedLafChange.hideSnapshotWithAnimation(); // Exibe a animação de transição
        });
    }

    /**
     * Realiza o login de um usuário, configurando o menu e exibindo a interface principal.
     * 
     * @param user O usuário que está realizando o login
     */
    public static void login(UsuarioVO user) {
        FlatAnimatedLafChange.showSnapshot();

        SwingUtilities.invokeLater(() -> {
            instance.frame.getContentPane().removeAll();
            instance.frame.getContentPane().setLayout(new BorderLayout());
            instance.frame.getContentPane().add(instance.panelSlider);

            SessaoVO sessao = new SessaoVO();
            sessao.setId(null);
            sessao.setNome(user.getNomeCompleto());
            sessao.setEmail(user.getEmail());
            sessao.setTipo(user.getTipo());

            new SessaoRN().salvarSessao(sessao);

            // Configura o menu dinamicamente com base no tipo do usuário
            MyDrawerBuilder drawerBuilder = (MyDrawerBuilder) instance.menu.getDrawerBuilder();
            System.out.println(user);
            drawerBuilder.setUser(user); // Define o usuário no menu

            // Exibe a interface principal
            instance.frame.repaint();
            instance.frame.revalidate();
            FlatAnimatedLafChange.hideSnapshotWithAnimation();

            FormManager.showForm(new PageWelcome());

            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,
                    "Bem-vindo(a), " + user.getNomeCompleto() + "!");
        });
    }


    /**
     * Oculta o menu lateral com uma transição animada.
     */
    public static void hideMenu() {
        instance.menuShowing = false; // Atualiza o estado do menu como oculto
        instance.panelSlider.addSlide(
            instance.mainForm, 
            SimpleTransition.getHideMenuTransition(instance.menu.getDrawerBuilder().getDrawerWidth(), instance.undecorated)
        ); // Adiciona uma transição suave para ocultar o menu
    }

    /**
     * Desfaz a ação de exibição de um formulário, voltando para o formulário anterior.
     */
    public static void undo() {
        if (isNewFormAble()) { // Verifica se pode desfazer
            if (!instance.menuShowing && instance.forms.isUndoAble()) { // Se o menu está oculto e pode desfazer
                instance.mainForm.showForm(instance.forms.undo(), SimpleTransition.getDefaultTransition(true)); // Transição para o formulário anterior
                instance.forms.getCurrent().formOpen(); // Abre o formulário atual
            }
        }
    }

    /**
     * Refaz a ação de exibição de um formulário, indo para o próximo formulário na pilha.
     */
    public static void redo() {
        if (isNewFormAble()) { // Verifica se pode refazer
            if (!instance.menuShowing && instance.forms.isRedoAble()) { // Se o menu está oculto e pode refazer
                instance.mainForm.showForm(instance.forms.redo()); // Exibe o próximo formulário
                instance.forms.getCurrent().formOpen(); // Abre o formulário atual
            }
        }
    }

    /**
     * Atualiza o formulário atual, caso o menu esteja oculto.
     */
    public static void refresh() {
        if (!instance.menuShowing) {
            instance.forms.getCurrent().formRefresh(); // Atualiza o formulário atual
        }
    }

    /**
     * Retorna a pilha de formulários (Undo/Redo) gerenciados.
     * 
     * @return Objeto de controle de desfazer/refazer para formulários.
     */
    public static UndoRedo<SimpleForm> getForms() {
        return instance.forms; // Retorna a pilha de formulários
    }

    /**
     * Verifica se pode exibir um novo formulário, verificando se o formulário atual pode ser fechado.
     * 
     * @return true se um novo formulário pode ser exibido, false caso contrário.
     */
    public static boolean isNewFormAble() {
        return instance.forms.getCurrent() == null || instance.forms.getCurrent().formClose(); // Verifica se o formulário atual pode ser fechado
    }

    /**
     * Atualiza a interface dos formulários temporários, aplicando mudanças na aparência (look and feel).
     */
    public static void updateTempFormUI() {
        for (SimpleForm f : instance.forms) { // Para cada formulário na pilha
            SwingUtilities.updateComponentTreeUI(f); // Atualiza a árvore de componentes (UI)
        }
    }
}
