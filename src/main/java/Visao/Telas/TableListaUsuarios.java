/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Visao.Telas;

import Visao.Components.CreateCustomTable;
import Visao.Components.SimpleForm;
import Visao.Utils.RedimencionarIcones;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;

/**
 *
 * @author john
 */
public class TableListaUsuarios extends SimpleForm {

    /**
     * Creates new form listaEsperaTable
     */
    // 1) Defina suas duas queries:
    private static final String QUERY_ATIVOS
            = // administrador não tem coluna ativo, então sem WHERE aqui
            "SELECT id, nome, email, 'Administrador' AS origem FROM administrador "
            + "UNION ALL "
            + "SELECT id, nome, email, 'Estagiario' AS origem  FROM estagiario  WHERE ativo = 1 "
            + "UNION ALL "
            + "SELECT id, nome, email, 'Orientador' AS origem  FROM orientador  WHERE ativo = 1 "
            + "UNION ALL "
            + "SELECT id, nome, email, 'Secretaria' AS origem  FROM secretaria  WHERE ativo = 1 "
            + "UNION ALL "
            + "SELECT id, nome, email, 'Pesquisador' AS origem FROM pesquisador WHERE ativo = 1;";

    private static final String QUERY_INATIVOS
            = // idem: sem WHERE no administrador
            "SELECT id, nome, email, 'Administrador' AS origem FROM administrador "
            + "UNION ALL "
            + "SELECT id, nome, email, 'Estagiario' AS origem  FROM estagiario  WHERE ativo = 0 "
            + "UNION ALL "
            + "SELECT id, nome, email, 'Orientador' AS origem  FROM orientador  WHERE ativo = 0 "
            + "UNION ALL "
            + "SELECT id, nome, email, 'Secretaria' AS origem  FROM secretaria  WHERE ativo = 0 "
            + "UNION ALL "
            + "SELECT id, nome, email, 'Pesquisador' AS origem FROM pesquisador WHERE ativo = 0;";
    private final String[] tableColumns = {"#", "ID", "Nome", "Email", "Tipo"};
    private boolean mostrandoInativos = false;
    private JToggleButton switchToggle;
    private CreateCustomTable customTable;

    public TableListaUsuarios() {
        initComponents();
     
        setLayout(new BorderLayout());
 
        // Remove pNorth do layout principal (para evitar duplicidade)
        remove(pNorth);

        // Cria o botão toggle
        switchToggle = new JToggleButton("Mostrar inativos");
        switchToggle.addActionListener(this::onToggle);

        // Cria o painel do toggle, para centralizar o botão
        javax.swing.JPanel pTogglePanel = new javax.swing.JPanel();
        pTogglePanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER));
        pTogglePanel.add(switchToggle);

        // Cria painel vertical para "empilhar" pNorth e toggle
        javax.swing.JPanel pTop = new javax.swing.JPanel();
        pTop.setLayout(new javax.swing.BoxLayout(pTop, javax.swing.BoxLayout.Y_AXIS));

        // Adiciona pNorth e depois painel do toggle
        pTop.add(pNorth);
        pTop.add(pTogglePanel);

        // Adiciona pTop no norte do layout principal
        add(pTop, BorderLayout.NORTH);

        // Configura painel_lista_espera
        painel_lista_espera.removeAll();
        painel_lista_espera.setLayout(new BorderLayout());

        add(painel_lista_espera, BorderLayout.CENTER);

        rebuildTable();
    }

    private void onToggle(ActionEvent e) {
        mostrandoInativos = switchToggle.isSelected();
        switchToggle.setText(mostrandoInativos ? "Mostrar ativos" : "Mostrar inativos");
        rebuildTable();
    }

    /**
     * Remove o conteúdo antigo e recria CreateCustomTable com a query adequada.
     */
    private void rebuildTable() {
        String query = mostrandoInativos ? QUERY_INATIVOS : QUERY_ATIVOS;
        boolean acaoInativar = mostrandoInativos;                // true = inativar, false = ativar
        String botaoLabel = mostrandoInativos ? "Ativar" : "Inativar";
        String icone = mostrandoInativos
                ? "/Multimidia/imagens/cadeado.png"
                : "/Multimidia/icon/cadeado_desbloqueado.png";

        painel_lista_espera.removeAll();

        customTable = new CreateCustomTable(
                query,
                tableColumns,
                "Todos os Usuários",
                "Usuarios",
                acaoInativar,
                botaoLabel,
                icone
        );

        painel_lista_espera.add(
                customTable.createCustomTable(
                        query,
                        tableColumns,
                        "Todos os Usuários",
                        "Usuarios"
                ),
                BorderLayout.CENTER
        );

        painel_lista_espera.revalidate();
        painel_lista_espera.repaint();
    }
    /**

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painel_lista_espera = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbListaEsperaNaoAtendidos = new javax.swing.JTable();
        pNorth = new javax.swing.JPanel();
        lbClinica = new javax.swing.JLabel();
        lbOrientador = new javax.swing.JLabel();
        lbLogoListaUsuarios = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(950, 650));
        setMinimumSize(new java.awt.Dimension(950, 650));
        setPreferredSize(new java.awt.Dimension(950, 650));
        setLayout(new java.awt.BorderLayout());

        painel_lista_espera.setBackground(new java.awt.Color(255, 255, 255));
        painel_lista_espera.setForeground(new java.awt.Color(255, 255, 255));
        painel_lista_espera.setMaximumSize(new java.awt.Dimension(950, 650));
        painel_lista_espera.setMinimumSize(new java.awt.Dimension(950, 650));
        painel_lista_espera.setPreferredSize(new java.awt.Dimension(950, 650));

        tbListaEsperaNaoAtendidos.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        tbListaEsperaNaoAtendidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", "Fulano de Tal", "19", "14/05/2023", "Masculino", "Adulto"},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Posição", "Nome", "Idade", "Data Cadastro", "Sexo", "Faixa Etária"
            }
        ));
        jScrollPane1.setViewportView(tbListaEsperaNaoAtendidos);

        javax.swing.GroupLayout painel_lista_esperaLayout = new javax.swing.GroupLayout(painel_lista_espera);
        painel_lista_espera.setLayout(painel_lista_esperaLayout);
        painel_lista_esperaLayout.setHorizontalGroup(
            painel_lista_esperaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painel_lista_esperaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1005, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(3, Short.MAX_VALUE))
        );
        painel_lista_esperaLayout.setVerticalGroup(
            painel_lista_esperaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painel_lista_esperaLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(110, Short.MAX_VALUE))
        );

        add(painel_lista_espera, java.awt.BorderLayout.CENTER);

        pNorth.setBackground(new java.awt.Color(0, 102, 102));
        pNorth.setPreferredSize(new java.awt.Dimension(638, 183));

        lbClinica.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lbClinica.setForeground(new java.awt.Color(255, 255, 255));
        lbClinica.setText("Clínica de Psicologia");

        lbOrientador.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        lbOrientador.setForeground(new java.awt.Color(255, 255, 255));
        lbOrientador.setText("Lista de Usuários");

        lbLogoListaUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Multimidia/imagens/logoOrientador2_resized.png"))); // NOI18N

        javax.swing.GroupLayout pNorthLayout = new javax.swing.GroupLayout(pNorth);
        pNorth.setLayout(pNorthLayout);
        pNorthLayout.setHorizontalGroup(
            pNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pNorthLayout.createSequentialGroup()
                .addComponent(lbLogoListaUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbClinica)
                    .addComponent(lbOrientador))
                .addContainerGap(588, Short.MAX_VALUE))
        );
        pNorthLayout.setVerticalGroup(
            pNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbLogoListaUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 183, Short.MAX_VALUE)
            .addGroup(pNorthLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(lbOrientador)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbClinica)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(pNorth, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbClinica;
    private javax.swing.JLabel lbLogoListaUsuarios;
    private javax.swing.JLabel lbOrientador;
    private javax.swing.JPanel pNorth;
    private javax.swing.JPanel painel_lista_espera;
    private javax.swing.JTable tbListaEsperaNaoAtendidos;
    // End of variables declaration//GEN-END:variables

}
