/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Visao.Utils;

import Visao.JframeManager.FormManager;
import java.util.function.Consumer;
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
                MessageAlerts.OK_OPTION, new PopupCallbackAction() {
                    @Override
                    public void action(PopupController pc, int i) {
                        // No action needed
                    }
                });
    }

    public void showSuccessMessage(String message) {
        MessageAlerts.getInstance().showMessage("Sucesso", message, MessageAlerts.MessageType.SUCCESS,
                MessageAlerts.OK_OPTION, new PopupCallbackAction() {
                    @Override
                    public void action(PopupController pc, int i) {
                        if (i == MessageAlerts.OK_OPTION) {
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

}
