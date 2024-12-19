package Regradenegocio;

import Persistencia.Dao.AtendimentoDAO;
import Persistencia.Entity.Atendimento;
import Persistencia.Entity.Estagiario;
import Persistencia.Entity.Prontuario;
import Services.CriptografiaService;
import VO.AtendimentoVO;
import VO.ProntuarioEletronicoVO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AtendimentoRN {

    private final AtendimentoDAO atendimentoDAO;

    public AtendimentoRN() {
        this.atendimentoDAO = new AtendimentoDAO();
    }

    /**
     * Método para preencher o atendimento com dados obrigatórios.
     * @param data Data do atendimento.
     * @param hora Hora do atendimento.
     * @param prontuario Prontuário associado.
     * @param estagiario Estagiário responsável.
     * @param relato Relato do atendimento.
     * @param preenchido Comparecimento do paciente.
     * @param justificativa Justificativa caso não tenha comparecido.
     * @param plantao Indica se o atendimento é emergencial.
     * @return Atendimento preenchido.
     * @throws IllegalArgumentException Caso dados obrigatórios não sejam preenchidos.
     */
    public Atendimento preencherAtendimento(LocalDate data, LocalTime hora, Prontuario prontuario, Estagiario estagiario,
                                            String relato, Boolean preenchido, String justificativa, Boolean plantao) {
        Atendimento atendimento = new Atendimento();
        atendimento.setData(data);
        atendimento.setHora(hora);
        atendimento.setProntuario(prontuario);
        atendimento.setEstagiario(estagiario);
        atendimento.setRelatoAtendimento(relato);
        atendimento.setPreenchido(preenchido);
        atendimento.setJustificativa(justificativa);
        atendimento.setPlantao(plantao); // Define se o atendimento é um plantão
        return atendimento;
    }

    /**
     * Método para salvar o atendimento no banco de dados.
     * @param atendimento Atendimento preenchido.
     */
    public void salvarAtendimento(AtendimentoVO atendimento) {
        if (atendimento == null) {
            throw new IllegalArgumentException("Atendimento não pode ser nulo.");
        }

        if (atendimento.getRelatoAtendimento() == null || atendimento.getRelatoAtendimento().trim().isEmpty()) {
            throw new IllegalArgumentException("O relato do atendimento é obrigatório.");
        }

        try {
            CriptografiaService criptografia = new CriptografiaService();
            atendimento.setRelatoAtendimento(criptografia.criptografarTexto(atendimento.getRelatoAtendimento()));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criptografar o relato do atendimento: " + e.getMessage(), e);
        }

        try {
            atendimentoDAO.salvar(atendimento.toEntity());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar atendimento no banco de dados: " + e.getMessage(), e);
        }
    }

    public List<Atendimento> buscarAtendimentosPorPacienteId(Integer pacienteId) {
        PacienteRN pacienteRN = new PacienteRN();
        ProntuarioEletronicoVO prontuario = pacienteRN.buscarProntuarioPorPacienteId(pacienteId);

        if (prontuario == null) {
            throw new IllegalArgumentException("Prontuário não encontrado para o paciente.");
        }

        AtendimentoDAO atendimentoDAO = new AtendimentoDAO();
        return atendimentoDAO.buscarPorProntuario(prontuario.getId());
    }

}
