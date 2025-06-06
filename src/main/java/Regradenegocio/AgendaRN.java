package Regradenegocio;

import Persistencia.Dao.AgendaDAO;
import Persistencia.Dao.EstagiarioDAO;
import Persistencia.Dao.OrientadorDAO;
import Persistencia.Entity.Agenda;
import VO.AgendaVO;
import VO.EstagiarioVO;
import VO.OrientadorVO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AgendaRN {
    private final AgendaDAO agendaDAO;
    private final OrientadorDAO orientadorDAO;
    private final EstagiarioDAO estagiarioDAO;

    public AgendaRN() {
        this.agendaDAO = new AgendaDAO();
        this.orientadorDAO = new OrientadorDAO();
        this.estagiarioDAO = new EstagiarioDAO();
    }

    /**
     * Salva uma agenda, aplicando validações e regras de negócio.
     *
     * @param agendaVO Objeto AgendaVO contendo os dados a serem salvos.
     */
    public void salvar(AgendaVO agendaVO) {
        validarAgenda(agendaVO); // Aplica validações antes de salvar
        Agenda agenda = agendaVO.toEntity(); // Converte o VO para Entity
        verificarConflitosDeAgendamento(agenda); // Verifica conflitos de horário
        agendaDAO.salvar(agenda); // Persiste no banco de dados
    }

    /**
     * Atualiza uma agenda existente.
     *
     * @param agendaVO Objeto AgendaVO contendo os dados atualizados.
     */
    public void atualizar(AgendaVO agendaVO) {
        validarAgenda(agendaVO); // Valida os dados
        Agenda agenda = agendaDAO.buscarPorId(agendaVO.getId()); // Busca a agenda existente
        if (agenda == null) {
            throw new IllegalArgumentException("Agenda com o ID especificado não encontrada.");
        }
        agendaVO.updateEntity(agenda); // Atualiza a entidade com os dados do VO
        verificarConflitosDeAgendamento(agenda); // Verifica conflitos de horário
        agendaDAO.salvar(agenda); // Salva as alterações
    }

    /**
     * Lista todas as agendas cadastradas.
     *
     * @return Lista de objetos AgendaVO representando as agendas cadastradas.
     */
    public List<AgendaVO> listarTodas() {
        List<Agenda> agendas = agendaDAO.listarTodos(); // Obtém todas as entidades do banco
        System.out.println(agendas); 
        
        if(agendas != null){
            return agendas.stream()
                    .map(AgendaVO::fromEntity)
                    .collect(Collectors.toList());
            
        }
        
        return new ArrayList<>();
    }
    
    /**
     * Lista as agendas dos alunos do Orientador.
     * (OBS.: Esse método pressupõe que a entidade Agenda possua uma referência ao orientador, 
     *  por exemplo, através do paciente: a.paciente.orientador.id)
     * @param email
     * @return 
     */
    public List<AgendaVO> listarPorOrientador(String email) {
        OrientadorVO orientador = OrientadorVO.fromEntity(orientadorDAO.buscarPorEmail(email)); 
        List<Agenda> agendas = agendaDAO.buscarPorOrientador(orientador);
        System.out.println(agendas);
        if (agendas != null) {
            return agendas.stream()
                    .map(AgendaVO::fromEntity)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
    
    /**
     * Lista as agendas do Estagiário.
     * @param email
     * @return 
     */
    public List<AgendaVO> listarPorEstagiario(String email) {
        EstagiarioVO estagiario = EstagiarioVO.fromEntity(estagiarioDAO.buscarPorEmail(email));
        List<Agenda> agendas = agendaDAO.buscarPorEstagiario(estagiario);
        System.out.println(agendas);
        if (agendas != null) {
            return agendas.stream()
                    .map(AgendaVO::fromEntity)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    /**
     * Busca uma agenda por ID.
     *
     * @param id ID da agenda a ser buscada.
     * @return Objeto AgendaVO correspondente à agenda encontrada, ou null se não for encontrada.
     */
//    public AgendaVO buscarPorId(int id) {
//        Agenda agenda = agendaDAO.buscarPorId(id);
//        return (agenda != null) ? new AgendaVO(agenda) : null;
//    }

    /**
     * Lista todas as agendas de um paciente.
     *
     * @param pacienteId ID do paciente.
     * @return Lista de agendas do paciente.
     */
//    public List<AgendaVO> listarPorPaciente(int pacienteId) {
//        List<Agenda> agendas = agendaDAO.buscarPorPacienteId(pacienteId);
//        return agendas.stream()
//                .map(AgendaVO::new)
//                .toList();
//    }

    /**
     * Lista todas as agendas de um estagiário.
     *
     * @param estagiarioId ID do estagiário.
     * @return Lista de agendas do estagiário.
     */
//    public List<AgendaVO> listarPorEstagiario(int estagiarioId) {
//        List<Agenda> agendas = agendaDAO.buscarPorEstagiarioId(estagiarioId);
//        return agendas.stream()
//                .map(AgendaVO::new)
//                .toList();
//    }

    /**
     * Remove uma agenda pelo ID.
     *
     * @param id ID da agenda a ser removida.
     */
    public void remover(int id) {
        Agenda agenda = agendaDAO.buscarPorId(id);
        if (agenda == null) {
            throw new IllegalArgumentException("Agenda com o ID especificado não encontrada.");
        }
        agendaDAO.deletar(agenda);
    }

    /**
     * Valida os dados de uma agenda antes de salvar ou atualizar.
     *
     * @param agendaVO Objeto AgendaVO a ser validado.
     */
    private void validarAgenda(AgendaVO agendaVO) {
        if (agendaVO.getData() == null) {
            throw new IllegalArgumentException("A data da agenda não pode ser nula.");
        }
        if (agendaVO.getHora() == null) {
            throw new IllegalArgumentException("A hora da agenda não pode ser nula.");
        }
        if (agendaVO.getData().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A data da agenda não pode estar no passado.");
        }
        if (agendaVO.getHora().isBefore(LocalTime.now()) && agendaVO.getData().isEqual(LocalDate.now())) {
            throw new IllegalArgumentException("O horário da agenda não pode estar no passado.");
        }
        if (agendaVO.getSala() == null || agendaVO.getSala() <= 0) {
            throw new IllegalArgumentException("A sala deve ser válida.");
        }
        if (agendaVO.getPacienteVO() == null) {
            throw new IllegalArgumentException("A agenda deve estar associada a um paciente.");
        }
        if (agendaVO.getEstagiarioVO() == null) {
            throw new IllegalArgumentException("A agenda deve estar associada a um estagiário.");
        }
    }

    /**
     * Verifica se há conflitos de horário para a agenda especificada.
     *
     * @param agenda Objeto Agenda a ser verificado.
     */
    private void verificarConflitosDeAgendamento(Agenda agenda) {
        List<Agenda> agendasExistentes = agendaDAO.buscarPorPacienteId(agenda.getPaciente().getId());
        for (Agenda existente : agendasExistentes) {
            if (existente.getData().isEqual(agenda.getData()) && existente.getHora().equals(agenda.getHora())) {
                throw new IllegalArgumentException("O paciente já possui um agendamento nesse horário.");
            }
        }
    }

    public List<Agenda> buscarAgendamentosPorPacienteETipo(int pacienteId, String tipoAtendimento) {
        AgendaDAO dao = new AgendaDAO();
        return dao.buscarPorPacienteETipoAtendimento(pacienteId, tipoAtendimento);
    }

}
