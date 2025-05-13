/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Visao.Utils;

import Visao.JframeManager.FormManager;
import java.util.function.Consumer;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import raven.alerts.MessageAlerts;
import raven.popup.component.PopupCallbackAction;
import raven.popup.component.PopupController;

/**
 *
 * @author john
 */
public class MessagesAlert {
    private boolean response;

    public void MessageAlertDesconectarOpcoes() {
        MessageAlerts.getInstance().showMessage("Logout", "Tem Certeza que deseja terminar a sessão?",
                MessageAlerts.MessageType.ERROR, MessageAlerts.YES_NO_OPTION, new PopupCallbackAction() {
                    @Override
                    public void action(PopupController pc, int i) {
                        if (i == MessageAlerts.YES_OPTION) {
                            FormManager.logout();
                        }
                    }
                });
    }

    public void showErrorMessage(String message) {
        MessageAlerts.getInstance().showMessage("Erro", message, MessageAlerts.MessageType.ERROR,
                MessageAlerts.CLOSED_OPTION, new PopupCallbackAction() {
                    @Override
                    public void action(PopupController pc, int i) {
                        // No action needed
                    }
                });
    }

    public void showSuccessMessage(String message) {
        MessageAlerts.getInstance().showMessage("Sucesso", message, MessageAlerts.MessageType.SUCCESS,
                MessageAlerts.CLOSED_OPTION, new PopupCallbackAction() {
                    @Override
                    public void action(PopupController pc, int i) {
                        if (i == MessageAlerts.CLOSED_OPTION) {
                            System.out.println("Vasco");
                        }
                    }
                });
    }
    
    public static void showWarningMessage(String message, Consumer<Boolean> callback) {
        
        MessageAlerts.getInstance().showMessage(
            "Aviso", 
            message, 
            MessageAlerts.MessageType.WARNING, 
            MessageAlerts.YES_NO_OPTION, 
            new PopupCallbackAction() {
                @Override
                public void action(PopupController pc, int i) {
                    boolean response = (i == MessageAlerts.YES_OPTION);
                    SwingUtilities.invokeLater(() -> callback.accept(response));
                }
            }
        );
    }
    
        public static void showChooseLista(Consumer<Integer> callback) {
            SwingUtilities.invokeLater(() -> {
            String title   = "Acesso à Lista";
            String message = "Para qual das listas você deseja ir?";
            String[] options = {"Geral", "Específica"};
            
            int choice = JOptionPane.showOptionDialog(
                null,                         // parent: null = centralizado na tela
                message,                      // mensagem
                title,                        // título da janela
                JOptionPane.DEFAULT_OPTION,   // tipo de botões
                JOptionPane.QUESTION_MESSAGE, // ícone de interrogação
                null,                         // ícone custom (null = padrão)
                options,                      // rótulos dos botões
                options[0]                    // botão default selecionado
            );
            
            // choice == 0 → "Geral"; choice == 1 → "Específica"; -1 = fechou a janela
            callback.accept(choice);
        });
    }

}
