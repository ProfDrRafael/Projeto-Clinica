package Persistencia.gui;

import Services.SenhaService;

public class TesteSenha {
    public static void main(String[] args) {
        SenhaService senhaService = new SenhaService();

        String senhaEstagiario = senhaService.criptografarSenha("senha123");

        System.out.println("Senha Estagiario: " + senhaEstagiario);
    }
}
