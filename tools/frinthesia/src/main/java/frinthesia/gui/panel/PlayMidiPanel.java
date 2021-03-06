/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frinthesia.gui.panel;

import com.frinika.frame.FrinikaFrame;
import com.frinika.project.ProjectContainer;
import com.sun.media.sound.SoftSynthesizer;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;

/**
 * MIDI browser panel.
 *
 * @version 0.1.0 2015/11/28
 * @author Frinthesia Project
 */
public class PlayMidiPanel extends javax.swing.JPanel implements FrinthesiaPanel {

    private PanelOpenerListener panelOpener;
    private File file;

    /**
     * Creates new form midiBrowserPanel
     */
    public PlayMidiPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        controlPanel = new javax.swing.JPanel();
        playPauseButton = new javax.swing.JButton();
        positionPanel = new javax.swing.JPanel();
        progressBar = new javax.swing.JProgressBar();

        setLayout(new java.awt.BorderLayout());

        playPauseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frinthesia/resources/icons/play16.png"))); // NOI18N
        playPauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playPauseButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout controlPanelLayout = new javax.swing.GroupLayout(controlPanel);
        controlPanel.setLayout(controlPanelLayout);
        controlPanelLayout.setHorizontalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlPanelLayout.createSequentialGroup()
                .addContainerGap(533, Short.MAX_VALUE)
                .addComponent(playPauseButton)
                .addContainerGap())
        );
        controlPanelLayout.setVerticalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(playPauseButton)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        add(controlPanel, java.awt.BorderLayout.PAGE_START);

        progressBar.setValue(30);

        javax.swing.GroupLayout positionPanelLayout = new javax.swing.GroupLayout(positionPanel);
        positionPanel.setLayout(positionPanelLayout);
        positionPanelLayout.setHorizontalGroup(
            positionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
        );
        positionPanelLayout.setVerticalGroup(
            positionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        add(positionPanel, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents

    private void playPauseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playPauseButtonActionPerformed
        try {
            FrinikaFrame project = new FrinikaFrame();
            MidiDevice mididDevice = new SoftSynthesizer();
            mididDevice.open();
            project.setProject(new ProjectContainer(MidiSystem.getSequence(file), mididDevice));
            project.getProjectContainer().getSequencer().start();
        } catch (Exception ex) {
            Logger.getLogger(PlayMidiPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_playPauseButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel controlPanel;
    private javax.swing.JButton playPauseButton;
    private javax.swing.JPanel positionPanel;
    private javax.swing.JProgressBar progressBar;
    // End of variables declaration//GEN-END:variables

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public boolean canNext() {
        return false;
    }

    @Override
    public FrinthesiaPanelRecord getPreviousPanel() {
        return null;
    }

    @Override
    public FrinthesiaPanelRecord getNextPanel() {
        return null;
    }

    @Override
    public void registerPanelOpener(PanelOpenerListener panelOpener) {
        this.panelOpener = panelOpener;
    }
}
