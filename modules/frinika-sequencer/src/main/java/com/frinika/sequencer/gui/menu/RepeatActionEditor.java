/*
 * Created on February 8, 2007
 * 
 * Copyright (c) 2007 Jens Gulden
 * 
 * http://www.frinika.com
 * 
 * This file is part of Frinika.
 * 
 * Frinika is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.

 * Frinika is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with Frinika; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.frinika.sequencer.gui.menu;

import com.frinika.gui.OptionsEditor;
import com.frinika.sequencer.gui.TimeFormat;
import com.frinika.sequencer.gui.TimeSelector;
import com.frinika.sequencer.project.AbstractSequencerProjectContainer;
import javax.swing.JPanel;

/**
 * Editor component for RepeatAction.
 *
 * (Created with NetBeans 5.5 gui-editor, see corresponding .form file.)
 * 
 * @author Jens Gulden
 */
class RepeatActionEditor extends JPanel implements OptionsEditor {
    
    private RepeatAction action;
    private TimeSelector timeSelector;
    
    /** Creates new form RepeatActionEditor */
    public RepeatActionEditor(RepeatAction action, AbstractSequencerProjectContainer project) {
        super();
        this.action = action;
        initComponents();
        timeSelector = new TimeSelector(action.repeatTicks, project, TimeFormat.BEAT_TICK);
        timeSelectorPanel.add(timeSelector);
        refresh();
    }
    
    public void refresh() {
        // update label displaying length of current selection
        repeatTicksSelectionRadioButton.setText("length of selection: " + timeSelector.formatString(action.selectionLength));
        if ( ! action.selectionSupportsGhosts ) ghostsCheckBox.setSelected(false);
        ghostsCheckBox.setEnabled(action.selectionSupportsGhosts);
        repeatSpinner.requestFocus();
    }
    
    public void update() {
        if (repeatTicksSelectionRadioButton.isSelected()) {
            action.repeatTicks = action.selectionLength;
        } else if (repeatTicksTimeSelectorRadioButton.isSelected()) {
            action.repeatTicks = timeSelector.getTicks();
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        repeatTicksButtonGroup = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        repeatSpinner = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        repeatTicksTimeSelectorRadioButton = new javax.swing.JRadioButton();
        timeSelectorPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        repeatTicksSelectionRadioButton = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        ghostsCheckBox = new javax.swing.JCheckBox();

        setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Repeat");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(jLabel1, gridBagConstraints);

        repeatSpinner.setModel(new javax.swing.SpinnerNumberModel(action.repeat, 1, 999, 1));
        repeatSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                repeatSpinnerStateChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(repeatSpinner, gridBagConstraints);

        jLabel2.setText("times, every");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(jLabel2, gridBagConstraints);

        repeatTicksButtonGroup.add(repeatTicksTimeSelectorRadioButton);
        repeatTicksTimeSelectorRadioButton.setSelected(true);
        repeatTicksTimeSelectorRadioButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        repeatTicksTimeSelectorRadioButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(repeatTicksTimeSelectorRadioButton, gridBagConstraints);

        timeSelectorPanel.setLayout(new java.awt.GridBagLayout());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(timeSelectorPanel, gridBagConstraints);

        jPanel1.setLayout(null);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 3, 3);
        add(jPanel1, gridBagConstraints);

        repeatTicksButtonGroup.add(repeatTicksSelectionRadioButton);
        repeatTicksSelectionRadioButton.setText("length of selection: 4.0:000");
        repeatTicksSelectionRadioButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        repeatTicksSelectionRadioButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 3, 3);
        add(repeatTicksSelectionRadioButton, gridBagConstraints);

        jPanel2.setLayout(null);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 3;
        add(jPanel2, gridBagConstraints);

        ghostsCheckBox.setSelected(action.ghost);
        ghostsCheckBox.setText("create ghosts");
        ghostsCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        ghostsCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        ghostsCheckBox.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ghostsCheckBoxStateChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 3, 0, 0);
        add(ghostsCheckBox, gridBagConstraints);

    }// </editor-fold>//GEN-END:initComponents

    private void repeatSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_repeatSpinnerStateChanged
        action.repeat = (Integer)repeatSpinner.getValue();
    }//GEN-LAST:event_repeatSpinnerStateChanged

    private void ghostsCheckBoxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ghostsCheckBoxStateChanged
        action.ghost = ghostsCheckBox.isSelected();
    }//GEN-LAST:event_ghostsCheckBoxStateChanged
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox ghostsCheckBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSpinner repeatSpinner;
    private javax.swing.ButtonGroup repeatTicksButtonGroup;
    private javax.swing.JRadioButton repeatTicksSelectionRadioButton;
    private javax.swing.JRadioButton repeatTicksTimeSelectorRadioButton;
    private javax.swing.JPanel timeSelectorPanel;
    // End of variables declaration//GEN-END:variables
    
}
