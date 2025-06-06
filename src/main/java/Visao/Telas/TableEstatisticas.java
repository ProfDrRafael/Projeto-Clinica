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
import Visao.Components.SimpleForm;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import org.jfree.chart.ChartPanel;

public class TableEstatisticas extends SimpleForm {

    private GraficosRN estatisticasRN = new GraficosRN();
    private GraficosFiltrarRN graficosFiltrarRN = new GraficosFiltrarRN();
    private final String tamanhoGraficoMedidas = "width 465px, height 300px";
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
        setLayout(new MigLayout("wrap 1, insets 15", "[grow, fill]", "[]15[]15[]"));

        topPanel = new JPanel(new MigLayout("wrap 1, insets 0", "[grow, fill]", "[]15[]15[]"));
        topPanel.setOpaque(false);

        centerPanel = new JPanel(new MigLayout("wrap 1, insets 0", "[grow, fill]", "[]15[]"));
        centerPanel.setOpaque(false);

        bottomPanel = new JPanel(new MigLayout("wrap 1, insets 0", "[grow, fill]", "[]15[]"));
        bottomPanel.setOpaque(false);

        indicatorsPanel = new JPanel(new GridBagLayout());
        indicatorsPanel.setOpaque(false);

        topPanel.add(createPanelIndicadores(indicatorsPanel), "growx, wrap");

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

        CardIndicador cardTotalPacientes = new CardIndicador(Color.lightGray, Color.getHSBColor(201 / 360f, 0.63f, 0.42f));
        cardTotalPacientes.setData("Total de Pacientes Ativos", String.valueOf(GraficosRN.getTotalPacientesAtivos()));
        gbc.gridx = 0;
        gbc.gridy = 0;
        indicatorsPanel.add(cardTotalPacientes, gbc);

        CardIndicador cardTaxaComparecimento = new CardIndicador(Color.lightGray, Color.getHSBColor(178 / 360f, 0.24f, 0.40f));
        cardTaxaComparecimento.setData("Taxa Média de Comparecimento", String.format("%.2f%%", GraficosRN.getTaxaMediaComparecimento()));
        gbc.gridx = 1;
        gbc.gridy = 0;
        indicatorsPanel.add(cardTaxaComparecimento, gbc);

        CardIndicador cardPacientesListaEspera = new CardIndicador(Color.lightGray, Color.getHSBColor(266 / 360f, 0.50f, 0.16f));
        cardPacientesListaEspera.setData("Pacientes na Lista de Espera", String.valueOf(GraficosRN.getPacientesListaEspera()));
        gbc.gridx = 0;
        gbc.gridy = 1;
        indicatorsPanel.add(cardPacientesListaEspera, gbc);

        CardIndicador cardAtendimentosMes = new CardIndicador(Color.lightGray, Color.getHSBColor(343 / 360f, 0.62f, 0.36f));
        cardAtendimentosMes.setData("Total de Atendimentos (Mês Atual)", String.valueOf(GraficosRN.getTotalAtendimentosMesAtual()));
        gbc.gridx = 1;
        gbc.gridy = 1;
        indicatorsPanel.add(cardAtendimentosMes, gbc);

        CardIndicador cardEstagiarosAtivos = new CardIndicador(Color.lightGray, Color.getHSBColor(255 / 360f, 0.66f, 0.61f));
        cardEstagiarosAtivos.setData("Estagiários Ativos", (String) String.valueOf(GraficosRN.getEstagiariosAtivos()));
        gbc.gridx = 0;
        gbc.gridy = 2;
        indicatorsPanel.add(cardEstagiarosAtivos, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        indicatorsPanel.add(cardEstagiarosAtivos, gbc);

        return indicatorsPanel;
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

    public JPanel createContainerGraficos(JPanel centerPanel) {
        SessaoVO sessaoVO = new SessaoRN().buscarUltimaSessao();
        UsuarioVO usuarioVO = new UsuarioRN().buscarUsuarioPorEmail(sessaoVO.getEmail(), "Pesquisador");

        String[] opcoesGrafico = {
            "Todos", "Gênero", "Estado Civil", "Raça/Cor/Etnia",
            "Evolução Atendimentos", "Agendados vs Realizados",
            "Lista de Espera", "Tempo Médio Atendimento",
            "Paciente por Idade", "Paciente Comparecimento",
            "Ocupação das Salas"
        };

        centerPanel.add(new JLabel("Escolha seu tipo de gráfico:"));

        this.cbGrafico = new JComboBox<>(opcoesGrafico);

        if (usuarioVO != null) {
            EntityManager em = JPAUtil.getEntityManager();
            Pesquisador pesquisador = em.find(Pesquisador.class, usuarioVO.getId());
            em.close();

            List<Permissao> permissoes = new UsuarioRN().buscarRecursosPermitidos(pesquisador);
            Set<String> recursosPermitidos = permissoes.stream()
                    .filter(Permissao::getPermitido)
                    .map(Permissao::getRecurso)
                    .collect(Collectors.toSet());

            List<String> opcoesVisiveis = Arrays.stream(opcoesGrafico)
                    .filter(recurso -> recursosPermitidos.contains(recurso))
                    .collect(Collectors.toList());
            
            opcoesGrafico = opcoesVisiveis.toArray(new String[0]);

            this.cbGrafico = new JComboBox<>(opcoesGrafico);
        }

        centerPanel.add(cbGrafico, "growx, wrap");

        this.containerGrafico = new JPanel(new CardLayout());
        containerGrafico.setOpaque(false);

        Map<String, JPanel> painelGrafico = new HashMap<>();

        painelGrafico.put("Todos", createPainelTodos(opcoesGrafico));
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
