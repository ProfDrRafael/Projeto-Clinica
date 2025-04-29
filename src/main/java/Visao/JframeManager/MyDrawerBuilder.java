package Visao.JframeManager;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Color;
import java.awt.Component;
import javax.swing.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import Visao.Telas.*;
import Visao.Utils.MessagesAlert;
import raven.swing.AvatarIcon;
import VO.UsuarioVO;
import Visao.Components.SimpleForm;

public class MyDrawerBuilder extends SimpleDrawerBuilder {

    private UsuarioVO user; // Modelo do usuário atual
    private final ThemesChange themesChange; // Classe para mudança de temas
    private Set<String> allowedMenuNames; // Nomes de menu permitidos
    private List<String> menuNames; // Nomes de todos os menus sem labels

    /**
     * Define o usuário atual e atualiza o cabeçalho do menu com o nome do usuário.
     *
     *
     * @param user O usuário a ser definido.
     */
    public void setUser(UsuarioVO user) {
        if (user == null) {
            System.out.println("Usuário nulo ao configurar o menu.");
        } else {
            System.out.println("Usuário configurado: " + user.getNomeCompleto() + ", tipo: " + user.getTipo());
        }
        this.user = user;
        SimpleHeaderData headerData = header.getSimpleHeaderData();
        headerData.setTitle(user != null ? user.getNomeCompleto() : "Usuário não configurado");
        header.setSimpleHeaderData(headerData);
        rebuildMenu();
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
     * Cria e retorna os dados do cabeçalho do menu, incluindo o ícone do usuário e
     * informações.
     *
     * Cria e retorna os dados do cabeçalho do menu, incluindo o ícone do usuário e informações.
     *
     * @return Dados do cabeçalho simples.
     */
    @Override
    public SimpleHeaderData getSimpleHeaderData() {
        AvatarIcon icon = new AvatarIcon(getClass().getResource("/Multimidia/imagens/cpanLogo.png"), 60, 60, 999);
        icon.setBorder(2); // Define a borda do ícone
        String nomeUsuario = user != null ? user.getNomeCompleto() : "Usuário";
        String emailUsuario = user != null ? user.getEmail() : "email@dominio.com";
        return new SimpleHeaderData()
                .setIcon(icon) // Define o ícone do cabeçalho
                .setTitle(nomeUsuario) // Define o título do cabeçalho
                .setDescription(emailUsuario) // Define a descrição do cabeçalho
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
     *
     * @return Opções de menu simples.
     */
    @Override
    public SimpleMenuOption getSimpleMenuOption() {

        // Lista para armazenar os nomes dos menus sem labels
        menuNames = new ArrayList<>();

        // Definição dos itens de menu
        MenuItem items[] = new MenuItem[]{
            new Item.Label("Principal"), // Label
            new Item("Estatísticas", "dashboard.svg"), // index 0
            new Item("Calendário", "calendar.svg"), // index 1
            new Item.Label("Menus"), // Label
            new Item("Administrador", "menu.svg"), // index 2
            new Item("Orientador", "menu.svg"), // index 3
            new Item("Secretária", "menu.svg"), // index 4
            new Item("Estagiário", "menu.svg"), // index 5
            new Item.Label("Cadastrar"), // Label
            new Item("Vincular Estagiário", "user.svg"), // index 6
            new Item("Usuário", "user.svg"), // index 7
            new Item("Paciente", "user.svg"), // index 8
            new Item("Prontuario", "forms.svg"), // index 9
            new Item("Agenda", "calendar.svg"), // index 10
            new Item("Formulário de Atendimento", "forms.svg"), // index 11
            new Item.Label("Listagem"), // Label
            new Item("Lista de Espera Geral", "listing.svg"), // index 11
            new Item("Lista de Espera Especifica", "listing.svg"), // index 12
            new Item("Agendamentos", "listing.svg"), // index 13
            new Item("Atendimentos", "listing.svg"), // index 14
            new Item("Todos os Estagiários", "listing.svg"), // index 15
            new Item("Todos os Pacientes", "listing.svg"), // index 16
            new Item("Todos os Usuários", "listing.svg"), // index 17
            new Item("Todos os Pacientes Inativos", "listing.svg"), // index 18
            new Item("Todos os Estagiários Inativos", "listing.svg"), // index 19
            new Item("Todos os Usuários Inativos", "listing.svg"), // index 20
            new Item.Label("Outros"), // Label
            new Item("Configurações", "settings.svg"), // index 21
            new Item("Deslogar", "logout.svg") // index 22
        };

        // Construir a lista de nomes de menu sem labels
        for (MenuItem item : items) {
            if (item instanceof Item.Label) {
                continue;
            } else if (item instanceof Item) {
                menuNames.add(((Item) item).getName());
            }
        }

        // Definir os nomes de menu permitidos com base no tipo de usuário
        allowedMenuNames = new HashSet<>();
        if (user != null) {
            System.out.println(user.getTipo());
            allowedMenuNames = getAllowedMenuNames(user.getTipo());
        } else {
            allowedMenuNames.add("Deslogar");
            System.out.println("Usuário não definido. Apenas 'Deslogar' será exibido.");
        }

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
            @Override
            public boolean menuValidation(int[] index) {
                if (user == null) {
                    return false; // Se o usuário não estiver definido, não mostra nenhum item
                }
                String menuName = menuNames.get(index[0]);
                return allowedMenuNames.contains(menuName);
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


        simpleMenuOption.addMenuEvent(new MenuEvent() {
            @Override
            public void selected(MenuAction action, int[] index) {
                String menuName = menuNames.get(index[0]);

                FormManager.showForm(new PageProgressBar(() -> FormManager.showForm(getFormByName(menuName))));
            }
        });

        // Configura as opções do menu
        simpleMenuOption.setMenus(items)
                .setBaseIconPath("Multimidia/menu") // Define o caminho base dos ícones
                .setIconScale(0.45f); // Define a escala dos ícones
        
        
        
        return simpleMenuOption; // Retorna as opções de menu
    }
    
    private SimpleForm getFormByName(String menuName) {
        return switch (menuName) {
            case "Estatísticas" -> new TableEstatisticas();
            case "Administrador" -> new MenuAdministrador();
            case "Orientador" -> new MenuOrientador();
            case "Secretária" -> new MenuSecretaria();
            case "Estagiário" -> new MenuEstagiario();
            case "Estagiário Cadastro" -> new FormEstagiario();
            case "Usuário" -> new FormUsuario();
            case "Paciente" -> new FormPaciente();
            case "Prontuário" -> new FormProntuario();
            case "Agenda" -> new FormAgenda();
            case "Calendário" -> new PageCalendario();
            case "Formulário de Atendimento" -> new FormAtendimento();
            case "Lista de Espera Geral" -> new TableListaEsperaGeral();
            case "Lista de Espera Específica" -> new TableListaEsperaEspecifica();
            case "Agendamentos" -> new TableListaAgenda();
            case "Atendimentos" -> new TableListaAtendimento();
            case "Todos os Estagiários" -> new TableListaEstagiarios();
            case "Todos os Pacientes" -> new TableListaPacientes();
            case "Toos os Usuários" -> new TableListaUsuarios();
            case "Todos os Pacientes Inativos" -> new TableListaPacientesInativos();
            case "Todos os Estagiários Inativos" -> new TableListaEstagiariosInativos();
            case "Todos os Usuários Inativos" -> new TableListaUsuariosInativos();
            case "Configurações" -> new PageWelcome();
            default -> new PageWelcome();
        };
    }
    

    /**
     * Configura o painel do menu lateral (drawer).
     *
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
     *
     * @return A largura do drawer.
     */
    @Override
    public int getDrawerWidth() {
        return 270; // Retorna a largura do drawer
    }

    @Override
    public void rebuildMenu() {
        System.out.println("Personalizando o rebuild do menu...");
        if (user != null) {
            System.out.println("Reconstruindo menu para o usuário: " + user.getNomeCompleto());
            allowedMenuNames = getAllowedMenuNames(user.getTipo());
        } else {
            allowedMenuNames = new HashSet<>();
            allowedMenuNames.add("Deslogar");
            System.out.println("Usuário não definido. Apenas 'Deslogar' será exibido.");
        }
        super.rebuildMenu(); // Opcional, se desejar manter o comportamento original
    }

    /**
     * Método que retorna os nomes dos menus permitidos para um determinado tipo de usuário.
     *
     * @param userType O tipo de usuário.
     * @return Um conjunto com os nomes dos menus permitidos.
     */
    private Set<String> getAllowedMenuNames(String userType) {
        Set<String> allowedMenus = new HashSet<>();
        switch (userType) {
            case "Administrador" -> allowedMenus.addAll(menuNames);
            case "Secretaria" -> {
                allowedMenus.add("Estatísticas");
                allowedMenus.add("Secretária");
                allowedMenus.add("Estagiário Cadastro");
                allowedMenus.add("Usuário");
                allowedMenus.add("Paciente");
                allowedMenus.add("Agenda");
                allowedMenus.add("Calendário");
                allowedMenus.add("Todos os Estagiários");
                allowedMenus.add("Todos os Pacientes");
                allowedMenus.add("Todos os Usuários");
                allowedMenus.add("Deslogar");
            }
            case "Orientador" -> {
                allowedMenus.add("Estatísticas");
                allowedMenus.add("Orientador");
                allowedMenus.add("Agenda");
                allowedMenus.add("Calendário");
                allowedMenus.add("Formulário de Atendimento");
                allowedMenus.add("Lista de Espera Geral");
                allowedMenus.add("Lista de Espera Especifica");
                allowedMenus.add("Deslogar");
            }
            case "Estagiario" -> {
                allowedMenus.add("Estatísticas");
                allowedMenus.add("Estagiário");
                allowedMenus.add("Paciente");
                allowedMenus.add("Prontuario");
                allowedMenus.add("Agenda");
                allowedMenus.add("Calendário");
                allowedMenus.add("Formulário de Atendimento");
                allowedMenus.add("Lista de Espera Geral");
                allowedMenus.add("Lista de Espera Especifica");
                allowedMenus.add("Deslogar");
            }
            default -> allowedMenus.add("Deslogar");
        }
        return allowedMenus;
    }
}
