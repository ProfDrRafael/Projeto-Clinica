package Regradenegocio;

import Persistencia.Dao.TabelaEstatisticaDAO;
import Persistencia.Entity.Atendimento;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
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

public class EstatisticasRN {

    private TabelaEstatisticaDAO tabelaEstatisticaDAO = new TabelaEstatisticaDAO();

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
            piePlot.setLabelFont(new Font(fontName, Font.PLAIN, 12));
            piePlot.setLabelPaint(Color.decode("#444444"));
        }
    }
    
    public static long getTotalPacientesAtivos() {
        return TabelaEstatisticaDAO.getTotalPacientesAtivos();
    }

    public static double getTaxaMediaComparecimento() {
        return TabelaEstatisticaDAO.getTaxaMediaComparecimento();
    }

    public static long getPacientesListaEspera() {
        return TabelaEstatisticaDAO.getPacientesListaEspera();
    }

    public static long getTotalAtendimentosMesAtual() {
        return TabelaEstatisticaDAO.getTotalAtendimentosMesAtual();
    }

    public static long getEstagiariosAtivos() {
        return TabelaEstatisticaDAO.getEstagiariosAtivos();
    }

    //========================================
    // Line Charts
    //========================================
    public JPanel createEvolucaoAtendimentoChartPanel() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Object[]> atendimentos = TabelaEstatisticaDAO.buscarAtendimentosPorUsuario(null);
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
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
        return chartPanel;
    }

    public JPanel createAgendadosVSRealizadosChartPanel() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Object[]> result = TabelaEstatisticaDAO.buscarAtendimentosAgendadosVersusRealizados(null);

        if (result != null && !result.isEmpty()) {
            for (Object[] row : result) {
                String date = row[0].toString();
                Number agendados = (Number) row[1];
                Number realizados = (Number) row[2];
                dataset.addValue(agendados, "Agendados", date);
                dataset.addValue(realizados, "Realizados", date);
            }
        }
        System.out.println("PRINTEEEEEEEEEEEEEEEEEEEEEEEE E SAI CORRENDO");
        System.out.println(dataset);

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

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");

        return chartPanel;
    }

    public JPanel createPacientesListaEsperaChartPanel() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Object[]> evolucaoLista = TabelaEstatisticaDAO.getEvolucaoListaEspera();

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
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
        return chartPanel;
    }

    public JPanel createTempoMedioAtendimentoChartPanel() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Object[]> result = TabelaEstatisticaDAO.getTempoMedioEspera();

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
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
        return chartPanel;
    }

    //========================================
    // Pie Charts
    //========================================
    public JPanel createGenderPieChartPanel() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        Map<String, Long> distribuicaoGenero = TabelaEstatisticaDAO.getDistribuicaoPacientes("genero");
        for (Map.Entry<String, Long> entry : distribuicaoGenero.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }
        JFreeChart chart = ChartFactory.createPieChart(
                "Distribuição por Gênero",
                dataset,
                true,
                true,
                false
        );

        themeStylization(chart);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBackground(Color.WHITE);
        chartPanel.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
        return chartPanel;
    }

    public JPanel createEstadoCivilPieChartPanel() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        Map<String, Long> distribuicaoEstadoCivil = TabelaEstatisticaDAO.getDistribuicaoPacientes("estadoCivil");
        for (Map.Entry<String, Long> entry : distribuicaoEstadoCivil.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }
        JFreeChart chart = ChartFactory.createPieChart(
                "Estado Civil",
                dataset,
                true,
                true,
                false
        );

        themeStylization(chart);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBackground(Color.white);
        chartPanel.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
        return chartPanel;
    }

    public JPanel createRacaCorEtniaPieChartPanel() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        Map<String, Long> distribuicaoRaca = TabelaEstatisticaDAO.getDistribuicaoPacientes("racaCorEtnia");
        for (Map.Entry<String, Long> entry : distribuicaoRaca.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
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
        ChartPanel chartPanel = new ChartPanel(chart);

        chartPanel.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
        return chartPanel;
    }

    //========================================
    // Bar Charts
    //========================================
    public JPanel createPacienteIdadeBarChartPanel() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Object[]> faixasEtarias = TabelaEstatisticaDAO.getPacientesPorFaixaEtaria();
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
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
        return chartPanel;
    }

    public JPanel createPacienteComparecimentoBarChartPanel() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Object[]> taxasComparecimento = TabelaEstatisticaDAO.getTaxaComparecimentoPorPeriodo("mes");

        if (taxasComparecimento != null && !taxasComparecimento.isEmpty()) {
            for (Object[] row : taxasComparecimento) {
                int ano = (int) row[0];
                int mes = (int) row[1];
                long totalAtendimentos = (long) row[2]; 
                long atendidos = (long) row[3];

                // Calcula a taxa de comparecimento, evitando divisão por zero
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

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.decode("#f97316"));
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
        return chartPanel;
    }

    public JPanel createSalaOcupacaoBarChartPanel() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Object[]> ocupacaoSalas = TabelaEstatisticaDAO.getOcupacaoSalas();

        System.out.println("Regradenegocio.EstatisticasRN.createDistribuicaoAtendimentosBarChartPanel()");
        for (Object[] row : ocupacaoSalas) {
            System.out.println(row + " -----------  " + ocupacaoSalas);
        }

        if (ocupacaoSalas != null && !ocupacaoSalas.isEmpty()) {
            for (Object[] row : ocupacaoSalas) {
                String nomeSala = row[0].toString();
                Long ocupacao = (Long) row[1];
                dataset.addValue(ocupacao, "Ocupação", nomeSala);
            }
        } else {
            dataset.addValue(0.0, "Ocupação", "Sem dados");
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Ocupação de Salas",
                "Sala",
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
        renderer.setSeriesPaint(0, Color.decode("#10b981"));
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
        return chartPanel;
    }

    public JPanel createDistribuicaoAtendimentosBarChartPanel() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Object[]> distribuicaoAtendimentos = TabelaEstatisticaDAO.getDistribuicaoAtendimentosPorSala();

        System.out.println("Regradenegocio.EstatisticasRN.createDistribuicaoAtendimentosBarChartPanel()");
        for (Object[] row : distribuicaoAtendimentos) {
            System.out.println(row + " -----------  " + distribuicaoAtendimentos);
        }

        if (distribuicaoAtendimentos != null && !distribuicaoAtendimentos.isEmpty()) {
            long totalAtendimentos = 0;
            for (Object[] row : distribuicaoAtendimentos) {
                totalAtendimentos += (Long) row[1];
            }
            for (Object[] row : distribuicaoAtendimentos) {
                String sala = row[0].toString();
                Long quantidade = (Long) row[1];
                double percentual = 0;
                if (totalAtendimentos > 0) {
                    percentual = (quantidade.doubleValue() / totalAtendimentos) * 100;
                }
                dataset.addValue(percentual, "Percentual", sala);
            }
        } else {
            dataset.addValue(0.0, "Percentual", "Sem dados");
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Distribuição de Atendimentos por Sala",
                "Sala",
                "Percentual",
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
        renderer.setSeriesPaint(0, Color.decode("#10b981"));
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
        return chartPanel;
    }
}
