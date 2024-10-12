/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Visao.Telas;
import java.awt.Image;
import javax.swing.ImageIcon;
import Visao.Components.SimpleForm;
import Visao.Utils.redimencionarIcones;

/**
 *
 * @author john
 */
public class TableListaEspera extends SimpleForm {

    /**
     * Creates new form listaEsperaTable
     */
    public TableListaEspera() {
        initComponents();
        
        redimencionarIcones redimencionarIcone = new redimencionarIcones();
        redimencionarIcone.redimensionarIcones(btCancelar, "/Multimidia/imagens/excluir.png");
        redimencionarIcone.redimensionarIcones(btEditar, "/Multimidia/imagens/editar-btn.png");
        redimencionarIcone.redimensionarIcones(btLupa, "/Multimidia/imagens/lupa.png");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painel_lista_espera = new javax.swing.JPanel();
        btEditar = new javax.swing.JButton();
        btCancelar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btLupa = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        pNorth = new javax.swing.JPanel();
        lbClinica = new javax.swing.JLabel();
        lbOrientador = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(1024, 768));
        setMinimumSize(new java.awt.Dimension(1024, 768));
        setPreferredSize(new java.awt.Dimension(1024, 768));
        setLayout(new java.awt.BorderLayout());

        painel_lista_espera.setBackground(new java.awt.Color(255, 255, 255));
        painel_lista_espera.setForeground(new java.awt.Color(255, 255, 255));
        painel_lista_espera.setMaximumSize(new java.awt.Dimension(1024, 768));
        painel_lista_espera.setMinimumSize(new java.awt.Dimension(1024, 768));
        painel_lista_espera.setPreferredSize(new java.awt.Dimension(1024, 768));

        btEditar.setBackground(new java.awt.Color(255, 255, 51));
        btEditar.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btEditar.setForeground(new java.awt.Color(51, 51, 51));
        btEditar.setText("Editar");
        btEditar.setBorder(btCancelar.getBorder());

        btCancelar.setBackground(new java.awt.Color(255, 102, 102));
        btCancelar.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btCancelar.setForeground(new java.awt.Color(51, 51, 51));
        btCancelar.setText("Solicitar Exlusão");
        btCancelar.setBorder(btLupa.getBorder());

        btLupa.setBackground(new java.awt.Color(204, 204, 204));
        btLupa.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btLupa.setForeground(new java.awt.Color(51, 51, 51));
        btLupa.setText("Lupa");

        jTable1.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", "Fulano de Tal", "19", "14/05/2023", "Masculino", "Adulto"},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Posição", "Nome", "Idade", "Data", "Sexo", "Faixa Etária"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout painel_lista_esperaLayout = new javax.swing.GroupLayout(painel_lista_espera);
        painel_lista_espera.setLayout(painel_lista_esperaLayout);
        painel_lista_esperaLayout.setHorizontalGroup(
            painel_lista_esperaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painel_lista_esperaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btLupa)
                .addGap(106, 106, 106))
            .addGroup(painel_lista_esperaLayout.createSequentialGroup()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 1062, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(painel_lista_esperaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1005, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );
        painel_lista_esperaLayout.setVerticalGroup(
            painel_lista_esperaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painel_lista_esperaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painel_lista_esperaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btEditar)
                    .addComponent(btCancelar)
                    .addComponent(btLupa))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(painel_lista_espera, java.awt.BorderLayout.CENTER);

        pNorth.setBackground(new java.awt.Color(0, 102, 102));
        pNorth.setPreferredSize(new java.awt.Dimension(638, 183));

        lbClinica.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lbClinica.setForeground(new java.awt.Color(255, 255, 255));
        lbClinica.setText("Clínica de Psicologia");

        lbOrientador.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        lbOrientador.setForeground(new java.awt.Color(255, 255, 255));
        lbOrientador.setText("Lista de Espera Não Atendidos");

        javax.swing.GroupLayout pNorthLayout = new javax.swing.GroupLayout(pNorth);
        pNorth.setLayout(pNorthLayout);
        pNorthLayout.setHorizontalGroup(
            pNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pNorthLayout.createSequentialGroup()
                .addContainerGap(62, Short.MAX_VALUE)
                .addGroup(pNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbClinica)
                    .addComponent(lbOrientador))
                .addGap(0, 545, Short.MAX_VALUE))
        );
        pNorthLayout.setVerticalGroup(
            pNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pNorthLayout.createSequentialGroup()
                .addGap(0, 67, Short.MAX_VALUE)
                .addComponent(lbOrientador)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbClinica)
                .addGap(0, 68, Short.MAX_VALUE))
        );

        add(pNorth, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCancelar;
    private javax.swing.JButton btEditar;
    private javax.swing.JButton btLupa;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lbClinica;
    private javax.swing.JLabel lbOrientador;
    private javax.swing.JPanel pNorth;
    private javax.swing.JPanel painel_lista_espera;
    // End of variables declaration//GEN-END:variables
    private void redimensionarIcones(){
        
        //botao Salvar
        ImageIcon iconeOriginalLupa = new ImageIcon(getClass().getResource("/imagens/lupa.png"));
        Image iconeEmEscalaLupa = iconeOriginalLupa.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
        btLupa.setIcon(new ImageIcon(iconeEmEscalaLupa));
        btLupa.setIconTextGap(10);
        
        //botao Editar
        ImageIcon iconeOriginalEditar = new ImageIcon(getClass().getResource("/imagens/editar-btn.png"));
        Image iconeEmEscalaEditar = iconeOriginalEditar.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
        btEditar.setIcon(new ImageIcon(iconeEmEscalaEditar));
        btEditar.setIconTextGap(10);
        
        //botao Cancelar
        ImageIcon iconeOriginalCancelar = new ImageIcon(getClass().getResource("/imagens/excluir.png"));
        Image iconeEmEscalaCancelar = iconeOriginalCancelar.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
        btCancelar.setIcon(new ImageIcon(iconeEmEscalaCancelar));
        btCancelar.setIconTextGap(10);
    }

}
