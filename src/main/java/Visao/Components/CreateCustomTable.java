package Visao.Components;

import Persistencia.Dao.ConsultasDinamicas;
import Persistencia.modelTemp.ModelProfile; // Importa o modelo de dados de perfil
import Visao.Components.pagination.Pagination; // Importa a classe de paginação
import Visao.JframeManager.FormManager;
import Visao.Telas.FormAgenda;
import Visao.Telas.FormAtendimento;
import Visao.Telas.FormPaciente;
import Visao.Telas.FormUsuario;
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

/**
 * Classe para criar uma tabela personalizada com paginação e recursos adicionais.
 * 
 * @author john
 */
public class CreateCustomTable {
    
    private static final int ROWS_PER_PAGE = 10; // Número de linhas por página
    private int totalPages; // Total de páginas
    private int currentPage = 1; // Página atual
    private DefaultTableModel model; // Modelo da tabela
    private Pagination pagination = new Pagination(); // Instância de paginação
    private String nomeTabela;
    private String[] tabelaColunas;
    private List<Object[]> allDataTable = new ArrayList<>();
    private JTable table;
    private String tipoFormTela;
    private String tableNameDB;
    private ArrayList<Integer> selectedRows = new ArrayList<>();
    
    // Variável para armazenar o texto de pesquisa (em minúsculo)
    private String searchQuery = "";

    public CreateCustomTable(String nomeTabela, String[] tabelaColunas, String tipoFormTela, String tableNameDB) {
        this.nomeTabela = nomeTabela;
        this.tabelaColunas = tabelaColunas;
        this.tipoFormTela = tipoFormTela;
        this.tableNameDB = tableNameDB;
    }

    /**
     * Método para criar a tabela personalizada.
     * 
     * @return Componente da tabela personalizada
     */
    public Component createCustomTable(String nomeTabela, String[] colunasTabela, String tipoFormTela, String tableNameDB) {
        // Criação do painel com layout Mig
        JPanel panel = new JPanel(new MigLayout("fillx,wrap,insets 10 0 10 0", "[fill]", "[][][]0[fill,grow]"));

        // Buscar dados do banco
        List<Object[]> data = ConsultasDinamicas.buscarTabelaConsultaAnonima(nomeTabela);
        
        // Atualiza a lista global de dados
        allDataTable = data;

        // Column names
        String[] columns = colunasTabela;
        
        // Cria o modelo da tabela com os nomes das colunas
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Permite edição apenas na primeira coluna (checkbox)
                return column == 0;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // Define as classes das colunas (boolean para checkbox)
                if (columnIndex == 0) return Boolean.class;
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
            if (i == 0) {
                column.setMaxWidth(50); // Coluna de seleção (checkbox)
            } else if (i == 1) {
                column.setMaxWidth(50); // Exemplo: coluna de índice
            } else {
                column.setPreferredWidth(150); // Configuração padrão
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
            if (column == 0) {
                Boolean isChecked = (Boolean) model.getValueAt(row, column);
                if (isChecked) {
                    if (!selectedRows.contains(row)) {
                        selectedRows.add(row);
                    }
                } else {
                    selectedRows.remove(Integer.valueOf(row));
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
     * Atualiza o RowFilter do TableRowSorter para combinar (opcionalmente) a pesquisa
     * e a exibição apenas dos dados da página atual.
     * 
     * @param page Página atual a ser exibida
     */
    private void updateRowFilter(int page) {
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

    /**
     * Cria a barra de ações (pesquisa, cadastrar, editar, remover) para o cabeçalho da tabela.
     */
    private Component createHeaderAction() {
        JPanel panel = new JPanel(new MigLayout("insets 5 20 5 20", "[fill,230]push[][]"));

        JTextField txtSearch = new JTextField();
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Buscar...");
        txtSearch.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("Multimidia/icon/search.svg", 0.4f));
        JButton cmdCreate = new JButton("Cadastrar");
        JButton cmdEdit = new JButton("Editar");
        JButton cmdDelete = new JButton("Remover");
        
        cmdCreate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switch (tipoFormTela) {
                    case "Lista de Espera Geral":
                        FormManager.showForm(new FormPaciente());
                        break;
                    case "Lista de Espera Especifica":
                        FormManager.showForm(new FormPaciente());
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
                        FormManager.showForm(new FormPaciente());
                        break;
                    case "Todos os Usuários":
                        FormManager.showForm(new FormUsuario());
                        break;
                    default:
                        FormManager.showForm(new PageWelcome());
                        break;
                }
            }
        });
        
        cmdEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Lógica para editar registros
            }
        });
        
        cmdDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedRows.size() > 0) {
                    MessagesAlert.showWarningMessage("Deseja continuar?", response -> {
                        if (response) {
                            List<Object> selectedIds = new ArrayList<>();
                            List<Integer> rowsToRemove = new ArrayList<>(selectedRows);
                            for (int i = rowsToRemove.size() - 1; i >= 0; i--) {
                                int row = rowsToRemove.get(i);
                                Object id = model.getValueAt(row, 1);  
                                selectedIds.add(id);
                                
                                boolean result;
                                if(tableNameDB == "Usuarios"){
                                    String tipoUsuario = (String) model.getValueAt(row, 4);
                                    result = ConsultasDinamicas.deletarRegistroConsultaAnonima(selectedIds, tipoUsuario);
                                } else {
                                    result = ConsultasDinamicas.deletarRegistroConsultaAnonima(selectedIds, tableNameDB);
                                }
                                
                                if(result){
                                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Registros removidos com sucesso");
                                    model.removeRow(row);
                                } else {
                                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Erro ao remover registros");
                                }    
                            }
                            selectedRows.clear();
                        }
                    });
                } else {
                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Selecione um registro para remover");
                }
            }
        });
        
        RedimencionarIcones redimencionarIcone = new RedimencionarIcones();
        redimencionarIcone.redimensionarIcones(cmdCreate, "/Multimidia/imagens/view.png", 10);
        redimencionarIcone.redimensionarIcones(cmdEdit, "/Multimidia/imagens/edit.png", 10);
        redimencionarIcone.redimensionarIcones(cmdDelete, "/Multimidia/imagens/delete.png", 10);

        panel.add(txtSearch);
        panel.add(cmdCreate);
        panel.add(cmdEdit);
        panel.add(cmdDelete);
        
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
                        public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
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

        panel.putClientProperty(FlatClientProperties.STYLE, "background:null;");
        return panel;
    }
}
