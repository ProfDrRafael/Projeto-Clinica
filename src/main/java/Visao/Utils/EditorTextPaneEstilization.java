/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Visao.Utils;

import javax.swing.JTextPane;
import javax.swing.text.rtf.RTFEditorKit;

/**
 *
 * @author john
 */
public class EditorTextPaneEstilization {
    public static void EstilizeEditorTextPane(JTextPane textPane) {
        textPane.setEditorKit(new RTFEditorKit());
        textPane.setContentType("text/rtf");
        
    }
}
