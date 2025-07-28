package Visao.Components;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.JPanel;

/**
 *
 * @author Raven
 */
public class PanelTemplate extends JPanel {

    public PanelTemplate() {
        init();
    }

    private void init() {
        putClientProperty(FlatClientProperties.STYLE, ""
                + "border:5,5,5,5;"
                + "background:null");
    }

    public void formInitAndOpen() {

    }

    public void formOpen() {

    }

    public void formRefresh(){
          

    }

    public boolean formClose() {
        return true;
    }
}
