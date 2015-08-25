package com.frinika.sequencer.gui.transport;

import static com.frinika.localization.CurrentLocale.getMessage;
import com.frinika.project.ProjectContainer;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.frinika.sequencer.FrinikaSequencer;

public class RewindAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FrinikaSequencer sequencer;
	
	public RewindAction(ProjectContainer project) {
		super(getMessage("sequencer.project.rewind"));
		this.sequencer=project.getSequencer();	
	//	putValue(ACCELERATOR_KEY,KeyStroke.getKeyStroke(
	//			KeyEvent.VK_SPACE,0));
	}
	public void actionPerformed(ActionEvent arg0) {
		sequencer.setTickPosition(0);
	}
	
}
