/*
 *
 * Copyright (c) 2006-2007 Paul John Leonard
 * 
 * http://www.frinika.com
 * 
 * This file is part of Frinika.
 * 
 * Frinika is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * Frinika is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Frinika; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package com.frinika.sequencer.gui.menu;

import static com.frinika.localization.CurrentLocale.getMessage;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.frinika.sequencer.gui.ProjectFrame;
import com.frinika.sequencer.FrinikaSequencer;

public class WarpToRightAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FrinikaSequencer sequencer;
	
	public WarpToRightAction(ProjectFrame project) {
		super(getMessage("sequencer.project.warptoright"));
		this.sequencer=project.getProjectContainer().getSequencer();	
	//	putValue(ACCELERATOR_KEY,KeyStroke.getKeyStroke(
	//			KeyEvent.VK_SPACE,0));
	}
	public void actionPerformed(ActionEvent arg0) {
		sequencer.setTickPosition(sequencer.getLoopEndPoint());
	}
	

	
}
