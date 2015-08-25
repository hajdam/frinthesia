package com.frinika.sequencer.gui.transport;

import static com.frinika.localization.CurrentLocale.getMessage;
import com.frinika.project.ProjectContainer;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import com.frinika.sequencer.gui.ProjectFrame;
import com.frinika.sequencer.FrinikaSequencer;
import com.frinika.sequencer.gui.RecordingDialog;

public class RecordAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FrinikaSequencer sequencer;
	private ProjectContainer project;
	
	public RecordAction(ProjectContainer project) {
		super(getMessage("sequencer.project.record"));
		this.sequencer=project.getSequencer();	
		this.project=project;
	}
	
	
    public void actionPerformed(ActionEvent e) {
        
        final JOptionPane recordingOptionPane = new JOptionPane(getMessage("sequencer.recording.takes"));
        
        //Frame to pop-up while recording to display each take
        final RecordingDialog recordingDialog = new RecordingDialog(recordingOptionPane,sequencer);

        recordingOptionPane.addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                String prop = evt.getPropertyName();

                if (recordingDialog.isVisible() 
                 && (evt.getSource() == recordingOptionPane)
                 && (prop.equals(JOptionPane.VALUE_PROPERTY))) {                    
                    int[] deployableTakes = recordingDialog.getDeployableTakes();
                    if(deployableTakes.length>0)
                    {
                        project.getEditHistoryContainer().mark(getMessage("recording"));
                        sequencer.deployTake(deployableTakes);
                        project.getEditHistoryContainer().notifyEditHistoryListeners();
                    }
                    sequencer.stop();
                }
            }
        });
        
        
        sequencer.startRecording();
    }
	
}
