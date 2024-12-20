package Visao.Utils;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author john
 */
public class HashMapNomesTabelas {
    // Private map with pre-named values
    private static final Map<String, String> TABLE_NAMES = new HashMap<>();

    // popular map
    static {
        String[][] preNamedValues = {
            // Paciente
            {"ID", "id"},
            {"Nome", "nome"},
            {"Telefone", "telefone"},
            {"Data de Nascimento", "data_nascimento"},
            {"Gênero", "genero"},
            {"Estado Civil", "estado_civil"},
            {"Data Inscrição", "data_inscricao"},
            {"Disponiblidade", "disponibilidade"},
            //Estagiarios
            {"Email", "email"},
            {"Ativo", "ativo"},
            {"Ano", "ano"},
            {"Orientador", "orientador_id"},
            //Atendimento
            {"Data", "data"},
            {"Data", "Hora"},
            {"Preenchido", "preenchido"},
            {"Comparecimento", "comparecimento"},
            {"Plantão", "plantao"},
            {"Prontuário", "prontuario_id"},
            {"Estagiário", "estagiario_id"},
            //Agenda
            {"Sala", "sala"},
            {"Atendimento", "atendimento_id"},
            {"Paciente", "paciente_id"},
            {"Estagiário", "estagiario_id"},
        };
        for (String[] pair : preNamedValues) {
            TABLE_NAMES.put(pair[0], pair[1]);
        }
    }

    public static String getPacienteName(String key) {
        return TABLE_NAMES.get(key);
    }
}
