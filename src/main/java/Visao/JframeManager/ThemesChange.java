package Visao.JframeManager;

import com.formdev.flatlaf.FlatClientProperties; // Propriedades do cliente para o Flat Look and Feel
import com.formdev.flatlaf.FlatLaf; // Classe base para o Flat Look and Feel
import com.formdev.flatlaf.extras.FlatAnimatedLafChange; // Para animações ao mudar o Look and Feel
import com.formdev.flatlaf.extras.FlatSVGIcon; // Para ícones SVG
import com.formdev.flatlaf.themes.FlatMacDarkLaf; // Tema escuro para Mac
import com.formdev.flatlaf.themes.FlatMacLightLaf; // Tema claro para Mac
import java.awt.Color; // Classe para manipulação de cores
import java.awt.EventQueue; // Para manipulação de eventos na fila de eventos
import javax.swing.Icon; // Interface para ícones
import javax.swing.JButton; // Classe para botões
import javax.swing.JPanel; // Classe base para criar painéis
import net.miginfocom.swing.MigLayout; // Layout de gerenciamento de posição baseado em regras

/**
 * Classe responsável pela mudança de temas (claro/escuro) na aplicação.
 *
 * @author Raven
 */
public class ThemesChange extends JPanel {

    /**
     * Construtor da classe ThemesChange. Chama o método de inicialização.
     */
    public ThemesChange() {
        init(); // Inicializa os componentes do painel
    }

    /**
     * Cria um ícone a partir de um arquivo SVG, aplicando um filtro de cor.
     *
     * @param path O caminho do arquivo SVG
     * @return O ícone criado
     */
    private Icon createIcon(String path) {
        FlatSVGIcon icon = new FlatSVGIcon(path, 0.7f); // Cria um ícone SVG com escala 0.7
        FlatSVGIcon.ColorFilter colorFilter = new FlatSVGIcon.ColorFilter(); // Cria um filtro de cor
        colorFilter.add(Color.decode("#969696"), Color.decode("#FAFAFA"), Color.decode("#969696")); // Define as cores para o filtro
        icon.setColorFilter(colorFilter); // Aplica o filtro de cor ao ícone
        return icon; // Retorna o ícone com o filtro aplicado
    }

    /**
     * Método que realiza a configuração inicial do painel de mudança de temas.
     */
    private void init() {
        putClientProperty(FlatClientProperties.STYLE, "" // Define a propriedade de estilo do cliente
                + "background:null"); // Define o fundo como nulo
        setLayout(new MigLayout("al center", "[fill,200]", "fill")); // Define o layout do painel usando MigLayout

        // Cria um painel interno com layout MigLayout
        JPanel panel = new JPanel(new MigLayout("fill", "[fill]10[fill]", "fill"));
        panel.putClientProperty(FlatClientProperties.STYLE, "" // Define propriedades de estilo para o painel interno
                + "arc:999;" // Define bordas arredondadas
                + "background:darken($Drawer.background,5%)"); // Define a cor de fundo escurecida

        // Cria botões para alterar entre os modos claro e escuro
        JButton buttonLight = new JButton(createIcon("Multimidia/icon/light.svg")); // Botão para modo claro
        JButton buttonDark = new JButton(createIcon("Multimidia/icon/dark.svg")); // Botão para modo escuro

        // Adiciona ouvintes de ação para os botões
        buttonLight.addActionListener(e -> changeMode(false)); // Muda para modo claro
        buttonDark.addActionListener(e -> changeMode(true)); // Muda para modo escuro

        // Define propriedades de estilo para o botão de modo claro
        buttonLight.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:999;" // Define bordas arredondadas
                + "[dark]background:null;" // Fundo nulo no tema escuro
                + "[light]background:$Drawer.background;" // Fundo do tema claro
                + "borderWidth:0;" // Remove borda
                + "focusWidth:0;" // Remove largura do foco
                + "innerFocusWidth:0;" // Remove largura do foco interno
                + "margin:3,5,3,5"); // Define margens

        // Define propriedades de estilo para o botão de modo escuro
        buttonDark.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:999;" // Define bordas arredondadas
                + "[dark]background:$Drawer.background;" // Fundo do tema escuro
                + "[light]background:null;" // Fundo nulo no tema claro
                + "borderWidth:0;" // Remove borda
                + "focusWidth:0;" // Remove largura do foco
                + "innerFocusWidth:0;" // Remove largura do foco interno
                + "margin:3,5,3,5"); // Define margens

        // Adiciona os botões ao painel interno
        panel.add(buttonDark); // Adiciona o botão escuro
        panel.add(buttonLight); // Adiciona o botão claro

        // Adiciona o painel interno ao painel principal
        add(panel);
    }

    /**
     * Muda entre os modos claro e escuro.
     *
     * @param dark Indica se o modo escuro deve ser ativado
     */
    private void changeMode(boolean dark) {
        // Verifica se o modo atual é diferente do desejado
        if (dark != FlatLaf.isLafDark()) {
            // Se o modo escuro deve ser ativado
            if (dark) {
                EventQueue.invokeLater(() -> { // Executa na fila de eventos
                    FlatAnimatedLafChange.showSnapshot(); // Mostra uma imagem instantânea antes da mudança
                    FlatMacDarkLaf.setup(); // Configura o tema escuro
                    FlatLaf.updateUI(); // Atualiza a interface do usuário
                    FormManager.updateTempFormUI(); // Atualiza a interface dos formulários temporários
                    FlatAnimatedLafChange.hideSnapshotWithAnimation(); // Esconde a imagem instantânea com animação
                });
            } else { // Se o modo claro deve ser ativado
                EventQueue.invokeLater(() -> { // Executa na fila de eventos
                    FlatAnimatedLafChange.showSnapshot(); // Mostra uma imagem instantânea antes da mudança
                    FlatMacLightLaf.setup(); // Configura o tema claro
                    FlatLaf.updateUI(); // Atualiza a interface do usuário
                    FormManager.updateTempFormUI(); // Atualiza a interface dos formulários temporários
                    FlatAnimatedLafChange.hideSnapshotWithAnimation(); // Esconde a imagem instantânea com animação
                });
            }
        }
    }
}
