/*
 * Created on 03-Aug-2006
 *
 * Copyright (c) 2006 P.J.Leonard
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
package com.frinika.frame.action;

import static com.frinika.localization.CurrentLocale.getMessage;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import com.frinika.global.FrinikaConfig;
import com.frinika.project.ProjectContainer;
import com.frinika.frame.FrinikaFrame;
import com.frinika.sequencer.gui.ProjectFrame;
import com.frinika.tracker.ProjectFileFilter;

/**
 * Triggers an open project dialog based on no Frame. Used by FrinikaMain when starting up
 * @author peter
 *
 */
public class OpenProjectAction extends  AbstractAction {
	private static final long serialVersionUID = 1L;	
	private static JFileChooser chooser = new JFileChooser();
	
	static {
		chooser.setDialogTitle(getMessage("project.menu.file.open_project.dialogtitle"));
		chooser.setFileFilter(new ProjectFileFilter());		
	}
	
	public static void setSelectedFile(File file)
	{
		chooser.setSelectedFile(file);
	}
	private ProjectFrame project;

	

		public void actionPerformed(ActionEvent e) {
			try {					
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File newProject = chooser.getSelectedFile();

					project=new FrinikaFrame(ProjectContainer
							.loadProject(newProject));
					FrinikaConfig.setLastProjectFilename(newProject
							.getAbsolutePath());
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}


		public ProjectFrame getProjectFrame() {
			return project;
		}
	}


