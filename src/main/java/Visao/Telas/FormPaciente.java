package Visao.Telas;

import Persistencia.Dao.*;
import Persistencia.Entity.*;
import Persistencia.Model.EnderecoModelCepApi;
import Regradenegocio.*;
import Services.ViaCepService;
import VO.*;
import Visao.Components.PanelTemplate;
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
import Visao.Utils.validation.ValidationService;
import Visao.Utils.validation.rules.NotEmptyRule;
import Visao.Utils.validation.rules.MinLengthRule;
import Visao.Utils.validation.rules.NumericFilterRule;
import Visao.Utils.validation.rules.ComboBoxSelectionRule;
import Visao.Utils.validation.rules.CepRule;
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
/**
 *
 * @author john
 */
public class FormPaciente extends PanelTemplate {

    private ValidationService validationService;
    private Endereco enderecoObject;
    private int pacienteId;

    /**
     * Creates new form telaCadastroPacientePanel
     */
    public FormPaciente() {
        initComponents();
        initValidacao();

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
        EditorTextPaneEstilization.JTextComponentUndoRedo(tpDisponibilidade);

        // Inicializa o serviço, passando o método que habilita/desabilita os botões de ação
        this.validationService = new ValidationService(this::setAcoesEnabled);

        // Registra as regras de forma declarativa
        configurarValidacao();

        // Valida o estado inicial de forma SILENCIOSA.
        // O botão começará desabilitado, mas sem erros visíveis.
        validationService.validateInitialState();
    }

    private void configurarValidacao() {
        // Nome do Paciente: não pode ser vazio
        validationService.addRule(tfPaciente, new NotEmptyRule("Nome do Paciente"), lbErroNome);

        // CEP: verifica o tamanho do cep retirando hiféns e pontos
        validationService.addRule(ftfCep, new CepRule(), lbErroCep);

        // Celular: deve ter 10 dígitos
        validationService.addRule(ftfCelular, new MinLengthRule(10), lbErroCelular);

        // Bairro: não pode ser vazio
        validationService.addRule(tfBairro, new NotEmptyRule("Bairro"), lbErroBairro);

        // Rua: não pode ser vazio
        validationService.addRule(tfRua, new NotEmptyRule("Rua"), lbErroRua);

        // Numero da casa: não pode ser vazio
        validationService.addRule(tfNumero, new NotEmptyRule("Número"), lbErroNum);

        // Numero da casa: deve conter apenas digitos numéricos
        validationService.addRule(tfNumero, new NumericFilterRule(), lbErroNum);

        // Valida se uma Nacionalidade foi escolhida
        validationService.addRule(cbNacionalidade,
                new ComboBoxSelectionRule("Escolha uma Nacionalidade...", "Nacionalidade"),
                lbErroNacionalidade);

        // Valida se um Estado foi escolhido
        validationService.addRule(cbEstado,
                new ComboBoxSelectionRule("Escolha um Estado...", "Estado"),
                lbErroEstado);
        // Valida se uma Cidade foi escolhida, AGORA DEPENDENDO do cbEstado
        validationService.addRule(cbCidade,
                new ComboBoxSelectionRule(
                        "Escolha uma Cidade...", // Placeholder da Cidade
                        "Cidade", // Nome do campo Cidade
                        cbEstado, // O ComboBox pai
                        "Por favor, escolha um estado primeiro." // A nova mensagem de erro
                ),
                lbErroCidade);

        // Valida se um Orientador foi escolhido
        validationService.addRule(cbOrientador,
                new ComboBoxSelectionRule("Escolha um Orientador...", "Orientador"),
                lbErroOrientador);

        // Valida se um Estagiário foi escolhido
        validationService.addRule(cbEstagiario,
                new ComboBoxSelectionRule("Escolha um Estagiário...", "Estagiario"),
                lbErroEstagiario);
    }

    /**
     * Um único método para habilitar/desabilitar todos os botões de ação.
     *
     * @param enabled true para habilitar, false para desabilitar.
     */
    private void setAcoesEnabled(boolean enabled) {
        btSalvar.setEnabled(enabled);
        btEditar.setEnabled(enabled); // Supondo que o btEditar também deva ser controlado

        if (enabled) {
            // Se o botão está HABILITADO, a dica não é mais necessária. A removemos.
            btSalvar.setToolTipText(null);
        } else {
            // Se o botão está DESABILITADO, adicionamos a dica útil para o usuário.
            btSalvar.setToolTipText("Por favor, preencha todos os campos obrigatórios.");
        }
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
        // Camada de Regra de Negócio
        PaisRN paisRN = new PaisRN();
        EstadoRN estadoRN = new EstadoRN();
        OrientadorRN orientadorRn = new OrientadorRN();
        EstagiarioRN estagiarioRn = new EstagiarioRN();

        // Buscando os dados através das RNs
        List<PaisVO> listaDePaises = paisRN.listarTodos();
        List<EstadoVO> listaDeEstados = estadoRN.listarTodos();
        List<OrientadorVO> todosOrientadores = orientadorRn.listarOrientadores();
        List<EstagiarioVO> todosEstagiarios = estagiarioRn.listarEstagiarios();

        // Limpando os ComboBoxes
        cbNacionalidade.removeAllItems();
        cbEstado.removeAllItems();
        cbCidade.removeAllItems();
        cbEstagiario.removeAllItems();
        cbOrientador.removeAllItems();

        // Adicionando os placeholders como OBJETOS VO
        cbNacionalidade.addItem(new PaisVO(0, "Escolha uma Nacionalidade..."));
        cbEstado.addItem(new EstadoVO(0, "Escolha um Estado...", ""));
        cbCidade.addItem(new CidadeVO(0, "Escolha uma Cidade..."));

        // TODO: Criar VOs para Orientador e Estagiário para remover as Strings
        cbOrientador.addItem("Escolha um Orientador...");
        cbEstagiario.addItem("Escolha um Estagiário...");

        // Populando os ComboBoxes com os VOs
        for (PaisVO pais : listaDePaises) {
            cbNacionalidade.addItem(pais);
        }
        for (EstadoVO estado : listaDeEstados) {
            cbEstado.addItem(estado);
        }

        // O loop de cidades é REMOVIDO daqui. A cidade só carrega ao escolher um estado.
        for (OrientadorVO orientador : todosOrientadores) {
            cbOrientador.addItem(orientador.getId() + " - " + orientador.getNomeCompleto());
        }
        for (EstagiarioVO estagiario : todosEstagiarios) {
            cbEstagiario.addItem(estagiario.getId() + " - " + estagiario.getNomeCompleto());
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
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
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
        lbErroNome = new javax.swing.JLabel();
        lbErroCelular = new javax.swing.JLabel();
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
        lbErroCep = new javax.swing.JLabel();
        lbErroRua = new javax.swing.JLabel();
        lbErroBairro = new javax.swing.JLabel();
        lbErroNum = new javax.swing.JLabel();
        lbErroNacionalidade = new javax.swing.JLabel();
        lbErroNaturalidade = new javax.swing.JLabel();
        lbErroEstado = new javax.swing.JLabel();
        lbErroCidade = new javax.swing.JLabel();
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
        lbErroOrientador = new javax.swing.JLabel();
        lbErroEstagiario = new javax.swing.JLabel();
        btSalvar = new javax.swing.JButton();
        btEditar = new javax.swing.JButton();

        dateChooser1.setTextField(tfData);

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        setMaximumSize(new java.awt.Dimension(950, 1600));
        setMinimumSize(new java.awt.Dimension(950, 1600));
        setPreferredSize(new java.awt.Dimension(950, 1600));
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

        lbErroNome.setForeground(new java.awt.Color(204, 0, 51));

        lbErroCelular.setForeground(new java.awt.Color(204, 0, 51));

        javax.swing.GroupLayout pIdentificacaoLayout = new javax.swing.GroupLayout(pIdentificacao);
        pIdentificacao.setLayout(pIdentificacaoLayout);
        pIdentificacaoLayout.setHorizontalGroup(
            pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pIdentificacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pIdentificacaoLayout.createSequentialGroup()
                        .addGroup(pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pIdentificacaoLayout.createSequentialGroup()
                                .addGroup(pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ftfCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbCelular))
                                .addGap(12, 12, 12)
                                .addGroup(pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbContato2)
                                    .addComponent(ftfCelularContato, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pIdentificacaoLayout.createSequentialGroup()
                                .addGroup(pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfProfissao, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbProfissao)
                                    .addComponent(lbEstadoCivil)
                                    .addComponent(cbEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbRaca)
                                    .addComponent(cbRacaCorEtnia, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbOrientacao)
                                    .addComponent(cbOrientacao, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(14, 14, 14)
                        .addGroup(pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbInstrucao)
                            .addComponent(cbInstrucao, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbContato3)
                            .addComponent(ftfCelularContatoResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pIdentificacaoLayout.createSequentialGroup()
                        .addGroup(pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbPaciente)
                            .addComponent(lbErroNome))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tfData, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbDataNascimento, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbGenero)
                            .addComponent(cbGenero, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lbErroCelular))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        pIdentificacaoLayout.setVerticalGroup(
            pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pIdentificacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pIdentificacaoLayout.createSequentialGroup()
                            .addComponent(lbPaciente)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tfPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pIdentificacaoLayout.createSequentialGroup()
                            .addComponent(lbGenero)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cbGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pIdentificacaoLayout.createSequentialGroup()
                        .addComponent(lbDataNascimento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbErroNome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pIdentificacaoLayout.createSequentialGroup()
                        .addGroup(pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbContato3)
                            .addComponent(lbContato2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ftfCelularContatoResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ftfCelularContato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pIdentificacaoLayout.createSequentialGroup()
                        .addComponent(lbCelular)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ftfCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbErroCelular)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(tfProfissao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(pIdentificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pIdentificacaoLayout.createSequentialGroup()
                                .addComponent(lbOrientacao, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbOrientacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbProfissao)))
                    .addGroup(pIdentificacaoLayout.createSequentialGroup()
                        .addComponent(lbInstrucao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbInstrucao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addContainerGap(53, Short.MAX_VALUE))
        );

        pEndereco.setBackground(java.awt.SystemColor.controlHighlight);
        pEndereco.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Endereço", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, cbEstagiario.getFont(), java.awt.SystemColor.controlDkShadow));
        pEndereco.setMaximumSize(new java.awt.Dimension(950, 460));
        pEndereco.setMinimumSize(new java.awt.Dimension(950, 460));
        pEndereco.setPreferredSize(new java.awt.Dimension(950, 460));

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

        lbErroCep.setForeground(new java.awt.Color(204, 0, 51));

        lbErroRua.setForeground(new java.awt.Color(204, 0, 51));

        lbErroBairro.setForeground(new java.awt.Color(204, 0, 51));

        lbErroNum.setForeground(new java.awt.Color(204, 0, 51));

        lbErroNacionalidade.setForeground(new java.awt.Color(204, 0, 51));

        lbErroNaturalidade.setForeground(new java.awt.Color(204, 0, 51));

        lbErroEstado.setForeground(new java.awt.Color(204, 0, 51));

        lbErroCidade.setForeground(new java.awt.Color(204, 0, 51));

        javax.swing.GroupLayout pEnderecoLayout = new javax.swing.GroupLayout(pEndereco);
        pEndereco.setLayout(pEnderecoLayout);
        pEnderecoLayout.setHorizontalGroup(
            pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pEnderecoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pEnderecoLayout.createSequentialGroup()
                        .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbBairro)
                            .addComponent(tfBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pEnderecoLayout.createSequentialGroup()
                                .addComponent(lbComplemento)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(tfComplemento))
                        .addGap(18, 18, 18)
                        .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbErroNum)
                            .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lbNumero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(tfNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(37, 37, 37))
                    .addGroup(pEnderecoLayout.createSequentialGroup()
                        .addComponent(lbErroEstado)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pEnderecoLayout.createSequentialGroup()
                        .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pEnderecoLayout.createSequentialGroup()
                                .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbNacionalidade, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbCep)
                                    .addComponent(lbErroNacionalidade))
                                .addGap(18, 18, 18)
                                .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbNaturalidade)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbErroNaturalidade)))
                            .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbNaturalidade1)
                                    .addComponent(lbErroBairro)
                                    .addGroup(pEnderecoLayout.createSequentialGroup()
                                        .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(pEnderecoLayout.createSequentialGroup()
                                                .addComponent(ftfCep, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btBuscarCep))
                                            .addComponent(lbErroCep))
                                        .addGap(18, 18, 18)
                                        .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lbErroRua)
                                            .addComponent(tfRua, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lbRua))))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pEnderecoLayout.createSequentialGroup()
                                    .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lbEstado)
                                        .addComponent(cbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lbCidade)
                                        .addComponent(cbCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lbErroCidade)))))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbErroNacionalidade)
                    .addComponent(lbErroNaturalidade))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbCep)
                    .addComponent(lbRua))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ftfCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btBuscarCep)
                    .addComponent(tfRua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbErroRua)
                    .addComponent(lbErroCep))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pEnderecoLayout.createSequentialGroup()
                        .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbBairro)
                            .addComponent(lbComplemento))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pEnderecoLayout.createSequentialGroup()
                        .addComponent(lbNumero)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbErroBairro)
                    .addComponent(lbErroNum, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbEstado)
                    .addComponent(lbCidade))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbErroEstado)
                    .addComponent(lbErroCidade))
                .addContainerGap(60, Short.MAX_VALUE))
        );

        pInscricao.setBackground(java.awt.SystemColor.controlHighlight);
        pInscricao.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Inscrição", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, cbEstagiario.getFont(), java.awt.SystemColor.controlDkShadow));
        pInscricao.setMaximumSize(new java.awt.Dimension(950, 420));
        pInscricao.setMinimumSize(new java.awt.Dimension(950, 420));
        pInscricao.setPreferredSize(new java.awt.Dimension(950, 420));

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

        lbErroOrientador.setForeground(new java.awt.Color(204, 0, 51));

        lbErroEstagiario.setForeground(new java.awt.Color(204, 0, 51));

        javax.swing.GroupLayout pInscricaoLayout = new javax.swing.GroupLayout(pInscricao);
        pInscricao.setLayout(pInscricaoLayout);
        pInscricaoLayout.setHorizontalGroup(
            pInscricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pInscricaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pInscricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbDisponibilidade)
                    .addGroup(pInscricaoLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(lbResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 894, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pInscricaoLayout.createSequentialGroup()
                        .addGroup(pInscricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbOrientador, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbOrientador)
                            .addComponent(tfResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbErroOrientador))
                        .addGap(93, 93, 93)
                        .addGroup(pInscricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbErroEstagiario)
                            .addComponent(lbEstagiario)
                            .addComponent(cbEstagiario, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbAtendido)
                            .addComponent(cbAtendido, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pInscricaoLayout.setVerticalGroup(
            pInscricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pInscricaoLayout.createSequentialGroup()
                .addComponent(lbDisponibilidade)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pInscricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbEstagiario)
                    .addComponent(lbOrientador))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pInscricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cbOrientador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbEstagiario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(pInscricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbErroOrientador)
                    .addComponent(lbErroEstagiario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pInscricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbResponsavel)
                    .addComponent(lbAtendido, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pInscricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pInscricaoLayout.createSequentialGroup()
                        .addComponent(tfResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(cbAtendido, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

        javax.swing.GroupLayout pCentroLayout = new javax.swing.GroupLayout(pCentro);
        pCentro.setLayout(pCentroLayout);
        pCentroLayout.setHorizontalGroup(
            pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pCentroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pCentroLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btEditar)
                        .addGap(18, 18, 18)
                        .addComponent(btSalvar))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pCentroLayout.createSequentialGroup()
                        .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(pIdentificacao, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pInscricao, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(pEndereco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(52, 52, 52))
        );
        pCentroLayout.setVerticalGroup(
            pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pCentroLayout.createSequentialGroup()
                .addComponent(pIdentificacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(pInscricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btSalvar)
                    .addComponent(btEditar))
                .addGap(24, 24, 24))
        );

        add(pCentro, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btBuscarCepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBuscarCepActionPerformed
        if (ftfCep.getText().isBlank()) {
            ftfCep.requestFocus();
            return;
        }

        ViaCepService cepService = new ViaCepService();
        try {
            String cepSemMascara = ftfCep.getText().replaceAll("[^0-9]", "");
            EnderecoModelCepApi enderecoApi = cepService.getEndereco(cepSemMascara);

            if (enderecoApi == null || enderecoApi.getLogradouro() == null || enderecoApi.getUf() == null) {
                throw new IOException("O CEP não retornou um endereço válido.");
            }

            // 1. Preenche os campos de texto
            tfRua.setText(enderecoApi.getLogradouro());
            tfBairro.setText(enderecoApi.getBairro());
            tfComplemento.setText(enderecoApi.getComplemento());

            // 2. REVALIDA OS CAMPOS DE TEXTO IMEDIATAMENTE
            validationService.revalidateComponent(tfRua);
            validationService.revalidateComponent(tfBairro);

            // 3. Encontra e seleciona o Estado
            String uf = enderecoApi.getUf();
            EstadoVO estadoEncontrado = null;
            for (int i = 0; i < cbEstado.getItemCount(); i++) {
                if (cbEstado.getItemAt(i) instanceof EstadoVO) {
                    EstadoVO estado = (EstadoVO) cbEstado.getItemAt(i);
                    if (estado.getSigla() != null && estado.getSigla().equalsIgnoreCase(uf)) {
                        estadoEncontrado = estado;
                        break;
                    }
                }
            }

            if (estadoEncontrado != null) {
                // 4. Seleciona o estado. O listener `cbEstadoItemStateChanged` será
                // acionado e irá popular o cbCidade.
                cbEstado.setSelectedItem(estadoEncontrado);

                // 5. Encontra e seleciona a Cidade
                String nomeCidade = enderecoApi.getLocalidade();
                CidadeRN cidadeRN = new CidadeRN();
                CidadeVO cidadeEncontrada = cidadeRN.buscarPorNomeEUF(nomeCidade, uf);

                if (cidadeEncontrada != null) {
                    cbCidade.setSelectedItem(cidadeEncontrada);
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Cidade '" + nomeCidade + "' não encontrada no banco de dados.");
                }
            } else {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Estado '" + uf + "' não encontrado no banco de dados.");
            }

            // 6. REVALIDA OS COMBOBOXES DE ENDEREÇO NO FINAL
            // Como os listeners já foram acionados, os erros devem ser limpos
            // e o estado do botão, atualizado corretamente.
            validationService.revalidateComponent(cbEstado);
            validationService.revalidateComponent(cbCidade);

            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Endereço preenchido com sucesso!");

        } catch (IOException ex) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Erro ao buscar o CEP: " + ex.getMessage());
            Logger.getLogger(FormPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btBuscarCepActionPerformed

    private void btSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarActionPerformed
        // 1. Mostra todos os erros pendentes na tela para o usuário.
        validationService.validateAll();

// 2. Usa o novo método para verificar se o formulário é válido.
        if (!validationService.isFormValid()) {
            MessagesAlert.showWarningMessage("Por favor, corrija os erros no formulário antes de salvar.", null);
            return;
        }
        PacienteRN pacienteRN = new PacienteRN();
        EnderecoRN enderecoRN = new EnderecoRN();
        ResponsavelRN responsalRN = new ResponsavelRN();
        LocalDate dataAgora = LocalDate.now();
        MessagesAlert messagesAlert = new MessagesAlert();

        try {
            // --- PREPARAÇÃO DOS DADOS ---

            // Pega o ID da Nacionalidade (País) a partir do PaisVO
            PaisVO paisSelecionado = (PaisVO) cbNacionalidade.getSelectedItem();
            int nacionalidadeId = paisSelecionado.getId();

            // Pega o ID da Cidade (ainda como String, até ser refatorado)
            int cidadeId = ((CidadeVO) cbCidade.getSelectedItem()).getId();

            // Monta o EnderecoVO
            EnderecoVO enderecoVO = new EnderecoVO(
                    tfRua.getText(),
                    Integer.parseInt(tfNumero.getText()),
                    tfBairro.getText(),
                    cidadeId, // Usa a variável que criamos
                    ftfCep.getText(),
                    tfComplemento.getText()
            );
            Endereco enderecoEntity = enderecoRN.salvarEndereco(enderecoVO);

            if (enderecoEntity != null) {
                // Pega os dados dos outros campos
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
                String disponibilidade = tpDisponibilidade.getText();

                // Pega IDs dos ComboBoxes que ainda usam String
                int estagiarioId = Integer.parseInt(cbEstagiario.getSelectedItem().toString().split(" - ")[0]);
                int orientadorId = Integer.parseInt(cbOrientador.getSelectedItem().toString().split(" - ")[0]);

                // Monta o ResponsavelVO
                String responsavel = tfResponsavel.getText();
                String contatoResponsavel = ftfCelularContatoResponsavel.getText();
                ResponsavelVO responsavelVO = new ResponsavelVO(responsavel, "PARENTE", contatoResponsavel, null, null, null, null, null);
                Responsavel responsavelEntity = responsalRN.salvar(responsavelVO);

                boolean atendido = cbAtendido.isSelected();
                
                // Monta o PacienteVO com os IDs corretos
                PacienteVO pacienteVO = new PacienteVO(
                        genero, celularContato, celular, pacienteNome, dataNascimento, dataAgora,
                        instrucao, profissao, estadoCivil, racaCorEtnia, orientacao,
                        nacionalidadeId, // <-- USA A VARIÁVEL DO PaisVO
                        disponibilidade,
                        estagiarioId,
                        orientadorId,
                        enderecoEntity, responsavelEntity, atendido, true
                );

                // Salva o paciente
                Paciente pacienteSalvo = pacienteRN.salvarPacienteRetornandoEntidade(pacienteVO);

                if (pacienteSalvo != null) {
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Paciente salvo com sucesso!");
                    FormManager.showForm(new PageWelcome());
                } else {
                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Erro ao salvar o paciente.");
                }
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Erro ao salvar o endereço.");
            }
        } catch (Exception e) {
            // Tratamento de erro mais específico para placeholders
            if (e instanceof ClassCastException || e.getMessage().contains("split")) {
                messagesAlert.showErrorMessage("Erro ao salvar: Um ou mais campos de seleção (como Nacionalidade ou Cidade) não foram preenchidos.");
            } else {
                messagesAlert.showErrorMessage("Erro ao salvar: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btSalvarActionPerformed

    private void cbEstadoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbEstadoItemStateChanged
        if (evt.getStateChange() != java.awt.event.ItemEvent.SELECTED) {
            return;
        }
        Object selectedObject = cbEstado.getSelectedItem();
        if (!(selectedObject instanceof EstadoVO)) {
            return;
        }
        EstadoVO estadoSelecionado = (EstadoVO) selectedObject;

        cbCidade.removeAllItems();
        cbCidade.addItem(new CidadeVO(0, "Escolha uma Cidade..."));

        if (estadoSelecionado.getId() != 0) {
            // AGORA USANDO A RN DE CIDADE
            CidadeRN cidadeRN = new CidadeRN();
            int estadoId = estadoSelecionado.getId();
            List<CidadeVO> cidadesFiltradas = cidadeRN.listarPorEstado(estadoId);

            // Adicionando os OBJETOS CidadeVO
            for (CidadeVO cidade : cidadesFiltradas) {
                cbCidade.addItem(cidade);
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

    private void initValidacao() {
        /* aplica filtro */
        ((PlainDocument) tfPaciente.getDocument()).setDocumentFilter(new FormPaciente.NoDigitsFilter());
        ((PlainDocument) tfProfissao.getDocument()).setDocumentFilter(new FormPaciente.NoDigitsFilter());
        ((PlainDocument) tfResponsavel.getDocument()).setDocumentFilter(new FormPaciente.NoDigitsFilter());

    }

    private static class NoDigitsFilter extends DocumentFilter {

        @Override
        public void insertString(DocumentFilter.FilterBypass fb, int offs, String str, AttributeSet a) throws BadLocationException {
            if (str != null && str.chars().allMatch(FormPaciente::isAllowedChar)) {
                super.insertString(fb, offs, str, a);
            }
        }

        @Override
        public void replace(DocumentFilter.FilterBypass fb, int offs, int len, String str, AttributeSet a) throws BadLocationException {
            if (str != null && str.chars().allMatch(FormPaciente::isAllowedChar)) {
                super.replace(fb, offs, len, str, a);
            }
        }
    }

    /**
     * helper: permite letras, acentos e espaço
     */
    private static boolean isAllowedChar(int codePoint) {
        return Character.isLetter(codePoint) || Character.isSpaceChar(codePoint);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btBuscarCep;
    private javax.swing.JButton btEditar;
    private javax.swing.JButton btSalvar;
    private javax.swing.JCheckBox cbAtendido;
    private javax.swing.JComboBox<CidadeVO> cbCidade;
    private javax.swing.JComboBox<EstadoVO> cbEstado;
    private javax.swing.JComboBox<String> cbEstadoCivil;
    private javax.swing.JComboBox<String> cbEstagiario;
    private javax.swing.JComboBox<String> cbGenero;
    private javax.swing.JComboBox<String> cbInstrucao;
    private javax.swing.JComboBox<PaisVO> cbNacionalidade;
    private javax.swing.JComboBox<String> cbOrientacao;
    private javax.swing.JComboBox<String> cbOrientador;
    private javax.swing.JComboBox<String> cbRacaCorEtnia;
    private com.raven.datechooser.DateChooser dateChooser1;
    private javax.swing.JFormattedTextField ftfCelular;
    private javax.swing.JFormattedTextField ftfCelularContato;
    private javax.swing.JFormattedTextField ftfCelularContatoResponsavel;
    private javax.swing.JFormattedTextField ftfCep;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JScrollPane jScrollPane1;
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
    private javax.swing.JLabel lbDisponibilidade;
    private javax.swing.JLabel lbErroBairro;
    private javax.swing.JLabel lbErroCelular;
    private javax.swing.JLabel lbErroCep;
    private javax.swing.JLabel lbErroCidade;
    private javax.swing.JLabel lbErroEstado;
    private javax.swing.JLabel lbErroEstagiario;
    private javax.swing.JLabel lbErroNacionalidade;
    private javax.swing.JLabel lbErroNaturalidade;
    private javax.swing.JLabel lbErroNome;
    private javax.swing.JLabel lbErroNum;
    private javax.swing.JLabel lbErroOrientador;
    private javax.swing.JLabel lbErroRua;
    private javax.swing.JLabel lbEstado;
    private javax.swing.JLabel lbEstadoCivil;
    private javax.swing.JLabel lbEstagiario;
    private javax.swing.JLabel lbGenero;
    private javax.swing.JLabel lbInstrucao;
    private javax.swing.JLabel lbLogoCadastro;
    private javax.swing.JLabel lbNaturalidade;
    private javax.swing.JLabel lbNaturalidade1;
    private javax.swing.JLabel lbNumero;
    private javax.swing.JLabel lbOrientacao;
    private javax.swing.JLabel lbOrientador;
    private javax.swing.JLabel lbPaciente;
    private javax.swing.JLabel lbProfissao;
    private javax.swing.JLabel lbProntuario;
    private javax.swing.JLabel lbRaca;
    private javax.swing.JLabel lbResponsavel;
    private javax.swing.JLabel lbRua;
    private javax.swing.JPanel pCentro;
    private javax.swing.JPanel pEndereco;
    private javax.swing.JPanel pIdentificacao;
    private javax.swing.JPanel pInscricao;
    private javax.swing.JPanel pNorth;
    private javax.swing.JTextField tfBairro;
    private javax.swing.JTextField tfComplemento;
    private javax.swing.JTextField tfData;
    private javax.swing.JTextField tfNumero;
    private javax.swing.JTextField tfPaciente;
    private javax.swing.JTextField tfProfissao;
    private javax.swing.JTextField tfResponsavel;
    private javax.swing.JTextField tfRua;
    private javax.swing.JTextPane tpDisponibilidade;
    // End of variables declaration//GEN-END:variables

}
