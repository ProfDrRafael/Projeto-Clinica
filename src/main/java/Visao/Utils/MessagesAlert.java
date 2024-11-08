/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Visao.Utils;

import Visao.JframeManager.FormManager;
import raven.alerts.MessageAlerts;
import raven.popup.component.PopupCallbackAction;
import raven.popup.component.PopupController;

/**
 *
 * @author john
 */
public class MessagesAlert {
    public void MessageAlertDesconectarOpcoes(){
        MessageAlerts.getInstance().showMessage("Logout", "Tem Certeza que deseja terminar a sessão?", MessageAlerts.MessageType.ERROR, MessageAlerts.YES_NO_OPTION, new PopupCallbackAction() {
            @Override
            public void action(PopupController pc, int i) {
                if (i == MessageAlerts.YES_OPTION) {
                    FormManager.logout();
                }
            }
        });
    }
}
