/*
 * Clique nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt para alterar esta licença
 * Clique nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java para editar este modelo
 */
package Visao.Components;

import Multimidia.Sample.SampleData; // Importa dados de amostra
import Persistencia.Dao.ConsultasDinamicas;
import Persistencia.Dao.JPAUtil;
import Persistencia.modelTemp.ModelEmployee; // Importa o modelo de dados de funcionário
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
import java.util.ArrayList;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel; // Importa modelo padrão de tabela
import javax.swing.table.TableColumn;
import net.miginfocom.swing.MigLayout; // Importa layout Mig
import raven.toast.Notifications;

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
        
        // Create the table model with column names and no rows initially
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Allow editing only on the first column (checkbox)
                return column == 0;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // Set column classes based on index (e.g., boolean for checkbox)
                if (columnIndex == 0) return Boolean.class;
                return super.getColumnClass(columnIndex);
            }
        };
        
        this.model = model;

        // Criação da tabela
        JTable table = new JTable(model);
        
        // Painel de rolagem da tabela
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Define borda vazia

        //Configurações dinâmicas das colunas da tabela
        for (int i = 0; i < colunasTabela.length; i++) {
            TableColumn column = table.getColumnModel().getColumn(i);

            // Configurações baseadas no índice da coluna
            if (i == 0) {
                column.setMaxWidth(50); // Exemplo: coluna de seleção (checkbox)
            } else if (i == 1) {
                column.setMaxWidth(50); // Exemplo: coluna de índice
            } else {
                column.setPreferredWidth(150); // Configuração padrão para demais colunas
            }
        }
        
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        // Desabilita a reordenação das colunas da tabela
        //table.getTableHeader().setReorderingAllowed(false);
        
        // Permitir sorting de colunas
        //table.setAutoCreateRowSorter(true);

        // Aplica o renderizador de célula de perfil na tabela
        table.setDefaultRenderer(ModelProfile.class, new TableProfileCellRenderer(table));

        // Aplica o checkbox customizado no cabeçalho da tabela
        table.getColumnModel().getColumn(0).setHeaderRenderer(new CheckBoxTableHeaderRenderer(table, 0));

        // Alinhamento do cabeçalho da tabela
        table.getTableHeader().setDefaultRenderer(new TableHeaderAlignment(table) {
            @Override
            protected int getAlignment(int column) {
                if (column == 1) {
                    return SwingConstants.CENTER; // Centraliza o cabeçalho da coluna 1
                }
                return SwingConstants.LEADING; // Alinhamento à esquerda para as demais colunas
            }
        });

        // Estilização do painel
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:20;" + // Arredondamento do painel
                "background:$Table.background;"); // Cor de fundo da tabela
        table.getTableHeader().putClientProperty(FlatClientProperties.STYLE, "" +
                "height:30;" + // Altura do cabeçalho da tabela
                "hoverBackground:null;" + // Cor de fundo ao passar o mouse
                "pressedBackground:null;" + // Cor de fundo ao pressionar
                "separatorColor:$TableHeader.background;"); // Cor do separador do cabeçalho
        table.putClientProperty(FlatClientProperties.STYLE, "" +
                "rowHeight:70;" + // Altura das linhas
                "showHorizontalLines:true;" + // Exibe linhas horizontais
                "intercellSpacing:0,1;" + // Espaçamento entre células
                "cellFocusColor:$TableHeader.hoverBackground;" + // Cor do foco da célula
                "selectionBackground:$TableHeader.hoverBackground;" + // Cor de fundo da seleção
                "selectionInactiveBackground:$TableHeader.hoverBackground;" + // Cor de fundo da seleção inativa
                "selectionForeground:$Table.foreground;"); // Cor do texto da seleção
        scrollPane.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, "" +
                "trackArc:$ScrollBar.thumbArc;" + // Arredondamento do polegar da barra de rolagem
                "trackInsets:3,3,3,3;" + // Margens do trilho da barra de rolagem
                "thumbInsets:3,3,3,3;" + // Margens do polegar da barra de rolagem
                "background:$Table.background;"); // Cor de fundo do painel de rolagem

        this.table = table;
        
        model.addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();

            // Check if the changed column is the checkbox column (column 0)
            if (column == 0) {
                Boolean isChecked = (Boolean) model.getValueAt(row, column);

                if (isChecked) {
                    // Add row to selectedRows if not already present
                    if (!selectedRows.contains(row)) {
                        selectedRows.add(row);
                    }
                } else {
                    // Remove row from selectedRows if it is deselected
                    selectedRows.remove(Integer.valueOf(row));
                }

                System.out.println("Updated selectedRows: " + selectedRows);
            }
        });
        
        // Criação do título
        JLabel title = new JLabel("Tabela de Listagem");
        title.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +2"); // Estilo do título
        panel.add(title, "gapx 20"); // Adiciona o título ao painel

        // Criação do cabeçalho
        panel.add(createHeaderAction()); // Adiciona ações do cabeçalho

        JSeparator separator = new JSeparator(); // Criação do separador
        separator.putClientProperty(FlatClientProperties.STYLE, "" +
                "foreground:$Table.gridColor;"); // Cor do separador
        panel.add(separator, "height 2"); // Adiciona o separador ao painel
        panel.add(scrollPane); // Adiciona o painel de rolagem da tabela ao painel
        
        totalPages = (int) Math.ceil((double) allDataTable.size() / ROWS_PER_PAGE); // Calcula o total de páginas
        
        loadPageData(allDataTable, currentPage); // Carrega dados da página atual

        
        // Inicializa e configura a paginação
        pagination = new Pagination();
        pagination.setBackground(new java.awt.Color(0, 102, 102)); // Define a cor de fundo da paginação
        pagination.setPagegination(currentPage, totalPages); // Define a página atual e total
        pagination.addEventPagination(page -> {
            currentPage = page; // Atualiza a página atual
            loadPageData(this.allDataTable, page); // Carrega dados da nova página
        });
        

        panel.add(pagination, "align center, wrap"); // Adiciona a paginação ao painel

        return panel; // Retorna o painel personalizado
    }
    
    /**
    * Método para carregar os dados da página atual na tabela.
    * 
    * @param data Lista de dados a serem carregados
    * @param page Número da página atual
    * @param table Tabela onde os dados serão carregados
    */
    private void loadPageData(List<Object[]> data, int page) {
        System.out.println("\nModel: " + model);
        model.setRowCount(0); // Limpa o modelo da tabela
        
        int start = (page - 1) * ROWS_PER_PAGE;
        int end = Math.min(start + ROWS_PER_PAGE, data.size());

        // Verifica se há dados para carregar
        if (data.isEmpty() || start >= data.size()) {
            return; // Não há dados para carregar na página atual
        }
        
        for (int i = start; i < end; i++) { 
            Object[] row = data.get(i);  
            // Certifique-se de que os dados carregados tenham o mesmo número de colunas da tabela
            Object[] rowData = new Object[this.tabelaColunas.length];
            for (int j = 0; j < this.tabelaColunas.length; j++) {
                if (j == 0) {
                    rowData[j] = false; // Inicializa a coluna de checkbox com 'false'
                } else if (j - 1 < row.length) {
                    rowData[j] = row[j - 1]; // Ajusta o índice para ignorar a primeira coluna (checkbox)
                } else {
                    rowData[j] = "..."; // Preenche com null caso os dados estejam faltando
                }
            }
            model.addRow(rowData); // Adiciona os dados à tabela
        }
    }

    /**
     * Método para criar ações no cabeçalho da tabela.
     * 
     * @return Componente com as ações do cabeçalho
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

                                ConsultasDinamicas.deletarRegistroConsultaAnonima(selectedIds, tableNameDB);
                                model.removeRow(row);
                            }
                            selectedRows.clear();
                            
                            System.out.println("IDs to be deleted: " + selectedIds);
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

        //cmdCreate.addActionListener(e -> showModal());
        panel.add(txtSearch);
        panel.add(cmdCreate);
        panel.add(cmdEdit);
        panel.add(cmdDelete);
        
        
        // Adiciona um ouvinte de documento para o campo de pesquisa
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterData(txtSearch);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterData(txtSearch);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterData(txtSearch);
            }
        });

        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:null;");
        return panel;
    }
    
    /**
    * Método para filtrar os dados com base na pesquisa.
    */
    private void filterData(JTextField txtSearch) {
        String query = txtSearch.getText().toLowerCase();
        List<Object[]> filteredData = allDataTable.stream()
            .filter(row -> Arrays.stream(row)
                                 .map(cell -> cell == null ? "" : cell.toString().toLowerCase()) 
                                 .anyMatch(cell -> cell.contains(query))
            )
            .toList();

        System.out.println("Dados filtrados: ");
        filteredData.forEach(row -> System.out.println(Arrays.toString(row)));
        System.out.println("\nConsulta: " + query);
        System.out.println("\nDados filtrados: ");
        filteredData.forEach(row -> System.out.println(Arrays.toString(row)));
        
        // Atualiza o número total de páginas
        this.totalPages = (int) Math.ceil((double) filteredData.size() / ROWS_PER_PAGE);
        loadPageData(filteredData, 1); // Carrega os dados filtrados na primeira página
    }
}
