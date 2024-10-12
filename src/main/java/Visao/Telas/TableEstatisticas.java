package Visao.Telas;

// Importações necessárias para componentes gráficos, manipulação de datas, cores e gráficos
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import raven.chart.ChartLegendRenderer;
import raven.chart.bar.HorizontalBarChart;
import raven.chart.data.category.DefaultCategoryDataset;
import raven.chart.data.pie.DefaultPieDataset;
import raven.chart.line.LineChart;
import raven.chart.pie.PieChart;
import Visao.Components.SimpleForm;
import Visao.Utils.DateCalculator;

/**
 * Classe responsável por criar e exibir o formulário do Dashboard com gráficos diversos.
 * Extende a classe SimpleForm para usar funcionalidades básicas de um formulário.
 * @author Raven
 */
public class TableEstatisticas extends SimpleForm {

    // Construtor do formulário que chama o método de inicialização
    public TableEstatisticas() {
        init();
    }

    // Método chamado para atualizar os dados e iniciar a animação dos gráficos
    @Override
    public void formRefresh() {
        lineChart.startAnimation();  // Inicia animação do gráfico de linha
        pieChart1.startAnimation();  // Inicia animação do primeiro gráfico de pizza
        pieChart2.startAnimation();  // Inicia animação do segundo gráfico de pizza
        pieChart3.startAnimation();  // Inicia animação do terceiro gráfico de pizza
        barChart1.startAnimation();  // Inicia animação do primeiro gráfico de barras horizontais
        barChart2.startAnimation();  // Inicia animação do segundo gráfico de barras horizontais
    }

    // Método chamado na inicialização do formulário
    @Override
    public void formInitAndOpen() {
        System.out.println("init and open");
    }

    // Método chamado quando o formulário é aberto
    @Override
    public void formOpen() {
        System.out.println("Open");
    }

    // Método que inicializa o layout e os gráficos do formulário
    private void init() {
        setLayout(new MigLayout("wrap,fill,gap 10", "fill"));  // Configura o layout com MigLayout
        createPieChart();  // Cria os gráficos de pizza
        createLineChart();  // Cria o gráfico de linha
        createBarChart();  // Cria os gráficos de barras horizontais
    }

    // Método que cria e configura os gráficos de pizza
    private void createPieChart() {
        // Criação e configuração do primeiro gráfico de pizza
        pieChart1 = new PieChart();
        JLabel header1 = new JLabel("Product Income");  // Define o cabeçalho do gráfico
        header1.putClientProperty(FlatClientProperties.STYLE, "font:+1");  // Aumenta o tamanho da fonte
        pieChart1.setHeader(header1);  // Define o cabeçalho no gráfico
        pieChart1.getChartColor().addColor(  // Adiciona as cores do gráfico
            Color.decode("#f87171"), Color.decode("#fb923c"), Color.decode("#fbbf24"),
            Color.decode("#a3e635"), Color.decode("#34d399"), Color.decode("#22d3ee"),
            Color.decode("#818cf8"), Color.decode("#c084fc")
        );
        pieChart1.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
        pieChart1.setDataset(createPieData());  // Define o dataset do gráfico
        add(pieChart1, "split 3,height 290");  // Adiciona o gráfico ao layout com altura fixa

        // Criação e configuração do segundo gráfico de pizza
        pieChart2 = new PieChart();
        JLabel header2 = new JLabel("Product Cost");  // Define o cabeçalho do gráfico
        header2.putClientProperty(FlatClientProperties.STYLE, "font:+1");  // Aumenta o tamanho da fonte
        pieChart2.setHeader(header2);  // Define o cabeçalho no gráfico
        pieChart2.getChartColor().addColor(  // Adiciona as cores do gráfico
            Color.decode("#f87171"), Color.decode("#fb923c"), Color.decode("#fbbf24"),
            Color.decode("#a3e635"), Color.decode("#34d399"), Color.decode("#22d3ee"),
            Color.decode("#818cf8"), Color.decode("#c084fc")
        );
        pieChart2.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
        pieChart2.setDataset(createPieData());  // Define o dataset do gráfico
        add(pieChart2, "height 290");  // Adiciona o gráfico ao layout com altura fixa

        // Criação e configuração do terceiro gráfico de pizza, do tipo "Donut"
        pieChart3 = new PieChart();
        JLabel header3 = new JLabel("Product Profit");  // Define o cabeçalho do gráfico
        header3.putClientProperty(FlatClientProperties.STYLE, "font:+1");  // Aumenta o tamanho da fonte
        pieChart3.setHeader(header3);  // Define o cabeçalho no gráfico
        pieChart3.getChartColor().addColor(  // Adiciona as cores do gráfico
            Color.decode("#f87171"), Color.decode("#fb923c"), Color.decode("#fbbf24"),
            Color.decode("#a3e635"), Color.decode("#34d399"), Color.decode("#22d3ee"),
            Color.decode("#818cf8"), Color.decode("#c084fc")
        );
        pieChart3.setChartType(PieChart.ChartType.DONUT_CHART);  // Define o tipo de gráfico como "Donut"
        pieChart3.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
        pieChart3.setDataset(createPieData());  // Define o dataset do gráfico
        add(pieChart3, "height 290");  // Adiciona o gráfico ao layout com altura fixa
    }

    // Método que cria e configura o gráfico de linha
    private void createLineChart() {
        lineChart = new LineChart();  // Cria um novo gráfico de linha
        lineChart.setChartType(LineChart.ChartType.CURVE);  // Define o tipo de gráfico como curva
        lineChart.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
        add(lineChart);  // Adiciona o gráfico ao layout
        createLineChartData();  // Cria os dados do gráfico de linha
    }

    // Método que cria e configura os gráficos de barras horizontais
    private void createBarChart() {
        // Criação e configuração do primeiro gráfico de barras horizontais
        barChart1 = new HorizontalBarChart();
        JLabel header1 = new JLabel("Monthly Income");  // Define o cabeçalho do gráfico
        header1.putClientProperty(FlatClientProperties.STYLE, "font:+1;border:0,0,5,0");  // Define o estilo do cabeçalho
        barChart1.setHeader(header1);  // Adiciona o cabeçalho ao gráfico
        barChart1.setBarColor(Color.decode("#f97316"));  // Define a cor das barras
        barChart1.setDataset(createData());  // Define o dataset do gráfico
        JPanel panel1 = new JPanel(new BorderLayout());  // Cria um painel para o gráfico
        panel1.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
        panel1.add(barChart1);  // Adiciona o gráfico ao painel
        add(panel1, "split 2,gap 0 20");  // Adiciona o painel ao layout

        // Criação e configuração do segundo gráfico de barras horizontais
        barChart2 = new HorizontalBarChart();
        JLabel header2 = new JLabel("Monthly Expense");  // Define o cabeçalho do gráfico
        header2.putClientProperty(FlatClientProperties.STYLE, "font:+1;border:0,0,5,0");  // Define o estilo do cabeçalho
        barChart2.setHeader(header2);  // Adiciona o cabeçalho ao gráfico
        barChart2.setBarColor(Color.decode("#10b981"));  // Define a cor das barras
        barChart2.setDataset(createData());  // Define o dataset do gráfico
        JPanel panel2 = new JPanel(new BorderLayout());  // Cria um painel para o gráfico
        panel2.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
        panel2.add(barChart2);  // Adiciona o gráfico ao painel
        add(panel2);  // Adiciona o painel ao layout
    }

    // Método que cria um dataset para gráficos de pizza
    private DefaultPieDataset createData() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();  // Cria um novo dataset
        Random random = new Random();  // Cria um objeto Random para gerar valores aleatórios
        dataset.addValue("July (ongoing)", random.nextInt(100));  // Adiciona valor aleatório para julho
        dataset.addValue("June", random.nextInt(100));  // Adiciona valor aleatório para junho
        dataset.addValue("May", random.nextInt(100));  // Adiciona valor aleatório para maio
        dataset.addValue("April", random.nextInt(100));  // Adiciona valor aleatório para abril
        dataset.addValue("March", random.nextInt(100));  // Adiciona valor aleatório para março
        dataset.addValue("February", random.nextInt(100));  // Adiciona valor aleatório para fevereiro
        return dataset;  // Retorna o dataset
    }

    // Método que cria o dataset para gráficos de pizza com categorias de produtos
    private DefaultPieDataset createPieData() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();  // Cria um novo dataset
        Random random = new Random();  // Cria um objeto Random para gerar valores aleatórios
        dataset.addValue("Bags", random.nextInt(100) + 50);  // Adiciona valor aleatório para "Bags"
        dataset.addValue("Hats", random.nextInt(100) + 50);  // Adiciona valor aleatório para "Hats"
        dataset.addValue("Glasses", random.nextInt(100) + 50);  // Adiciona valor aleatório para "Glasses"
        dataset.addValue("Watches", random.nextInt(100) + 50);  // Adiciona valor aleatório para "Watches"
        dataset.addValue("Jewelry", random.nextInt(100) + 50);  // Adiciona valor aleatório para "Jewelry"
        return dataset;  // Retorna o dataset
    }

    // Método que cria os dados para o gráfico de linha
    private void createLineChartData() {
        DefaultCategoryDataset<String, String> categoryDataset = new DefaultCategoryDataset<>();  // Cria um novo dataset
        Calendar cal = Calendar.getInstance();  // Obtém a data atual
        SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy");  // Formato da data
        Random ran = new Random();  // Objeto Random para gerar valores aleatórios
        int randomDate = 30;  // Quantidade de dias de dados a serem gerados

        // Adiciona dados aleatórios ao dataset para cada dia
        for (int i = 1; i <= randomDate; i++) {
            String date = df.format(cal.getTime());  // Formata a data atual
            categoryDataset.addValue(ran.nextInt(700) + 5, "Income", date);  // Adiciona valor para "Income"
            categoryDataset.addValue(ran.nextInt(700) + 5, "Expense", date);  // Adiciona valor para "Expense"
            categoryDataset.addValue(ran.nextInt(700) + 5, "Profit", date);  // Adiciona valor para "Profit"
            cal.add(Calendar.DATE, 1);  // Avança para o próximo dia
        }

        // Controle da legenda do gráfico para não exibir todas as legendas
        try {
            Date date = df.parse(categoryDataset.getColumnKey(0));  // Obtém a primeira data do dataset
            Date dateEnd = df.parse(categoryDataset.getColumnKey(categoryDataset.getColumnCount() - 1));  // Obtém a última data

            DateCalculator dcal = new DateCalculator(date, dateEnd);  // Calcula a diferença entre as datas
            long diff = dcal.getDifferenceDays();  // Obtém a diferença em dias

            double d = Math.ceil((diff / 10f));  // Calcula o intervalo para exibir as legendas
            lineChart.setLegendRenderer(new ChartLegendRenderer() {
                @Override
                public Component getLegendComponent(Object legend, int index) {
                    if (index % d == 0) {
                        return super.getLegendComponent(legend, index);  // Exibe a legenda apenas em intervalos definidos
                    } else {
                        return null;  // Não exibe a legenda em outras posições
                    }
                }
            });
        } catch (ParseException e) {
            System.err.println(e);  // Exibe erro de parsing de data
        }

        lineChart.setCategoryDataset(categoryDataset);  // Define o dataset no gráfico de linha
        lineChart.getChartColor().addColor(  // Adiciona as cores das linhas
            Color.decode("#38bdf8"), Color.decode("#fb7185"), Color.decode("#34d399")
        );
        JLabel header = new JLabel("Income Data");  // Cria o cabeçalho do gráfico
        header.putClientProperty(FlatClientProperties.STYLE, "font:+1;border:0,0,5,0");  // Define o estilo do cabeçalho
        lineChart.setHeader(header);  // Define o cabeçalho no gráfico
    }

    // Declaração dos componentes de gráficos usados no formulário
    private LineChart lineChart;
    private HorizontalBarChart barChart1;
    private HorizontalBarChart barChart2;
    private PieChart pieChart1;
    private PieChart pieChart2;
    private PieChart pieChart3;
}
