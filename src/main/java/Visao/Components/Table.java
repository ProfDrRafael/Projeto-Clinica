package Visao.Components;

import Persistencia.Dao.TabelaDAO;
import Persistencia.Model.ModelProfile; // Importa o modelo de dados de perfil
import Visao.Components.pagination.Pagination; // Importa a classe de paginação
import Visao.JframeManager.FormManager;
import Visao.Telas.FormAgenda;
import Visao.Telas.FormAtendimento;
import Visao.Telas.FormPaciente;
import Visao.Telas.FormUsuario;
import Visao.Telas.PageGrupos;
import Visao.Telas.PageWelcome;
import Visao.Utils.MessagesAlert;
import Visao.Utils.RedimencionarIcones; // Importa utilitário para redimensionar ícones
import Visao.Utils.table.CheckBoxTableHeaderRenderer; // Importa renderizador de cabeçalho de tabela com checkbox
import Visao.Utils.table.TableHeaderAlignment; // Importa alinhamento de cabeçalho de tabela
import Visao.Utils.table.TableProfileCellRenderer; // Importa renderizador de células de perfil na tabela
import com.formdev.flatlaf.FlatClientProperties; // Importa propriedades do cliente FlatLaf
import com.formdev.flatlaf.extras.FlatSVGIcon; // Importa ícones SVG do FlatLaf
import java.awt.Component; // Importa a classe Component
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List; // Importa a lista
import javax.swing.BorderFactory; // Importa fábrica de bordas
import javax.swing.JButton; // Importa o botão
import javax.swing.JLabel; // Importa o rótulo
import javax.swing.JPanel; // Importa o painel
import javax.swing.JScrollPane; // Importa o painel de rolagem
import javax.swing.JSeparator; // Importa o separador
import javax.swing.JTable; // Importa a tabela
import javax.swing.JTextField; // Importa o campo de texto
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants; // Importa constantes de alinhamento
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel; // Importa modelo padrão de tabela
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import net.miginfocom.swing.MigLayout; // Importa layout Mig
import raven.toast.Notifications;
import javax.swing.RowFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.SwingUtilities;

/**
 * Classe para criar uma tabela personalizada com paginação e recursos
 * adicionais.
 *
 * @author john
 */
public class Table extends JPanel {

    private static final int ROWS_PER_PAGE = 10;
    private static final int COLUMN_WIDTH_SMALL = 50;
    private static final int COLUMN_WIDTH_DEFAULT = 150;
    private int totalPages;
    private int currentPage = 1;
    private DefaultTableModel model; // Modelo da tabela
    private Pagination pagination = new Pagination();
    private String consultaAnonima;
    private String[] tabelaColunas;
    private List<Object[]> allDataTable = new ArrayList<>();
    private JTable table;
    private String tipoFormTela;
    private String tableNameDB;
    private String statusButtonLabel;
    private boolean acao_ativar_ou_inativar;
    private ArrayList<Integer> selectedRows = new ArrayList<>();
    private String iconeAtivacao;
    private Integer foreignKeyId;

    // Variável para armazenar o texto de pesquisa (em minúsculo)
    private String searchQuery = "";

    public Table(String consultaAnonima, String[] tabelaColunas, String tipoFormTela, String tableNameDB, boolean acao_ativar_ou_inativar, String statusButtonLabel, String iconeAtivacao) {
        consultaAnonima = consultaAnonima;
        this.tabelaColunas = tabelaColunas;
        this.tipoFormTela = tipoFormTela;
        this.tableNameDB = tableNameDB;
        this.acao_ativar_ou_inativar = acao_ativar_ou_inativar;
        this.statusButtonLabel = statusButtonLabel;
        this.iconeAtivacao = iconeAtivacao;
    }

    public void setForeignKeyId(Integer foreignKeyId) {
        this.foreignKeyId = foreignKeyId;
    }

    /**
     * Método para criar a tabela personalizada.
     *
     * @param consultaAnonima
     * @param colunasTabela
     * @param dadosBuscados
     * @param tableNameDB
     * @return Componente da tabela personalizada
     */
    public Component createCustomTable(String consultaAnonima, String[] colunasTabela, String tableNameDB, List<Object[]> dadosBuscados) {
        // Criação do painel com layout Mig
        JPanel panel = new JPanel(new MigLayout("fillx,wrap,insets 10 0 10 0", "[fill]", "[][][]0[fill,grow]"));

        if (dadosBuscados == null) {
            // Buscar dados do banco
            List<Object[]> resultData = TabelaDAO.buscarTabelaConsultaAnonima(consultaAnonima);

            // Atualiza a lista global de dados
            allDataTable = resultData;

        } else {
            allDataTable = dadosBuscados;
        }

        // Column names
        tabelaColunas = colunasTabela;

        // Cria o modelo da tabela com os nomes das colunas
        DefaultTableModel model = new DefaultTableModel(tabelaColunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Permite edição apenas na primeira coluna (checkbox)
                return column == 0;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // Define as classes das colunas (boolean para checkbox)
                if (columnIndex == 0) {
                    return Boolean.class;
                }
                return super.getColumnClass(columnIndex);
            }
        };
        this.model = model;

        // Carrega TODOS os dados no modelo (não apenas da página atual)
        loadAllData(allDataTable);

        // Criação da tabela
        JTable table = new JTable(model);

        // Cria um TableRowSorter para manipular os filtros de exibição
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        // Painel de rolagem da tabela
        // Permitir sorting de colunas
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Define borda vazia

        // Configurações dinâmicas das colunas da tabela
        for (int i = 0; i < colunasTabela.length; i++) {
            TableColumn column = table.getColumnModel().getColumn(i);

            switch (i) {
                case 0 ->
                    column.setMaxWidth(COLUMN_WIDTH_SMALL); // Coluna de seleção (checkbox)
                case 1 ->
                    column.setMaxWidth(COLUMN_WIDTH_SMALL); // coluna de índice (IDs)
                default ->
                    column.setPreferredWidth(COLUMN_WIDTH_DEFAULT); // Configuração padrão
            }
        }

        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        // Aplica o renderizador de célula de perfil na tabela
        table.setDefaultRenderer(ModelProfile.class, new TableProfileCellRenderer(table));

        // Aplica o checkbox customizado no cabeçalho da tabela
        table.getColumnModel().getColumn(0).setHeaderRenderer(new CheckBoxTableHeaderRenderer(table, 0));

        // Alinhamento do cabeçalho da tabela
        table.getTableHeader().setDefaultRenderer(new TableHeaderAlignment(table) {
            @Override
            protected int getAlignment(int column) {
                if (column == 1) {
                    return SwingConstants.CENTER;
                }
                return SwingConstants.LEADING;
            }
        });

        // Estilização do painel
        panel.putClientProperty(FlatClientProperties.STYLE, "arc:20;background:$Table.background;");
        table.getTableHeader().putClientProperty(FlatClientProperties.STYLE, "height:30;hoverBackground:null;pressedBackground:null;separatorColor:$TableHeader.background;");
        table.putClientProperty(FlatClientProperties.STYLE, "rowHeight:70;showHorizontalLines:true;intercellSpacing:0,1;cellFocusColor:$TableHeader.hoverBackground;selectionBackground:$TableHeader.hoverBackground;selectionInactiveBackground:$TableHeader.hoverBackground;selectionForeground:$Table.foreground;");
        scrollPane.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, "trackArc:$ScrollBar.thumbArc;trackInsets:3,3,3,3;thumbInsets:3,3,3,3;background:$Table.background;");

        this.table = table;

        // Evento para lidar com remoções (listener para alterações na seleção de checkbox)
        model.addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();

            if (column == 0 && row >= 0 && row < model.getRowCount()) {
                Object value = model.getValueAt(row, column);
                if (value instanceof Boolean) {
                    Boolean isChecked = (Boolean) value;
                    if (isChecked) {
                        if (!selectedRows.contains(row)) {
                            selectedRows.add(row);
                        }
                    } else {
                        selectedRows.remove(Integer.valueOf(row));
                    }
                }
            }
        });

        // Criação do título
        JLabel title = new JLabel("Tabela de Listagem");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +2");
        panel.add(title, "gapx 20");

        // Criação do cabeçalho com ações
        panel.add(createHeaderAction());

        JSeparator separator = new JSeparator();
        separator.putClientProperty(FlatClientProperties.STYLE, "foreground:$Table.gridColor;");
        panel.add(separator, "height 2");
        panel.add(scrollPane);

        // Calcula o total de páginas com base em todos os dados carregados (ou filtrados, se houver pesquisa)
        totalPages = (int) Math.ceil((double) table.getRowSorter().getViewRowCount() / ROWS_PER_PAGE);
        // Atualiza o filtro para exibir somente a página atual
        updateRowFilter(currentPage);

        // Inicializa e configura a paginação
        pagination = new Pagination();
        pagination.setBackground(new java.awt.Color(0, 102, 102));
        pagination.setPagegination(currentPage, totalPages);
        pagination.addEventPagination(page -> {
            currentPage = page;
            updateRowFilter(page);
        });

        panel.add(pagination, "align center, wrap");
        return panel;
    }

    /**
     * Carrega TODOS os dados no modelo, sem filtragem por página.
     */
    private void loadAllData(List<Object[]> data) {
        model.setRowCount(0); // Limpa o modelo

        for (Object[] row : data) {
            Object[] rowData = new Object[this.tabelaColunas.length];

            for (int j = 0; j < this.tabelaColunas.length; j++) {

                if (j == 0) {
                    rowData[j] = false; // Checkbox inicializado como false

                } else if (j - 1 < row.length) {
                    rowData[j] = row[j - 1];

                } else {
                    rowData[j] = "...";
                }
            }
            model.addRow(rowData);
        }
    }

    /**
     * Atualiza o RowFilter do TableRowSorter para combinar (opcionalmente) a
     * pesquisa e a exibição apenas dos dados da página atual.
     *
     * @param page Página atual a ser exibida
     */
    private void updateRowFilter(int page) {
        if (table.getRowSorter() == null) {
            return;
        }

        TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) table.getRowSorter();
        // Define o filtro de pesquisa, se houver
        RowFilter<DefaultTableModel, Integer> searchFilter = null;
        if (searchQuery != null && !searchQuery.isEmpty()) {
            searchFilter = new RowFilter<DefaultTableModel, Integer>() {
                @Override
                public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                    for (int i = 0; i < entry.getValueCount(); i++) {
                        Object value = entry.getValue(i);
                        if (value != null && value.toString().toLowerCase().contains(searchQuery)) {
                            return true;
                        }
                    }
                    return false;
                }
            };
        }
        // Primeiro, aplique apenas o filtro de pesquisa (se houver) para obter a contagem filtrada
        sorter.setRowFilter(searchFilter);
        int totalFiltered = sorter.getViewRowCount();
        // Calcula os limites da página atual
        int start = (page - 1) * ROWS_PER_PAGE;
        int end = start + ROWS_PER_PAGE;
        // Cria uma lista com os índices do modelo que devem ser exibidos na página atual
        List<Integer> indices = new ArrayList<>();
        for (int i = start; i < end && i < totalFiltered; i++) {
            int modelIndex = sorter.convertRowIndexToModel(i);
            indices.add(modelIndex);
        }
        // Filtro de paginação que só inclui as linhas cujos índices estão na lista
        RowFilter<DefaultTableModel, Integer> paginationFilter = new RowFilter<DefaultTableModel, Integer>() {
            @Override
            public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                return indices.contains(entry.getIdentifier());
            }
        };
        // Combina (se houver filtro de pesquisa) ou aplica apenas o de paginação
        if (searchFilter != null) {
            sorter.setRowFilter(RowFilter.andFilter(Arrays.asList(searchFilter, paginationFilter)));
        } else {
            sorter.setRowFilter(paginationFilter);
        }
    }

    private Object[] getRowData(DefaultTableModel model, int row) {
        int columnCount = model.getColumnCount();
        Object[] rowData = new Object[columnCount];

        for (int col = 0; col < columnCount; col++) {
            rowData[col] = model.getValueAt(row, col);
        }

        return rowData;
    }

    /**
     * Cria a barra de ações (pesquisa, cadastrar, editar, remover) para o
     * cabeçalho da tabela.
     */
    private Component createHeaderAction() {
        JPanel panel = new JPanel(new MigLayout("insets 5 20 5 20", "[fill,230]push[][]"));

        JTextField txtSearch = new JTextField();
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Buscar...");
        txtSearch.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("Multimidia/icon/search.svg", 0.4f));
        JButton cmdCreate = new JButton("Cadastrar");
        JButton cmdEdit = new JButton("Editar");
        JButton cmdInativar = new JButton(statusButtonLabel);

        adicionarListenerTextFieldPesquisar(txtSearch);
        adicionarListenerBotaoCadastrar(cmdCreate);
        adicionarListenerBotaoEditar(cmdEdit);
        adicionarListenertBotaoInativar(cmdInativar);

        RedimencionarIcones redimencionarIcone = new RedimencionarIcones();
        redimencionarIcone.redimensionarIcones(cmdCreate, "/Multimidia/imagens/plus-cadastrar.png", 10);
        redimencionarIcone.redimensionarIcones(cmdEdit, "/Multimidia/imagens/edit.png", 10);
        redimencionarIcone.redimensionarIcones(cmdInativar, iconeAtivacao, 10);

        panel.add(txtSearch);
        panel.add(cmdCreate);
        panel.add(cmdEdit);
        panel.add(cmdInativar);

        panel.putClientProperty(FlatClientProperties.STYLE, "background:null;");
        return panel;
    }

    private void adicionarListenerBotaoCadastrar(JButton cmdCreate) {
        cmdCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (tipoFormTela) {
                    case "Lista de Espera Geral" ->
                        FormManager.showForm(new FormPaciente());
                    case "Lista de Espera Especifica" ->
                        FormManager.showForm(new FormPaciente());
                    case "Agendamentos" ->
                        FormManager.showForm(new FormAgenda());
                    case "Atendimentos" ->
                        FormManager.showForm(new FormAtendimento());
                    case "Todos os Estagiários" ->
                        FormManager.showForm(new FormUsuario());
                    case "Todos os Pacientes" ->
                        FormManager.showForm(new FormPaciente());
                    case "Todos os Usuários" ->
                        FormManager.showForm(new FormUsuario());
                    case "Grupos" ->
                        FormManager.showForm(new PageGrupos());
                    default ->
                        FormManager.showForm(new PageWelcome());
                }
            }
        });
    }

    private void adicionarListenerTextFieldPesquisar(JTextField txtSearch) {
        // Ouvinte para atualização da pesquisa: a cada alteração, atualiza a variável de pesquisa,
        // recalcula o total de páginas e redefine o filtro (resetando para a página 1)
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSearch();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSearch();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateSearch();
            }

            private void updateSearch() {
                searchQuery = txtSearch.getText().toLowerCase();
                TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) table.getRowSorter();
                // Aplica o filtro de pesquisa temporariamente para obter a contagem filtrada
                if (searchQuery != null && !searchQuery.isEmpty()) {
                    sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
                        @Override
                        public boolean include(RowFilter.Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                            for (int i = 0; i < entry.getValueCount(); i++) {
                                Object value = entry.getValue(i);
                                if (value != null && value.toString().toLowerCase().contains(searchQuery)) {
                                    return true;
                                }
                            }
                            return false;
                        }
                    });
                } else {
                    sorter.setRowFilter(null);
                }
                int totalFiltered = sorter.getViewRowCount();
                totalPages = (int) Math.ceil((double) totalFiltered / ROWS_PER_PAGE);
                // Atualiza o filtro para exibir a página 1 dos resultados filtrados
                currentPage = 1;
                updateRowFilter(currentPage);
                pagination.setPagegination(currentPage, totalPages);
            }
        });
    }

    private void adicionarListenerBotaoEditar(JButton cmdEdit) {
        cmdEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = table.getSelectedRows();

                if (selectedRows.length != 1) {
                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER,
                            selectedRows.length == 0 ? "Selecione um paciente para editar." : "Selecione apenas um paciente para editar.");
                    return;
                }

                int selectedRow = selectedRows[0];
                Object idObj = model.getValueAt(selectedRow, 1);

                String tipoUsuario = "";

                if (tableNameDB == "Usuarios") {
                    tipoUsuario = String.valueOf(model.getValueAt(selectedRow, 4));
                }

                Integer id = null;

                try {
                    id = Integer.valueOf(idObj.toString());
                } catch (NumberFormatException ex) {
                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Erro ao obter o ID do paciente.");
                    return;
                }

                if (id == null) {
                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Erro ao obter o ID do paciente.");
                    return;
                }

                FormPaciente pacienteForm = new FormPaciente();
                FormUsuario formUsuario = new FormUsuario();

                try {
                    switch (tipoFormTela) {
                        case "Lista de Espera Geral":
                            pacienteForm.preencherDadosFormulario(id);
                            FormManager.showForm(pacienteForm);
                            break;

                        case "Lista de Espera Especifica":
                            pacienteForm.preencherDadosFormulario(id);
                            FormManager.showForm(pacienteForm);
                            break;

                        case "Agendamentos":
                            FormManager.showForm(new FormAgenda());
                            break;

                        case "Atendimentos":
                            FormManager.showForm(new FormAtendimento());
                            break;

                        case "Todos os Estagiários":
                            FormManager.showForm(new FormUsuario());
                            break;

                        case "Todos os Pacientes":
                            pacienteForm.preencherDadosFormulario(id);
                            FormManager.showForm(pacienteForm);
                            break;

                        case "Todos os Usuários":
                            formUsuario.preencherDadosFormulario(id, tipoUsuario);
                            FormManager.showForm(formUsuario);
                            break;
                        case "Grupos":
                            FormManager.showForm(new PageGrupos());
                            break;
                        default:
                            FormManager.showForm(new PageWelcome());
                            break;
                    }
                } catch (Exception ex) {
                    System.out.println("Erro ao editar paciente: " + ex);
                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Erro ao carregar os dados do paciente.");
                }
            }
        });
    }

    private void adicionarListenertBotaoInativar(JButton cmdInativar) {
        cmdInativar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if ("Pacientes do Grupo".equals(tipoFormTela)) {
                    if (selectedRows.isEmpty()) {
                        Notifications.getInstance().show(Notifications.Type.ERROR,
                                Notifications.Location.TOP_CENTER,
                                "Selecione ao menos um paciente.");
                        return;
                    }

                    MessagesAlert.showWarningMessage("Deseja realmente remover estes vínculos?", resp -> {
                        if (!resp) {
                            return;
                        }

                        try {
                            // pega o grupoId que você setou externamente
                            int grupoId = foreignKeyId;

                            // constroi lista de paciente_id
                            TableRowSorter<DefaultTableModel> sorter
                                    = (TableRowSorter<DefaultTableModel>) table.getRowSorter();
                            List<Integer> pacienteIds = new ArrayList<>();
                            for (int viewRow : selectedRows) {
                                int modelRow = sorter.convertRowIndexToModel(viewRow);
                                pacienteIds.add((Integer) model.getValueAt(modelRow, 1));
                            }
                            String inClause = pacienteIds.stream()
                                    .map(String::valueOf)
                                    .collect(Collectors.joining(","));

                            String sql
                                    = "DELETE FROM grupo_x_paciente "
                                    + "WHERE grupo_id = " + foreignKeyId
                                    + "  AND paciente_id IN (" + inClause + ")";

                            boolean resultadoConsultaAnonima = TabelaDAO.executarConsultaAnonima(sql);

                            if (resultadoConsultaAnonima) {
                                // remove as linhas da tabela em memória…
                                Notifications.getInstance().show(Notifications.Type.SUCCESS,
                                        Notifications.Location.TOP_CENTER,
                                        "Vínculos removidos com sucesso");
                            } else {
                                Notifications.getInstance().show(Notifications.Type.ERROR,
                                        Notifications.Location.TOP_CENTER,
                                        "Falha ao remover vínculos");
                            }
                        } catch (Exception ex) {
                            Logger.getLogger(getClass().getName())
                                    .log(Level.SEVERE, "Erro ao remover vínculos", ex);
                            Notifications.getInstance().show(Notifications.Type.ERROR,
                                    Notifications.Location.TOP_CENTER,
                                    "Erro inesperado ao remover");
                        }
                    });
                    return;  // interrompe o listener genérico
                }

                if (selectedRows.size() > 0) {
                    MessagesAlert.showWarningMessage("Deseja inativar esse registro?", response -> {
                        try {
                            if (response) {
                                inativarRegistros();
                            }
                        } catch (Exception error) {
                            System.out.println("Não foi possível inativar, erro: " + error);
                        }
                    });
                } else {
                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Selecione um registro para remover");
                }
            }
        });
    }

    private void inativarRegistros() {
        Map<String, List<Object>> userTypeMap = new HashMap<>();
        List<Integer> rowsToRemove = new ArrayList<>(selectedRows);
        List<Object> selectedIds = new ArrayList<>();

        System.out.println("rowToRemove" + rowsToRemove + " selectedIds " + selectedIds + " acao " + acao_ativar_ou_inativar);

        for (int i = rowsToRemove.size() - 1; i >= 0; i--) {
            int row = rowsToRemove.get(i);

            if (row >= 0 && row < model.getRowCount()) {
                Object id = model.getValueAt(row, 1);

                if ("Usuarios".equals(tableNameDB)) {
                    String tipoUsuario = (String) model.getValueAt(row, 4);

                    userTypeMap.computeIfAbsent(tipoUsuario, k -> new ArrayList<>()).add(id);

                } else {
                    selectedIds.add(id);
                }
            }
        }

        boolean allSuccess = true;

        if ("Usuarios".equals(tableNameDB)) {
            for (Map.Entry<String, List<Object>> entry : userTypeMap.entrySet()) {
                if (entry.getKey().equals("Administrador")) {
                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Não é possível remover o administrador!");
                    return;
                }

                boolean result = TabelaDAO.inativarRegistroConsultaAnonima(entry.getValue(), tableNameDB, acao_ativar_ou_inativar, entry.getKey());

                if (!result) {
                    allSuccess = false;
                }
            }
        } else {
            if ("Paciente".equals(tableNameDB)) {
                allSuccess = TabelaDAO.inativarPacienteArquivoMorto(selectedIds);

            } else {

                allSuccess = TabelaDAO.inativarRegistroConsultaAnonima(selectedIds, tableNameDB, acao_ativar_ou_inativar, "");

            }
        }

        if (allSuccess) {
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Registros inativados com sucesso");
            List<Integer> viewRowsToRemove = new ArrayList<>(selectedRows);
            TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) table.getRowSorter();

            Collections.sort(viewRowsToRemove, Collections.reverseOrder());

            for (Integer viewRow : viewRowsToRemove) {
                try {
                    int modelRow = sorter.convertRowIndexToModel(viewRow);

                    if (modelRow >= 0 && modelRow < model.getRowCount()) {
                        model.removeRow(modelRow);
                    }
                } catch (IndexOutOfBoundsException error) {
                    System.err.println("Erro ao remover linha: " + viewRow + " - " + error.getMessage());
                }
            }

            selectedRows.clear();

            int totalFiltered = sorter.getViewRowCount();
            totalPages = (int) Math.ceil((double) totalFiltered / ROWS_PER_PAGE);

            if (currentPage > totalPages) {
                currentPage = totalPages;
            }

            updateRowFilter(currentPage);
            pagination.setPagegination(currentPage, totalPages);

        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Erro ao inativar registros");
        }

    }
}
