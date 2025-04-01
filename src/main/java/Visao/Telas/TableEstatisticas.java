package Visao.Telas;

import Regradenegocio.EstatisticasRN;
import Visao.Components.SimpleForm;
import Visao.Components.CardIndicador;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

public class TableEstatisticas extends SimpleForm {

    private EstatisticasRN estatisticasRN = new EstatisticasRN();

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
        String chartSize = "width 465px, height 300px";
        String chartSizeDefault = "growx";

        setLayout(new MigLayout("wrap 1, insets 15", "[grow, fill]", "[]15[]15[]"));

        JPanel topPanel = new JPanel(new MigLayout("wrap 1, insets 0", "[grow, fill]", "[]15[]15[]"));
        topPanel.setOpaque(false);

        JPanel centerPanel = new JPanel(new MigLayout("wrap 1, insets 0", "[grow, fill]", "[]15[]"));
        centerPanel.setOpaque(false);

        JPanel bottomPanel = new JPanel(new MigLayout("wrap 1, insets 0", "[grow, fill]", "[]15[]"));
        bottomPanel.setOpaque(false);

        JPanel indicatorsPanel = new JPanel(new GridBagLayout());
        indicatorsPanel.setOpaque(false);

        topPanel.add(createPanelIndicadores(indicatorsPanel), "growx, wrap");
        
        bottomPanel.add(createContainerGraficos(centerPanel), chartSize);

        add(topPanel, chartSizeDefault);
        add(centerPanel, chartSizeDefault);
        add(bottomPanel, chartSizeDefault);
    }
    
    /**
     * Wraps the provided chart panel in a styled JPanel using a BorderLayout.
     *
     * @param chartPanel the chart panel to wrap
     * @param width desired width
     * @param height desired height
     * @return a styled JPanel containing the chart panel
     */
    private JPanel createStyledPanel(JPanel chartPanel, int width, int height) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
        panel.add(chartPanel, BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(width, height));
        return panel;
    }
    
    private JPanel createPanelIndicadores(JPanel indicatorsPanel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1.0;

        CardIndicador cardTotalPacientes = new CardIndicador(Color.lightGray, Color.getHSBColor(201 / 360f, 0.63f, 0.42f));
        cardTotalPacientes.setData("Total de Pacientes Ativos", String.valueOf(EstatisticasRN.getTotalPacientesAtivos()));
        gbc.gridx = 0;
        gbc.gridy = 0;
        indicatorsPanel.add(cardTotalPacientes, gbc);

        CardIndicador cardTaxaComparecimento = new CardIndicador(Color.lightGray, Color.getHSBColor(178 / 360f, 0.24f, 0.40f));
        cardTaxaComparecimento.setData("Taxa Média de Comparecimento", String.format("%.2f%%", EstatisticasRN.getTaxaMediaComparecimento()));
        gbc.gridx = 1;
        gbc.gridy = 0;
        indicatorsPanel.add(cardTaxaComparecimento, gbc);

        CardIndicador cardPacientesListaEspera = new CardIndicador(Color.lightGray, Color.getHSBColor(266 / 360f, 0.50f, 0.16f));
        cardPacientesListaEspera.setData("Pacientes na Lista de Espera", String.valueOf(EstatisticasRN.getPacientesListaEspera()));
        gbc.gridx = 0;
        gbc.gridy = 1;
        indicatorsPanel.add(cardPacientesListaEspera, gbc);

        CardIndicador cardAtendimentosMes = new CardIndicador(Color.lightGray, Color.getHSBColor(343 / 360f, 0.62f, 0.36f));
        cardAtendimentosMes.setData("Total de Atendimentos (Mês Atual)", String.valueOf(EstatisticasRN.getTotalAtendimentosMesAtual()));
        gbc.gridx = 1;
        gbc.gridy = 1;
        indicatorsPanel.add(cardAtendimentosMes, gbc);

        CardIndicador cardEstagiarosAtivos = new CardIndicador(Color.lightGray, Color.getHSBColor(255 / 360f, 0.66f, 0.61f));
        cardEstagiarosAtivos.setData("Estagiários Ativos", (String) String.valueOf(EstatisticasRN.getEstagiariosAtivos()));
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
    
    public JPanel createContainerGraficos(JPanel centerPanel){
        String[] opcoesGrafico = {
            "Gênero", "Estado Civil", "Raça/Cor/Etnia",
            "Evolução Atendimentos", "Agendados vs Realizados",
            "Lista de Espera", "Tempo Médio Atendimento",
            "Paciente por Idade", "Paciente Comparecimento",
            "Ocupação das Salas", "Distribuição Atendimentos"
        };

        JComboBox<String> cbGrafico = new JComboBox<>(opcoesGrafico);
        centerPanel.add(cbGrafico, "growx, wrap");

        JPanel containerGrafico = new JPanel(new CardLayout());
        containerGrafico.setOpaque(false);

        Map<String, JPanel> painelGrafico = new HashMap<>();

        painelGrafico.put("Gênero", createStyledPanel(estatisticasRN.createGenderPieChartPanel(), 310, 290));
        painelGrafico.put("Estado Civil", createStyledPanel(estatisticasRN.createEstadoCivilPieChartPanel(), 310, 290));
        painelGrafico.put("Raça/Cor/Etnia", createStyledPanel(estatisticasRN.createRacaCorEtniaPieChartPanel(), 310, 290));
        painelGrafico.put("Evolução Atendimentos", createStyledPanel(estatisticasRN.createEvolucaoAtendimentoChartPanel(), 465, 300));
        painelGrafico.put("Agendados vs Realizados", createStyledPanel(estatisticasRN.createAgendadosVSRealizadosChartPanel(), 465, 300));
        painelGrafico.put("Lista de Espera", createStyledPanel(estatisticasRN.createPacientesListaEsperaChartPanel(), 465, 145));
        painelGrafico.put("Tempo Médio Atendimento", createStyledPanel(estatisticasRN.createTempoMedioAtendimentoChartPanel(), 465, 145));
        painelGrafico.put("Paciente por Idade", createStyledPanel(estatisticasRN.createPacienteIdadeBarChartPanel(), 465, 145));
        painelGrafico.put("Paciente Comparecimento", createStyledPanel(estatisticasRN.createPacienteComparecimentoBarChartPanel(), 465, 145));
        painelGrafico.put("Ocupação das Salas", createStyledPanel(estatisticasRN.createSalaOcupacaoBarChartPanel(), 465, 200));
        painelGrafico.put("Distribuição Atendimentos", createStyledPanel(estatisticasRN.createDistribuicaoAtendimentosBarChartPanel(), 465, 200));

        for (Map.Entry<String, JPanel> entry : painelGrafico.entrySet()) {
            containerGrafico.add(entry.getValue(), entry.getKey());
        }

        String defaultGraph = "Gênero"; 
        ((CardLayout) containerGrafico.getLayout()).show(containerGrafico, defaultGraph);

        cbGrafico.addActionListener(e -> {
            String selectedGraph = (String) cbGrafico.getSelectedItem();
            
            if (selectedGraph != null) {
                ((CardLayout) containerGrafico.getLayout()).show(containerGrafico, selectedGraph);
            }
        });
        
        return containerGrafico;
    }

}
