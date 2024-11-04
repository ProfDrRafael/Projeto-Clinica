/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Visao.Components;

import Multimidia.Sample.SampleData;
import Persistencia.modelTemp.ModelEmployee;
import Persistencia.modelTemp.ModelProfile;
import Visao.Components.pagination.Pagination;
import Visao.Utils.redimencionarIcones;
import Visao.Utils.table.CheckBoxTableHeaderRenderer;
import Visao.Utils.table.TableHeaderAlignment;
import Visao.Utils.table.TableProfileCellRenderer;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Component;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author john
 */
public class CreateCustomTable {
    
    private static final int ROWS_PER_PAGE = 10; // Rows per page
    private int totalPages;
    private int currentPage = 1;
    private DefaultTableModel model;
    private Pagination pagination = new Pagination();


    
    public Component createCustomTable() {
        JPanel panel = new JPanel(new MigLayout("fillx,wrap,insets 10 0 10 0", "[fill]", "[][][]0[fill,grow]"));

        // create table model
        Object columns[] = new Object[]{"Selecionar", "#", "Nome", "Data", "Salário", "Posição", "Descrição"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // allow cell editable at column 0 for checkbox
                return column == 0;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // use boolean type at column 0 for checkbox
                if (columnIndex == 0)
                    return Boolean.class;
                // use profile class
                if (columnIndex == 2) {
                    return ModelProfile.class;
                }
                return super.getColumnClass(columnIndex);
            }
        };

        // create table
        JTable table = new JTable(model);

        // table scroll
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // table option
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumnModel().getColumn(1).setMaxWidth(50);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);
        table.getColumnModel().getColumn(6).setPreferredWidth(250);

        // disable reordering table column
        table.getTableHeader().setReorderingAllowed(false);

        // apply profile cell renderer
        table.setDefaultRenderer(ModelProfile.class, new TableProfileCellRenderer(table));

        // apply checkbox custom to table header
        table.getColumnModel().getColumn(0).setHeaderRenderer(new CheckBoxTableHeaderRenderer(table, 0));

        // alignment table header
        table.getTableHeader().setDefaultRenderer(new TableHeaderAlignment(table) {
            @Override
            protected int getAlignment(int column) {
                if (column == 1) {
                    return SwingConstants.CENTER;
                }
                return SwingConstants.LEADING;
            }
        });

        // style
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:20;" +
                "background:$Table.background;");
        table.getTableHeader().putClientProperty(FlatClientProperties.STYLE, "" +
                "height:30;" +
                "hoverBackground:null;" +
                "pressedBackground:null;" +
                "separatorColor:$TableHeader.background;");
        table.putClientProperty(FlatClientProperties.STYLE, "" +
                "rowHeight:70;" +
                "showHorizontalLines:true;" +
                "intercellSpacing:0,1;" +
                "cellFocusColor:$TableHeader.hoverBackground;" +
                "selectionBackground:$TableHeader.hoverBackground;" +
                "selectionInactiveBackground:$TableHeader.hoverBackground;" +
                "selectionForeground:$Table.foreground;");
        scrollPane.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, "" +
                "trackArc:$ScrollBar.thumbArc;" +
                "trackInsets:3,3,3,3;" +
                "thumbInsets:3,3,3,3;" +
                "background:$Table.background;");

        // create title
        JLabel title = new JLabel("Tabela de Listagem");
        title.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +2");
        panel.add(title, "gapx 20");

        // create header
        panel.add(createHeaderAction());

        JSeparator separator = new JSeparator();
        separator.putClientProperty(FlatClientProperties.STYLE, "" +
                "foreground:$Table.gridColor;");
        panel.add(separator, "height 2");
        panel.add(scrollPane);

        // sample data
//        int count = 0;
//        for (ModelEmployee d : SampleData.getSampleEmployeeData(false)) {
//            model.addRow(d.toTableRowCustom(table.getRowCount() + 1));
//            count++;
//        }

        // Add sample data
        List<ModelEmployee> allData = SampleData.getSampleEmployeeData(false);
        totalPages = (int) Math.ceil((double) allData.size() / ROWS_PER_PAGE);
        loadPageData(allData, currentPage);
        
        // Initialize and configure pagination
        pagination = new Pagination();
        pagination.setBackground(new java.awt.Color(0, 102, 102));
        pagination.setPagegination(currentPage, totalPages);
        pagination.addEventPagination(page -> {
            currentPage = page;
            loadPageData(allData, page);
        });
        panel.add(pagination, "align center, wrap");
        
        table.setAutoCreateRowSorter(true);
        
        return panel;
    }
    
    // Load data for the specified page
    private void loadPageData(List<ModelEmployee> allData, int page) {
        // Clear the current table data
        model.setRowCount(0);

        // Calculate the start and end indices for the data on the current page
        int start = (page - 1) * ROWS_PER_PAGE;
        int end = Math.min(start + ROWS_PER_PAGE, allData.size());

        // Check if there's data for the page before attempting to add rows
        if (start < end) {
            for (int i = start; i < end; i++) {
                model.addRow(allData.get(i).toTableRowCustom(i + 1));
            }
        } else {
            // Log or handle the case where there is no data for the requested page
            System.out.println("No data available for page " + page);
        }

        System.out.println("Loading page " + page + " with data range: " + start + " to " + end);
        // Update pagination display or disable pagination controls if necessary
        pagination.setPagegination(page, totalPages);
    }
    
    private Component createHeaderAction() {
        JPanel panel = new JPanel(new MigLayout("insets 5 20 5 20", "[fill,230]push[][]"));

        JTextField txtSearch = new JTextField();
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Buscar...");
        txtSearch.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("Multimidia/icon/search.svg", 0.4f));
        JButton cmdCreate = new JButton("Cadastrar");
        JButton cmdEdit = new JButton("Editar");
        JButton cmdDelete = new JButton("Remover");
        
        redimencionarIcones redimencionarIcone = new redimencionarIcones();
        
        redimencionarIcone.redimensionarIcones(cmdCreate, "/Multimidia/imagens/view.png", 10);
        redimencionarIcone.redimensionarIcones(cmdEdit, "/Multimidia/imagens/edit.png", 10);
        redimencionarIcone.redimensionarIcones(cmdDelete, "/Multimidia/imagens/delete.png", 10);

        //cmdCreate.addActionListener(e -> showModal());
        panel.add(txtSearch);
        panel.add(cmdCreate);
        panel.add(cmdEdit);
        panel.add(cmdDelete);

        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:null;");
        return panel;
    }
    
}
