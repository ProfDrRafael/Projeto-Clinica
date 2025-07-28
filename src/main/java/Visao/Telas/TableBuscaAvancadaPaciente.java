/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Visao.Telas;

import Persistencia.Entity.Paciente;
import Regradenegocio.SessaoRN;
import VO.SessaoVO;
import Visao.Components.Table;
import Visao.Components.PanelTemplate;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JToggleButton;

/**
 *
 * @author john
 */
public class TableBuscaAvancadaPaciente extends PanelTemplate {

    private String[] tableColumns = new String[]{"#", "ID", "Nome", "Telefone", "Data de Nascimento", "Gênero", "Estado Civil"};
    private boolean mostrandoInativos = false;
    private JToggleButton switchToggle;
    private Table customTable;
    private List<Paciente> pacientesFiltrados;

    public TableBuscaAvancadaPaciente(List<Paciente> pacientesFiltrados, List<String> colunasSelecionadas) {
        this.pacientesFiltrados = pacientesFiltrados;
        this.tableColumns = colunasSelecionadas.toArray(String[]::new);

        initComponents();

        filtroUsuarioPesquisador();

        setLayout(new BorderLayout());

        // Remove pNorth do layout principal para evitar duplicidade
        remove(pNorth);

        // Cria o toggle button para alternar entre ativos e inativos
        switchToggle = new JToggleButton("Mostrar inativos");
        switchToggle.addActionListener(this::onToggle);

        // Painel para centralizar o toggle
        javax.swing.JPanel pTogglePanel = new javax.swing.JPanel();
        pTogglePanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER));
        pTogglePanel.add(switchToggle);

        // Painel vertical que empilha pNorth e toggle
        javax.swing.JPanel pTop = new javax.swing.JPanel();
        pTop.setLayout(new javax.swing.BoxLayout(pTop, javax.swing.BoxLayout.Y_AXIS));

        pTop.add(pNorth);
        pTop.add(pTogglePanel);

        add(pTop, BorderLayout.NORTH);

        // Configura painel_lista_espera para o centro
        painel_lista_espera.removeAll();
        painel_lista_espera.setLayout(new BorderLayout());
        add(painel_lista_espera, BorderLayout.CENTER);

        rebuildTable();
    }

    private void filtroUsuarioPesquisador() {
        SessaoVO sessaoVO = new SessaoRN().buscarUltimaSessao();
        if (sessaoVO.getTipo().equals("Pesquisador")) {
            tableColumns = Arrays.stream(tableColumns)
                    .filter(col -> !col.equals("Nome"))
                    .toArray(String[]::new);
        }
    }

    private void onToggle(ActionEvent e) {
        mostrandoInativos = switchToggle.isSelected();
        switchToggle.setText(mostrandoInativos ? "Mostrar ativos" : "Mostrar inativos");
        rebuildTable();
    }

    private void rebuildTable() {
        boolean acaoInativar = mostrandoInativos;  // true para "Ativar", false para "Inativar"
        String botaoLabel = mostrandoInativos ? "Ativar" : "Inativar";
        String icone = mostrandoInativos ? "/Multimidia/imagens/cadeado.png" : "/Multimidia/icon/cadeado_desbloqueado.png";

        painel_lista_espera.removeAll();

        customTable = new Table(
                null,
                tableColumns,
                "Todos os Pacientes",
                "Paciente",
                acaoInativar,
                botaoLabel,
                icone
        );

        List<Object[]> dadosTabela = converterPacientesParaObjectArray(pacientesFiltrados);

        painel_lista_espera.add(
                customTable.createCustomTable(
                        null,
                        tableColumns,
                        "Paciente",
                        dadosTabela
                ),
                BorderLayout.CENTER
        );

        painel_lista_espera.revalidate();
        painel_lista_espera.repaint();
    }

    public List<Object[]> converterPacientesParaObjectArray(List<Paciente> pacientes) {
        List<Object[]> lista = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Paciente paciente : pacientes) {
            List<Object> linha = new ArrayList<>();

            for (String col : tableColumns) {
                try {
                    switch (col) {
                        case "#":
                            break;
                        case "ID":
                            linha.add(paciente.getId());
                            break;
                        case "Nome":
                            linha.add(paciente.getNome());
                            break;
                        case "Telefone":
                            linha.add(paciente.getTelefone());
                            break;
                        case "Telefone Contato":
                            linha.add(paciente.getTelefoneContato() != null ? paciente.getTelefoneContato() : "");
                            break;
                        case "Data de Nascimento":
                            linha.add(paciente.getDataNascimento() != null ? paciente.getDataNascimento().format(dtf) : "");
                            break;
                        case "Naturalidade":
                            linha.add(paciente.getNaturalidade() != null ? paciente.getNaturalidade() : "");
                            break;
                        case "Nacionalidade":
                            linha.add(paciente.getNacionalidade() != null ? paciente.getNacionalidade().getNome() : "");
                            break;
                        case "Raça/Cor/Etnia":
                            linha.add(paciente.getRacaCorEtnia() != null ? paciente.getRacaCorEtnia() : "");
                            break;
                        case "Gênero":
                            linha.add(paciente.getGenero() != null ? paciente.getGenero() : "");
                            break;
                        case "Orientação Sexual":
                            linha.add(paciente.getOrientacaoSexual() != null ? paciente.getOrientacaoSexual() : "");
                            break;
                        case "Estado Civil":
                            linha.add(paciente.getEstadoCivil() != null ? paciente.getEstadoCivil() : "");
                            break;
                        case "Grau de Instrução":
                            linha.add(paciente.getGrauInstrucao() != null ? paciente.getGrauInstrucao() : "");
                            break;
                        case "Profissão":
                            linha.add(paciente.getProfissao() != null ? paciente.getProfissao() : "");
                            break;
                        case "Responsável":
                            linha.add(paciente.getResponsavel() != null ? paciente.getResponsavel().getNome() : "");
                            break;
                        case "Disponibilidade":
                            linha.add(paciente.getDisponibilidade() != null ? paciente.getDisponibilidade() : "");
                            break;
                        case "Endereço":
                            linha.add(paciente.getEndereco() != null ? paciente.getEndereco().getRua() : "");
                            break;
                        case "Bairro":
                            linha.add(paciente.getEndereco() != null ? paciente.getEndereco().getBairro() : "");
                            break;
                        case "Numero":
                            linha.add(paciente.getEndereco() != null ? paciente.getEndereco().getNumero() : "");
                            break;
                        case "Complemento":
                            linha.add(paciente.getEndereco() != null ? paciente.getEndereco().getComplemento() : "");
                            break;
                        case "Cidade":
                            linha.add(paciente.getEndereco() != null && paciente.getEndereco().getCidade() != null
                                    ? paciente.getEndereco().getCidade().getNome() : "");
                            break;
                        case "Estado":
                            linha.add(paciente.getEndereco() != null
                                    && paciente.getEndereco().getCidade() != null
                                    && paciente.getEndereco().getCidade().getEstado() != null
                                    ? paciente.getEndereco().getCidade().getEstado().getNome() : "");
                            break;
                        case "CEP":
                            linha.add(paciente.getEndereco() != null ? paciente.getEndereco().getCep() : "");
                            break;
                        case "Data de Inscrição":
                            linha.add(paciente.getDataInscricao() != null ? paciente.getDataInscricao().format(dtf) : "");
                            break;
                        case "Ativo":
                            linha.add(Boolean.TRUE.equals(paciente.getAtivo()) ? "Sim" : "Não");
                            break;
                        case "Atendido":
                            linha.add(Boolean.TRUE.equals(paciente.getAtendido()) ? "Sim" : "Não");
                            break;
                        case "Grupo":
                            linha.add(paciente.getGrupo() != null ? paciente.getGrupo() : "");
                            break;
                        case "Orientador":
                            linha.add(paciente.getOrientador() != null ? paciente.getOrientador().getNome() : "");
                            break;
                        case "Estagiário":
                            linha.add(paciente.getEstagiario() != null ? paciente.getEstagiario().getNome() : "");
                            break;
                        default:
                            linha.add("—");
                            break;
                    }
                } catch (Exception e) {
                    linha.add("[Erro]");
                    e.printStackTrace();
                }

            }
            lista.add(linha.toArray());
        }

//        for (Paciente p : pacientes) {
//            Object[] linha = new Object[]{
//                p.getId(),
//                p.getNome(),
//                //p.getProfissao(),
//                p.getTelefone(),
//                //p.getTelefoneContato() != null ? p.getTelefoneContato() : "",
//                p.getDataNascimento() != null ? p.getDataNascimento().format(dtf) : "",
//                //p.getNaturalidade() != null ? p.getNaturalidade() : "",
//                //p.getNacionalidade() != null ? p.getNacionalidade().getNome() : "",
//                //p.getRacaCorEtnia() != null ? p.getRacaCorEtnia() : "",
//                p.getGenero() != null ? p.getGenero() : "",
//                //p.getOrientacao_sexual() != null ? p.getOrientacao_sexual() : "",
//                p.getEstadoCivil() != null ? p.getEstadoCivil() : "",
//                //p.getGrauInstrucao() != null ? p.getGrauInstrucao() : "",
//                //p.getResponsavel() != null ? p.getResponsavel().getNome() : "",
//                //p.getDisponibilidade() != null ? p.getDisponibilidade() : "",
//                //p.getEndereco() != null ? p.getEndereco().getRua() : "",
//                //p.getEndereco() != null ? p.getEndereco().getCep() : "",
//                //p.getDataInscricao() != null ? p.getDataInscricao().format(dtf) : "",
//                //p.getAtivo(),
//                //p.getAtendido()
//            };
//            lista.add(linha);
//        }
        return lista;
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tbListaEsperaNaoAtendidos = new javax.swing.JTable();
        pNorth = new javax.swing.JPanel();
        lbClinica = new javax.swing.JLabel();
        lbOrientador = new javax.swing.JLabel();
        lblLogoListaPacientes = new javax.swing.JLabel();

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
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(213, Short.MAX_VALUE))
        );

        add(painel_lista_espera, java.awt.BorderLayout.CENTER);

        pNorth.setBackground(new java.awt.Color(0, 102, 102));
        pNorth.setPreferredSize(new java.awt.Dimension(638, 183));

        lbClinica.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lbClinica.setForeground(new java.awt.Color(255, 255, 255));
        lbClinica.setText("Clínica de Psicologia");

        lbOrientador.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        lbOrientador.setForeground(new java.awt.Color(255, 255, 255));
        lbOrientador.setText("Lista de Pacientes");

        lblLogoListaPacientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Multimidia/imagens/listaPacientes.png"))); // NOI18N

        javax.swing.GroupLayout pNorthLayout = new javax.swing.GroupLayout(pNorth);
        pNorth.setLayout(pNorthLayout);
        pNorthLayout.setHorizontalGroup(
            pNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pNorthLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblLogoListaPacientes, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(pNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbClinica)
                    .addComponent(lbOrientador))
                .addContainerGap(530, Short.MAX_VALUE))
        );
        pNorthLayout.setVerticalGroup(
            pNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pNorthLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(lbOrientador)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbClinica)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pNorthLayout.createSequentialGroup()
                .addGap(0, 7, Short.MAX_VALUE)
                .addComponent(lblLogoListaPacientes, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        add(pNorth, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbClinica;
    private javax.swing.JLabel lbOrientador;
    private javax.swing.JLabel lblLogoListaPacientes;
    private javax.swing.JPanel pNorth;
    private javax.swing.JPanel painel_lista_espera;
    private javax.swing.JTable tbListaEsperaNaoAtendidos;
    // End of variables declaration//GEN-END:variables

}
