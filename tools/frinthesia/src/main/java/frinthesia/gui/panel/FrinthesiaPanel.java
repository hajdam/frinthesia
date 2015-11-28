/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frinthesia.gui.panel;

/**
 * Frinthesia panel interface.
 *
 * @version 0.1.0 2015/11/28
 * @author Frinthesia Project
 */
public interface FrinthesiaPanel {

    /**
     * Returns true if next panel operation is available.
     *
     * @return true if next panel is supported
     */
    boolean canNext();

    /**
     * Returns identification of previous panel or null if start panel should be
     * used.
     *
     * @return panel identification
     */
    FrinthesiaPanelRecord getPreviousPanel();

    /**
     * Returns identification of previous panel or null if start panel should be
     * used.
     *
     * @return panel identification
     */
    FrinthesiaPanelRecord getNextPanel();

    /**
     * Registers panel opener in this panel.
     *
     * @param panelOpener
     */
    void registerPanelOpener(PanelOpenerListener panelOpener);

    public interface PanelOpenerListener {

        /**
         * Opens given panel in main fram.
         *
         * @param panelRecord
         */
        void openPanel(FrinthesiaPanelRecord panelRecord);
    }
}
