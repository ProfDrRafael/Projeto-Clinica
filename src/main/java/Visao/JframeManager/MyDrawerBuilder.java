package Visao.JframeManager;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Color;
import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import raven.drawer.component.DrawerPanel;
import raven.drawer.component.SimpleDrawerBuilder;
import raven.drawer.component.footer.SimpleFooterData;
import raven.drawer.component.header.SimpleHeaderData;
import raven.drawer.component.header.SimpleHeaderStyle;
import raven.drawer.component.menu.MenuAction;
import raven.drawer.component.menu.MenuEvent;
import raven.drawer.component.menu.MenuValidation;
import raven.drawer.component.menu.SimpleMenuOption;
import raven.drawer.component.menu.SimpleMenuStyle;
import raven.drawer.component.menu.data.Item;
import raven.drawer.component.menu.data.MenuItem;
import Visao.Telas.TableEstatisticas;
import Visao.Telas.MenuAdministrador;
import Visao.Telas.MenuEstagiario;
import Visao.Telas.MenuOrientador;
import Visao.Telas.MenuSecretaria;
import Visao.Telas.FormAgenda;
import Visao.Telas.FormEstagiario;
import Visao.Telas.FormPaciente;
import Visao.Telas.FormAtendimento;
import Visao.Telas.TableListaEspera;
import Visao.Telas.FormProntuario;
import Visao.Telas.FormUsuario;
import Persistencia.modelTemp.ModelUser; // Modelo de usuário
import Visao.Telas.PageWelcome;
import Visao.Utils.MessagesAlert;
import raven.swing.AvatarIcon;

/**
 * Classe que constrói um menu lateral (drawer) com opções personalizadas.
 * Esta classe estende `SimpleDrawerBuilder` e implementa a configuração de cabeçalho,
 * rodapé e opções de menu com base em um usuário.
 * 
 * @author Raven
 */
public class MyDrawerBuilder extends SimpleDrawerBuilder {

    private ModelUser user; // Modelo do usuário atual
    private final ThemesChange themesChange; // Classe para mudança de temas

    /**
     * Define o usuário atual e atualiza o cabeçalho do menu com o nome do usuário.
     * 
     * @param user O usuário a ser definido.
     */
    public void setUser(ModelUser user) {
        this.user = user;
        SimpleHeaderData headerData = header.getSimpleHeaderData();
        headerData.setTitle(user.getUserName()); // Atualiza o título do cabeçalho
        header.setSimpleHeaderData(headerData); // Define os dados do cabeçalho
        rebuildMenu(); // Reconstrói o menu com as novas informações
    }

    /**
     * Construtor da classe que inicializa a mudança de temas.
     */
    public MyDrawerBuilder() {
        themesChange = new ThemesChange();
    }

    @Override
    public Component getFooter() {
        return themesChange; // Retorna o componente de rodapé
    }

    /**
     * Cria e retorna os dados do cabeçalho do menu, incluindo o ícone do usuário e informações.
     * 
     * @return Dados do cabeçalho simples.
     */
    @Override
    public SimpleHeaderData getSimpleHeaderData() {
        AvatarIcon icon = new AvatarIcon(getClass().getResource("/Multimidia/imagens/cpanLogo.png"), 60, 60, 999);
        icon.setBorder(2); // Define a borda do ícone
        return new SimpleHeaderData()
                .setIcon(icon) // Define o ícone do cabeçalho
                .setTitle("Ra Ven") // Define o título do cabeçalho
                .setDescription("cpan.ufms.br") // Define a descrição do cabeçalho
                .setHeaderStyle(new SimpleHeaderStyle() {

                    @Override
                    public void styleTitle(JLabel label) {
                        label.putClientProperty(FlatClientProperties.STYLE, ""
                                + "[light]foreground:#FAFAFA"); // Estilo do título
                    }

                    @Override
                    public void styleDescription(JLabel label) {
                        label.putClientProperty(FlatClientProperties.STYLE, ""
                                + "[light]foreground:#E1E1E1"); // Estilo da descrição
                    }
                });
    }

    /**
     * Retorna os dados do rodapé do menu.
     * 
     * @return Dados do rodapé simples.
     */
    @Override
    public SimpleFooterData getSimpleFooterData() {
        return new SimpleFooterData(); // Retorna um rodapé simples
    }

    /**
     * Cria e retorna as opções de menu, incluindo ícones e validação.
     * 
     * @return Opções de menu simples.
     */
    @Override
    public SimpleMenuOption getSimpleMenuOption() {

        MenuItem items[] = new MenuItem[]{
            new Item.Label("Principal"), // Rótulo principal
            new Item("Estatísticas", "dashboard.svg"), // Item do painel
            new Item.Label("Menus"), // Rótulo para aplicativos web
            new Item("Administrador", "menu.svg"),
            new Item("Orientador", "menu.svg"),
            new Item("Secretária", "menu.svg"),
            new Item("Estagiário", "menu.svg"),
            new Item.Label("Cadastrar"),
            new Item("Estagiário", "user.svg"),
            new Item("Usuário", "user.svg"),
            new Item("Paciente", "user.svg"),
            new Item("Prontuario", "forms.svg"),
            new Item("Agenda", "calendar.svg"),
            new Item("Formulário de Atendimento", "forms.svg"),
            new Item.Label("Listagem"),
            new Item("Lista de Espera", "listing.svg"),
            new Item("Todos os Estagiários", "listing.svg"),
            new Item("Todos os Pacientes", "listing.svg"),
            new Item("Todos os Usuários", "listing.svg"),
            new Item.Label("Outros"),
            new Item("Configurações", "settings.svg"),
            new Item("Deslogar", "logout.svg") // Item de logout
            
//            new Item.Label("Secretária"), // Rótulo para aplicativos web
//            new Item("Menu", "forms.svg"),
//            new Item("Cadastrar Estagiário", "forms.svg"),
//            new Item("Cadastrar Usuário", "forms.svg"),
//            new Item("Cadastrar Prontuario", "forms.svg"),
//            new Item("Cadastrar Paciente", "forms.svg"),
//            new Item("Lista de Espera", "forms.svg"),
//            new Item("Cadastrar Agenda", "forms.svg"),
//            new Item("Formulário de Atendimento", "forms.svg"),
//            new Item("Deslogar", "logout.svg"), // Item de logout
//            
//            new Item.Label("Supervisor"), // Rótulo para aplicativos web
//            new Item("Menu", "forms.svg"),
//            new Item("Lista de Espera", "forms.svg"),
//            new Item("Cadastrar Agenda", "forms.svg"),
//            new Item("Formulário de Atendimento", "forms.svg"),
//            new Item("Deslogar", "logout.svg"), // Item de logout
//            
//            new Item.Label("Estagiário"), // Rótulo para aplicativos web
//            new Item("Menu", "forms.svg"),
//            new Item("Cadastrar Paciente", "forms.svg"),
//            new Item("Lista de Espera", "forms.svg"),
//            new Item("Cadastrar Agenda", "forms.svg"),
//            new Item("Formulário de Atendimento", "forms.svg"),
//            new Item("Deslogar", "logout.svg") // Item de logout
        };

        SimpleMenuOption simpleMenuOption = new SimpleMenuOption() {
            @Override
            public Icon buildMenuIcon(String path, float scale) {
                FlatSVGIcon icon = new FlatSVGIcon(path, scale); // Cria um ícone SVG
                FlatSVGIcon.ColorFilter colorFilter = new FlatSVGIcon.ColorFilter();
                // Define os filtros de cor para o ícone
                colorFilter.add(Color.decode("#969696"), Color.decode("#FAFAFA"), Color.decode("#969696"));
                icon.setColorFilter(colorFilter);
                return icon; // Retorna o ícone com o filtro aplicado
            }
        };

        simpleMenuOption.setMenuValidation(new MenuValidation() {

            /**
             * Verifica se a configuração do menu é válida com base nos índices de exibição.
             * 
             * @param index Índices do menu atual.
             * @param indexHide Índices do menu a ser ocultado.
             * @return true se o menu deve ser exibido, false caso contrário.
             */
            private boolean checkMenu(int[] index, int[] indexHide) {
                if (index.length == indexHide.length) {
                    for (int i = 0; i < index.length; i++) {
                        if (index[i] != indexHide[i]) {
                            return true; // Retorna verdadeiro se os índices não coincidirem
                        }
                    }
                    return false; // Retorna falso se todos os índices coincidirem
                }
                return true; // Retorna verdadeiro se o comprimento dos índices não coincidir
            }

            @Override
            public boolean menuValidation(int[] index) {
                if (user == null) {
                    return false; // Se o usuário não estiver definido, o menu não é válido
                }
                if (!user.isAdmin()) {
                    // Se o usuário não for administrador, verifica quais opções devem ser ocultadas
                    boolean act
                            // `Email`->`Gropu Read`->`Read 3`
                            = checkMenu(index, new int[]{1, 2, 3})
                            // `Email`->`Gropu Read`->`Read 5`
                            && checkMenu(index, new int[]{1, 2, 5})
                            // `Email`->`Group Read`->`Group Item->`Item 4`
                            && checkMenu(index, new int[]{1, 2, 2, 3})
                            // `Advanced UI`->`Owl Carousel`
                            && checkMenu(index, new int[]{4, 1})
                            // `Special Pages`
                            && checkMenu(index, new int[]{8});
                    return act; // Retorna o resultado da validação
                }
                return true; // Retorna verdadeiro se o usuário for administrador
            }
        });

        simpleMenuOption.setMenuStyle(new SimpleMenuStyle() {
            @Override
            public void styleMenuItem(JButton menu, int[] index) {
                // Define o estilo dos itens do menu
                menu.putClientProperty(FlatClientProperties.STYLE, ""
                        + "[light]foreground:#FAFAFA;"
                        + "arc:10"); // Estilo de cor e bordas arredondadas
            }

            @Override
            public void styleMenu(JComponent component) {
                // Define o estilo do componente do menu
                component.putClientProperty(FlatClientProperties.STYLE, ""
                        + "background:$Drawer.background"); // Define a cor de fundo
            }

            @Override
            public void styleLabel(JLabel label) {
                // Define o estilo dos rótulos do menu
                label.putClientProperty(FlatClientProperties.STYLE, ""
                        + "[light]foreground:darken(#FAFAFA,15%);"
                        + "[dark]foreground:darken($Label.foreground,30%)");
            }
        });
        
        // Define o comportamento dos eventos de menu
        simpleMenuOption.addMenuEvent(new MenuEvent() {
            @Override
            public void selected(MenuAction action, int[] index) {
                if (index.length == 1) {
                    switch (index[0]) {
                        case 0 -> FormManager.showForm(new TableEstatisticas());
                        case 1 -> FormManager.showForm(new MenuAdministrador());
                        case 2 -> FormManager.showForm(new MenuOrientador());
                        case 3 -> FormManager.showForm(new MenuSecretaria());
                        case 4 -> FormManager.showForm(new MenuEstagiario());
                        case 5 -> FormManager.showForm(new FormEstagiario());
                        case 6 -> FormManager.showForm(new FormUsuario());
                        case 7 -> FormManager.showForm(new FormPaciente());
                        case 8 -> FormManager.showForm(new FormProntuario());
                        case 9 -> FormManager.showForm(new FormAgenda());
                        case 10 -> FormManager.showForm(new FormAtendimento());
                        case 11 -> FormManager.showForm(new TableListaEspera());
                        case 12 -> FormManager.showForm(new PageWelcome());
                        case 13 -> FormManager.showForm(new PageWelcome());
                        case 14 -> FormManager.showForm(new PageWelcome());
                        case 15 -> FormManager.showForm(new PageWelcome());
                        case 16 -> {
                            MessagesAlert logout = new MessagesAlert();
                            logout.MessageAlertDesconectarOpcoes();
                        }
                        default -> System.out.println("Nenhum formulário correspondente encontrado.");
                    }
                } 
                // Verificar submenu
//                    else if (index.length == 2) {
//                    if (index[0] == 1) {
//                        if (index[1] == 0) {
//                            FormManager.showForm(new tempInboxForm()); // Exibe o formulário de Inbox
//                        } else if (index[1] == 1) {
//                            FormManager.showForm(new tempReadForm()); // Exibe o formulário de leitura
//                        }
//                    }
//                }
            }
        });

        // Configura as opções do menu
        simpleMenuOption.setMenus(items)
                .setBaseIconPath("Multimidia/menu") // Define o caminho base dos ícones
                .setIconScale(0.45f); // Define a escala dos ícones
        return simpleMenuOption; // Retorna as opções de menu
    }

    /**
     * Configura o painel do menu lateral (drawer).
     * 
     * @param drawerPanel O painel do drawer a ser configurado.
     */
    @Override
    public void build(DrawerPanel drawerPanel) {
        drawerPanel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Drawer.background"); // Define a cor de fundo do painel
    }

    /**
     * Retorna a largura do painel do menu lateral.
     * 
     * @return A largura do drawer.
     */
    @Override
    public int getDrawerWidth() {
        return 270; // Retorna a largura do drawer
    }
}
