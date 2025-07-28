/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Regradenegocio;

import com.formdev.flatlaf.FlatClientProperties;
import com.raven.datechooser.DateChooser;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import net.miginfocom.swing.MigLayout;
import org.jfree.chart.ChartPanel;
import raven.alerts.MessageAlerts;

/**
 *
 * @author john
 */
public class GraficosFiltrarRN {

    private final int larguraGrafico = 465;
    private final int alturaGrafico = 400;
    private final int larguraCheckbox = 150;
    private final int alturaCheckbox = 20;

    public JPanel criarFiltro(String tipoGrafico, JPanel bottomPanel, JPanel containerGrafico, GraficosRN graficosRN) {
        JPanel painelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelFiltro.setOpaque(false);

        switch (tipoGrafico) {
            case "Gênero" -> {
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

                    ChartPanel painelGrafico = graficosRN.createGenderPieChartPanel(filtros);
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
            case "Estado Civil" -> {
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

                    ChartPanel painelGrafico = graficosRN.createEstadoCivilPieChartPanel(filtros);
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
            case "Raça/Cor/Etnia" -> {
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

                    ChartPanel painelGrafico = graficosRN.createRacaCorEtniaPieChartPanel(filtros);
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
            case "Evolução Atendimentos" -> {
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

                        ChartPanel painelGrafico = graficosRN.createEvolucaoAtendimentoChartPanel(dataInicio, dataFim, periodo);
                        painelGrafico.setBackground(Color.white);
                        painelGrafico.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");

                        novoPainel.add(painelGrafico, BorderLayout.CENTER);

                        containerGrafico.add(novoPainel, "Evolução Atendimentos");
                        ((CardLayout) containerGrafico.getLayout()).show(containerGrafico, "Evolução Atendimentos");

                        bottomPanel.revalidate();
                        bottomPanel.repaint();
                    } catch (ParseException ex) {
                        MessageAlerts.getInstance().showMessage("Falha!", "Erro ao escolher as datas, tente novamente", MessageAlerts.MessageType.DEFAULT);
                    }
                });

                return painelFiltro;
            }
            case "Agendados vs Realizados" -> {
                painelFiltro.setPreferredSize(new Dimension(larguraGrafico, 50));

                JTextField tfInicio = new JTextField(10);
                JTextField tfFim = new JTextField(10);

                DateChooser dateChooserInicio = new DateChooser();
                DateChooser dateChooserFim = new DateChooser();

                dateChooserInicio.setTextField(tfInicio);
                dateChooserFim.setTextField(tfFim);

                String[] periodos = {"Dia", "Semana", "Mês", "Ano"};
                JComboBox<String> cbPeriodo = new JComboBox<>(periodos);

                String[] tiposAtendimentos = {"Triagem", "Psicoterapia", "Emergencial", "Grupo"};
                JComboBox<String> cbTipoAtendimento = new JComboBox<>(tiposAtendimentos);

                JButton btAplicar = new JButton("Aplicar");

                painelFiltro.add(new JLabel("De:"));
                painelFiltro.add(tfInicio);

                painelFiltro.add(new JLabel("Até:"));
                painelFiltro.add(tfFim);

                painelFiltro.add(new JLabel("Período:"));
                painelFiltro.add(cbPeriodo);

                painelFiltro.add(new JLabel("Tipo:"));
                painelFiltro.add(cbTipoAtendimento);

                painelFiltro.add(btAplicar);

                btAplicar.addActionListener(e -> {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        LocalDate dataInicio = tfInicio.getText().isEmpty() ? null
                                : sdf.parse(tfInicio.getText()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                        LocalDate dataFim = tfFim.getText().isEmpty() ? null
                                : sdf.parse(tfFim.getText()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        String periodo = cbPeriodo.getSelectedItem().toString().toLowerCase();

                        String tipoAtendimento = cbTipoAtendimento.getSelectedItem().toString().toLowerCase();

                        JPanel novoPainel = new JPanel(new BorderLayout());
                        novoPainel.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
                        novoPainel.setPreferredSize(new Dimension(larguraGrafico, alturaGrafico + 500));

                        novoPainel.add(painelFiltro, BorderLayout.NORTH);

                        ChartPanel painelGrafico = graficosRN.createAgendadosVSRealizadosChartPanel(dataInicio, dataFim, periodo, tipoAtendimento);
                        painelGrafico.setBackground(Color.white);
                        painelGrafico.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");

                        novoPainel.add(painelGrafico, BorderLayout.CENTER);

                        containerGrafico.add(novoPainel, "Agendados vs Realizados");
                        ((CardLayout) containerGrafico.getLayout()).show(containerGrafico, "Agendados vs Realizados");

                        bottomPanel.revalidate();
                        bottomPanel.repaint();
                    } catch (ParseException ex) {
                        MessageAlerts.getInstance().showMessage("Falha!", "Erro ao escolher as datas, tente novamente", MessageAlerts.MessageType.DEFAULT);
                    }
                });

                return painelFiltro;
            }
            case "Lista de Espera" -> {
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

                        ChartPanel painelGrafico = graficosRN.createPacientesListaEsperaChartPanel(dataInicio, dataFim, periodo);
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
            case "Tempo Médio Atendimento" -> {
                painelFiltro.setPreferredSize(new Dimension(larguraGrafico, 50));

                JTextField tfInicio = new JTextField(10);
                JTextField tfFim = new JTextField(10);

                DateChooser dateChooserInicio = new DateChooser();
                DateChooser dateChooserFim = new DateChooser();

                dateChooserInicio.setTextField(tfInicio);
                dateChooserFim.setTextField(tfFim);

                String[] periodos = {"Dia", "Semana", "Mês", "Ano"};
                JComboBox<String> cbPeriodo = new JComboBox<>(periodos);

                String[] tiposAtendimentos = {"Triagem", "Psicoterapia", "Emergencial", "Grupo"};
                JComboBox<String> cbTipoAtendimento = new JComboBox<>(tiposAtendimentos);

                JButton btAplicar = new JButton("Aplicar");

                painelFiltro.add(new JLabel("De:"));
                painelFiltro.add(tfInicio);

                painelFiltro.add(new JLabel("Até:"));
                painelFiltro.add(tfFim);

                painelFiltro.add(new JLabel("Período:"));
                painelFiltro.add(cbPeriodo);

                painelFiltro.add(new JLabel("Tipo:"));
                painelFiltro.add(cbTipoAtendimento);

                painelFiltro.add(btAplicar);

                btAplicar.addActionListener(e -> {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        LocalDate dataInicio = tfInicio.getText().isEmpty() ? null
                                : sdf.parse(tfInicio.getText()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                        LocalDate dataFim = tfFim.getText().isEmpty() ? null
                                : sdf.parse(tfFim.getText()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        String periodo = cbPeriodo.getSelectedItem().toString().toLowerCase();

                        String tipoAtendimento = cbTipoAtendimento.getSelectedItem().toString().toLowerCase();

                        JPanel novoPainel = new JPanel(new BorderLayout());
                        novoPainel.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
                        novoPainel.setPreferredSize(new Dimension(larguraGrafico, alturaGrafico + 500));

                        novoPainel.add(painelFiltro, BorderLayout.NORTH);

                        ChartPanel painelGrafico = graficosRN.createTempoMedioAtendimentoChartPanel(dataInicio, dataFim, periodo, tipoAtendimento);
                        painelGrafico.setBackground(Color.white);
                        painelGrafico.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");

                        novoPainel.add(painelGrafico, BorderLayout.CENTER);

                        containerGrafico.add(novoPainel, "Tempo Médio de Atendimento");
                        ((CardLayout) containerGrafico.getLayout()).show(containerGrafico, "Tempo Médio de Atendimento");

                        bottomPanel.revalidate();
                        bottomPanel.repaint();
                    } catch (ParseException ex) {
                        MessageAlerts.getInstance().showMessage("Falha!", "Erro ao escolher as datas, tente novamente", MessageAlerts.MessageType.DEFAULT);
                    }
                });

                return painelFiltro;
            }
            case "Paciente por Idade" -> {
                painelFiltro.setPreferredSize(new Dimension(larguraGrafico, 300));
                painelFiltro.setLayout(new MigLayout("wrap 1", "[grow]"));

                List<JSpinner[]> spinners = new ArrayList<>();

                for (int i = 0; i < 3; i++) {
                    JPanel faixaPanel = new JPanel(new MigLayout("insets 5", "[][grow]10[][grow]", ""));

                    faixaPanel.setBorder(BorderFactory.createTitledBorder("Faixa Etária " + (i + 1)));

                    faixaPanel.add(new JLabel("Início:"), "right");
                    JSpinner spinnerInicio = new JSpinner(new SpinnerNumberModel(0, 0, 150, 1));
                    faixaPanel.add(spinnerInicio, "growx");

                    faixaPanel.add(new JLabel("Fim:"), "right");
                    JSpinner spinnerFim = new JSpinner(new SpinnerNumberModel(0, 0, 150, 1));
                    faixaPanel.add(spinnerFim, "growx");

                    spinners.add(new JSpinner[]{spinnerInicio, spinnerFim});
                    painelFiltro.add(faixaPanel, "growx");
                }

                JButton btAplicar = new JButton("Aplicar");
                painelFiltro.add(btAplicar, "align center");

                btAplicar.addActionListener(e -> {
                    List<int[]> faixas = new ArrayList<>();

                    for (JSpinner[] spinnerPar : spinners) {
                        int inicio = (int) spinnerPar[0].getValue();
                        int fim = (int) spinnerPar[1].getValue();

                        if (inicio < fim) {
                            faixas.add(new int[]{inicio, fim});
                        }
                    }

                    JPanel novoPainel = new JPanel(new BorderLayout());
                    novoPainel.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
                    novoPainel.setPreferredSize(new Dimension(larguraGrafico, alturaGrafico));

                    novoPainel.add(painelFiltro, BorderLayout.NORTH);

                    ChartPanel painelGrafico = graficosRN.createPacienteIdadeBarChartPanel(faixas);
                    painelGrafico.setBackground(Color.white);
                    painelGrafico.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");

                    novoPainel.add(painelGrafico, BorderLayout.SOUTH);

                    containerGrafico.add(novoPainel, "Paciente por Idade");
                    ((CardLayout) containerGrafico.getLayout()).show(containerGrafico, "Paciente por Idade");

                    bottomPanel.revalidate();
                    bottomPanel.repaint();
                });

                return painelFiltro;
            }
            case "Paciente Comparecimento" -> {
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

                        ChartPanel painelGrafico = graficosRN.createPacienteComparecimentoBarChartPanel(dataInicio, dataFim, periodo);
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
            case "Ocupação das Salas" -> {
                painelFiltro.removeAll();
                painelFiltro.setPreferredSize(new Dimension(larguraGrafico, 50));

                // --- 1. Intervalo de datas ---
                JTextField tfInicio = new JTextField(8);
                JTextField tfFim = new JTextField(8);
                DateChooser dateChooserInicio = new DateChooser();
                DateChooser dateChooserFim = new DateChooser();
                dateChooserInicio.setTextField(tfInicio);
                dateChooserFim.setTextField(tfFim);

                painelFiltro.add(new JLabel("De:"));
                painelFiltro.add(tfInicio);
                painelFiltro.add(new JLabel("Até:"));
                painelFiltro.add(tfFim);

                // --- 2. Turno ---
                String[] turnos = {"Todos", "Manhã", "Tarde"};
                JComboBox<String> cbTurno = new JComboBox<>(turnos);
                cbTurno.setPreferredSize(new Dimension(90, alturaCheckbox));
                painelFiltro.add(new JLabel("Turno:"));
                painelFiltro.add(cbTurno);

                // Botão aplicar
                JButton btAplicar = new JButton("Aplicar");
                painelFiltro.add(btAplicar);

                btAplicar.addActionListener(e -> {
                    // Parse das datas
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate inicio;
                    LocalDate fim;
                    try {
                        inicio = tfInicio.getText().isEmpty()
                                ? LocalDate.of(1970, 1, 1)
                                : LocalDate.parse(tfInicio.getText(), dtf);
                        fim = tfFim.getText().isEmpty()
                                ? LocalDate.now()
                                : LocalDate.parse(tfFim.getText(), dtf);
                    } catch (DateTimeParseException ex) {
                        MessageAlerts.getInstance().showMessage(
                                "Data inválida", "Use o formato dd-MM-aaaa.", MessageAlerts.MessageType.ERROR
                        );
                        return;
                    }

                    // Cálculo de horas pelo turno
                    String turnoSel = cbTurno.getSelectedItem().toString();
                    int horaIni = 0, horaFim = 23;
                    switch (turnoSel) {
                        case "Manhã":
                            horaIni = 8;
                            horaFim = 11;
                            break;
                        case "Tarde":
                            horaIni = 12;
                            horaFim = 17;
                            break;
                    }

                    // Gera o gráfico com os filtros aplicados
                    ChartPanel painelGrafico = graficosRN.createSalaOcupacaoBarChartPanel(
                            inicio, fim, horaIni, horaFim
                    );

                    // Atualiza o painel principal
                    JPanel novoPainel = new JPanel(new BorderLayout());
                    novoPainel.putClientProperty(FlatClientProperties.STYLE,
                            "border:5,5,5,5,$Component.borderColor,,20");
                    novoPainel.setPreferredSize(
                            new Dimension(larguraGrafico, alturaGrafico + 500)
                    );
                    novoPainel.add(painelFiltro, BorderLayout.NORTH);
                    painelGrafico.setBackground(Color.WHITE);
                    novoPainel.add(painelGrafico, BorderLayout.CENTER);

                    containerGrafico.add(novoPainel, "Ocupação das Salas");
                    ((CardLayout) containerGrafico.getLayout())
                            .show(containerGrafico, "Ocupação das Salas");
                    bottomPanel.revalidate();
                    bottomPanel.repaint();
                });

                return painelFiltro;
            }
        }

        return painelFiltro;
    }
}
