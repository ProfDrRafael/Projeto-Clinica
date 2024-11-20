/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Visao.Telas;
import Visao.Components.SimpleForm;
import Visao.Utils.EditorTextPaneEstilization;
import Visao.Utils.RedimencionarIcones;

/**
 *
 * @author john
 */
public class FormProntuario extends SimpleForm {

    /**
     * Creates new form prontuarioForm
     */
    public FormProntuario() {
        initComponents();
        //redimensionarIcones();
        
        EditorTextPaneEstilization.EstilizeEditorTextPane(tpObservacoes);
        EditorTextPaneEstilization.EstilizeEditorTextPane(tpQueixa);
        
        RedimencionarIcones redimencionarIcone = new RedimencionarIcones();
        redimencionarIcone.redimensionarIcones(btSalvar, "/Multimidia/imagens/salvar-btn.png");
        redimencionarIcone.redimensionarIcones(btEditar, "/Multimidia/imagens/editar-btn.png");
        redimencionarIcone.redimensionarIcones(btCancelar, "/Multimidia/imagens/excluir.png");
        redimencionarIcone.redimensionarIcones(btHistoricoAtendimentos, "/Multimidia/imagens/historicoAtendimentos.png");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pNorth = new javax.swing.JPanel();
        lbClinica = new javax.swing.JLabel();
        lbProntuario = new javax.swing.JLabel();
        pnForm = new javax.swing.JPanel();
        jlQueixaInicial = new javax.swing.JLabel();
        jlObservacoes = new javax.swing.JLabel();
        tfPaciente = new javax.swing.JTextField();
        jlPaciente = new javax.swing.JLabel();
        cbEstagiario = new javax.swing.JComboBox<>();
        jlEstagiario = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btEditar = new javax.swing.JButton();
        btCancelar = new javax.swing.JButton();
        btSalvar = new javax.swing.JButton();
        btHistoricoAtendimentos = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tpQueixa = new javax.swing.JTextPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        tpObservacoes = new javax.swing.JTextPane();
        jSeparator2 = new javax.swing.JSeparator();
        cbEncaminhado = new javax.swing.JComboBox<>();
        lbEncaminhado = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(950, 764));
        setMinimumSize(new java.awt.Dimension(950, 764));
        setPreferredSize(new java.awt.Dimension(950, 764));
        setLayout(new java.awt.BorderLayout());

        pNorth.setBackground(new java.awt.Color(0, 102, 102));
        pNorth.setPreferredSize(new java.awt.Dimension(638, 183));

        lbClinica.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lbClinica.setForeground(new java.awt.Color(255, 255, 255));
        lbClinica.setText("Clínica de Psicologia");

        lbProntuario.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        lbProntuario.setForeground(new java.awt.Color(255, 255, 255));
        lbProntuario.setText("Prontuário");

        javax.swing.GroupLayout pNorthLayout = new javax.swing.GroupLayout(pNorth);
        pNorth.setLayout(pNorthLayout);
        pNorthLayout.setHorizontalGroup(
            pNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pNorthLayout.createSequentialGroup()
                .addContainerGap(142, Short.MAX_VALUE)
                .addGroup(pNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbClinica)
                    .addComponent(lbProntuario))
                .addGap(0, 655, Short.MAX_VALUE))
        );
        pNorthLayout.setVerticalGroup(
            pNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pNorthLayout.createSequentialGroup()
                .addGap(0, 72, Short.MAX_VALUE)
                .addComponent(lbProntuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbClinica)
                .addGap(0, 63, Short.MAX_VALUE))
        );

        add(pNorth, java.awt.BorderLayout.NORTH);

        pnForm.setBackground(java.awt.SystemColor.controlHighlight);
        pnForm.setPreferredSize(new java.awt.Dimension(980, 438));

        jlQueixaInicial.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jlQueixaInicial.setForeground(new java.awt.Color(0, 102, 102));
        jlQueixaInicial.setText("Queixa inicial:");

        jlObservacoes.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jlObservacoes.setForeground(new java.awt.Color(0, 102, 102));
        jlObservacoes.setText("Observações:");

        tfPaciente.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        jlPaciente.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jlPaciente.setForeground(new java.awt.Color(0, 102, 102));
        jlPaciente.setText("*Paciente:");

        cbEstagiario.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        cbEstagiario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Isabella", "Pedro", "Bruno" }));

        jlEstagiario.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jlEstagiario.setForeground(new java.awt.Color(0, 102, 102));
        jlEstagiario.setText("*Estagiário:");

        btEditar.setBackground(new java.awt.Color(255, 102, 102));
        btEditar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btEditar.setForeground(new java.awt.Color(51, 51, 51));
        btEditar.setText("Cancelar");

        btCancelar.setBackground(new java.awt.Color(255, 255, 51));
        btCancelar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btCancelar.setForeground(new java.awt.Color(51, 51, 51));
        btCancelar.setText("Editar");

        btSalvar.setBackground(new java.awt.Color(102, 255, 102));
        btSalvar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btSalvar.setForeground(new java.awt.Color(51, 51, 51));
        btSalvar.setText("Salvar");

        btHistoricoAtendimentos.setBackground(new java.awt.Color(204, 204, 204));
        btHistoricoAtendimentos.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btHistoricoAtendimentos.setForeground(new java.awt.Color(51, 51, 51));
        btHistoricoAtendimentos.setText("Histórico de atendimentos");

        tpQueixa.setFont(cbEstagiario.getFont());
        jScrollPane3.setViewportView(tpQueixa);

        tpObservacoes.setFont(cbEstagiario.getFont());
        jScrollPane4.setViewportView(tpObservacoes);

        cbEncaminhado.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        cbEncaminhado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Isabella", "Pedro", "Bruno" }));

        lbEncaminhado.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbEncaminhado.setForeground(new java.awt.Color(0, 102, 102));
        lbEncaminhado.setText("*Encaminhado por:");

        javax.swing.GroupLayout pnFormLayout = new javax.swing.GroupLayout(pnForm);
        pnForm.setLayout(pnFormLayout);
        pnFormLayout.setHorizontalGroup(
            pnFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnFormLayout.createSequentialGroup()
                .addGap(199, 199, 199)
                .addComponent(btEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btCancelar)
                .addGap(18, 18, 18)
                .addComponent(btSalvar)
                .addGap(18, 18, 18)
                .addComponent(btHistoricoAtendimentos, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnFormLayout.createSequentialGroup()
                .addGroup(pnFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnFormLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator2))
                    .addGroup(pnFormLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(pnFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnFormLayout.createSequentialGroup()
                                .addGroup(pnFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jlEstagiario)
                                    .addComponent(cbEstagiario, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbEncaminhado)
                                    .addComponent(cbEncaminhado, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnFormLayout.createSequentialGroup()
                                        .addComponent(jlPaciente)
                                        .addGap(312, 312, 312))
                                    .addComponent(tfPaciente, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)))
                            .addGroup(pnFormLayout.createSequentialGroup()
                                .addGroup(pnFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlObservacoes))
                                .addGap(18, 18, 18)
                                .addGroup(pnFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnFormLayout.createSequentialGroup()
                                        .addComponent(jlQueixaInicial)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(jScrollPane3)))))
                    .addGroup(pnFormLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1)))
                .addContainerGap())
        );
        pnFormLayout.setVerticalGroup(
            pnFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnFormLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(pnFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlEstagiario)
                    .addComponent(jlPaciente))
                .addGap(6, 6, 6)
                .addGroup(pnFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbEstagiario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(lbEncaminhado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbEncaminhado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(pnFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlObservacoes)
                    .addComponent(jlQueixaInicial))
                .addGap(18, 18, 18)
                .addGroup(pnFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                    .addComponent(jScrollPane4))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(pnFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btSalvar)
                    .addGroup(pnFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btEditar)
                        .addComponent(btHistoricoAtendimentos)
                        .addComponent(btCancelar)))
                .addGap(54, 54, 54))
        );

        add(pnForm, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCancelar;
    private javax.swing.JButton btEditar;
    private javax.swing.JButton btHistoricoAtendimentos;
    private javax.swing.JButton btSalvar;
    private javax.swing.JComboBox<String> cbEncaminhado;
    private javax.swing.JComboBox<String> cbEstagiario;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel jlEstagiario;
    private javax.swing.JLabel jlObservacoes;
    private javax.swing.JLabel jlPaciente;
    private javax.swing.JLabel jlQueixaInicial;
    private javax.swing.JLabel lbClinica;
    private javax.swing.JLabel lbEncaminhado;
    private javax.swing.JLabel lbProntuario;
    private javax.swing.JPanel pNorth;
    private javax.swing.JPanel pnForm;
    private javax.swing.JTextField tfPaciente;
    private javax.swing.JTextPane tpObservacoes;
    private javax.swing.JTextPane tpQueixa;
    // End of variables declaration//GEN-END:variables

}
