/*
 * Clique nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt para alterar esta licença
 * Clique nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java para editar este modelo
 */
package Visao.Components;

import Multimidia.Sample.SampleData; // Importa dados de amostra
import Persistencia.modelTemp.ModelEmployee; // Importa o modelo de dados de funcionário
import Persistencia.modelTemp.ModelProfile; // Importa o modelo de dados de perfil
import Visao.Components.pagination.Pagination; // Importa a classe de paginação
import Visao.Utils.RedimencionarIcones; // Importa utilitário para redimensionar ícones
import Visao.Utils.table.CheckBoxTableHeaderRenderer; // Importa renderizador de cabeçalho de tabela com checkbox
import Visao.Utils.table.TableHeaderAlignment; // Importa alinhamento de cabeçalho de tabela
import Visao.Utils.table.TableProfileCellRenderer; // Importa renderizador de células de perfil na tabela
import com.formdev.flatlaf.FlatClientProperties; // Importa propriedades do cliente FlatLaf
import com.formdev.flatlaf.extras.FlatSVGIcon; // Importa ícones SVG do FlatLaf
import java.awt.Component; // Importa a classe Component
import java.util.List; // Importa a lista
import javax.swing.BorderFactory; // Importa fábrica de bordas
import javax.swing.JButton; // Importa o botão
import javax.swing.JLabel; // Importa o rótulo
import javax.swing.JPanel; // Importa o painel
import javax.swing.JScrollPane; // Importa o painel de rolagem
import javax.swing.JSeparator; // Importa o separador
import javax.swing.JTable; // Importa a tabela
import javax.swing.JTextField; // Importa o campo de texto
import javax.swing.RowSorter;
import javax.swing.RowSorter.SortKey;
import javax.swing.SortOrder;
import javax.swing.SwingConstants; // Importa constantes de alinhamento
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel; // Importa modelo padrão de tabela
import javax.swing.table.TableModel;
import javax.swing.event.RowSorterEvent;
import net.miginfocom.swing.MigLayout; // Importa layout Mig

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
    private List<ModelEmployee> sortedData; // Holds the globally sorted data

    /**
     * Método para criar a tabela personalizada.
     * 
     * @return Componente da tabela personalizada
     */
    public Component createCustomTable() {
        // Criação do painel com layout Mig
        JPanel panel = new JPanel(new MigLayout("fillx,wrap,insets 10 0 10 0", "[fill]", "[][][]0[fill,grow]"));

        // Criação do modelo da tabela
        Object columns[] = new Object[]{"Selecionar", "#", "Nome", "Data", "Salário", "Posição", "Descrição"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Permite que a célula na coluna 0 seja editável para o checkbox
                return column == 0;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // Usa tipo booleano na coluna 0 para o checkbox
                if (columnIndex == 0)
                    return Boolean.class;
                // Usa a classe de perfil na coluna 2
                if (columnIndex == 2) {
                    return ModelProfile.class;
                }
                return super.getColumnClass(columnIndex); // Retorna a classe padrão
            }
        };

        // Criação da tabela
        JTable table = new JTable(model);

        // Painel de rolagem da tabela
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Define borda vazia

        // Configurações das colunas da tabela
        table.getColumnModel().getColumn(0).setMaxWidth(50); // Largura máxima para a coluna de seleção
        table.getColumnModel().getColumn(1).setMaxWidth(50); // Largura máxima para a coluna de número
        table.getColumnModel().getColumn(2).setPreferredWidth(150); // Largura preferencial para a coluna de nome
        table.getColumnModel().getColumn(5).setPreferredWidth(100); // Largura preferencial para a coluna de posição
        table.getColumnModel().getColumn(6).setPreferredWidth(250); // Largura preferencial para a coluna de descrição

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

        // Dados de amostra
//        for (ModelEmployee d : SampleData.getSampleEmployeeData(false)) {
//            model.addRow(d.toTableRowCustom(table.getRowCount() + 1)); // Adiciona dados de amostra ao modelo
//        }
//        table.getRowSorter().addRowSorterListener(e -> {
//            if (e.getType() == RowSorterEvent.Type.SORT_ORDER_CHANGED) {
//                RowSorter<? extends TableModel> sorter = (RowSorter<? extends TableModel>) e.getSource();
//                List<? extends RowSorter.SortKey> sortKeys = sorter.getSortKeys();
//                if (!sortKeys.isEmpty()) {
//                    SortKey sortKey = sortKeys.get(0); // Get primary sort key
//                    int columnIndex = sortKey.getColumn();
//                    SortOrder order = sortKey.getSortOrder();
//
//                    // Sort sortedData based on columnIndex and order
//                    sortedData.sort((emp1, emp2) -> {
//                        int comparison = 0;
//
//                        switch (columnIndex) {
//                            case 1: // Profile Name (assuming this column displays profile name)
//                                comparison = emp1.getProfile().getName().compareTo(emp2.getProfile().getName());
//                                break;
//                            case 2: // Location
//                                comparison = emp1.getProfile().getLocation().compareTo(emp2.getProfile().getLocation());
//                                break;
//                            case 3: // Date
//                                comparison = emp1.getDate().compareTo(emp2.getDate());
//                                break;
//                            case 4: // Salary
//                                comparison = Double.compare(emp1.getSalary(), emp2.getSalary());
//                                break;
//                            case 5: // Position
//                                comparison = emp1.getPosition().compareTo(emp2.getPosition());
//                                break;
//                            case 6: // Description
//                                comparison = emp1.getDescription().compareTo(emp2.getDescription());
//                                break;
//                            default:
//                                // Handle other columns if needed
//                                comparison = 0;
//                                break;
//                        }
//
//                        return order == SortOrder.ASCENDING ? comparison : -comparison;
//                    });
//
//                    loadPageData(sortedData, currentPage); // Refresh table with sorted data
//                }
//            }
//        });


        // Adiciona dados de exemplo
        List<ModelEmployee> allData = SampleData.getSampleEmployeeData(false); // Obtém todos os dados de funcionários
        totalPages = (int) Math.ceil((double) allData.size() / ROWS_PER_PAGE); // Calcula o total de páginas
        loadPageData(allData, currentPage); // Carrega dados da página atual
        
        // Inicializa e configura a paginação
        pagination = new Pagination();
        pagination.setBackground(new java.awt.Color(0, 102, 102)); // Define a cor de fundo da paginação
        pagination.setPagegination(currentPage, totalPages); // Define a página atual e total
        pagination.addEventPagination(page -> {
            currentPage = page; // Atualiza a página atual
            loadPageData(allData, page); // Carrega dados da nova página
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
    private void loadPageData(List<ModelEmployee> data, int page) {
        model.setRowCount(0); // Limpa o modelo da tabela
        int start = (page - 1) * ROWS_PER_PAGE;
        int end = Math.min(start + ROWS_PER_PAGE, data.size());

        // Verifica se há dados para carregar
        if (data.isEmpty() || start >= data.size()) {
            return; // Não há dados para carregar na página atual
        }

        // Adiciona as linhas da página atual ao modelo da tabela
        for (int i = start; i < end; i++) {
            model.addRow(data.get(i).toTableRowCustom(i + 1)); // Adiciona a linha de dados
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
       String query = txtSearch.getText().toLowerCase(); // Obtém o texto da pesquisa em minúsculas
       List<ModelEmployee> allData = SampleData.getSampleEmployeeData(false); // Obtém todos os dados

       // Filtra os dados com base na consulta
       List<ModelEmployee> filteredData = allData.stream()
               .filter(employee -> employee.getDate().toLowerCase().contains(query) || // Exemplo: busca pela data
                                  String.valueOf(employee.getSalary()).contains(query) || // Exemplo: busca pelo salário
                                  employee.getPosition().toLowerCase().contains(query) || // Exemplo: busca pela posição
                                  employee.getDescription().toLowerCase().contains(query) || // Exemplo: busca pela descrição
                                  employee.getProfile().getName().toLowerCase().contains(query) // Exemplo: busca pelo nome do perfil
               )
               .toList(); // Coleta os resultados filtrados

       // Atualiza o número total de páginas
       totalPages = (int) Math.ceil((double) filteredData.size() / ROWS_PER_PAGE);
       loadPageData(filteredData, 1); // Carrega os dados filtrados na primeira página
   }
}
