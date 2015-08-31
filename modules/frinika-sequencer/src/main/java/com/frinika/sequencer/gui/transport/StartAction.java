package com.frinika.sequencer.gui.transport;

import static com.frinika.localization.CurrentLocale.getMessage;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.frinika.sequencer.FrinikaSequencer;
import com.frinika.sequencer.project.AbstractSequencerProjectContainer;

public class StartAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FrinikaSequencer sequencer;
	private AbstractSequencerProjectContainer project;
	
	public StartAction(AbstractSequencerProjectContainer project) {
		super(getMessage("sequencer.project.start_stop"));
		this.sequencer=project.getSequencer();
		this.project=project;

	}
	
	public void actionPerformed(ActionEvent arg0) {
			
		if (!sequencer.isRunning()) sequencer.start();	
	}
	
}
