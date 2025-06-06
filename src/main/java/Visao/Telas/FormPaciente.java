package Visao.Telas;

import Persistencia.Dao.*;
import Persistencia.Entity.*;
import Persistencia.modelTemp.EnderecoModelCepApi;
import Regradenegocio.*;
import Services.ViaCepService;
import VO.*;
import Visao.Components.SimpleForm;
import Visao.JframeManager.FormManager;
import Visao.Utils.RedimencionarIcones;
import Visao.Utils.EditorTextPaneEstilization;
import Visao.Utils.MessagesAlert;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.HeadlessException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import raven.toast.Notifications;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
/**
 *
 * @author john
 */
public class FormPaciente extends SimpleForm {

    private Endereco enderecoObject;
    private int pacienteId;

    /**
     * Creates new form telaCadastroPacientePanel
     */
    public FormPaciente() {
        initComponents();
        // remove background customizado e volta ao default do L&F
        pCentro.putClientProperty(FlatClientProperties.STYLE, "background:null");
        pInscricao.putClientProperty(FlatClientProperties.STYLE, "background:null");
        pEndereco.putClientProperty(FlatClientProperties.STYLE, "background:null");
        pIdentificacao.putClientProperty(FlatClientProperties.STYLE, "background:null");
        //redimensionarIcones();

        inserirDadosFormulario();

        RedimencionarIcones redimencionarIcone = new RedimencionarIcones();
        redimencionarIcone.redimensionarIcones(btSalvar, "/Multimidia/imagens/approved-icon.png", 40);
        redimencionarIcone.redimensionarIcones(btEditar, "/Multimidia/imagens/editar-btn.png", 40);

        EditorTextPaneEstilization.EstilizeEditorTextPane(tpDisponibilidade);
        EditorTextPaneEstilization.JTextComponentStylization(tpDisponibilidade, btNegrito, btItalico, btSublinhado);
        EditorTextPaneEstilization.JTextComponentUndoRedo(tpDisponibilidade);

        cbbGrupos.setVisible(false);
        cbCriarGrupo.setVisible(false);
        mostrarCamposCriacaoGrupo(false);

        configurarComponentesGrupo();
    }

    public void preencherDadosFormulario(int pacienteId) {
        this.pacienteId = pacienteId;

        try {
            PacienteRN pacienteRN = new PacienteRN();
            PacienteVO paciente = pacienteRN.buscarPacientePorId(pacienteId);

            if (paciente == null) {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Paciente não encontrado!");
                return;
            }

            tfPaciente.setText(paciente.getNome());
            cbGenero.setSelectedItem(paciente.getGenero());
            ftfCelular.setText(paciente.getCelular());
            ftfCelularContato.setText(paciente.getCelularContato());
            tfData.setText(paciente.getDataNascimento());
            cbInstrucao.setSelectedItem(paciente.getInstrucao());
            tfProfissao.setText(paciente.getProfissao());
            cbEstadoCivil.setSelectedItem(paciente.getEstadoCivil());
            cbRacaCorEtnia.setSelectedItem(paciente.getRaca_cor_etnia());
            cbOrientacao.setSelectedItem(paciente.getOrientacao());
            cbNacionalidade.setSelectedItem(paciente.getNacionalidade());
            tpDisponibilidade.setText(paciente.getDisponibilidade());

            if (paciente.getResponsavel() != null) {
                tfResponsavel.setText(paciente.getResponsavel().getNome());
                ftfCelularContatoResponsavel.setText(paciente.getResponsavel().getTelefone());
            }

            cbAtendido.setSelected(paciente.isAtendido());

            if (paciente.getEndereco() != null) {
                Endereco endereco = paciente.getEndereco();

                tfRua.setText(endereco.getRua());
                tfNumero.setText(String.valueOf(endereco.getNumero()));
                tfBairro.setText(endereco.getBairro());
                tfComplemento.setText(endereco.getComplemento());
                ftfCep.setText(endereco.getCep());

                Cidade cidade = endereco.getCidade();
                if (cidade != null) {
                    cbCidade.setSelectedItem(cidade.getId() + " - " + cidade.getNome());
                }
            }

            btEditar.setEnabled(true);
            btEditar.setVisible(true);
            btSalvar.setEnabled(false);
            btSalvar.setVisible(false);

        } catch (Exception ex) {
            System.out.println("Erro ao editar paciente: " + ex);
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Erro ao carregar dados do paciente!");
        }
    }

    public void inserirDadosFormulario() {
        PaisDAO paisDaoInstancia = new PaisDAO();
        CidadeDAO cidadeDaoInstancia = new CidadeDAO();
        EstadoDAO estadoDaoInstancia = new EstadoDAO();
        OrientadorRN orientadorRn = new OrientadorRN();
        EstagiarioRN estagiarioRn = new EstagiarioRN();

        List<Pais> todosPaises = paisDaoInstancia.buscarTodos();
        List<Cidade> todasCidades = cidadeDaoInstancia.buscarTodos();
        List<Estado> todosEstados = estadoDaoInstancia.buscarTodos();

        List<OrientadorVO> todosOrientadores = orientadorRn.listarOrientadores();
        List<EstagiarioVO> todosEstagiarios = estagiarioRn.listarEstagiarios();

        cbNacionalidade.removeAllItems();
        cbEstado.removeAllItems();
        cbCidade.removeAllItems();

        cbEstagiario.removeAllItems();
        cbOrientador.removeAllItems();

        // Add Paises to Nacionalidade ComboBox
        for (Pais pais : todosPaises) {
            cbNacionalidade.addItem(String.valueOf(pais.getId()) + " - " + pais.getNome());
        }

        // Add Estados to Estado ComboBox
        for (Estado estado : todosEstados) {
            cbEstado.addItem(String.valueOf(estado.getId()) + " - " + estado.getNome());
        }

        // Add Cidades to Cidade ComboBox
        for (Cidade cidade : todasCidades) {
            cbCidade.addItem(String.valueOf(cidade.getId()) + " - " + cidade.getNome());
        }

        // Add Orientadores to Orientador ComboBox
        for (OrientadorVO orientador : todosOrientadores) {
            cbOrientador.addItem(String.valueOf(orientador.getId()) + " - " + orientador.getNomeCompleto());
        }

        // Add Estagiarios to Estagiario ComboBox
        for (EstagiarioVO estagiario : todosEstagiarios) {
            cbEstagiario.addItem(String.valueOf(estagiario.getId()) + " - " + estagiario.getNomeCompleto());
        }

        btEditar.setEnabled(false);
        btEditar.setVisible(false);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateChooser1 = new com.raven.datechooser.DateChooser();
        pNorth = new javax.swing.JPanel();
        lbClinica = new javax.swing.JLabel();
        lbProntuario = new javax.swing.JLabel();
        lbLogoCadastro = new javax.swing.JLabel();
        pCentro = new javax.swing.JPanel();
        pIdentificacao = new javax.swing.JPanel();
        lbGenero = new javax.swing.JLabel();
        cbGenero = new javax.swing.JComboBox<>();
        lbContato2 = new javax.swing.JLabel();
        ftfCelularContato = new javax.swing.JFormattedTextField();
        ftfCelular = new javax.swing.JFormattedTextField();
        lbCelular = new javax.swing.JLabel();
        tfPaciente = new javax.swing.JTextField();
        lbPaciente = new javax.swing.JLabel();
        lbDataNascimento = new javax.swing.JLabel();
        tfData = new javax.swing.JTextField();
        lbInstrucao = new javax.swing.JLabel();
        cbInstrucao = new javax.swing.JComboBox<>();
        lbProfissao = new javax.swing.JLabel();
        tfProfissao = new javax.swing.JTextField();
        cbEstadoCivil = new javax.swing.JComboBox<>();
        lbEstadoCivil = new javax.swing.JLabel();
        cbRacaCorEtnia = new javax.swing.JComboBox<>();
        lbRaca = new javax.swing.JLabel();
        lbOrientacao = new javax.swing.JLabel();
        cbOrientacao = new javax.swing.JComboBox<>();
        lbContato3 = new javax.swing.JLabel();
        ftfCelularContatoResponsavel = new javax.swing.JFormattedTextField();
        pEndereco = new javax.swing.JPanel();
        lbCidade = new javax.swing.JLabel();
        lbRua = new javax.swing.JLabel();
        tfRua = new javax.swing.JTextField();
        tfBairro = new javax.swing.JTextField();
        lbBairro = new javax.swing.JLabel();
        lbNumero = new javax.swing.JLabel();
        lbNaturalidade1 = new javax.swing.JLabel();
        cbEstado = new javax.swing.JComboBox<>();
        lbCep = new javax.swing.JLabel();
        ftfCep = new javax.swing.JFormattedTextField();
        tfComplemento = new javax.swing.JTextField();
        lbComplemento = new javax.swing.JLabel();
        lbEstado = new javax.swing.JLabel();
        cbNacionalidade = new javax.swing.JComboBox<>();
        cbCidade = new javax.swing.JComboBox<>();
        lbNaturalidade = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        btBuscarCep = new javax.swing.JButton();
        tfNumero = new javax.swing.JTextField();
        pBotoes = new javax.swing.JPanel();
        pInscricao = new javax.swing.JPanel();
        lbDisponibilidade = new javax.swing.JLabel();
        cbEstagiario = new javax.swing.JComboBox<>();
        lbEstagiario = new javax.swing.JLabel();
        lbResponsavel = new javax.swing.JLabel();
        cbOrientador = new javax.swing.JComboBox<>();
        lbOrientador = new javax.swing.JLabel();
        cbAtendido = new javax.swing.JCheckBox();
        lbAtendido = new javax.swing.JLabel();
        tfResponsavel = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tpDisponibilidade = new javax.swing.JTextPane();
        btNegrito = new javax.swing.JButton();
        btItalico = new javax.swing.JButton();
        btSublinhado = new javax.swing.JButton();
        btSalvar = new javax.swing.JButton();
        btEditar = new javax.swing.JButton();
        pGrupo = new javax.swing.JPanel();
        cbVerificaGrupo = new javax.swing.JCheckBox();
        cbbGrupos = new javax.swing.JComboBox<>();
        cbCriarGrupo = new javax.swing.JCheckBox();
        lbDescricaoGrupo = new javax.swing.JLabel();
        lbNomeGrupo = new javax.swing.JLabel();
        tfNomeGrupo = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        taDescricaoGrupo = new javax.swing.JTextArea();
        jSeparator1 = new javax.swing.JSeparator();
        btnSalvarGrupo = new javax.swing.JButton();

        dateChooser1.setTextField(tfData);

        setMaximumSize(new java.awt.Dimension(1024, 1450));
        setMinimumSize(new java.awt.Dimension(1024, 1450));
        setPreferredSize(new java.awt.Dimension(1024, 1900));
        setLayout(new java.awt.BorderLayout());

        pNorth.setBackground(new java.awt.Color(0, 102, 102));
        pNorth.setMaximumSize(new java.awt.Dimension(1024, 130));
        pNorth.setMinimumSize(new java.awt.Dimension(1024, 130));

        lbClinica.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lbClinica.setForeground(new java.awt.Color(255, 255, 255));
        lbClinica.setText("Clínica de Psicologia");

        lbProntuario.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        lbProntuario.setForeground(new java.awt.Color(255, 255, 255));
        lbProntuario.setText("Cadastro de Paciente");

        lbLogoCadastro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Multimidia/imagens/logoPreCadastro_resized.png"))); // NOI18N

        javax.swing.GroupLayout pNorthLayout = new javax.swing.GroupLayout(pNorth);
        pNorth.setLayout(pNorthLayout);
        pNorthLayout.setHorizontalGroup(
            pNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pNorthLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbLogoCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbClinica)
                    .addComponent(lbProntuario))
                .addGap(558, 558, 558))
        );
        pNorthLayout.setVerticalGroup(
            pNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pNorthLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lbProntuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbClinica)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pNorthLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbLogoCadastro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(pNorth, java.awt.BorderLayout.PAGE_START);

        pCentro.setBackground(java.awt.SystemColor.controlHighlight);
        pCentro.setAutoscrolls(true);
        pCentro.setPreferredSize(new java.awt.Dimension(1024, 768));

        pIdentificacao.setBackground(java.awt.SystemColor.controlHighlight);
        pIdentificacao.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Identificação", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, btSalvar.getFont(), java.awt.SystemColor.controlDkShadow));
        pIdentificacao.setForeground(java.awt.SystemColor.controlHighlight);
        pIdentificacao.setMaximumSize(new java.awt.Dimension(950, 450));
        pIdentificacao.setMinimumSize(new java.awt.Dimension(950, 450));
        pIdentificacao.setPreferredSize(new java.awt.Dimension(950, 450));

        lbGenero.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbGenero.setForeground(new java.awt.Color(0, 102, 102));
        lbGenero.setText("*Gênero:");

        cbGenero.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        cbGenero.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Feminino", "Não-Binário", "Outro", "Prefiro não informar" }));

        lbContato2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbContato2.setForeground(new java.awt.Color(0, 102, 102));
        lbContato2.setText("Celular (Recado):");

        try {
            ftfCelularContato.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) #####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        ftfCelularContato.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        try {
            ftfCelular.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) #####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        ftfCelular.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        lbCelular.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbCelular.setForeground(new java.awt.Color(0, 102, 102));
        lbCelular.setText("*Celular:");

        tfPaciente.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        tfPaciente.setToolTipText("ex. Rafael da Silva");

        lbPaciente.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbPaciente.setForeground(new java.awt.Color(0, 102, 102));
        lbPaciente.setText("*Paciente:");

        lbDataNascimento.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbDataNascimento.setForeground(new java.awt.Color(0, 102, 102));
        lbDataNascimento.setText("*Data de Nascimento:");

        tfData.setFont(tfPaciente.getFont());

        lbInstrucao.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbInstrucao.setForeground(new java.awt.Color(0, 102, 102));
        lbInstrucao.setText("Grau de Instrução:");

        cbInstrucao.setFont(cbGenero.getFont());
        cbInstrucao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Analfabeto", "Ensino Fundamental incompleto", "Ensino Fundamental completo", "Ensino Médio incompleto", "Ensino Médio completo", "Ensino Técnico", "Superior incompleto", "Superior completo", "Pós-graduação", "Mestrado", "Doutorado", "Pós-doutorado" }));

        lbProfissao.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbProfissao.setForeground(new java.awt.Color(0, 102, 102));
        lbProfissao.setText("Profissão:");

        tfProfissao.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        tfProfissao.setToolTipText("ex. Rafael da Silva");

        cbEstadoCivil.setFont(cbGenero.getFont());
        cbEstadoCivil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Solteiro(a)", "Casado(a)", "Divorciado(a)", "Viúvo(a)", "União Estável", "Separado(a)" }));

        lbEstadoCivil.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbEstadoCivil.setForeground(new java.awt.Color(0, 102, 102));
        lbEstadoCivil.setText("Estado Civil:");

        cbRacaCorEtnia.setFont(cbGenero.getFont());
        cbRacaCorEtnia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Negro(a)", "Branco(a)", "Amarelo(a)", "Pardo(a)", "Indígena" }));

        lbRaca.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbRaca.setForeground(new java.awt.Color(0, 102, 102));
        lbRaca.setText("Raça/Cor/Etnia:");

        lbOrientacao.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbOrientacao.setForeground(new java.awt.Color(0, 102, 102));
        lbOrientacao.setText("Orientação Sexual:");

        cbOrientacao.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        cbOrientacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Heterossexual", "Homossexual", "Bissexual", "Assexual", "Pansexual", "Demissexual", "Queer", "Omnissexual" }));

        lbContato3.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbContato3.setForeground(new java.awt.Color(0, 102, 102));
        lbContato3.setText("Telefone (Responsável):");

        try {
            ftfCelularContatoResponsavel.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) #####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        ftfCelularContatoResponsavel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        javax.swing.GroupLayout pIdentificacaoLayout = new javax.swing.GroupLayout(pIdentificacao);
        pIdentificacao.setLayout(pIdentificacaoLayout);
        pIdentificacaoLayout.setHorizontalGroup(
            pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pIdentificacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pIdentificacaoLayout.createSequentialGroup()
                            .addGroup(pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lbPaciente, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lbCelular, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(ftfCelular, javax.swing.GroupLayout.Alignment.LEADING))
                            .addGap(12, 12, 12)
                            .addGroup(pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lbContato2)
                                .addComponent(ftfCelularContato, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(14, 14, 14)
                            .addGroup(pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lbContato3)
                                .addComponent(ftfCelularContatoResponsavel)))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pIdentificacaoLayout.createSequentialGroup()
                            .addComponent(tfPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(tfData, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbDataNascimento, javax.swing.GroupLayout.Alignment.LEADING))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lbGenero)
                                .addComponent(cbGenero, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pIdentificacaoLayout.createSequentialGroup()
                        .addGroup(pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbOrientacao)
                            .addComponent(cbOrientacao, 0, 265, Short.MAX_VALUE)
                            .addComponent(lbEstadoCivil)
                            .addComponent(cbEstadoCivil, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbRaca)
                            .addComponent(cbRacaCorEtnia, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pIdentificacaoLayout.createSequentialGroup()
                                .addGroup(pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfProfissao, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbProfissao))
                                .addGap(18, 18, 18)
                                .addGroup(pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbInstrucao)
                                    .addComponent(cbInstrucao, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        pIdentificacaoLayout.setVerticalGroup(
            pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pIdentificacaoLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbDataNascimento)
                    .addComponent(lbPaciente)
                    .addComponent(lbGenero))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tfData, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tfPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pIdentificacaoLayout.createSequentialGroup()
                        .addComponent(lbCelular)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ftfCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pIdentificacaoLayout.createSequentialGroup()
                        .addGroup(pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbContato3)
                            .addComponent(lbContato2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ftfCelularContatoResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ftfCelularContato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tfProfissao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pIdentificacaoLayout.createSequentialGroup()
                        .addComponent(lbInstrucao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbInstrucao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pIdentificacaoLayout.createSequentialGroup()
                        .addGroup(pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbOrientacao)
                            .addComponent(lbProfissao))
                        .addGap(12, 12, 12)
                        .addComponent(cbOrientacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24)
                .addGroup(pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pIdentificacaoLayout.createSequentialGroup()
                        .addComponent(lbEstadoCivil)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pIdentificacaoLayout.createSequentialGroup()
                        .addComponent(lbRaca)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbRacaCorEtnia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pEndereco.setBackground(java.awt.SystemColor.controlHighlight);
        pEndereco.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Endereço", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, cbEstagiario.getFont(), java.awt.SystemColor.controlDkShadow));
        pEndereco.setMaximumSize(new java.awt.Dimension(950, 420));
        pEndereco.setMinimumSize(new java.awt.Dimension(950, 420));
        pEndereco.setPreferredSize(new java.awt.Dimension(950, 420));

        lbCidade.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbCidade.setForeground(new java.awt.Color(0, 102, 102));
        lbCidade.setText("*Cidade:");

        lbRua.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbRua.setForeground(new java.awt.Color(0, 102, 102));
        lbRua.setText("*Rua:");

        tfRua.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        tfRua.setToolTipText("ex. Marechal Rondon");

        tfBairro.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        tfBairro.setToolTipText("ex. Centro");

        lbBairro.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbBairro.setForeground(new java.awt.Color(0, 102, 102));
        lbBairro.setText("*Bairro:");

        lbNumero.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbNumero.setForeground(new java.awt.Color(0, 102, 102));
        lbNumero.setText("*Número:");

        lbNaturalidade1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbNaturalidade1.setForeground(new java.awt.Color(0, 102, 102));
        lbNaturalidade1.setText("*Nacionalidade:");

        cbEstado.setFont(cbGenero.getFont());
        cbEstado.setMaximumSize(new java.awt.Dimension(128, 39));
        cbEstado.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbEstadoItemStateChanged(evt);
            }
        });

        lbCep.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbCep.setForeground(new java.awt.Color(0, 102, 102));
        lbCep.setText("*CEP:");

        try {
            ftfCep.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.###-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        ftfCep.setFont(ftfCelular.getFont());

        tfComplemento.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        tfComplemento.setToolTipText("ex. Centro");

        lbComplemento.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbComplemento.setForeground(new java.awt.Color(0, 102, 102));
        lbComplemento.setText("Complemento:");

        lbEstado.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbEstado.setForeground(new java.awt.Color(0, 102, 102));
        lbEstado.setText("*Estado:");

        cbNacionalidade.setFont(cbGenero.getFont());
        cbNacionalidade.setMaximumSize(new java.awt.Dimension(128, 39));

        cbCidade.setFont(ftfCelularContato.getFont());

        lbNaturalidade.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbNaturalidade.setForeground(new java.awt.Color(0, 102, 102));
        lbNaturalidade.setText("*Naturalidade:");

        jComboBox1.setFont(cbGenero.getFont());
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Abadia de Goiás (GO)", "Abadia dos Dourados (MG)", "Abadiânia (GO)", "Abaeté (MG)", "Abaetetuba (PA)", "Abaiara (CE)", "Abaíra (BA)", "Abaré (BA)", "Abatiá (PR)", "Abdon Batista (SC)", "Abelardo Luz (SC)", "Abel Figueiredo (PA)", "Abre Campo (MG)", "Abreu e Lima (PE)", "Abreulândia (TO)", "Acaiaca (MG)", "Açailândia (MA)", "Acajutiba (BA)", "Acará (PA)", "Acarape (CE)", "Acaraú (CE)", "Acari (RN)", "Acauã (PI)", "Aceguá (RS)", "Acopiara (CE)", "Acorizal (MT)", "Acrelândia (AC)", "Acreúna (GO)", "Açucena (MG)", "Adamantina (SP)", "Adelândia (GO)", "Adolfo (SP)", "Adrianópolis (PR)", "Adustina (BA)", "Afogados da Ingazeira (PE)", "Afonso Bezerra (RN)", "Afonso Cláudio (ES)", "Afonso Cunha (MA)", "Afrânio (PE)", "Afuá (PA)", "Agrestina (PE)", "Agricolândia (PI)", "Agrolândia (SC)", "Agronômica (SC)", "Água Azul do Norte (PA)", "Água Boa (MG)", "Água Boa (MT)", "Água Branca (AL)", "Água Branca (PB)", "Água Branca (PI)", "Água Clara (MS)", "Água Comprida (MG)", "Água Doce (SC)", "Água Doce do Maranhão (MA)", "Água Doce do Norte (ES)", "Água Fria (BA)", "Água Fria de Goiás (GO)", "Aguaí (SP)", "Água Limpa (GO)", "Aguanil (MG)", "Água Nova (RN)", "Água Preta (PE)", "Água Santa (RS)", "Águas Belas (PE)", "Águas da Prata (SP)", "Águas de Chapecó (SC)", "Águas de Lindóia (SP)", "Águas de Santa Bárbara (SP)", "Águas de São Pedro (SP)", "Águas Formosas (MG)", "Águas Frias (SC)", "Águas Lindas de Goiás (GO)", "Águas Mornas (SC)", "Águas Vermelhas (MG)", "Agudo (RS)", "Agudos (SP)", "Agudos do Sul (PR)", "Águia Branca (ES)", "Aguiar (PB)", "Aguiarnópolis (TO)", "Aimorés (MG)", "Aiquara (BA)", "Aiuaba (CE)", "Aiuruoca (MG)", "Ajuricaba (RS)", "Alagoa (MG)", "Alagoa Grande (PB)", "Alagoa Nova (PB)", "Alagoinha (PB)", "Alagoinha (PE)", "Alagoinha do Piauí (PI)", "Alagoinhas (BA)", "Alambari (SP)", "Albertina (MG)", "Alcântara (MA)", "Alcântaras (CE)", "Alcantil (PB)", "Alcinópolis (MS)", "Alcobaça (BA)", "Aldeias Altas (MA)", "Alecrim (RS)", "Alegre (ES)", "Alegrete (RS)", "Alegrete do Piauí (PI)", "Alegria (RS)", "Além Paraíba (MG)", "Alenquer (PA)", "Alexandria (RN)", "Alexânia (GO)", "Alfenas (MG)", "Alfredo Chaves (ES)", "Alfredo Marcondes (SP)", "Alfredo Vasconcelos (MG)", "Alfredo Wagner (SC)", "Algodão de Jandaíra (PB)", "Alhandra (PB)", "Aliança (PE)", "Aliança do Tocantins (TO)", "Almadina (BA)", "Almas (TO)", "Almeirim (PA)", "Almenara (MG)", "Almino Afonso (RN)", "Almirante Tamandaré (PR)", "Almirante Tamandaré do Sul (RS)", "Aloândia (GO)", "Alpercata (MG)", "Alpestre (RS)", "Alpinópolis (MG)", "Alta Floresta (MT)", "Alta Floresta d'Oeste (RO)", "Altair (SP)", "Altamira (PA)", "Altamira do Maranhão (MA)", "Altamira do Paraná (PR)", "Altaneira (CE)", "Alterosa (MG)", "Altinho (PE)", "Altinópolis (SP)", "Alto Alegre (RR)", "Alto Alegre (RS)", "Alto Alegre (SP)", "Alto Alegre do Maranhão (MA)", "Alto Alegre do Pindaré (MA)", "Alto Alegre dos Parecis (RO)", "Alto Araguaia (MT)", "Alto Bela Vista (SC)", "Alto da Boa Vista (MT)", "Alto Caparaó (MG)", "Alto do Rodrigues (RN)", "Alto Feliz (RS)", "Alto Garças (MT)", "Alto Horizonte (GO)", "Alto Jequitibá (MG)", "Alto Longá (PI)", "Altônia (PR)", "Alto Paraguai (MT)", "Alto Paraíso (PR)", "Alto Paraíso (RO)", "Alto Paraíso de Goiás (GO)", "Alto Paraná (PR)", "Alto Parnaíba (MA)", "Alto Piquiri (PR)", "Alto Rio Doce (MG)", "Alto Rio Novo (ES)", "Altos (PI)", "Alto Santo (CE)", "Alto Taquari (MT)", "Alumínio (SP)", "Alvarães (AM)", "Alvarenga (MG)", "Álvares Florence (SP)", "Álvares Machado (SP)", "Álvaro de Carvalho (SP)", "Alvinlândia (SP)", "Alvinópolis (MG)", "Alvorada (RS)", "Alvorada (TO)", "Alvorada de Minas (MG)", "Alvorada d'Oeste (RO)", "Alvorada do Gurgueia (PI)", "Alvorada do Norte (GO)", "Alvorada do Sul (PR)", "Amajari (RR)", "Amambai (MS)", "Amapá (AP)", "Amapá do Maranhão (MA)", "Amaporã (PR)", "Amaraji (PE)", "Amaral Ferrador (RS)", "Amaralina (GO)", "Amarante (PI)", "Amarante do Maranhão (MA)", "Amargosa (BA)", "Amaturá (AM)", "Amélia Rodrigues (BA)", "América Dourada (BA)", "Americana (SP)", "Americano do Brasil (GO)", "Américo Brasiliense (SP)", "Américo de Campos (SP)", "Ametista do Sul (RS)", "Amontada (CE)", "Amorinópolis (GO)", "Amparo (PB)", "Amparo (SP)", "Amparo do São Francisco (SE)", "Amparo do Serra (MG)", "Ampére (PR)", "Anadia (AL)", "Anagé (BA)", "Anahy (PR)", "Anajás (PA)", "Anajatuba (MA)", "Analândia (SP)", "Anamã (AM)", "Ananás (TO)", "Ananindeua (PA)", "Anápolis (GO)", "Anapu (PA)", "Anapurus (MA)", "Anastácio (MS)", "Anaurilândia (MS)", "Anchieta (ES)", "Anchieta (SC)", "Andaraí (BA)", "Andirá (PR)", "Andorinha (BA)", "Andradas (MG)", "Andradina (SP)", "André da Rocha (RS)", "Andrelândia (MG)", "Angatuba (SP)", "Angelândia (MG)", "Angélica (MS)", "Angelim (PE)", "Angelina (SC)", "Angical (BA)", "Angical do Piauí (PI)", "Angico (TO)", "Angicos (RN)", "Angra dos Reis (RJ)", "Anguera (BA)", "Ângulo (PR)", "Anhanguera (GO)", "Anhembi (SP)", "Anhumas (SP)", "Anicuns (GO)", "Anísio de Abreu (PI)", "Anita Garibaldi (SC)", "Anitápolis (SC)", "Anori (AM)", "Anta Gorda (RS)", "Antas (BA)", "Antonina (PR)", "Antonina do Norte (CE)", "Antônio Almeida (PI)", "Antônio Cardoso (BA)", "Antônio Carlos (MG)", "Antônio Carlos (SC)", "Antônio Dias (MG)", "Antônio Gonçalves (BA)", "Antônio João (MS)", "Antônio Martins (RN)", "Antônio Olinto (PR)", "Antônio Prado (RS)", "Antônio Prado de Minas (MG)", "Aparecida (PB)", "Aparecida (SP)", "Aparecida de Goiânia (GO)", "Aparecida d'Oeste (SP)", "Aparecida do Rio Doce (GO)", "Aparecida do Rio Negro (TO)", "Aparecida do Taboado (MS)", "Aperibé (RJ)", "Apiacá (ES)", "Apiacás (MT)", "Apiaí (SP)", "Apicum-Açu (MA)", "Apiúna (SC)", "Apodi (RN)", "Aporá (BA)", "Aporé (GO)", "Apuarema (BA)", "Apucarana (PR)", "Apuí (AM)", "Apuiarés (CE)", "Aquidabã (SE)", "Aquidauana (MS)", "Aquiraz (CE)", "Arabutã (SC)", "Araçagi (PB)", "Araçaí (MG)", "Aracaju (SE)", "Araçariguama (SP)", "Araçás (BA)", "Aracati (CE)", "Aracatu (BA)", "Araçatuba (SP)", "Araci (BA)", "Aracitaba (MG)", "Aracoiaba (CE)", "Araçoiaba (PE)", "Araçoiaba da Serra (SP)", "Aracruz (ES)", "Araçu (GO)", "Araçuaí (MG)", "Aragarças (GO)", "Aragoiânia (GO)", "Aragominas (TO)", "Araguacema (TO)", "Araguaçu (TO)", "Araguaiana (MT)", "Araguaína (TO)", "Araguainha (MT)", "Araguanã (MA)", "Araguanã (TO)", "Araguapaz (GO)", "Araguari (MG)", "Araguatins (TO)", "Araioses (MA)", "Aral Moreira (MS)", "Aramari (BA)", "Arambaré (RS)", "Arame (MA)", "Aramina (SP)", "Arandu (SP)", "Arantina (MG)", "Arapeí (SP)", "Arapiraca (AL)", "Arapoema (TO)", "Araponga (MG)", "Arapongas (PR)", "Araporã (MG)", "Arapoti (PR)", "Arapuá (MG)", "Arapuã (PR)", "Araputanga (MT)", "Araquari (SC)", "Arara (PB)", "Araranguá (SC)", "Araraquara (SP)", "Araras (SP)", "Ararendá (CE)", "Arari (MA)", "Araricá (RS)", "Araripe (CE)", "Araripina (PE)", "Araruama (RJ)", "Araruna (PB)", "Araruna (PR)", "Arataca (BA)", "Aratiba (RS)", "Aratuba (CE)", "Aratuípe (BA)", "Arauá (SE)", "Araucária (PR)", "Araújos (MG)", "Araxá (MG)", "Arceburgo (MG)", "Arco-Íris (SP)", "Arcos (MG)", "Arcoverde (PE)", "Areado (MG)", "Areal (RJ)", "Arealva (SP)", "Areia (PB)", "Areia Branca (RN)", "Areia Branca (SE)", "Areia de Baraúnas (PB)", "Areial (PB)", "Areias (SP)", "Areiópolis (SP)", "Arenápolis (MT)", "Arenópolis (GO)", "Arez (Rio Grande do Norte) (RN)", "Argirita (MG)", "Aricanduva (MG)", "Arinos (MG)", "Aripuanã (MT)", "Ariquemes (RO)", "Ariranha (SP)", "Ariranha do Ivaí (PR)", "Armação dos Búzios (RJ)", "Armazém (SC)", "Arneiroz (CE)", "Aroazes (PI)", "Aroeiras (PB)", "Aroeiras do Itaim (PI)", "Arraial (PI)", "Arraial do Cabo (RJ)", "Arraias (TO)", "Arroio do Meio (RS)", "Arroio do Padre (RS)", "Arroio do Sal (RS)", "Arroio dos Ratos (RS)", "Arroio do Tigre (RS)", "Arroio Grande (RS)", "Arroio Trinta (SC)", "Artur Nogueira (SP)", "Aruanã (GO)", "Arujá (SP)", "Arvoredo (SC)", "Arvorezinha (RS)", "Ascurra (SC)", "Aspásia (SP)", "Assaí (PR)", "Assaré (CE)", "Assis (SP)", "Assis Brasil (AC)", "Assis Chateaubriand (PR)", "Assu (RN)", "Assunção (PB)", "Assunção do Piauí (PI)", "Astolfo Dutra (MG)", "Astorga (PR)", "Atalaia (AL)", "Atalaia (PR)", "Atalaia do Norte (AM)", "Atalanta (SC)", "Ataléia (MG)", "Atibaia (SP)", "Atílio Vivácqua (ES)", "Augustinópolis (TO)", "Augusto Corrêa (PA)", "Augusto de Lima (MG)", "Augusto Pestana (RS)", "Áurea (RS)", "Aurelino Leal (BA)", "Auriflama (SP)", "Aurilândia (GO)", "Aurora (CE)", "Aurora (SC)", "Aurora do Pará (PA)", "Aurora do Tocantins (TO)", "Autazes (AM)", "Avaí (SP)", "Avanhandava (SP)", "Avaré (SP)", "Aveiro (PA)", "Avelino Lopes (PI)", "Avelinópolis (GO)", "Axixá (MA)", "Axixá do Tocantins (TO)" }));

        btBuscarCep.setBackground(java.awt.SystemColor.menu);
        btBuscarCep.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btBuscarCep.setForeground(new java.awt.Color(51, 51, 51));
        btBuscarCep.setText("Buscar");
        btBuscarCep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBuscarCepActionPerformed(evt);
            }
        });

        tfNumero.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        tfNumero.setToolTipText("ex. Marechal Rondon");

        javax.swing.GroupLayout pEnderecoLayout = new javax.swing.GroupLayout(pEndereco);
        pEndereco.setLayout(pEnderecoLayout);
        pEnderecoLayout.setHorizontalGroup(
            pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pEnderecoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pEnderecoLayout.createSequentialGroup()
                        .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbNacionalidade, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbCep))
                        .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pEnderecoLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbNaturalidade)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pEnderecoLayout.createSequentialGroup()
                                .addGap(54, 54, 54)
                                .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfRua, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbRua))))
                        .addGap(0, 15, Short.MAX_VALUE))
                    .addGroup(pEnderecoLayout.createSequentialGroup()
                        .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pEnderecoLayout.createSequentialGroup()
                                .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lbNumero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tfNumero))
                                .addGap(18, 18, 18)
                                .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbBairro)
                                    .addComponent(tfBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbComplemento)
                                    .addComponent(tfComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pEnderecoLayout.createSequentialGroup()
                                .addComponent(ftfCep, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btBuscarCep))
                            .addGroup(pEnderecoLayout.createSequentialGroup()
                                .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbEstado)
                                    .addComponent(cbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbCidade)
                                    .addComponent(cbCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(lbNaturalidade1))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        pEnderecoLayout.setVerticalGroup(
            pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pEnderecoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbNaturalidade1)
                    .addComponent(lbNaturalidade))
                .addGap(1, 1, 1)
                .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbNacionalidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbCep)
                    .addComponent(lbRua))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ftfCep, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btBuscarCep))
                    .addComponent(tfRua, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbNumero)
                    .addComponent(lbBairro)
                    .addComponent(lbComplemento))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbEstado)
                    .addComponent(lbCidade))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        pBotoes.setBackground(java.awt.SystemColor.controlHighlight);
        pBotoes.setMaximumSize(new java.awt.Dimension(892, 30));
        pBotoes.setMinimumSize(new java.awt.Dimension(892, 30));
        pBotoes.setPreferredSize(new java.awt.Dimension(892, 30));

        javax.swing.GroupLayout pBotoesLayout = new javax.swing.GroupLayout(pBotoes);
        pBotoes.setLayout(pBotoesLayout);
        pBotoesLayout.setHorizontalGroup(
            pBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pBotoesLayout.setVerticalGroup(
            pBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 74, Short.MAX_VALUE)
        );

        pInscricao.setBackground(java.awt.SystemColor.controlHighlight);
        pInscricao.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Inscrição", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, cbEstagiario.getFont(), java.awt.SystemColor.controlDkShadow));
        pInscricao.setMaximumSize(new java.awt.Dimension(950, 300));
        pInscricao.setMinimumSize(new java.awt.Dimension(950, 300));
        pInscricao.setPreferredSize(new java.awt.Dimension(950, 300));

        lbDisponibilidade.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbDisponibilidade.setForeground(new java.awt.Color(0, 102, 102));
        lbDisponibilidade.setText("Disponibilidade:");

        cbEstagiario.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        cbEstagiario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bruna", "Pedro", "Maria" }));

        lbEstagiario.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbEstagiario.setForeground(new java.awt.Color(0, 102, 102));
        lbEstagiario.setText("*Estagiário:");

        lbResponsavel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbResponsavel.setForeground(new java.awt.Color(0, 102, 102));
        lbResponsavel.setText("Responsável:");

        cbOrientador.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        cbOrientador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Fernanda Costa", "Bruna", "Pedro", "Maria" }));

        lbOrientador.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbOrientador.setForeground(new java.awt.Color(0, 102, 102));
        lbOrientador.setText("*Orientador:");

        cbAtendido.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        lbAtendido.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbAtendido.setForeground(new java.awt.Color(0, 102, 102));
        lbAtendido.setText("Atendido:");

        tfResponsavel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        tpDisponibilidade.setFont(cbEstadoCivil.getFont());
        jScrollPane1.setViewportView(tpDisponibilidade);

        javax.swing.GroupLayout pInscricaoLayout = new javax.swing.GroupLayout(pInscricao);
        pInscricao.setLayout(pInscricaoLayout);
        pInscricaoLayout.setHorizontalGroup(
            pInscricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pInscricaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pInscricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pInscricaoLayout.createSequentialGroup()
                        .addGroup(pInscricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbOrientador, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbOrientador))
                        .addGap(18, 18, 18)
                        .addGroup(pInscricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pInscricaoLayout.createSequentialGroup()
                                .addComponent(lbEstagiario)
                                .addGap(183, 183, 183)
                                .addComponent(lbResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pInscricaoLayout.createSequentialGroup()
                                .addComponent(cbEstagiario, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tfResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pInscricaoLayout.createSequentialGroup()
                        .addGroup(pInscricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(pInscricaoLayout.createSequentialGroup()
                                .addComponent(lbDisponibilidade)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btNegrito)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btItalico)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btSublinhado))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 585, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pInscricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbAtendido)
                            .addComponent(cbAtendido, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        pInscricaoLayout.setVerticalGroup(
            pInscricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pInscricaoLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(pInscricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbDisponibilidade)
                    .addGroup(pInscricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btNegrito, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btItalico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btSublinhado)))
                .addGap(18, 18, 18)
                .addGroup(pInscricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pInscricaoLayout.createSequentialGroup()
                        .addComponent(lbAtendido)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbAtendido, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pInscricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pInscricaoLayout.createSequentialGroup()
                        .addComponent(lbOrientador)
                        .addGap(6, 6, 6))
                    .addGroup(pInscricaoLayout.createSequentialGroup()
                        .addComponent(lbEstagiario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbResponsavel))
                .addGap(16, 16, 16)
                .addGroup(pInscricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cbOrientador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pInscricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbEstagiario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tfResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        btSalvar.setBackground(new java.awt.Color(102, 255, 102));
        btSalvar.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btSalvar.setForeground(new java.awt.Color(51, 51, 51));
        btSalvar.setText("Salvar");
        btSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarActionPerformed(evt);
            }
        });

        btEditar.setBackground(new java.awt.Color(255, 255, 51));
        btEditar.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btEditar.setForeground(new java.awt.Color(51, 51, 51));
        btEditar.setText("Editar");
        btEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditarActionPerformed(evt);
            }
        });

        pGrupo.setBackground(java.awt.SystemColor.controlHighlight);
        pGrupo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Grupo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), java.awt.SystemColor.controlDkShadow)); // NOI18N

        cbVerificaGrupo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbVerificaGrupo.setForeground(new java.awt.Color(0, 0, 0));
        cbVerificaGrupo.setText("Você faz parte de algum grupo?");
        cbVerificaGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbVerificaGrupoActionPerformed(evt);
            }
        });

        cbbGrupos.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        cbCriarGrupo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbCriarGrupo.setForeground(new java.awt.Color(0, 0, 0));
        cbCriarGrupo.setText("Criar novo grupo");
        cbCriarGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCriarGrupoActionPerformed(evt);
            }
        });

        lbDescricaoGrupo.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbDescricaoGrupo.setForeground(new java.awt.Color(0, 102, 102));
        lbDescricaoGrupo.setText("Descrição");

        lbNomeGrupo.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbNomeGrupo.setForeground(new java.awt.Color(0, 102, 102));
        lbNomeGrupo.setText("Nome");

        tfNomeGrupo.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        taDescricaoGrupo.setColumns(20);
        taDescricaoGrupo.setRows(5);
        jScrollPane2.setViewportView(taDescricaoGrupo);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        btnSalvarGrupo.setBackground(new java.awt.Color(153, 255, 102));
        btnSalvarGrupo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSalvarGrupo.setForeground(new java.awt.Color(51, 51, 51));
        btnSalvarGrupo.setText("Salvar");
        btnSalvarGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarGrupoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pGrupoLayout = new javax.swing.GroupLayout(pGrupo);
        pGrupo.setLayout(pGrupoLayout);
        pGrupoLayout.setHorizontalGroup(
            pGrupoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pGrupoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pGrupoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbbGrupos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbVerificaGrupo, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                    .addComponent(cbCriarGrupo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(60, 60, 60)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pGrupoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pGrupoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lbNomeGrupo)
                        .addComponent(tfNomeGrupo)
                        .addComponent(lbDescricaoGrupo)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnSalvarGrupo))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pGrupoLayout.setVerticalGroup(
            pGrupoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pGrupoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pGrupoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pGrupoLayout.createSequentialGroup()
                        .addComponent(lbNomeGrupo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfNomeGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbDescricaoGrupo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSalvarGrupo)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pGrupoLayout.createSequentialGroup()
                        .addGroup(pGrupoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pGrupoLayout.createSequentialGroup()
                                .addComponent(cbVerificaGrupo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbbGrupos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbCriarGrupo))
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 15, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout pCentroLayout = new javax.swing.GroupLayout(pCentro);
        pCentro.setLayout(pCentroLayout);
        pCentroLayout.setHorizontalGroup(
            pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pCentroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pGrupo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pCentroLayout.createSequentialGroup()
                        .addComponent(pBotoes, javax.swing.GroupLayout.PREFERRED_SIZE, 110, Short.MAX_VALUE)
                        .addGap(846, 846, 846))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pCentroLayout.createSequentialGroup()
                        .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(pIdentificacao, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pInscricao, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(pEndereco, javax.swing.GroupLayout.DEFAULT_SIZE, 956, Short.MAX_VALUE)
                    .addGroup(pCentroLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btEditar)
                        .addGap(18, 18, 18)
                        .addComponent(btSalvar)))
                .addGap(52, 52, 52))
        );
        pCentroLayout.setVerticalGroup(
            pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pCentroLayout.createSequentialGroup()
                .addComponent(pIdentificacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pInscricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btSalvar)
                    .addComponent(btEditar))
                .addGap(19, 19, 19)
                .addComponent(pBotoes, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        add(pCentro, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btBuscarCepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBuscarCepActionPerformed
        if (ftfCep.getText().equals("")) {
            ftfCep.requestFocus();
        } else {
            ViaCepService cep = new ViaCepService();
            try {
                // Remove a máscara do CEP
                String cepSemMascara = ftfCep.getText().replaceAll("[^0-9]", "");

                EnderecoModelCepApi enderecoObject = cep.getEndereco(cepSemMascara);

                if (enderecoObject == null) {
                    throw new IOException("O CEP não retornou um endereço válido.");
                }

                tfRua.setText(enderecoObject.getLogradouro());
                tfBairro.setText(enderecoObject.getBairro());
                tfComplemento.setText(enderecoObject.getComplemento());
                // Supondo que cbCidade seja um JComboBox<String> e você queira adicionar a cidade retornada
                cbCidade.addItem(enderecoObject.getLocalidade());

                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Cep encontrado com sucesso!");

            } catch (IOException ex) {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Erro ao buscar o CEP: " + ex.getMessage());
                Logger.getLogger(FormPaciente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btBuscarCepActionPerformed

    private void btSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarActionPerformed
        PacienteRN pacienteRN = new PacienteRN();
        EnderecoRN enderecoRN = new EnderecoRN();
        ResponsavelRN responsalRN = new ResponsavelRN();
        LocalDate dataAgora = LocalDate.now();
        MessagesAlert messagesAlert = new MessagesAlert();

        try {
            EnderecoVO enderecoVO = new EnderecoVO(
                    tfRua.getText(),
                    Integer.parseInt(tfNumero.getText()),
                    tfBairro.getText(),
                    Integer.parseInt(cbCidade.getSelectedItem().toString().split(" - ")[0]),
                    ftfCep.getText(),
                    tfComplemento.getText()
            );

            Endereco enderecoEntity = enderecoRN.salvarEndereco(enderecoVO);

            // salvar o endereço
            if (enderecoEntity != null) {
                // Extract ID values from ComboBoxes
                String genero = cbGenero.getSelectedItem().toString();
                String celularContato = ftfCelularContato.getText();
                String celular = ftfCelular.getText();
                String pacienteNome = tfPaciente.getText();
                String dataNascimento = tfData.getText();
                String instrucao = cbInstrucao.getSelectedItem().toString();
                String profissao = tfProfissao.getText();
                String estadoCivil = cbEstadoCivil.getSelectedItem().toString();
                String racaCorEtnia = cbRacaCorEtnia.getSelectedItem().toString();
                String orientacao = cbOrientacao.getSelectedItem().toString();
                String nacionalidade = cbNacionalidade.getSelectedItem().toString().split(" - ")[0];
                String disponibilidade = tpDisponibilidade.getText();
                String estagiario = cbEstagiario.getSelectedItem().toString().split(" - ")[0];
                String orientador = cbOrientador.getSelectedItem().toString().split(" - ")[0];

                String responsavel = tfResponsavel.getText();
                String contatoResponsavel = ftfCelularContatoResponsavel.getText();

                ResponsavelVO responsavelVO = new ResponsavelVO(
                        responsavel,
                        "PARENTE",
                        contatoResponsavel,
                        null,
                        null,
                        null,
                        null,
                        null
                );

                Responsavel responsavelEntity = responsalRN.salvar(responsavelVO);

                ResponsavelDAO responsavelDAO = new ResponsavelDAO();

                responsavelEntity = responsavelDAO.buscarPorId(responsavelEntity.getId());

                boolean atendido = cbAtendido.isSelected();

                PacienteVO pacienteVO = new PacienteVO(
                        genero,
                        celularContato,
                        celular,
                        pacienteNome,
                        dataNascimento,
                        dataAgora,
                        instrucao,
                        profissao,
                        estadoCivil,
                        racaCorEtnia,
                        orientacao,
                        Integer.parseInt(nacionalidade),
                        disponibilidade,
                        Integer.parseInt(estagiario),
                        Integer.parseInt(orientador),
                        enderecoEntity,
                        responsavelEntity,
                        atendido,
                        true
                );

                // salvar o paciente
                Paciente pacienteSalvo = pacienteRN.salvarPacienteRetornandoEntidade(pacienteVO);

                if (pacienteSalvo != null) {
                    Integer pacienteId = pacienteSalvo.getId();

                    if (cbVerificaGrupo.isSelected()) {
                        Integer grupoId = Integer.parseInt(cbbGrupos.getSelectedItem().toString().split(" - ")[0]);

                        GrupoXPacienteRN grupoXPacienteRN = new GrupoXPacienteRN();

                        // Remover associações anteriores
                        List<GrupoXPaciente> anteriores = grupoXPacienteRN.listarPorPaciente(pacienteId);
                        for (GrupoXPaciente registro : anteriores) {
                            new GrupoXPacienteDAO().deletar(registro);
                        }

                        // Nova associação
                        GrupoXPacienteVO grupoXPacienteVO = new GrupoXPacienteVO();
                        grupoXPacienteVO.setPacienteId(pacienteId);
                        grupoXPacienteVO.setGrupoId(grupoId);
                        grupoXPacienteVO.setDataEntrada(LocalDate.now());

                        grupoXPacienteRN.salvar(grupoXPacienteVO);
                    }

                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,
                            "Paciente salvo com sucesso!");
                    FormManager.showForm(new PageWelcome());
                } else {
                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER,
                            "Erro ao salvar o paciente.");
                }
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER,
                        "Erro ao salvar o endereço.");
            }

        } catch (HeadlessException | NumberFormatException e) {
            messagesAlert.showErrorMessage("Erro: " + e.getMessage());
        }

        MessagesAlert.showWarningMessage("Deseja continuar cadastrando?", response -> {
            if (!response) {
                FormManager.showForm(new PageWelcome());

            }
        });
    }//GEN-LAST:event_btSalvarActionPerformed

    private void cbEstadoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbEstadoItemStateChanged
        CidadeDAO cidadeDaoInstancia = new CidadeDAO();
        String selectedStateId = cbEstado.getSelectedItem().toString().split(" - ")[0];;

        if (selectedStateId != null) {
            // Filter cities based on the selected state
            List<Cidade> filteredCidades = cidadeDaoInstancia.buscarPorEstado(Integer.parseInt(selectedStateId));
            cbCidade.removeAllItems();

            for (Cidade cidade : filteredCidades) {
                cbCidade.addItem(cidade.getId() + " - " + cidade.getNome());
            }
        }
    }//GEN-LAST:event_cbEstadoItemStateChanged

    private void btEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditarActionPerformed
        PacienteRN pacienteRN = new PacienteRN();
        EnderecoRN enderecoRN = new EnderecoRN();
        ResponsavelRN responsalRN = new ResponsavelRN();
        MessagesAlert messagesAlert = new MessagesAlert();

        try {
            Endereco enderecoEntity = null;
            if (pacienteId != 0) {
                enderecoEntity = pacienteRN.buscarPacientePorId(pacienteId).getEndereco();
            }

            EnderecoVO enderecoVO = new EnderecoVO(
                    tfRua.getText(),
                    Integer.valueOf(tfNumero.getText()),
                    tfBairro.getText(),
                    Integer.valueOf(cbCidade.getSelectedItem().toString().split(" - ")[0]),
                    ftfCep.getText(),
                    tfComplemento.getText()
            );

            if (enderecoEntity != null) {
                enderecoVO.setId(enderecoEntity.getId());
                enderecoEntity = enderecoRN.atualizar(enderecoVO);
            } else {
                enderecoEntity = enderecoRN.salvarEndereco(enderecoVO);
            }

            Responsavel responsavelEntity = null;
            if (pacienteId != 0) {
                responsavelEntity = pacienteRN.buscarPacientePorId(pacienteId).getResponsavel();
            }

            String responsavel = tfResponsavel.getText();
            String contatoResponsavel = ftfCelularContatoResponsavel.getText();

            ResponsavelVO responsavelVO = new ResponsavelVO(
                    responsavel,
                    "PARENTE",
                    contatoResponsavel,
                    null, null, null, null, null
            );

            if (responsavelEntity != null) {
                responsavelVO.setId(responsavelEntity.getId());
                responsavelEntity = responsalRN.atualizar(responsavelVO);
            } else {
                responsavelEntity = responsalRN.salvar(responsavelVO);
            }

            PacienteVO pacienteVO = new PacienteVO(
                    cbGenero.getSelectedItem().toString(),
                    ftfCelularContato.getText(),
                    ftfCelular.getText(),
                    tfPaciente.getText(),
                    tfData.getText(),
                    LocalDate.now(),
                    cbInstrucao.getSelectedItem().toString(),
                    tfProfissao.getText(),
                    cbEstadoCivil.getSelectedItem().toString(),
                    cbRacaCorEtnia.getSelectedItem().toString(),
                    cbOrientacao.getSelectedItem().toString(),
                    Integer.valueOf(cbNacionalidade.getSelectedItem().toString().split(" - ")[0]),
                    tpDisponibilidade.getText(),
                    Integer.valueOf(cbEstagiario.getSelectedItem().toString().split(" - ")[0]),
                    Integer.valueOf(cbOrientador.getSelectedItem().toString().split(" - ")[0]),
                    enderecoEntity,
                    responsavelEntity,
                    cbAtendido.isSelected(),
                    true
            );

            pacienteVO.setId(pacienteId);

            if (pacienteRN.editarPaciente(pacienteVO)) {
                // Se estiver habilitado o grupo
                if (cbVerificaGrupo.isSelected()) {
                    Integer grupoId = Integer.parseInt(cbbGrupos.getSelectedItem().toString().split(" - ")[0]);

                    GrupoXPacienteRN grupoXPacienteRN = new GrupoXPacienteRN();

                    // Remove associações anteriores do paciente
//                    List<GrupoXPaciente> anteriores = grupoXPacienteRN.listarPorPaciente(pacienteId);
//                    for (GrupoXPaciente registro : anteriores) {
//                        new GrupoXPacienteDAO().deletar(registro);
//                    }

                    // Cria nova associação com grupo
                    GrupoXPacienteVO grupoXPacienteVO = new GrupoXPacienteVO();
                    grupoXPacienteVO.setPacienteId(pacienteId);
                    grupoXPacienteVO.setGrupoId(grupoId);
                    grupoXPacienteRN.salvar(grupoXPacienteVO);
                }

                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Paciente editado com sucesso!");
                FormManager.showForm(new PageWelcome());
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Erro ao editar o paciente.");
            }

        } catch (Exception e) {
            messagesAlert.showErrorMessage("Erro: " + e.getMessage());
        }

        MessagesAlert.showWarningMessage("Deseja continuar editando?", response -> {
            if (!response) {
                FormManager.showForm(new PageWelcome());

            }
        });
    }//GEN-LAST:event_btEditarActionPerformed

    private void configurarComponentesGrupo() {
        // Estado inicial
        cbbGrupos.setEnabled(false);
        cbCriarGrupo.setEnabled(false);

        lbNomeGrupo.setVisible(false);
        lbDescricaoGrupo.setVisible(false);
        tfNomeGrupo.setVisible(false);
        taDescricaoGrupo.setVisible(false);
        btnSalvarGrupo.setVisible(false);

        // Inicializa combobox de grupos
        Regradenegocio.GrupoRN grupoRN = new Regradenegocio.GrupoRN();
        grupoRN.listarTodos().forEach(g -> cbbGrupos.addItem(g.getId() + " - " + g.getNome()));
    }

    private void mostrarCamposCriacaoGrupo(boolean visivel) {
        lbNomeGrupo.setVisible(visivel);
        tfNomeGrupo.setVisible(visivel);
        lbDescricaoGrupo.setVisible(visivel);
        taDescricaoGrupo.setVisible(visivel);
        jScrollPane2.setVisible(visivel);
        taDescricaoGrupo.getParent().revalidate();
        taDescricaoGrupo.getParent().repaint();
        btnSalvarGrupo.setVisible(visivel);
    }

    private void btnSalvarGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarGrupoActionPerformed
        String nome = tfNomeGrupo.getText().trim();
        String descricao = taDescricaoGrupo.getText().trim();

        if (nome.isEmpty() || descricao.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER,
                    "Preencha todos os campos do grupo!");
            return;
        }

        GrupoVO grupoVO = new GrupoVO();
        grupoVO.setNome(nome);
        grupoVO.setDescricao(descricao);

        Regradenegocio.GrupoRN grupoRN = new Regradenegocio.GrupoRN();
        try {
            grupoRN.salvar(grupoVO);
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,
                    "Grupo salvo com sucesso!");

            // Atualizar o combobox com todos os grupos
            cbbGrupos.removeAllItems();
            grupoRN.listarTodos().forEach(g -> cbbGrupos.addItem(g.getId() + " - " + g.getNome()));
            cbCriarGrupo.setSelected(false);
            mostrarCamposCriacaoGrupo(false);
        } catch (Exception e) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER,
                    "Erro ao salvar grupo: " + e.getMessage());
        }
    }//GEN-LAST:event_btnSalvarGrupoActionPerformed

    private void cbVerificaGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbVerificaGrupoActionPerformed
        boolean selecionado = cbVerificaGrupo.isSelected();
        cbbGrupos.setEnabled(selecionado);
        cbbGrupos.setVisible(selecionado);
        cbCriarGrupo.setEnabled(selecionado);
        cbCriarGrupo.setVisible(selecionado);

        if (!selecionado) {
            cbCriarGrupo.setSelected(false);
            mostrarCamposCriacaoGrupo(false);
        }
    }//GEN-LAST:event_cbVerificaGrupoActionPerformed

    private void cbCriarGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCriarGrupoActionPerformed
        boolean criar = cbCriarGrupo.isSelected();
        mostrarCamposCriacaoGrupo(criar);
    }//GEN-LAST:event_cbCriarGrupoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btBuscarCep;
    private javax.swing.JButton btEditar;
    private javax.swing.JButton btItalico;
    private javax.swing.JButton btNegrito;
    private javax.swing.JButton btSalvar;
    private javax.swing.JButton btSublinhado;
    private javax.swing.JButton btnSalvarGrupo;
    private javax.swing.JCheckBox cbAtendido;
    private javax.swing.JComboBox<String> cbCidade;
    private javax.swing.JCheckBox cbCriarGrupo;
    private javax.swing.JComboBox<String> cbEstado;
    private javax.swing.JComboBox<String> cbEstadoCivil;
    private javax.swing.JComboBox<String> cbEstagiario;
    private javax.swing.JComboBox<String> cbGenero;
    private javax.swing.JComboBox<String> cbInstrucao;
    private javax.swing.JComboBox<String> cbNacionalidade;
    private javax.swing.JComboBox<String> cbOrientacao;
    private javax.swing.JComboBox<String> cbOrientador;
    private javax.swing.JComboBox<String> cbRacaCorEtnia;
    private javax.swing.JCheckBox cbVerificaGrupo;
    private javax.swing.JComboBox<String> cbbGrupos;
    private com.raven.datechooser.DateChooser dateChooser1;
    private javax.swing.JFormattedTextField ftfCelular;
    private javax.swing.JFormattedTextField ftfCelularContato;
    private javax.swing.JFormattedTextField ftfCelularContatoResponsavel;
    private javax.swing.JFormattedTextField ftfCep;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbAtendido;
    private javax.swing.JLabel lbBairro;
    private javax.swing.JLabel lbCelular;
    private javax.swing.JLabel lbCep;
    private javax.swing.JLabel lbCidade;
    private javax.swing.JLabel lbClinica;
    private javax.swing.JLabel lbComplemento;
    private javax.swing.JLabel lbContato2;
    private javax.swing.JLabel lbContato3;
    private javax.swing.JLabel lbDataNascimento;
    private javax.swing.JLabel lbDescricaoGrupo;
    private javax.swing.JLabel lbDisponibilidade;
    private javax.swing.JLabel lbEstado;
    private javax.swing.JLabel lbEstadoCivil;
    private javax.swing.JLabel lbEstagiario;
    private javax.swing.JLabel lbGenero;
    private javax.swing.JLabel lbInstrucao;
    private javax.swing.JLabel lbLogoCadastro;
    private javax.swing.JLabel lbNaturalidade;
    private javax.swing.JLabel lbNaturalidade1;
    private javax.swing.JLabel lbNomeGrupo;
    private javax.swing.JLabel lbNumero;
    private javax.swing.JLabel lbOrientacao;
    private javax.swing.JLabel lbOrientador;
    private javax.swing.JLabel lbPaciente;
    private javax.swing.JLabel lbProfissao;
    private javax.swing.JLabel lbProntuario;
    private javax.swing.JLabel lbRaca;
    private javax.swing.JLabel lbResponsavel;
    private javax.swing.JLabel lbRua;
    private javax.swing.JPanel pBotoes;
    private javax.swing.JPanel pCentro;
    private javax.swing.JPanel pEndereco;
    private javax.swing.JPanel pGrupo;
    private javax.swing.JPanel pIdentificacao;
    private javax.swing.JPanel pInscricao;
    private javax.swing.JPanel pNorth;
    private javax.swing.JTextArea taDescricaoGrupo;
    private javax.swing.JTextField tfBairro;
    private javax.swing.JTextField tfComplemento;
    private javax.swing.JTextField tfData;
    private javax.swing.JTextField tfNomeGrupo;
    private javax.swing.JTextField tfNumero;
    private javax.swing.JTextField tfPaciente;
    private javax.swing.JTextField tfProfissao;
    private javax.swing.JTextField tfResponsavel;
    private javax.swing.JTextField tfRua;
    private javax.swing.JTextPane tpDisponibilidade;
    // End of variables declaration//GEN-END:variables

}
