package com.frinika.sequencer.gui.transport;

import static com.frinika.localization.CurrentLocale.getMessage;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.frinika.sequencer.FrinikaSequencer;
import com.frinika.sequencer.project.AbstractSequencerProjectContainer;

public class StartStopAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FrinikaSequencer sequencer;
	private AbstractSequencerProjectContainer project;
	StartAction startAction;
	StopAction stopAction;
	public StartStopAction(AbstractSequencerProjectContainer project) {
		super(getMessage("sequencer.project.start_stop"));
		this.sequencer=project.getSequencer();
		this.project=project;
		this.startAction=new StartAction(project);
		this.stopAction=new StopAction(project);
		
	//	putValue(ACCELERATOR_KEY,KeyStroke.getKeyStroke(
	//			KeyEvent.VK_SPACE,0));
	}
	
	public void actionPerformed(ActionEvent arg0) {
		
		
		if (sequencer.isRunning()) stopAction.actionPerformed(arg0);
		else startAction.actionPerformed(arg0);
	}
	
}
