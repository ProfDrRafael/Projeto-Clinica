package Visao.JframeManager;

import Regradenegocio.SessaoRN;
import Services.AutenticacaoService;
import VO.AdministradorVO;
import VO.SessaoVO;
import VO.UsuarioVO;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange; // Utilitário para mudanças de temas com animações

import java.awt.*;
import javax.swing.*;

import Visao.Components.NavigationBar; // Componente principal do formulário
import Visao.Components.PanelTemplate; // Classe base para formulários simples
import Visao.Slider.PanelSlider; // Painel que implementa transições deslizantes
import Visao.Slider.SimpleTransition; // Transições simples para o PanelSlider
import Visao.Utils.UndoRedo;
import Visao.Telas.FormLogin;
import Visao.Telas.PageProgressBar;
import Visao.Telas.PageWelcome;
import java.lang.reflect.InvocationTargetException;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;

/**
 * Classe responsável por gerenciar a exibição de formulários, transições e o
 * menu lateral. Implementa funcionalidades de login, logout e transições entre
 * formulários. Também lida com as ações de desfazer/refazer, e atualização da
 * interface gráfica.
 *
 * @author Raven
 */
public class FormManager {

    private static FormManager instance; // Instância única da classe (Singleton)
    private final JFrame frame; // Janela principal da aplicação

    private final UndoRedo<PanelTemplate> forms = new UndoRedo<>(); // Controle de formulários com funções de desfazer/refazer

    private boolean menuShowing = true; // Estado atual do menu (se está visível ou não)
    private final PanelSlider panelSlider; // Painel que implementa as transições entre menu e formulários
    private final NavigationBar mainForm; // Formulário principal da aplicação
    private final Sidebar menu; // Sidebar lateral da aplicação
    private final boolean undecorated; // Define se a janela está sem decoração (barra de título, bordas)

    /**
     * Método para inicializar o FormManager. Cria uma instância da classe
     * associada à janela principal.
     *
     * @param frame Janela principal
     * @param undecorated Se a janela terá bordas e barra de título ou não
     */
    public static void install(JFrame frame, boolean undecorated) {
        instance = new FormManager(frame, undecorated); // Inicializa a instância
    }

    /**
     * Construtor privado da classe, usado para inicializar os componentes e o
     * menu.
     *
     * @param frame Janela principal
     * @param undecorated Se a janela será decorada ou não
     */
    private FormManager(JFrame frame, boolean undecorated) {
        this.frame = frame; // Associa a janela principal
        panelSlider = new PanelSlider(); // Inicializa o painel deslizante
        mainForm = new NavigationBar(undecorated); // Inicializa o formulário principal
        menu = new Sidebar(new SidebarContent()); // Inicializa o menu com um Drawer personalizado
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
    public static void showForm(PanelTemplate component) {
        if (!isNewFormAble()) {
            return;
        }

        PanelTemplate atual = instance.forms.getCurrent();
        // Se já estamos exibindo um formulário do mesmo tipo, não empilha de novo
        if (atual != null && atual.getClass().equals(component.getClass())) {
            // Apenas reaplica/transiciona para o mesmo form sem empilhar
            if (instance.menuShowing) {
                instance.menuShowing = false;
                Image oldImage = instance.panelSlider.createOldImage();
                instance.mainForm.setForm(component);
                instance.panelSlider.addSlide(
                        instance.mainForm,
                        SimpleTransition.getSwitchFormTransition(oldImage, instance.menu.getDrawerBuilder().getDrawerWidth())
                );
            } else {
                instance.mainForm.showForm(component);
            }
            // Reabre/refresh no form atual
            instance.forms.getCurrent().formInitAndOpen();
            return;
        }

        // Se for um form novo, empilha normalmente
        instance.forms.add(component);

        if (instance.menuShowing) {
            instance.menuShowing = false;
            Image oldImage = instance.panelSlider.createOldImage();
            instance.mainForm.setForm(component);
            instance.panelSlider.addSlide(
                    instance.mainForm,
                    SimpleTransition.getSwitchFormTransition(oldImage, instance.menu.getDrawerBuilder().getDrawerWidth())
            );
        } else {
            instance.mainForm.showForm(component);
        }

        instance.forms.getCurrent().formInitAndOpen();
    }

    /**
     *
     * @param esqueciSenha
     */
    public static void EsqueciSenha(PanelTemplate esqueciSenha) {
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
     * Realiza o logout, removendo os componentes atuais e exibindo a tela de
     * login.
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

    public static void loading(PageProgressBar telaLoading) {
        instance.frame.getContentPane().removeAll();

        instance.frame.getContentPane().setLayout(new MigLayout("fill", "[]", "[]"));

        instance.frame.getContentPane().add(telaLoading, "grow");

        instance.frame.revalidate();
        instance.frame.repaint();
    }

    /**
     * Realiza o login de um usuário, configurando o menu e exibindo a interface
     * principal.
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
            SidebarContent drawerBuilder = (SidebarContent) instance.menu.getDrawerBuilder();

            drawerBuilder.setUser(user); // Define o usuário no menu

            // Exibe a interface principal
            instance.frame.repaint();
            instance.frame.revalidate();
            FlatAnimatedLafChange.hideSnapshotWithAnimation();

            FormManager.showForm(new PageWelcome());

        });
    }
    
    // Login para testes
    public static void login() {
        FlatAnimatedLafChange.showSnapshot();

        SwingUtilities.invokeLater(() -> {
            instance.frame.getContentPane().removeAll();
            instance.frame.getContentPane().setLayout(new BorderLayout());
            instance.frame.getContentPane().add(instance.panelSlider);
            
            String nome = "nome";
            String email = "email";
            String senha = "senha";

            SessaoVO sessao = new SessaoVO();
            sessao.setId(null);
            sessao.setNome("admin");
            sessao.setEmail("admin@admin.com");
            sessao.setTipo("Administrador");

            new SessaoRN().salvarSessao(sessao);
            
            UsuarioVO user = new AdministradorVO(1, nome, email, senha);

            // Configura o menu dinamicamente com base no tipo do usuário
            SidebarContent drawerBuilder = (SidebarContent) instance.menu.getDrawerBuilder();

            drawerBuilder.setUser(user); // Define o usuário no menu

            // Exibe a interface principal
            instance.frame.repaint();
            instance.frame.revalidate();
            FlatAnimatedLafChange.hideSnapshotWithAnimation();

            FormManager.showForm(new PageWelcome());

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
     * Desfaz a ação de exibição de um formulário, voltando para o formulário
     * anterior.
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
     * Refaz a ação de exibição de um formulário, indo para o próximo formulário
     * na pilha.
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
    public static UndoRedo<PanelTemplate> getForms() {
        return instance.forms; // Retorna a pilha de formulários
    }

    /**
     * Verifica se pode exibir um novo formulário, verificando se o formulário
     * atual pode ser fechado.
     *
     * @return true se um novo formulário pode ser exibido, false caso
     * contrário.
     */
    public static boolean isNewFormAble() {
        return instance.forms.getCurrent() == null || instance.forms.getCurrent().formClose(); // Verifica se o formulário atual pode ser fechado
    }

    /**
     * Atualiza a interface dos formulários temporários, aplicando mudanças na
     * aparência (look and feel).
     */
    public static void updateTempFormUI() {
        // 1) atualiza cada PanelTemplate registrado
        for (PanelTemplate f : instance.forms) {
            SwingUtilities.updateComponentTreeUI(f);
        }
        // 2) atualiza TODO o restante da UI: frame, panelSlider, mainForm, menus, etc.
        for (Window w : Window.getWindows()) {
            SwingUtilities.updateComponentTreeUI(w);
        }
    }

    public static void reloadCurrentForm() {
        if (!instance.menuShowing && instance.forms.getCurrent() != null) {
            PanelTemplate currentForm = instance.forms.getCurrent();

            if (isNewFormAble()) {
                try {
                    PanelTemplate newForm = currentForm.getClass().getDeclaredConstructor().newInstance();

                    showForm(newForm);

                } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER,
                            "Erro ao recarregar o formulário: " + e.getMessage());
                }
            }
        }
    }
}
