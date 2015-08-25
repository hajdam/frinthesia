package com.frinika.sequencer.gui.transport;

import static com.frinika.localization.CurrentLocale.getMessage;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.frinika.project.ProjectContainer;
import com.frinika.sequencer.gui.ProjectFrame;
import com.frinika.sequencer.FrinikaSequencer;

public class StartStopAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FrinikaSequencer sequencer;
	private ProjectContainer project;
	StartAction startAction;
	StopAction stopAction;
	public StartStopAction(ProjectContainer project) {
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
