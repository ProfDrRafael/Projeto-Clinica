package Regradenegocio;

import Persistencia.Dao.TabelaEstatisticaDAO;
import Persistencia.Entity.Atendimento;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jfree.chart.ChartFactory;
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
    public JFreeChart createEvolucaoAtendimentoChartPanel() {
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

        return chart;
    }

    public JFreeChart createEvolucaoAtendimentoChartPanel(LocalDate inicio, LocalDate fim, String periodo) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Object[]> atendimentos = TabelaEstatisticaDAO.buscarEvolucaoAtendimentos(inicio, fim, periodo);

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

        return chart;
    }

    public JFreeChart createAgendadosVSRealizadosChartPanel() {
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

        return chart;
    }

    public JFreeChart createPacientesListaEsperaChartPanel(LocalDate inicio, LocalDate fim, String periodo) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Object[]> evolucaoLista = TabelaEstatisticaDAO.getEvolucaoListaEspera(inicio, fim, periodo);

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

        return chart;
    }

    public JFreeChart createPacientesListaEsperaChartPanel() {
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

        return chart;
    }

    public JFreeChart createTempoMedioAtendimentoChartPanel() {
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

        return chart;
    }

    //========================================
    // Pie Charts
    //========================================
    public JFreeChart createGenderPieChartPanel(Set filtrosSelecionados) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        Map<String, Long> distribuicaoGenero = TabelaEstatisticaDAO.getDistribuicaoPacientes("genero");

        for (Map.Entry<String, Long> entry : distribuicaoGenero.entrySet()) {
            if (filtrosSelecionados == null || filtrosSelecionados.isEmpty() || filtrosSelecionados.contains(entry.getKey())) {
                dataset.setValue(entry.getKey() + ": " + entry.getValue(), entry.getValue());
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

        return chart;
    }

    public JFreeChart createEstadoCivilPieChartPanel(Set filtrosSelecionados) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        Map<String, Long> distribuicaoEstadoCivil = TabelaEstatisticaDAO.getDistribuicaoPacientes("estadoCivil");

        for (Map.Entry<String, Long> entry : distribuicaoEstadoCivil.entrySet()) {
            if (filtrosSelecionados == null || filtrosSelecionados.isEmpty() || filtrosSelecionados.contains(entry.getKey())) {
                dataset.setValue(entry.getKey() + ": " + entry.getValue(), entry.getValue());
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

        return chart;
    }

    public JFreeChart createRacaCorEtniaPieChartPanel(Set filtrosSelecionados) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        Map<String, Long> distribuicaoRaca = TabelaEstatisticaDAO.getDistribuicaoPacientes("racaCorEtnia");
        for (Map.Entry<String, Long> entry : distribuicaoRaca.entrySet()) {
            if (filtrosSelecionados == null || filtrosSelecionados.isEmpty() || filtrosSelecionados.contains(entry.getKey())) {
                dataset.setValue(entry.getKey() + ": " + entry.getValue(), entry.getValue());
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

        return chart;
    }

    //========================================
    // Bar Charts
    //========================================
    public JFreeChart createPacienteIdadeBarChartPanel() {
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

        return chart;
    }

    public JFreeChart createPacienteIdadeBarChartPanel(List<int[]> faixasEtariasSelecionadas) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Object[]> faixasEtarias = TabelaEstatisticaDAO.getPacientesPorFaixaEtaria(faixasEtariasSelecionadas);
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

        return chart;
    }

    public JFreeChart createPacienteComparecimentoBarChartPanel(LocalDate inicio, LocalDate fim, String periodo) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Object[]> taxasComparecimento = TabelaEstatisticaDAO.getTaxaComparecimentoPorPeriodo(inicio, fim, periodo);

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

        return chart;
    }

    public JFreeChart createPacienteComparecimentoBarChartPanel() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Object[]> taxasComparecimento = TabelaEstatisticaDAO.getTaxaComparecimentoPorPeriodo("mes");

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

        return chart;
    }

    public JFreeChart createSalaOcupacaoBarChartPanel(String filtroSala) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Object[]> ocupacaoSalas = TabelaEstatisticaDAO.getOcupacaoSalas();

        if (ocupacaoSalas != null && !ocupacaoSalas.isEmpty()) {
            for (Object[] row : ocupacaoSalas) {
                String nomeSala = row[0].toString();
                Long ocupacao = (Long) row[1];

                if (filtroSala == null || filtroSala.equals("Todos") || nomeSala.equals(filtroSala)) {
                    dataset.addValue(ocupacao, "Ocupação", nomeSala);
                }
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

        this.graficoOcupacaoSalaDados = chart;
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.decode("#10b981"));

        return chart;
    }

    public JFreeChart createDistribuicaoAtendimentosBarChartPanel(String filtroSala) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Object[]> distribuicaoAtendimentos = TabelaEstatisticaDAO.getDistribuicaoAtendimentosPorSala();

        if (distribuicaoAtendimentos != null && !distribuicaoAtendimentos.isEmpty()) {
            long totalAtendimentos = 0;

            for (Object[] row : distribuicaoAtendimentos) {
                String sala = row[0].toString();
                if (filtroSala == null || filtroSala.equals("Todos") || sala.equals(filtroSala)) {
                    totalAtendimentos += (Long) row[1];
                }
            }

            for (Object[] row : distribuicaoAtendimentos) {
                String sala = row[0].toString();
                Long quantidade = (Long) row[1];

                if (filtroSala == null || filtroSala.equals("Todos") || sala.equals(filtroSala)) {
                    double percentual = 0;
                    if (totalAtendimentos > 0) {
                        percentual = (quantidade.doubleValue() / totalAtendimentos) * 100;
                    }
                    dataset.addValue(percentual, "Percentual", sala);
                }
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

        this.graficoDistribuicaoAtendimentoDados = chart;

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.decode("#10b981"));

        return chart;
    }

    /**
     * @return the tabelaEstatisticaDAO
     */
    public TabelaEstatisticaDAO getTabelaEstatisticaDAO() {
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
