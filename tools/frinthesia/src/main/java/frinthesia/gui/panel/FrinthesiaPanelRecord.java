/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frinthesia.gui.panel;

import javax.swing.JPanel;

/**
 *
 *
 * @version 0.1.0 2015/11/28
 * @author Frinthesia Project
 */
public class FrinthesiaPanelRecord {

    private JPanel panel;
    private String panelName;
    private boolean lazy = false;

    public FrinthesiaPanelRecord(JPanel panel, String panelName, boolean lazy) {
        this.panel = panel;
        this.panelName = panelName;
        this.lazy = lazy;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public String getPanelName() {
        return panelName;
    }

    public void setPanelName(String panelName) {
        this.panelName = panelName;
    }

    public boolean isLazy() {
        return lazy;
    }

    public void setLazy(boolean lazy) {
        this.lazy = lazy;
    }
}
