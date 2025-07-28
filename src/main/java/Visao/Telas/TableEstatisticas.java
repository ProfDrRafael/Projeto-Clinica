package Visao.Telas;

import Persistencia.Dao.JPAUtil;
import Persistencia.Entity.Permissao;
import Persistencia.Entity.Pesquisador;
import Regradenegocio.GraficosFiltrarRN;
import Regradenegocio.GraficosRN;
import Regradenegocio.SessaoRN;
import Regradenegocio.UsuarioRN;
import VO.SessaoVO;
import VO.UsuarioVO;
import Visao.Components.PanelTemplate;
import Visao.Components.CardIndicador;
import com.formdev.flatlaf.FlatClientProperties;
import jakarta.persistence.EntityManager;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import org.jfree.chart.ChartPanel;

public class TableEstatisticas extends PanelTemplate {

    private GraficosRN estatisticasRN = new GraficosRN();
    private GraficosFiltrarRN graficosFiltrarRN = new GraficosFiltrarRN();
    private final String tamanhoGraficoMedidas = "width 465px, height 400px";
    private final int larguraGrafico = 465;
    private final int alturaGrafico = 400;
    private final int larguraCheckbox = 150;
    private final int alturaCheckbox = 20;
    private final String tamanhoGraficoAutoAjuste = "growx";
    private final String graficoPadraoSelecionado = "Todos";
    private JPanel topPanel;
    private JPanel centerPanel;
    private JPanel bottomPanel;
    private JPanel indicatorsPanel;
    private JComboBox<String> cbGrafico;
    private JPanel containerGrafico;
    private JPanel conteinerGraficoIndividual;
    String[] opcoesGraficoPesquisador;
    List<String> cardsPermitidos;
    List<String> graficosPermitidos;

    public TableEstatisticas() {
        init();
    }

    @Override
    public void formRefresh() {
        init();
    }

    @Override
    public void formInitAndOpen() {
        System.out.println("init and open");
    }

    @Override
    public void formOpen() {
        System.out.println("Open");
    }

    private void init() {
        setLayout(new MigLayout("wrap 1, insets 15", "[grow, fill]", "[grow]15[grow]15[grow]"));

        topPanel = new JPanel(new MigLayout("wrap", "[grow, fill]", ""));

        topPanel.setOpaque(false);

        centerPanel = new JPanel(new MigLayout("wrap 1, insets 0", "[grow, fill]", "[]15[]"));
        centerPanel.setOpaque(false);

        bottomPanel = new JPanel(new MigLayout("wrap 1, insets 0", "[grow, fill]", "[]15[]"));
        bottomPanel.setOpaque(false);

        indicatorsPanel = new JPanel(new GridBagLayout());
        indicatorsPanel.setOpaque(false);

        filtrosGraficoPesquisador();

        topPanel.add(createPanelIndicadores(indicatorsPanel), "growx, growy, wrap");

        bottomPanel.add(createContainerGraficos(centerPanel), this.tamanhoGraficoAutoAjuste);

        add(topPanel, this.tamanhoGraficoAutoAjuste);
        add(centerPanel, this.tamanhoGraficoAutoAjuste);
        add(bottomPanel, this.tamanhoGraficoAutoAjuste);
    }

    private JPanel createStyledPanel(ChartPanel painelGrafico, String tipoGrafico) {
        painelGrafico.setBackground(Color.white);
        painelGrafico.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");

        conteinerGraficoIndividual = new JPanel(new BorderLayout());

        conteinerGraficoIndividual.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
        conteinerGraficoIndividual.setPreferredSize(new Dimension(larguraGrafico, alturaGrafico));

        conteinerGraficoIndividual.add(painelGrafico, BorderLayout.CENTER);

        if (!tipoGrafico.equals("")) {
            conteinerGraficoIndividual.add(graficosFiltrarRN.criarFiltro(tipoGrafico, bottomPanel, containerGrafico, estatisticasRN), BorderLayout.NORTH);
        }

        return conteinerGraficoIndividual;
    }

    private JPanel createPanelIndicadores(JPanel indicatorsPanel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1.0;

        Dimension tamanhoPadraoCard = new Dimension(250, 80);
        boolean mostrarTodos = (cardsPermitidos == null);

        List<CardIndicador> cards = new java.util.ArrayList<>();

        if (mostrarTodos || cardsPermitidos.contains("Total de Pacientes Ativos")) {
            cards.add(criaCard("Total de Pacientes Ativos", String.valueOf(GraficosRN.getTotalPacientesAtivos()), new Color(44, 62, 80), new Color(52, 73, 94), tamanhoPadraoCard));
        }

        if (mostrarTodos || cardsPermitidos.contains("Taxa Média de Comparecimento")) {
            cards.add(criaCard("Taxa Média de Comparecimento", String.format("%.2f%%", GraficosRN.getTaxaMediaComparecimento()), new Color(26, 188, 156), new Color(22, 160, 133), tamanhoPadraoCard));
        }

        if (mostrarTodos || cardsPermitidos.contains("Pacientes na Lista de Espera")) {
            cards.add(criaCard("Pacientes na Lista de Espera", String.valueOf(GraficosRN.getPacientesListaEspera()), new Color(155, 89, 182), new Color(142, 68, 173), tamanhoPadraoCard));
        }

        if (mostrarTodos || cardsPermitidos.contains("Total de Atendimentos (Mês Atual)")) {
            cards.add(criaCard("Total de Atendimentos (Mês Atual)", String.valueOf(GraficosRN.getTotalAtendimentosMesAtual()), new Color(230, 126, 34), new Color(211, 84, 0), tamanhoPadraoCard));
        }
        
        if (mostrarTodos || cardsPermitidos.contains("Estagiários Ativos")) {
            cards.add(criaCard("Estagiários Ativos", String.valueOf(GraficosRN.getEstagiariosAtivos()), new Color(52, 152, 219), new Color(41, 128, 185), tamanhoPadraoCard));
        }

        int y = 0;
        for (int i = 0; i < cards.size(); i++) {
            CardIndicador card = cards.get(i);
            gbc.gridy = y;

            boolean isLast = (i == cards.size() - 1);
            boolean shouldSpan = (cards.size() % 2 == 1 && isLast);

            if (shouldSpan) {
                gbc.gridx = 0;
                gbc.gridwidth = 2;
            } else {
                gbc.gridx = i % 2;
                gbc.gridwidth = 1;
            }

            indicatorsPanel.add(card, gbc);

            if (!shouldSpan && i % 2 == 1) {
                y++;
            } else if (shouldSpan) {
                y++;
            }
        }

        return indicatorsPanel;
    }

    private CardIndicador criaCard(String titulo, String valor, Color corPrimaria, Color corSecundaria, Dimension tamanho) {
        CardIndicador card = new CardIndicador(corPrimaria, corSecundaria);
        card.setData(titulo, valor);
        card.setPreferredSize(tamanho);
        return card;
    }

    public JPanel createPainelTodos(String[] opcoesGrafico) {
        Set<String> opcoesPermitidas = new HashSet<>(Arrays.asList(opcoesGrafico));

        JPanel painelTodos = new JPanel();
        painelTodos.setLayout(new BoxLayout(painelTodos, BoxLayout.Y_AXIS));
        painelTodos.setOpaque(false);

        if (opcoesPermitidas.contains("Gênero")) {
            painelTodos.add(createStyledPanel(estatisticasRN.createGenderPieChartPanel(null), ""));
        }

        if (opcoesPermitidas.contains("Estado Civil")) {
            painelTodos.add(createStyledPanel(estatisticasRN.createEstadoCivilPieChartPanel(null), ""));
        }

        if (opcoesPermitidas.contains("Raça/Cor/Etnia")) {
            painelTodos.add(createStyledPanel(estatisticasRN.createRacaCorEtniaPieChartPanel(null), ""));
        }

        if (opcoesPermitidas.contains("Evolução Atendimentos")) {
            painelTodos.add(createStyledPanel(estatisticasRN.createEvolucaoAtendimentoChartPanel(), ""));
        }

        if (opcoesPermitidas.contains("Agendados vs Realizados")) {
            painelTodos.add(createStyledPanel(estatisticasRN.createAgendadosVSRealizadosChartPanel(), ""));
        }

        if (opcoesPermitidas.contains("Lista de Espera")) {
            painelTodos.add(createStyledPanel(estatisticasRN.createPacientesListaEsperaChartPanel(), ""));
        }

        if (opcoesPermitidas.contains("Tempo Médio Atendimento")) {
            painelTodos.add(createStyledPanel(estatisticasRN.createTempoMedioAtendimentoChartPanel(), ""));
        }

        if (opcoesPermitidas.contains("Paciente por Idade")) {
            painelTodos.add(createStyledPanel(estatisticasRN.createPacienteIdadeBarChartPanel(), ""));
        }

        if (opcoesPermitidas.contains("Paciente Comparecimento")) {
            painelTodos.add(createStyledPanel(estatisticasRN.createPacienteComparecimentoBarChartPanel(), ""));
        }

        if (opcoesPermitidas.contains("Ocupação das Salas")) {
            painelTodos.add(createStyledPanel(estatisticasRN.createSalaOcupacaoBarChartPanel(), ""));
        }

        return painelTodos;
    }

    public void filtrosGraficoPesquisador() {
        SessaoVO sessaoVO = new SessaoRN().buscarUltimaSessao();
        UsuarioVO usuarioVO = new UsuarioRN().buscarUsuarioPorEmail(sessaoVO.getEmail(), "Pesquisador");

        if (usuarioVO != null) {
            EntityManager em = JPAUtil.getEntityManager();
            Pesquisador pesquisador = em.find(Pesquisador.class, usuarioVO.getId());
            em.close();

            List<Permissao> permissoes = new UsuarioRN().buscarRecursosPermitidos(pesquisador);
            Set<String> recursosPermitidos = permissoes.stream()
                    .filter(Permissao::getPermitido)
                    .map(Permissao::getRecurso)
                    .collect(Collectors.toSet());

            System.out.println("Recursos permitidos:");
            recursosPermitidos.forEach(System.out::println);

            String[] todosCards = {
                "Total de Pacientes Ativos",
                "Taxa Média de Comparecimento",
                "Pacientes na Lista de Espera",
                "Total de Atendimentos (Mês Atual)",
                "Estagiários Ativos"
            };

            String[] todosGraficos = {
                "Gênero", "Estado Civil", "Raça/Cor/Etnia",
                "Evolução Atendimentos", "Agendados vs Realizados",
                "Lista de Espera", "Tempo Médio Atendimento",
                "Paciente por Idade", "Paciente Comparecimento",
                "Ocupação das Salas"
            };

            cardsPermitidos = Arrays.stream(todosCards)
                    .filter(recursosPermitidos::contains)
                    .collect(Collectors.toList());

            graficosPermitidos = Arrays.stream(todosGraficos)
                    .filter(recursosPermitidos::contains)
                    .collect(Collectors.toList());

            List<String> opcoesVisiveis = new java.util.ArrayList<>();
            opcoesVisiveis.add("Todos");
            opcoesVisiveis.addAll(graficosPermitidos);

            opcoesGraficoPesquisador = opcoesVisiveis.toArray(new String[0]);
        } else {
            opcoesGraficoPesquisador = new String[]{
                "Todos",
                "Gênero", "Estado Civil", "Raça/Cor/Etnia",
                "Evolução Atendimentos", "Agendados vs Realizados",
                "Lista de Espera", "Tempo Médio Atendimento",
                "Paciente por Idade", "Paciente Comparecimento",
                "Ocupação das Salas"
            };
        }

        this.cbGrafico = new JComboBox<>(opcoesGraficoPesquisador);

    }

    public JPanel createContainerGraficos(JPanel centerPanel) {

        centerPanel.add(cbGrafico, "growx, wrap");

        this.containerGrafico = new JPanel(new CardLayout());
        containerGrafico.setOpaque(false);

        Map<String, JPanel> painelGrafico = new HashMap<>();

        painelGrafico.put("Todos", createPainelTodos(opcoesGraficoPesquisador));
        painelGrafico.put("Gênero", createStyledPanel(estatisticasRN.createGenderPieChartPanel(null), "Gênero"));
        painelGrafico.put("Estado Civil", createStyledPanel(estatisticasRN.createEstadoCivilPieChartPanel(null), "Estado Civil"));
        painelGrafico.put("Raça/Cor/Etnia", createStyledPanel(estatisticasRN.createRacaCorEtniaPieChartPanel(null), "Raça/Cor/Etnia"));
        painelGrafico.put("Evolução Atendimentos", createStyledPanel(estatisticasRN.createEvolucaoAtendimentoChartPanel(), "Evolução Atendimentos"));
        painelGrafico.put("Agendados vs Realizados", createStyledPanel(estatisticasRN.createAgendadosVSRealizadosChartPanel(), "Agendados vs Realizados"));
        painelGrafico.put("Lista de Espera", createStyledPanel(estatisticasRN.createPacientesListaEsperaChartPanel(), "Lista de Espera"));
        painelGrafico.put("Tempo Médio Atendimento", createStyledPanel(estatisticasRN.createTempoMedioAtendimentoChartPanel(), "Tempo Médio Atendimento"));
        painelGrafico.put("Paciente por Idade", createStyledPanel(estatisticasRN.createPacienteIdadeBarChartPanel(), "Paciente por Idade"));
        painelGrafico.put("Paciente Comparecimento", createStyledPanel(estatisticasRN.createPacienteComparecimentoBarChartPanel(), "Paciente Comparecimento"));
        painelGrafico.put("Ocupação das Salas", createStyledPanel(estatisticasRN.createSalaOcupacaoBarChartPanel(), "Ocupação das Salas"));

        for (Map.Entry<String, JPanel> entry : painelGrafico.entrySet()) {
            containerGrafico.add(entry.getValue(), entry.getKey());
        }

        ((CardLayout) containerGrafico.getLayout()).show(containerGrafico, graficoPadraoSelecionado);

        cbGrafico.addActionListener(e -> {
            String graficoSelecionado = (String) cbGrafico.getSelectedItem();

            if (graficoSelecionado != null) {
                ((CardLayout) containerGrafico.getLayout()).show(containerGrafico, graficoSelecionado);

                bottomPanel.removeAll();

                if ("Todos".equals(graficoSelecionado)) {
                    bottomPanel.add(containerGrafico, tamanhoGraficoAutoAjuste);
                } else if ("Paciente por Idade".equals(graficoSelecionado)) {
                    bottomPanel.add(containerGrafico, "width 465px, height 600px");
                } else {
                    bottomPanel.add(containerGrafico, this.tamanhoGraficoMedidas);
                }

                bottomPanel.revalidate();
                bottomPanel.repaint();
            }
        });

        return containerGrafico;
    }

}
