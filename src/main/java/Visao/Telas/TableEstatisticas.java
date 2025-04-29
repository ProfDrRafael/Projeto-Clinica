package Visao.Telas;

import Regradenegocio.EstatisticasRN;
import Visao.Components.SimpleForm;
import Visao.Components.CardIndicador;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JSlider;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import com.raven.datechooser.DateChooser;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.JTextField;
import raven.alerts.MessageAlerts;

public class TableEstatisticas extends SimpleForm {

    private EstatisticasRN estatisticasRN = new EstatisticasRN();
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

    private JPanel criarFiltro(String tipoGrafico) {
        JPanel painelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelFiltro.setOpaque(false);

        switch (tipoGrafico) {
            case "genero" -> {
                painelFiltro.setPreferredSize(new Dimension(larguraGrafico, 50));

                JCheckBox cbMasculino = new JCheckBox("Masculino", true);
                JCheckBox cbFeminino = new JCheckBox("Feminino", true);
                JCheckBox cbNaoBinario = new JCheckBox("Não-Binário", true);
                JCheckBox cbOutro = new JCheckBox("Outro", true);
                JCheckBox cbNaoInformar = new JCheckBox("Prefiro não informar", true);
                JButton btAplicar = new JButton("Aplicar");

                Dimension checkboxSize = new Dimension(larguraCheckbox, alturaCheckbox);
                cbMasculino.setPreferredSize(checkboxSize);
                cbFeminino.setPreferredSize(checkboxSize);
                cbNaoBinario.setPreferredSize(checkboxSize);
                cbOutro.setPreferredSize(checkboxSize);
                cbNaoInformar.setPreferredSize(checkboxSize);

                painelFiltro.add(cbMasculino);
                painelFiltro.add(cbFeminino);
                painelFiltro.add(cbNaoBinario);
                painelFiltro.add(cbOutro);
                painelFiltro.add(cbNaoInformar);
                painelFiltro.add(btAplicar);

                btAplicar.addActionListener(e -> {
                    Set<String> filtros = new HashSet<>();
                    if (cbMasculino.isSelected()) {
                        filtros.add("Masculino");
                    }
                    if (cbFeminino.isSelected()) {
                        filtros.add("Feminino");
                    }
                    if (cbNaoBinario.isSelected()) {
                        filtros.add("Não-Binário");
                    }
                    if (cbOutro.isSelected()) {
                        filtros.add("Outro");
                    }
                    if (cbNaoInformar.isSelected()) {
                        filtros.add("Prefiro não informar");
                    }

                    JPanel novoPainel = new JPanel(new BorderLayout());
                    novoPainel.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
                    novoPainel.setPreferredSize(new Dimension(larguraGrafico, alturaGrafico + 500));

                    novoPainel.add(painelFiltro, BorderLayout.NORTH);

                    ChartPanel painelGrafico = new ChartPanel(estatisticasRN.createGenderPieChartPanel(filtros));
                    painelGrafico.setBackground(Color.white);
                    painelGrafico.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");

                    novoPainel.add(painelGrafico, BorderLayout.CENTER);

                    containerGrafico.add(novoPainel, "Gênero");
                    ((CardLayout) containerGrafico.getLayout()).show(containerGrafico, "Gênero");

                    bottomPanel.revalidate();
                    bottomPanel.repaint();
                });

                return painelFiltro;
            }
            case "estadoCivil" -> {
                painelFiltro.setPreferredSize(new Dimension(larguraGrafico, 70));

                JCheckBox cbSolteiro = new JCheckBox("Solteiro(a)", true);
                JCheckBox cbCasado = new JCheckBox("Casado(a)", true);
                JCheckBox cbDivorciado = new JCheckBox("Divorciado(a)", true);
                JCheckBox cbViuvo = new JCheckBox("Viúvo(a)", true);
                JCheckBox cbUniaoEstavel = new JCheckBox("União Estável", true);
                JCheckBox cbSeparado = new JCheckBox("Separado(a)", true);
                JButton btAplicar = new JButton("Aplicar");

                Dimension checkboxSize = new Dimension(larguraCheckbox, alturaCheckbox);
                cbSolteiro.setPreferredSize(checkboxSize);
                cbCasado.setPreferredSize(checkboxSize);
                cbDivorciado.setPreferredSize(checkboxSize);
                cbViuvo.setPreferredSize(checkboxSize);
                cbUniaoEstavel.setPreferredSize(checkboxSize);
                cbSeparado.setPreferredSize(checkboxSize);

                painelFiltro.add(cbSolteiro);
                painelFiltro.add(cbCasado);
                painelFiltro.add(cbDivorciado);
                painelFiltro.add(cbViuvo);
                painelFiltro.add(cbUniaoEstavel);
                painelFiltro.add(cbSeparado);
                painelFiltro.add(btAplicar);

                btAplicar.addActionListener(e -> {
                    Set<String> filtros = new HashSet<>();
                    if (cbSolteiro.isSelected()) {
                        filtros.add("Solteiro(a)");
                    }
                    if (cbCasado.isSelected()) {
                        filtros.add("Casado(a)");
                    }
                    if (cbDivorciado.isSelected()) {
                        filtros.add("Divorciado(a)");
                    }
                    if (cbViuvo.isSelected()) {
                        filtros.add("Viúvo(a)");
                    }
                    if (cbUniaoEstavel.isSelected()) {
                        filtros.add("União Estável");
                    }
                    if (cbSeparado.isSelected()) {
                        filtros.add("Separado(a)");
                    }

                    JPanel novoPainel = new JPanel(new BorderLayout());
                    novoPainel.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
                    novoPainel.setPreferredSize(new Dimension(larguraGrafico, alturaGrafico + 500));

                    novoPainel.add(painelFiltro, BorderLayout.NORTH);

                    ChartPanel painelGrafico = new ChartPanel(estatisticasRN.createEstadoCivilPieChartPanel(filtros));
                    painelGrafico.setBackground(Color.white);
                    painelGrafico.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");

                    novoPainel.add(painelGrafico, BorderLayout.CENTER);

                    containerGrafico.add(novoPainel, "Estado Civil");
                    ((CardLayout) containerGrafico.getLayout()).show(containerGrafico, "Estado Civil");

                    bottomPanel.revalidate();
                    bottomPanel.repaint();
                });

                return painelFiltro;
            }
            case "raca/cor/etnia" -> {
                painelFiltro.setPreferredSize(new Dimension(larguraGrafico, 50));

                JCheckBox cbNegro = new JCheckBox("Negro(a)", true);
                JCheckBox cbBranco = new JCheckBox("Branco(a)", true);
                JCheckBox cbAmarelo = new JCheckBox("Amarelo(a)", true);
                JCheckBox cbPardo = new JCheckBox("Pardo(a)", true);
                JCheckBox cbIndigena = new JCheckBox("Indígena", true);
                JButton btAplicar = new JButton("Aplicar");

                Dimension checkboxSize = new Dimension(larguraCheckbox, alturaCheckbox);
                cbNegro.setPreferredSize(checkboxSize);
                cbBranco.setPreferredSize(checkboxSize);
                cbAmarelo.setPreferredSize(checkboxSize);
                cbPardo.setPreferredSize(checkboxSize);
                cbIndigena.setPreferredSize(checkboxSize);

                painelFiltro.add(cbNegro);
                painelFiltro.add(cbBranco);
                painelFiltro.add(cbAmarelo);
                painelFiltro.add(cbPardo);
                painelFiltro.add(cbIndigena);
                painelFiltro.add(btAplicar);

                btAplicar.addActionListener(e -> {
                    Set<String> filtros = new HashSet<>();
                    if (cbNegro.isSelected()) {
                        filtros.add("Negro(a)");
                    }
                    if (cbBranco.isSelected()) {
                        filtros.add("Branco(a)");
                    }
                    if (cbAmarelo.isSelected()) {
                        filtros.add("Amarelo(a)");
                    }
                    if (cbPardo.isSelected()) {
                        filtros.add("Pardo(a)");
                    }
                    if (cbIndigena.isSelected()) {
                        filtros.add("Indígena");
                    }

                    JPanel novoPainel = new JPanel(new BorderLayout());
                    novoPainel.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
                    novoPainel.setPreferredSize(new Dimension(larguraGrafico, alturaGrafico + 500));

                    novoPainel.add(painelFiltro, BorderLayout.NORTH);

                    ChartPanel painelGrafico = new ChartPanel(estatisticasRN.createRacaCorEtniaPieChartPanel(filtros));
                    painelGrafico.setBackground(Color.white);
                    painelGrafico.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");

                    novoPainel.add(painelGrafico, BorderLayout.CENTER);

                    containerGrafico.add(novoPainel, "Raça/Cor/Etnia");
                    ((CardLayout) containerGrafico.getLayout()).show(containerGrafico, "Raça/Cor/Etnia");

                    bottomPanel.revalidate();
                    bottomPanel.repaint();
                });

                return painelFiltro;
            }
            case "evolucaoAtendimento" -> {
                painelFiltro.setPreferredSize(new Dimension(larguraGrafico, 50));

                JTextField tfInicio = new JTextField(10);
                JTextField tfFim = new JTextField(10);

                DateChooser dateChooserInicio = new DateChooser();
                DateChooser dateChooserFim = new DateChooser();

                dateChooserInicio.setTextField(tfInicio);
                dateChooserFim.setTextField(tfFim);

                String[] periodos = {"Dia", "Semana", "Mês", "Ano"};
                JComboBox<String> cbPeriodo = new JComboBox<>(periodos);

                JButton btAplicar = new JButton("Aplicar");

                painelFiltro.add(new JLabel("De:"));
                painelFiltro.add(tfInicio);

                painelFiltro.add(new JLabel("Até:"));
                painelFiltro.add(tfFim);

                painelFiltro.add(new JLabel("Período:"));
                painelFiltro.add(cbPeriodo);

                painelFiltro.add(btAplicar);

                btAplicar.addActionListener(e -> {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        LocalDate dataInicio = tfInicio.getText().isEmpty() ? null
                                : sdf.parse(tfInicio.getText()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                        LocalDate dataFim = tfFim.getText().isEmpty() ? null
                                : sdf.parse(tfFim.getText()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        String periodo = cbPeriodo.getSelectedItem().toString().toLowerCase();

                        JPanel novoPainel = new JPanel(new BorderLayout());
                        novoPainel.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
                        novoPainel.setPreferredSize(new Dimension(larguraGrafico, alturaGrafico + 500));

                        novoPainel.add(painelFiltro, BorderLayout.NORTH);

                        novoPainel.add(painelFiltro, BorderLayout.NORTH);

                        ChartPanel painelGrafico = new ChartPanel(
                                estatisticasRN.createEvolucaoAtendimentoChartPanel(dataInicio, dataFim, periodo)
                        );
                        painelGrafico.setBackground(Color.white);
                        painelGrafico.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");

                        novoPainel.add(painelGrafico, BorderLayout.CENTER);

                        containerGrafico.add(novoPainel, "createEvolucaoAtendimentoChartPanel");
                        ((CardLayout) containerGrafico.getLayout()).show(containerGrafico, "createEvolucaoAtendimentoChartPanel");

                        bottomPanel.revalidate();
                        bottomPanel.repaint();
                    } catch (ParseException ex) {
                        MessageAlerts.getInstance().showMessage("Falha!", "Erro ao escolher as datas, tente novamente", MessageAlerts.MessageType.DEFAULT);
                    }
                });

                return painelFiltro;
            }
//            case "agendadosVSrealizados" ->
//                new MenuEstagiario();
            case "listaEspera" -> {
                painelFiltro.setPreferredSize(new Dimension(larguraGrafico, 50));

                JTextField tfInicio = new JTextField(10);
                JTextField tfFim = new JTextField(10);

                DateChooser dateChooserInicio = new DateChooser();
                DateChooser dateChooserFim = new DateChooser();

                dateChooserInicio.setTextField(tfInicio);
                dateChooserFim.setTextField(tfFim);

                String[] periodos = {"Dia", "Semana", "Mês", "Ano"};
                JComboBox<String> cbPeriodo = new JComboBox<>(periodos);

                JButton btAplicar = new JButton("Aplicar");

                painelFiltro.add(new JLabel("De:"));
                painelFiltro.add(tfInicio);

                painelFiltro.add(new JLabel("Até:"));
                painelFiltro.add(tfFim);

                painelFiltro.add(new JLabel("Período:"));
                painelFiltro.add(cbPeriodo);

                painelFiltro.add(btAplicar);

                btAplicar.addActionListener(e -> {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        LocalDate dataInicio = tfInicio.getText().isEmpty() ? null
                                : sdf.parse(tfInicio.getText()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                        LocalDate dataFim = tfFim.getText().isEmpty() ? null
                                : sdf.parse(tfFim.getText()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        String periodo = cbPeriodo.getSelectedItem().toString().toLowerCase();

                        JPanel novoPainel = new JPanel(new BorderLayout());
                        novoPainel.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
                        novoPainel.setPreferredSize(new Dimension(larguraGrafico, alturaGrafico + 500));

                        novoPainel.add(painelFiltro, BorderLayout.NORTH);

                        novoPainel.add(painelFiltro, BorderLayout.NORTH);

                        ChartPanel painelGrafico = new ChartPanel(
                                estatisticasRN.createPacientesListaEsperaChartPanel(dataInicio, dataFim, periodo)
                        );
                        painelGrafico.setBackground(Color.white);
                        painelGrafico.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");

                        novoPainel.add(painelGrafico, BorderLayout.CENTER);

                        containerGrafico.add(novoPainel, "Lista de Espera");
                        ((CardLayout) containerGrafico.getLayout()).show(containerGrafico, "Lista de Espera");

                        bottomPanel.revalidate();
                        bottomPanel.repaint();
                    } catch (ParseException ex) {
                        MessageAlerts.getInstance().showMessage("Falha!", "Erro ao escolher as datas, tente novamente", MessageAlerts.MessageType.DEFAULT);
                    }
                });

                return painelFiltro;
            }
//            case "tempoMedioAtendimento" ->
//                new FormUsuario();
            case "pacienteIdade" -> {
                painelFiltro.setPreferredSize(new Dimension(larguraGrafico, 300));
                painelFiltro.setLayout(new GridLayout(4, 1));

                List<JSlider> slidersMin = new ArrayList<>();
                List<JSlider> slidersMax = new ArrayList<>();
                List<JLabel> labelsMin = new ArrayList<>();
                List<JLabel> labelsMax = new ArrayList<>();

                for (int i = 0; i < 3; i++) {
                    JPanel faixaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    faixaPanel.setBorder(BorderFactory.createTitledBorder("Faixa Etária " + (i + 1)));

                    JSlider sliderMin = new JSlider(0, 100, i * 10);
                    JSlider sliderMax = new JSlider(0, 100, i * 10 + 10);

                    sliderMin.setMajorTickSpacing(10);
                    sliderMin.setPaintTicks(true);
                    sliderMin.setPaintLabels(true);
                    sliderMin.setLabelTable(sliderMin.createStandardLabels(10));

                    sliderMax.setMajorTickSpacing(10);
                    sliderMax.setPaintTicks(true);
                    sliderMax.setPaintLabels(true);
                    sliderMax.setLabelTable(sliderMax.createStandardLabels(10));

                    JLabel labelMin = new JLabel("Mín: " + sliderMin.getValue());
                    JLabel labelMax = new JLabel("Máx: " + sliderMax.getValue());

                    sliderMin.addChangeListener(e -> labelMin.setText("Mín: " + sliderMin.getValue()));
                    sliderMax.addChangeListener(e -> labelMax.setText("Máx: " + sliderMax.getValue()));

                    slidersMin.add(sliderMin);
                    slidersMax.add(sliderMax);
                    labelsMin.add(labelMin);
                    labelsMax.add(labelMax);

                    faixaPanel.add(labelMin);
                    faixaPanel.add(sliderMin);
                    faixaPanel.add(labelMax);
                    faixaPanel.add(sliderMax);

                    painelFiltro.add(faixaPanel);
                }

                JButton btAplicar = new JButton("Aplicar");
                painelFiltro.add(btAplicar);

                btAplicar.addActionListener(e -> {
                    List<int[]> faixas = new ArrayList<>();
                    for (int i = 0; i < 3; i++) {
                        int min = slidersMin.get(i).getValue();
                        int max = slidersMax.get(i).getValue();
                        if (min > max) {
                            int temp = min;
                            min = max;
                            max = temp;
                        }
                        faixas.add(new int[]{min, max});
                    }

                    JPanel novoPainel = new JPanel(new BorderLayout());
                    novoPainel.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
                    novoPainel.setPreferredSize(new Dimension(larguraGrafico, alturaGrafico));

                    novoPainel.add(painelFiltro, BorderLayout.NORTH);

                    ChartPanel painelGrafico = new ChartPanel(estatisticasRN.createPacienteIdadeBarChartPanel(faixas));
                    painelGrafico.setBackground(Color.white);
                    painelGrafico.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");

                    novoPainel.add(painelGrafico, BorderLayout.CENTER);

                    containerGrafico.add(novoPainel, "Paciente por Idade");
                    ((CardLayout) containerGrafico.getLayout()).show(containerGrafico, "Paciente por Idade");

                    bottomPanel.revalidate();
                    bottomPanel.repaint();
                });

                return painelFiltro;
            }
            case "pacienteComparecimento" -> {
                painelFiltro.setPreferredSize(new Dimension(larguraGrafico, 50));

                JTextField tfInicio = new JTextField(10);
                JTextField tfFim = new JTextField(10);

                DateChooser dateChooserInicio = new DateChooser();
                DateChooser dateChooserFim = new DateChooser();

                dateChooserInicio.setTextField(tfInicio);
                dateChooserFim.setTextField(tfFim);

                String[] periodos = {"Dia", "Semana", "Mês", "Ano"};
                JComboBox<String> cbPeriodo = new JComboBox<>(periodos);

                JButton btAplicar = new JButton("Aplicar");

                painelFiltro.add(new JLabel("De:"));
                painelFiltro.add(tfInicio);

                painelFiltro.add(new JLabel("Até:"));
                painelFiltro.add(tfFim);

                painelFiltro.add(new JLabel("Período:"));
                painelFiltro.add(cbPeriodo);

                painelFiltro.add(btAplicar);

                btAplicar.addActionListener(e -> {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        LocalDate dataInicio = tfInicio.getText().isEmpty() ? null
                                : sdf.parse(tfInicio.getText()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                        LocalDate dataFim = tfFim.getText().isEmpty() ? null
                                : sdf.parse(tfFim.getText()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        String periodo = cbPeriodo.getSelectedItem().toString().toLowerCase();

                        JPanel novoPainel = new JPanel(new BorderLayout());
                        novoPainel.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
                        novoPainel.setPreferredSize(new Dimension(larguraGrafico, alturaGrafico + 500));

                        novoPainel.add(painelFiltro, BorderLayout.NORTH);

                        novoPainel.add(painelFiltro, BorderLayout.NORTH);

                        ChartPanel painelGrafico = new ChartPanel(
                                estatisticasRN.createPacienteComparecimentoBarChartPanel(dataInicio, dataFim, periodo)
                        );
                        painelGrafico.setBackground(Color.white);
                        painelGrafico.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");

                        novoPainel.add(painelGrafico, BorderLayout.CENTER);

                        containerGrafico.add(novoPainel, "Paciente Comparecimento");
                        ((CardLayout) containerGrafico.getLayout()).show(containerGrafico, "Paciente Comparecimento");

                        bottomPanel.revalidate();
                        bottomPanel.repaint();
                    } catch (ParseException ex) {
                        MessageAlerts.getInstance().showMessage("Falha!", "Erro ao escolher as datas, tente novamente", MessageAlerts.MessageType.DEFAULT);
                    }
                });

                return painelFiltro;
            }
            case "ocupacaoSalas" -> {
                painelFiltro.setPreferredSize(new Dimension(larguraGrafico, 50));
                Dimension comboBoxSize = new Dimension(larguraCheckbox, alturaCheckbox);

                JComboBox cbGraficoSalas = new JComboBox<>();

                JFreeChart chart = estatisticasRN.getGraficoOcupacaoSalaDados();

                CategoryDataset dataset = chart.getCategoryPlot().getDataset();
                if (dataset instanceof DefaultCategoryDataset dcd) {
                    cbGraficoSalas.addItem("Todos");
                    for (int i = 0; i < dcd.getColumnCount(); i++) {
                        String nomeSala = (String) dcd.getColumnKey(i);
                        cbGraficoSalas.addItem(nomeSala);
                    }
                }

                JButton btAplicar = new JButton("Aplicar");

                cbGraficoSalas.setPreferredSize(comboBoxSize);

                painelFiltro.add(cbGraficoSalas);
                painelFiltro.add(btAplicar);

                btAplicar.addActionListener(e -> {
                    String salaSelecionada = (String) cbGraficoSalas.getSelectedItem();

                    JPanel novoPainel = new JPanel(new BorderLayout());
                    novoPainel.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
                    novoPainel.setPreferredSize(new Dimension(larguraGrafico, alturaGrafico + 500));

                    novoPainel.add(painelFiltro, BorderLayout.NORTH);

                    novoPainel.add(painelFiltro, BorderLayout.NORTH);

                    ChartPanel painelGrafico = new ChartPanel(estatisticasRN.createSalaOcupacaoBarChartPanel(salaSelecionada));
                    painelGrafico.setBackground(Color.white);
                    painelGrafico.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");

                    novoPainel.add(painelGrafico, BorderLayout.CENTER);

                    containerGrafico.add(novoPainel, "Ocupação das Salas");
                    ((CardLayout) containerGrafico.getLayout()).show(containerGrafico, "Ocupação das Salas");

                    bottomPanel.revalidate();
                    bottomPanel.repaint();
                });

                return painelFiltro;
            }
            case "distribuicaoAtendimentos" -> {
                painelFiltro.setPreferredSize(new Dimension(larguraGrafico, 50));
                Dimension comboBoxSize = new Dimension(larguraCheckbox, alturaCheckbox);

                JComboBox cbGraficoSalas = new JComboBox<>();

                JFreeChart chart = estatisticasRN.getGraficoDistribuicaoAtendimentoDados();

                CategoryDataset dataset = chart.getCategoryPlot().getDataset();
                if (dataset instanceof DefaultCategoryDataset dcd) {
                    cbGraficoSalas.addItem("Todos");
                    for (int i = 0; i < dcd.getColumnCount(); i++) {
                        String nomeSala = (String) dcd.getColumnKey(i);
                        cbGraficoSalas.addItem(nomeSala);
                    }
                }

                JButton btAplicar = new JButton("Aplicar");

                cbGraficoSalas.setPreferredSize(comboBoxSize);

                painelFiltro.add(cbGraficoSalas);
                painelFiltro.add(btAplicar);

                btAplicar.addActionListener(e -> {
                    String salaSelecionada = (String) cbGraficoSalas.getSelectedItem();

                    JPanel novoPainel = new JPanel(new BorderLayout());
                    novoPainel.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
                    novoPainel.setPreferredSize(new Dimension(larguraGrafico, alturaGrafico + 500));

                    novoPainel.add(painelFiltro, BorderLayout.NORTH);

                    novoPainel.add(painelFiltro, BorderLayout.NORTH);

                    ChartPanel painelGrafico = new ChartPanel(estatisticasRN.createDistribuicaoAtendimentosBarChartPanel(salaSelecionada));
                    painelGrafico.setBackground(Color.white);
                    painelGrafico.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");

                    novoPainel.add(painelGrafico, BorderLayout.CENTER);

                    containerGrafico.add(novoPainel, "Distribuição Atendimentos");
                    ((CardLayout) containerGrafico.getLayout()).show(containerGrafico, "Distribuição Atendimentos");

                    bottomPanel.revalidate();
                    bottomPanel.repaint();
                });

                return painelFiltro;
            }
        }

        return painelFiltro;
    }

    private JPanel createStyledPanel(JFreeChart chart, String tipoGrafico) {
        ChartPanel painelGrafico = new ChartPanel(chart);
        painelGrafico.setBackground(Color.white);
        painelGrafico.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");

        conteinerGraficoIndividual = new JPanel(new BorderLayout());

        conteinerGraficoIndividual.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
        conteinerGraficoIndividual.setPreferredSize(new Dimension(larguraGrafico, alturaGrafico));

        conteinerGraficoIndividual.add(painelGrafico, BorderLayout.CENTER);

        if (!tipoGrafico.equals("")) {
            conteinerGraficoIndividual.add(criarFiltro(tipoGrafico), BorderLayout.NORTH);
        }

        return conteinerGraficoIndividual;
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

    public JPanel createPainelTodos() {
        JPanel painelTodos = new JPanel();
        painelTodos.setLayout(new BoxLayout(painelTodos, BoxLayout.Y_AXIS));
        painelTodos.setOpaque(false);

        painelTodos.add(createStyledPanel(estatisticasRN.createGenderPieChartPanel(null), ""));
        painelTodos.add(createStyledPanel(estatisticasRN.createEstadoCivilPieChartPanel(null), ""));
        painelTodos.add(createStyledPanel(estatisticasRN.createRacaCorEtniaPieChartPanel(null), ""));
        painelTodos.add(createStyledPanel(estatisticasRN.createEvolucaoAtendimentoChartPanel(), ""));
        painelTodos.add(createStyledPanel(estatisticasRN.createAgendadosVSRealizadosChartPanel(), ""));
        painelTodos.add(createStyledPanel(estatisticasRN.createPacientesListaEsperaChartPanel(), ""));
        painelTodos.add(createStyledPanel(estatisticasRN.createTempoMedioAtendimentoChartPanel(), ""));
        painelTodos.add(createStyledPanel(estatisticasRN.createPacienteIdadeBarChartPanel(), ""));
        painelTodos.add(createStyledPanel(estatisticasRN.createPacienteComparecimentoBarChartPanel(), ""));
        painelTodos.add(createStyledPanel(estatisticasRN.createSalaOcupacaoBarChartPanel(null), ""));
        painelTodos.add(createStyledPanel(estatisticasRN.createDistribuicaoAtendimentosBarChartPanel(null), ""));

        return painelTodos;
    }

    public JPanel createContainerGraficos(JPanel centerPanel) {
        String[] opcoesGrafico = {
            "Todos", "Gênero", "Estado Civil", "Raça/Cor/Etnia",
            "Evolução Atendimentos", "Agendados vs Realizados",
            "Lista de Espera", "Tempo Médio Atendimento",
            "Paciente por Idade", "Paciente Comparecimento",
            "Ocupação das Salas", "Distribuição Atendimentos"
        };

        this.cbGrafico = new JComboBox<>(opcoesGrafico);
        centerPanel.add(cbGrafico, "growx, wrap");

        this.containerGrafico = new JPanel(new CardLayout());
        containerGrafico.setOpaque(false);

        Map<String, JPanel> painelGrafico = new HashMap<>();

        //painelGrafico.put("Todos", createPainelTodos());
        painelGrafico.put("Gênero", createStyledPanel(estatisticasRN.createGenderPieChartPanel(null), "genero"));
        painelGrafico.put("Estado Civil", createStyledPanel(estatisticasRN.createEstadoCivilPieChartPanel(null), "estadoCivil"));
        painelGrafico.put("Raça/Cor/Etnia", createStyledPanel(estatisticasRN.createRacaCorEtniaPieChartPanel(null), "raca/cor/etnia"));
        //painelGrafico.put("Evolução Atendimentos", createStyledPanel(estatisticasRN.createEvolucaoAtendimentoChartPanel(), "evolucaoAtendimento"));
        painelGrafico.put("Agendados vs Realizados", createStyledPanel(estatisticasRN.createAgendadosVSRealizadosChartPanel(), "agendadosVSrealizados"));
        painelGrafico.put("Lista de Espera", createStyledPanel(estatisticasRN.createPacientesListaEsperaChartPanel(), "listaEspera"));
        //painelGrafico.put("Tempo Médio Atendimento", createStyledPanel(estatisticasRN.createTempoMedioAtendimentoChartPanel(), "tempoMedioAtendimento"));
        painelGrafico.put("Paciente por Idade", createStyledPanel(estatisticasRN.createPacienteIdadeBarChartPanel(), "pacienteIdade"));
        painelGrafico.put("Paciente Comparecimento", createStyledPanel(estatisticasRN.createPacienteComparecimentoBarChartPanel(), "pacienteComparecimento"));
        painelGrafico.put("Ocupação das Salas", createStyledPanel(estatisticasRN.createSalaOcupacaoBarChartPanel(null), "ocupacaoSalas"));
        //painelGrafico.put("Distribuição Atendimentos", createStyledPanel(estatisticasRN.createDistribuicaoAtendimentosBarChartPanel(null), "distribuicaoAtendimentos"));

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
