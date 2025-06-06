package Regradenegocio;

import Persistencia.Dao.GraficosDAO;
import Persistencia.Entity.Atendimento;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.text.AttributedString;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.PieSectionEntity;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

public class GraficosRN {

    private GraficosDAO tabelaEstatisticaDAO = new GraficosDAO();
    private JFreeChart graficoGeneroDados;
    private JFreeChart graficoEstadoCivilDados;
    private JFreeChart graficoRacaCorEtniaDados;
    private JFreeChart graficoEvolucaoAtendimentosDados;
    private JFreeChart graficoAgendadosRealizadosDados;
    private JFreeChart graficoListaEsperaDados;
    private JFreeChart graficoTempoMedioDados;
    private JFreeChart graficoPacienteIdadeDados;
    private JFreeChart graficoPacienteComparecimentoDados;
    private JFreeChart graficoOcupacaoSalaDados;
    private JFreeChart graficoDistribuicaoAtendimentoDados;
    private JFreeChart graficoTotalPacientesAtivosDados;
    private JFreeChart graficoTaxaComparecimentoDados;
    private JFreeChart graficoPacientesListaEsperaDados;
    private JFreeChart graficoTotalAtendimentosMesAtualDados;
    private JFreeChart graficoEstagiariosAtivosDados;

    public void themeStylization(JFreeChart chart) {
        String fontName = "Inter";
        StandardChartTheme theme = (StandardChartTheme) StandardChartTheme.createJFreeTheme();

        theme.setTitlePaint(Color.decode("#222222"));
        theme.setExtraLargeFont(new Font(fontName, Font.PLAIN, 16));
        theme.setLargeFont(new Font(fontName, Font.BOLD, 14));
        theme.setRegularFont(new Font(fontName, Font.PLAIN, 12));
        theme.setRangeGridlinePaint(Color.decode("#E0E0E0"));
        theme.setPlotBackgroundPaint(Color.white);
        theme.setChartBackgroundPaint(Color.white);
        theme.setAxisOffset(new RectangleInsets(5, 5, 5, 5));
        theme.setBarPainter(new StandardBarPainter());
        theme.setAxisLabelPaint(Color.decode("#444444"));
        theme.apply(chart);

        chart.setTextAntiAlias(true);
        chart.setAntiAlias(true);

        Plot plot = chart.getPlot();

        if (plot instanceof CategoryPlot) {
            CategoryPlot categoryPlot = (CategoryPlot) plot;
            categoryPlot.setOutlineVisible(false);
            categoryPlot.getRangeAxis().setAxisLineVisible(false);
            categoryPlot.getRangeAxis().setTickMarksVisible(false);
            categoryPlot.setRangeGridlineStroke(new BasicStroke());
            categoryPlot.getRangeAxis().setTickLabelPaint(Color.decode("#444444"));
            categoryPlot.getDomainAxis().setTickLabelPaint(Color.decode("#444444"));

            if (categoryPlot.getRenderer() instanceof BarRenderer) {
                BarRenderer barRenderer = (BarRenderer) categoryPlot.getRenderer();
                barRenderer.setShadowVisible(false);
                barRenderer.setBarPainter(new StandardBarPainter());
                barRenderer.setMaximumBarWidth(0.10);
                if (categoryPlot.getDataset().getRowCount() > 0) {
                    barRenderer.setSeriesPaint(0, Color.decode("#4572a7"));
                }
            } else if (categoryPlot.getRenderer() instanceof LineAndShapeRenderer) {
                LineAndShapeRenderer lineRenderer = (LineAndShapeRenderer) categoryPlot.getRenderer();
            }
        } else if (plot instanceof PiePlot) {
            PiePlot piePlot = (PiePlot) plot;
            piePlot.setOutlineVisible(false);
            piePlot.setBackgroundPaint(Color.WHITE);
            piePlot.setLabelFont(new Font(fontName, Font.PLAIN, 10));
            piePlot.setLabelPaint(Color.decode("#444444"));
        }
    }

    public static long getTotalPacientesAtivos() {
        return GraficosDAO.getTotalPacientesAtivos();
    }

    public static double getTaxaMediaComparecimento() {
        return GraficosDAO.getTaxaMediaComparecimento();
    }

    public static long getPacientesListaEspera() {
        return GraficosDAO.getPacientesListaEspera();
    }

    public static long getTotalAtendimentosMesAtual() {
        return GraficosDAO.getTotalAtendimentosMesAtual();
    }

    public static long getEstagiariosAtivos() {
        return GraficosDAO.getEstagiariosAtivos();
    }

    //========================================
    // Line Charts
    //========================================
    public ChartPanel createEvolucaoAtendimentoChartPanel() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Object[]> atendimentos = GraficosDAO.buscarAtendimentosPorUsuario(null);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        if (atendimentos != null && !atendimentos.isEmpty()) {
            Map<String, Integer> dataCount = new HashMap<>();
            for (Object atendimentoObj : atendimentos) {
                Object[] row = (Object[]) atendimentoObj;
                Atendimento a = (Atendimento) row[0];
                LocalDate data = a.getData();
                String formattedDate = data.format(dtf);
                dataCount.put(formattedDate, dataCount.getOrDefault(formattedDate, 0) + 1);
            }
            for (Map.Entry<String, Integer> entry : dataCount.entrySet()) {
                dataset.addValue(entry.getValue(), "Atendimentos", entry.getKey());
            }
        }
        JFreeChart chart = ChartFactory.createLineChart(
                "Evolução de Atendimentos",
                "Data",
                "Atendimentos",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        themeStylization(chart);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.decode("#38bdf8"));

        ChartPanel painelGrafico = new ChartPanel(chart);
        painelGrafico.setBackground(Color.white);
        painelGrafico.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");

        return painelGrafico;
    }

    public ChartPanel createEvolucaoAtendimentoChartPanel(LocalDate inicio, LocalDate fim, String periodo) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Object[]> atendimentos = GraficosDAO.buscarEvolucaoAtendimentos(inicio, fim, periodo);

        for (Object[] row : atendimentos) {
            String label;
            int quantidade;

            switch (periodo) {
                case "dia" -> {
                    Date data = (Date) row[0];
                    label = new SimpleDateFormat("dd/MM/yyyy").format(data);
                    quantidade = ((Number) row[1]).intValue();
                }
                case "semana" -> {
                    int ano = (Integer) row[0];
                    int semana = (Integer) row[1];
                    label = "Semana " + semana + "/" + ano;
                    quantidade = ((Number) row[2]).intValue();
                }
                case "mês" -> {
                    int ano = (Integer) row[0];
                    int mes = (Integer) row[1];
                    label = String.format("%02d/%d", mes, ano);
                    quantidade = ((Number) row[2]).intValue();
                }
                case "ano" -> {
                    int ano = (Integer) row[0];
                    label = String.valueOf(ano);
                    quantidade = ((Number) row[1]).intValue();
                }
                default ->
                    throw new IllegalArgumentException("Período inválido: " + periodo);
            }

            dataset.addValue(quantidade, "Atendimentos", label);
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Evolução de Atendimentos",
                "Período",
                "Atendimentos",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        themeStylization(chart);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.decode("#38bdf8"));

        ChartPanel painelGrafico = new ChartPanel(chart);
        painelGrafico.setBackground(Color.white);
        painelGrafico.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");

        return painelGrafico;
    }

    public ChartPanel createAgendadosVSRealizadosChartPanel() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Object[]> result = GraficosDAO.buscarAtendimentosAgendadosVersusRealizados(null);

        if (result != null && !result.isEmpty()) {
            for (Object[] row : result) {
                String date = row[0].toString();
                Number agendados = (Number) row[1];
                Number realizados = (Number) row[2];
                dataset.addValue(agendados, "Agendados", date);
                dataset.addValue(realizados, "Realizados", date);
            }
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Agendados vs Realizados",
                "Data",
                "Quantidade",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        themeStylization(chart);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.decode("#38bdf8")); // azul
        renderer.setSeriesPaint(1, Color.decode("#fb7185")); // rosa

        ChartPanel painelGrafico = new ChartPanel(chart);
        painelGrafico.setBackground(Color.white);
        painelGrafico.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");

        return painelGrafico;
    }

    public ChartPanel createAgendadosVSRealizadosChartPanel(LocalDate inicio, LocalDate fim, String periodo, String tipoAtendimento) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Object[]> result = GraficosDAO.buscarAtendimentosAgendadosVersusRealizados(inicio, fim, periodo, tipoAtendimento, null);

        if (result != null && !result.isEmpty()) {
            for (Object[] row : result) {
                String label;
                Number agendados;
                Number realizados;

                switch (periodo) {
                    case "dia" -> {
                        LocalDate data = (LocalDate) row[0];
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        label = data.format(formatter);
                        agendados = (Number) row[1];
                        realizados = (Number) row[2];
                    }
                    case "semana" -> {
                        int ano = (Integer) row[0];
                        int semana = (Integer) row[1];
                        label = "Semana " + semana + "/" + ano;
                        agendados = (Number) row[2];
                        realizados = (Number) row[3];
                    }
                    case "mês" -> {
                        int ano = (Integer) row[0];
                        int mes = (Integer) row[1];
                        label = String.format("%02d/%d", mes, ano);
                        agendados = (Number) row[2];
                        realizados = (Number) row[3];
                    }
                    case "ano" -> {
                        int ano = (Integer) row[0];
                        label = String.valueOf(ano);
                        agendados = (Number) row[1];
                        realizados = (Number) row[2];
                    }
                    default ->
                        throw new IllegalArgumentException("Período inválido: " + periodo);
                }

                dataset.addValue(agendados, "Agendados", label);
                dataset.addValue(realizados, "Realizados", label);
            }
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Agendados vs Realizados",
                "Período",
                "Quantidade",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        themeStylization(chart);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.decode("#38bdf8")); // azul
        renderer.setSeriesPaint(1, Color.decode("#fb7185")); // rosa

        ChartPanel painelGrafico = new ChartPanel(chart);
        painelGrafico.setBackground(Color.white);
        painelGrafico.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");

        return painelGrafico;
    }

    public ChartPanel createPacientesListaEsperaChartPanel(LocalDate inicio, LocalDate fim, String periodo) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Object[]> evolucaoLista = GraficosDAO.getEvolucaoListaEspera(inicio, fim, periodo);

        SimpleDateFormat format;
        switch (periodo) {
            case "dia" ->
                format = new SimpleDateFormat("dd/MM/yyyy");
            case "semana" ->
                format = new SimpleDateFormat("'Semana' w/yyyy");
            case "mês" ->
                format = new SimpleDateFormat("MM/yyyy");
            case "ano" ->
                format = new SimpleDateFormat("yyyy");
            default ->
                format = new SimpleDateFormat("yyyy-MM-dd");
        }

        for (Object[] row : evolucaoLista) {
            String label;

            switch (periodo) {
                case "dia" -> {
                    Date data = (Date) row[0];
                    label = format.format(data);
                }
                case "semana" -> {
                    int ano = (Integer) row[0];
                    int semana = (Integer) row[1];
                    label = "Semana " + semana + "/" + ano;
                }
                case "mês" -> {
                    int ano = (Integer) row[0];
                    int mes = (Integer) row[1];
                    label = String.format("%02d/%d", mes, ano);
                }
                case "ano" -> {
                    int ano = (Integer) row[0];
                    label = String.valueOf(ano);
                }
                default ->
                    label = "Desconhecido";
            }

            int indexQuantidade = switch (periodo) {
                case "dia", "ano" ->
                    1;
                case "semana", "mês" ->
                    2;
                default ->
                    throw new IllegalArgumentException("Período inválido.");
            };

            Number quantidade = (Number) row[indexQuantidade];
            dataset.addValue(quantidade, "Pacientes", label);
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Evolução Lista de Espera",
                "Período",
                "Quantidade",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        themeStylization(chart);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.decode("#34d399"));

        ChartPanel painelGrafico = new ChartPanel(chart);
        painelGrafico.setBackground(Color.white);
        painelGrafico.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");

        return painelGrafico;
    }

    public ChartPanel createPacientesListaEsperaChartPanel() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Object[]> evolucaoLista = GraficosDAO.getEvolucaoListaEspera();

        if (evolucaoLista != null && !evolucaoLista.isEmpty()) {
            for (Object[] row : evolucaoLista) {
                int ano = (int) row[0];
                int mes = (int) row[1];
                long quantidade = (long) row[2];
                String periodo = mes + "/" + ano;
                dataset.addValue(quantidade, "Pacientes", periodo);
            }
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Evolução Lista de Espera",
                "Período",
                "Quantidade",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        themeStylization(chart);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.decode("#34d399"));

        ChartPanel painelGrafico = new ChartPanel(chart);
        painelGrafico.setBackground(Color.white);
        painelGrafico.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");

        return painelGrafico;
    }

    public ChartPanel createTempoMedioAtendimentoChartPanel() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Object[]> result = GraficosDAO.getTempoMedioEspera();

        for (Object[] row : result) {
            String value = row[0].toString();
            Number avgWaitTime = (Number) row[1];
            dataset.addValue(avgWaitTime, "Tempo Médio", value);
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Tempo Médio de Espera",
                "Categoria",
                "Dias",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        themeStylization(chart);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.decode("#fb7185"));

        ChartPanel painelGrafico = new ChartPanel(chart);
        painelGrafico.setBackground(Color.white);
        painelGrafico.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");

        return painelGrafico;
    }

    public ChartPanel createTempoMedioAtendimentoChartPanel(LocalDate inicio, LocalDate fim, String periodo, String tipoAtendimento) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Object[]> result = GraficosDAO.buscarTempoMedioEspera(inicio, fim, periodo, tipoAtendimento);
        System.err.println("QUERY:::" + result);
        if (result != null && !result.isEmpty()) {
            for (Object[] row : result) {
                String label;
                Number tempoMedio;

                switch (periodo) {
                    case "dia" -> {
                        LocalDate data = (LocalDate) row[0];
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        label = data.format(formatter);
                        tempoMedio = (Number) row[1];
                    }
                    case "semana" -> {
                        int ano = (Integer) row[0];
                        int semana = (Integer) row[1];
                        label = "Semana " + semana + "/" + ano;
                        tempoMedio = (Number) row[1];
                    }
                    case "mês" -> {
                        int ano = (Integer) row[0];
                        int mes = (Integer) row[1];
                        label = String.format("%02d/%d", mes, ano);
                        tempoMedio = (Number) row[2];
                    }
                    case "ano" -> {
                        int ano = (Integer) row[0];
                        label = String.valueOf(ano);
                        tempoMedio = (Number) row[1];
                    }
                    default ->
                        throw new IllegalArgumentException("Período inválido: " + periodo);
                }

                dataset.addValue(tempoMedio, "Tempo Médio", label);
            }
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Tempo Médio de Espera",
                "Período",
                "Dias",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        themeStylization(chart);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.decode("#fb7185")); // rosa

        ChartPanel painelGrafico = new ChartPanel(chart);
        painelGrafico.setBackground(Color.white);
        painelGrafico.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");

        return painelGrafico;
    }

    //========================================
    // Pie Charts
    //========================================
    public void criarEventoDestaqueGraficoPizza(ChartPanel painelGrafico, JFreeChart chart) {
        painelGrafico.addChartMouseListener(new ChartMouseListener() {
            private Comparable ultimaChave;

            @Override
            public void chartMouseMoved(ChartMouseEvent evento) {
                ChartEntity entidade = evento.getEntity();
                PiePlot plot = (PiePlot) chart.getPlot();

                if (entidade instanceof PieSectionEntity) {
                    PieSectionEntity secao = (PieSectionEntity) entidade;
                    Comparable chaveAtual = secao.getSectionKey();

                    if (!chaveAtual.equals(ultimaChave)) {
                        if (ultimaChave != null) {
                            plot.setExplodePercent(ultimaChave, 0);

                        }

                        plot.setExplodePercent(chaveAtual, 0.10);
                        plot.setLabelPaint(Color.BLACK);
                        plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));

                        plot.setLabelGenerator(new PieSectionLabelGenerator() {
                            @Override
                            public String generateSectionLabel(PieDataset dados, Comparable chave) {
                                if (chave.equals(chaveAtual)) {
                                    Number valor = dados.getValue(chave);
                                    return chave + ": " + valor;
                                } else {
                                    return null;
                                }

                            }

                            @Override
                            public AttributedString generateAttributedSectionLabel(PieDataset dados, Comparable chave) {
                                return null;
                            }
                        });

                        ultimaChave = chaveAtual;
                    }
                } else if (ultimaChave != null) {
                    plot.setExplodePercent(ultimaChave, 0);
                    ultimaChave = null;
                }

            }

            @Override
            public void chartMouseClicked(ChartMouseEvent event) {

            }
        });
    }

    public ChartPanel createGenderPieChartPanel(Set filtrosSelecionados) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        Map<String, Long> distribuicaoGenero = GraficosDAO.getDistribuicaoPacientes("genero");

        for (Map.Entry<String, Long> entry : distribuicaoGenero.entrySet()) {
            if (filtrosSelecionados == null || filtrosSelecionados.isEmpty() || filtrosSelecionados.contains(entry.getKey())) {
                dataset.setValue(entry.getKey(), entry.getValue());
            }
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Distribuição por Gênero",
                dataset,
                true,
                true,
                false
        );

        themeStylization(chart);

        ChartPanel painelGrafico = new ChartPanel(chart);
        painelGrafico.setBackground(Color.white);
        painelGrafico.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");

        criarEventoDestaqueGraficoPizza(painelGrafico, chart);

        return painelGrafico;
    }

    public ChartPanel createEstadoCivilPieChartPanel(Set filtrosSelecionados) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        Map<String, Long> distribuicaoEstadoCivil = GraficosDAO.getDistribuicaoPacientes("estadoCivil");

        for (Map.Entry<String, Long> entry : distribuicaoEstadoCivil.entrySet()) {
            if (filtrosSelecionados == null || filtrosSelecionados.isEmpty() || filtrosSelecionados.contains(entry.getKey())) {
                dataset.setValue(entry.getKey(), entry.getValue());
            }
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Estado Civil",
                dataset,
                true,
                true,
                false
        );

        themeStylization(chart);

        ChartPanel painelGrafico = new ChartPanel(chart);
        painelGrafico.setBackground(Color.white);
        painelGrafico.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");

        criarEventoDestaqueGraficoPizza(painelGrafico, chart);

        return painelGrafico;
    }

    public ChartPanel createRacaCorEtniaPieChartPanel(Set filtrosSelecionados) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        Map<String, Long> distribuicaoRaca = GraficosDAO.getDistribuicaoPacientes("racaCorEtnia");
        for (Map.Entry<String, Long> entry : distribuicaoRaca.entrySet()) {
            if (filtrosSelecionados == null || filtrosSelecionados.isEmpty() || filtrosSelecionados.contains(entry.getKey())) {
                dataset.setValue(entry.getKey(), entry.getValue());
            }
        }
        JFreeChart chart = ChartFactory.createPieChart(
                "Raça/Cor/Etnia",
                dataset,
                true,
                true,
                false
        );

        themeStylization(chart);

        chart.setBackgroundPaint(Color.white);

        ChartPanel painelGrafico = new ChartPanel(chart);
        painelGrafico.setBackground(Color.white);
        painelGrafico.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");

        criarEventoDestaqueGraficoPizza(painelGrafico, chart);

        return painelGrafico;
    }

    //========================================
    // Bar Charts
    //========================================
    public ChartPanel createPacienteIdadeBarChartPanel() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Object[]> faixasEtarias = GraficosDAO.getPacientesPorFaixaEtaria();
        if (faixasEtarias != null && !faixasEtarias.isEmpty()) {
            for (Object[] row : faixasEtarias) {
                String faixa = (String) row[0];
                Long quantidade = (Long) row[1];
                dataset.addValue(quantidade, faixa, "");
            }
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Distribuição por Faixa Etária",
                "Faixa Etária",
                "Quantidade",
                dataset,
                PlotOrientation.HORIZONTAL,
                true,
                true,
                false
        );

        themeStylization(chart);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.decode("#10b981"));

        ChartPanel painelGrafico = new ChartPanel(chart);
        painelGrafico.setBackground(Color.white);
        painelGrafico.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");

        return painelGrafico;
    }

    public ChartPanel createPacienteIdadeBarChartPanel(List<int[]> faixasEtariasSelecionadas) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        List<Object[]> faixasEtarias = GraficosDAO.getPacientesPorFaixaEtaria(faixasEtariasSelecionadas);

        if (faixasEtarias != null && !faixasEtarias.isEmpty()) {
            for (Object[] row : faixasEtarias) {
                String faixa = (String) row[0];
                Long quantidade = (Long) row[1];
                System.out.println("QUANTIDADE: " + quantidade + " FAIXA: " + faixa);
                dataset.addValue(quantidade, faixa, "");
            }
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Distribuição por Faixa Etária",
                "Faixa Etária",
                "Quantidade",
                dataset,
                PlotOrientation.HORIZONTAL,
                true,
                true,
                false
        );

        themeStylization(chart);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.decode("#10b981"));

        ChartPanel painelGrafico = new ChartPanel(chart);
        painelGrafico.setBackground(Color.white);
        painelGrafico.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");

        return painelGrafico;
    }

    public ChartPanel createPacienteComparecimentoBarChartPanel(LocalDate inicio, LocalDate fim, String periodo) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Object[]> taxasComparecimento = GraficosDAO.getTaxaComparecimentoPorPeriodo(inicio, fim, periodo);

        SimpleDateFormat format;
        switch (periodo) {
            case "dia" ->
                format = new SimpleDateFormat("dd/MM/yyyy");
            case "semana" ->
                format = new SimpleDateFormat("'Semana' w/yyyy");
            case "mês" ->
                format = new SimpleDateFormat("MM/yyyy");
            case "ano" ->
                format = new SimpleDateFormat("yyyy");
            default ->
                format = new SimpleDateFormat("yyyy-MM-dd");
        }

        for (Object[] row : taxasComparecimento) {
            String label;

            switch (periodo) {
                case "dia" -> {
                    Date data = (Date) row[0];
                    label = format.format(data);
                }
                case "semana" -> {
                    int ano = (Integer) row[0];
                    int semana = (Integer) row[1];
                    label = "Semana " + semana + "/" + ano;
                }
                case "mês" -> {
                    int ano = (Integer) row[0];
                    int mes = (Integer) row[1];
                    label = String.format("%02d/%d", mes, ano);
                }
                case "ano" -> {
                    int ano = (Integer) row[0];
                    label = String.valueOf(ano);
                }
                default ->
                    label = "Desconhecido";
            }

            int indexTotal;
            int indexAtendidos;

            switch (periodo) {
                case "dia", "ano" -> {
                    indexTotal = 1;
                    indexAtendidos = 2;
                }
                case "semana", "mês" -> {
                    indexTotal = 2;
                    indexAtendidos = 3;
                }
                default ->
                    throw new IllegalArgumentException("Período inválido.");
            }

            Number total = (Number) row[indexTotal];
            Number atendidos = (Number) row[indexAtendidos];

            double taxa = total.longValue() == 0 ? 0 : (atendidos.doubleValue() / total.doubleValue()) * 100;
            dataset.addValue(taxa, "Taxa de Comparecimento", label);
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Taxa de Comparecimento",
                "Período",
                "Taxa (%)",
                dataset,
                PlotOrientation.HORIZONTAL,
                true,
                true,
                false
        );

        themeStylization(chart);

        this.graficoTaxaComparecimentoDados = chart;

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.decode("#f97316"));

        ChartPanel painelGrafico = new ChartPanel(chart);
        painelGrafico.setBackground(Color.white);
        painelGrafico.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");

        return painelGrafico;
    }

    public ChartPanel createPacienteComparecimentoBarChartPanel() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Object[]> taxasComparecimento = GraficosDAO.getTaxaComparecimentoPorPeriodo("mes");

        if (taxasComparecimento != null && !taxasComparecimento.isEmpty()) {
            for (Object[] row : taxasComparecimento) {
                int ano = (int) row[0];
                int mes = (int) row[1];
                long totalAtendimentos = (long) row[2];
                long atendidos = (long) row[3];

                double taxa = totalAtendimentos == 0 ? 0.0 : ((double) atendidos / totalAtendimentos) * 100;

                String periodo = mes + "/" + ano;
                dataset.addValue(taxa, "Taxa de Comparecimento", periodo);
            }
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Taxa de Comparecimento",
                "Período",
                "Taxa (%)",
                dataset,
                PlotOrientation.HORIZONTAL,
                true,
                true,
                false
        );

        themeStylization(chart);

        this.graficoTaxaComparecimentoDados = chart;

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.decode("#f97316"));

        ChartPanel painelGrafico = new ChartPanel(chart);
        painelGrafico.setBackground(Color.white);
        painelGrafico.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");

        return painelGrafico;
    }

    public ChartPanel createSalaOcupacaoBarChartPanel() {
        DefaultCategoryDataset dsQuantidade = new DefaultCategoryDataset();
        DefaultCategoryDataset dsPercentual = new DefaultCategoryDataset();

        List<Object[]> raw = GraficosDAO.getDistribuicaoAtendimentosPorSala();
        long total = raw.stream().mapToLong(r -> (Long) r[1]).sum();

        for (Object[] row : raw) {
            String sala = row[0].toString();
            long qtd = (Long) row[1];
            double pct = total > 0 ? qtd * 100.0 / total : 0;

            dsQuantidade.addValue(qtd, "Quantidade", sala);
            dsPercentual.addValue(pct, "Percentual", sala);
        }

        // plot com dois eixos
        CategoryPlot plot = new CategoryPlot();
        // eixo primário: quantidade
        plot.setDataset(0, dsQuantidade);
        plot.setRenderer(0, new BarRenderer());
        plot.setRangeAxis(0, new NumberAxis("Quantidade"));

        // eixo secundário: percentual
        plot.setDataset(1, dsPercentual);
        LineAndShapeRenderer lineRenderer = new LineAndShapeRenderer();
        lineRenderer.setDefaultShapesVisible(true);
        plot.setRenderer(1, lineRenderer);
        NumberAxis pctAxis = new NumberAxis("Percentual (%)");
        plot.setRangeAxis(1, pctAxis);
        plot.mapDatasetToRangeAxis(1, 1);

        plot.setDomainAxis(new CategoryAxis("Sala"));
        plot.setOrientation(PlotOrientation.VERTICAL);
        plot.setBackgroundPaint(Color.WHITE);

        // legenda, título, tema
        JFreeChart chart = new JFreeChart(
                "Ocupação de Salas", JFreeChart.DEFAULT_TITLE_FONT, plot, true
        );
        themeStylization(chart);

        ChartPanel panel = new ChartPanel(chart);
        panel.setBackground(Color.WHITE);
        return panel;
    }

    public ChartPanel createSalaOcupacaoBarChartPanel(
        LocalDate inicio, LocalDate fim,
        int horaInicio, int horaFim
) {
    DefaultCategoryDataset dsQuantidade = new DefaultCategoryDataset();
    DefaultCategoryDataset dsPercentual  = new DefaultCategoryDataset();

    List<Object[]> raw = GraficosDAO.getDistribuicaoAtendimentosPorSala(
        inicio, fim, horaInicio, horaFim
    );
    long total = raw.stream().mapToLong(r -> (Long) r[1]).sum();
    for (Object[] row : raw) {
        String sala = row[0].toString();
        long qtd    = (Long) row[1];
        double pct  = total > 0 ? qtd * 100.0 / total : 0;
        dsQuantidade.addValue(qtd,       "Quantidade", sala);
        dsPercentual .addValue(pct,       "Percentual", sala);
    }

    // Montagem do gráfico com dois eixos
    CategoryPlot plot = new CategoryPlot();
    plot.setDataset(0, dsQuantidade);
    plot.setRenderer(0, new BarRenderer());
    plot.setRangeAxis(0, new NumberAxis("Quantidade"));

    plot.setDataset(1, dsPercentual);
    LineAndShapeRenderer lr = new LineAndShapeRenderer();
    lr.setDefaultShapesVisible(true);
    plot.setRenderer(1, lr);
    NumberAxis pctAxis = new NumberAxis("Percentual (%)");
    plot.setRangeAxis(1, pctAxis);
    plot.mapDatasetToRangeAxis(1, 1);

    plot.setDomainAxis(new CategoryAxis("Sala"));
    plot.setOrientation(PlotOrientation.VERTICAL);
    plot.setBackgroundPaint(Color.WHITE);

    JFreeChart chart = new JFreeChart(
        "Ocupação de Salas", JFreeChart.DEFAULT_TITLE_FONT, plot, true
    );
    themeStylization(chart);
    return new ChartPanel(chart);
}


    /**
     * @return the tabelaEstatisticaDAO
     */
    public GraficosDAO getTabelaEstatisticaDAO() {
        return tabelaEstatisticaDAO;
    }

    /**
     * @return the graficoGeneroDados
     */
    public JFreeChart getGraficoGeneroDados() {
        return graficoGeneroDados;
    }

    /**
     * @return the graficoEstadoCivilDados
     */
    public JFreeChart getGraficoEstadoCivilDados() {
        return graficoEstadoCivilDados;
    }

    /**
     * @return the graficoRacaCorEtniaDados
     */
    public JFreeChart getGraficoRacaCorEtniaDados() {
        return graficoRacaCorEtniaDados;
    }

    /**
     * @return the graficoEvolucaoAtendimentosDados
     */
    public JFreeChart getGraficoEvolucaoAtendimentosDados() {
        return graficoEvolucaoAtendimentosDados;
    }

    /**
     * @return the graficoAgendadosRealizadosDados
     */
    public JFreeChart getGraficoAgendadosRealizadosDados() {
        return graficoAgendadosRealizadosDados;
    }

    /**
     * @return the graficoListaEsperaDados
     */
    public JFreeChart getGraficoListaEsperaDados() {
        return graficoListaEsperaDados;
    }

    /**
     * @return the graficoTempoMedioDados
     */
    public JFreeChart getGraficoTempoMedioDados() {
        return graficoTempoMedioDados;
    }

    /**
     * @return the graficoPacienteIdadeDados
     */
    public JFreeChart getGraficoPacienteIdadeDados() {
        return graficoPacienteIdadeDados;
    }

    /**
     * @return the graficoPacienteComparecimentoDados
     */
    public JFreeChart getGraficoPacienteComparecimentoDados() {
        return graficoPacienteComparecimentoDados;
    }

    /**
     * @return the graficoOcupacaoSalaDados
     */
    public JFreeChart getGraficoOcupacaoSalaDados() {
        return graficoOcupacaoSalaDados;
    }

    /**
     * @return the graficoDistribuicaoAtendimentoDados
     */
    public JFreeChart getGraficoDistribuicaoAtendimentoDados() {
        return graficoDistribuicaoAtendimentoDados;
    }

    /**
     * @return the graficoTotalPacientesAtivosDados
     */
    public JFreeChart getGraficoTotalPacientesAtivosDados() {
        return graficoTotalPacientesAtivosDados;
    }

    /**
     * @return the graficoTaxaComparecimentoDados
     */
    public JFreeChart getGraficoTaxaComparecimentoDados() {
        return graficoTaxaComparecimentoDados;
    }

    /**
     * @return the graficoPacientesListaEsperaDados
     */
    public JFreeChart getGraficoPacientesListaEsperaDados() {
        return graficoPacientesListaEsperaDados;
    }

    /**
     * @return the graficoTotalAtendimentosMesAtualDados
     */
    public JFreeChart getGraficoTotalAtendimentosMesAtualDados() {
        return graficoTotalAtendimentosMesAtualDados;
    }

    /**
     * @return the graficoEstagiariosAtivosDados
     */
    public JFreeChart getGraficoEstagiariosAtivosDados() {
        return graficoEstagiariosAtivosDados;
    }
}