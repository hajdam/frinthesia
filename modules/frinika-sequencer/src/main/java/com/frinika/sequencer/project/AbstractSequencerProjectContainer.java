/*
 * Created on Aug 13, 2010
 *
 * Copyright (c) 2005 Peter Johan Salomonsen (http://www.petersalomonsen.com)
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

package com.frinika.sequencer.project;

import com.frinika.audio.DynamicMixer;
import com.frinika.audio.io.BufferedRandomAccessFileManager;
import com.frinika.audio.toot.AudioInjector;
import com.frinika.base.AbstractProjectContainer;
import com.frinika.base.FrinikaAudioServer;
import com.frinika.global.FrinikaConfig;
import static com.frinika.localization.CurrentLocale.getMessage;
import com.frinika.sequencer.FrinikaSequence;
import com.frinika.sequencer.FrinikaSequencer;
import com.frinika.sequencer.FrinikaTrackWrapper;
import com.frinika.sequencer.MidiResource;
import com.frinika.sequencer.gui.clipboard.MyClipboard;
import com.frinika.sequencer.gui.mixer.SynthWrapper;
import com.frinika.sequencer.gui.selection.DragList;
import com.frinika.sequencer.gui.selection.LaneSelection;
import com.frinika.sequencer.gui.selection.MidiSelection;
import com.frinika.sequencer.gui.selection.MultiEventSelection;
import com.frinika.sequencer.gui.selection.PartSelection;
import com.frinika.sequencer.gui.selection.SelectionFocusable;
import com.frinika.sequencer.model.AudioLane;
import com.frinika.model.EditHistoryContainer;
import com.frinika.model.EditHistoryRecorder;
import com.frinika.sequencer.converter.MidiSequenceConverter;
import com.frinika.sequencer.model.Lane;
import com.frinika.sequencer.model.MidiLane;
import com.frinika.sequencer.model.MidiPart;
import com.frinika.sequencer.model.ProjectLane;
import com.frinika.sequencer.model.SoloManager;
import com.frinika.sequencer.model.SynthLane;
import com.frinika.sequencer.model.TextLane;
import com.frinika.sequencer.model.ViewableLaneList;
import com.frinika.sequencer.model.tempo.TempoList;
import com.frinika.sequencer.model.timesignature.TimeSignatureList;
import com.frinika.sequencer.model.util.TimeUtils;
import com.frinika.sequencer.project.mididevices.gui.MidiDevicesPanel;
import com.frinika.tootX.midi.MidiConsumer;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import uk.org.toot.audio.core.AudioProcess;
import uk.org.toot.audio.mixer.AudioMixer;

// Should be implemented instead but there are problems with class loading in serialization

public abstract class AbstractSequencerProjectContainer extends AbstractProjectContainer implements EditHistoryRecorder<Lane>,MidiConsumer,
        Serializable,DynamicMixer {

    protected int ticksPerQuarterNote = FrinikaConfig.TICKS_PER_QUARTER;
    protected transient MidiResource midiResource;
    protected transient FrinikaSequencer sequencer;
    
    public abstract File getFile();

    public abstract FrinikaSequencer getSequencer();

    public abstract EditHistoryContainer getEditHistoryContainer();

    public abstract List<Lane> getLanes();

    public abstract MidiDeviceDescriptorIntf getMidiDeviceDescriptor(MidiDevice midiDevice);

    public abstract int getMidiDeviceDescriptorIndex(MidiDevice midiDevice);

    public abstract List<MidiDeviceDescriptorIntf> getMidiDeviceDescriptors();

    public abstract void loadMidiOutDevice(MidiDeviceDescriptorIntf descriptor) throws MidiUnavailableException;

    public abstract ProjectLane getProjectLane();

    public abstract SynthLane createSynthLane(MidiDeviceDescriptorIntf desc);

    public abstract void removeMidiOutDevice(MidiDevice midiDevice);

    public abstract FrinikaAudioServer getAudioServer();

    public abstract AudioMixer getMixer();

    public abstract AudioInjector getOutputProcess();

    public abstract FrinikaSequence getSequence();

    public abstract TempoList getTempoList();

    public abstract void injectIntoOutput(AudioProcess process);

    public abstract int getPixelsPerRedraw();

    public abstract void setPixelsPerRedraw(int i);

    public abstract TimeSignatureList getTimeSignatureList();

    public abstract int getTicksPerBeat();

    public abstract long getEndTick();

    public abstract void setEndTick(long tick);

    public abstract TimeUtils getTimeUtils();

    public abstract long eventQuantize(long tick);

    public abstract long partQuantize(long tick);

    public abstract MultiEventSelection getMultiEventSelection();

    public abstract PartSelection getPartSelection();

    public abstract LaneSelection getLaneSelection();

    public abstract MidiLane createMidiLane();

    public abstract SelectionFocusable getSelectionFocus();

    public abstract void setSelectionFocus(SelectionFocusable focus);

    public abstract MyClipboard clipBoard();

    public abstract AudioLane createAudioLane();

    public abstract TextLane createTextLane();

    public abstract SoloManager getSoloManager();

    public abstract MidiResource getMidiResource();

    public abstract MidiSelection getMidiSelection();

    public abstract DragList getDragList();

    public abstract void message(String string);

    public abstract void error(String string);

    public abstract double getPianoRollSnapQuantization();

    public abstract void setPianoRollSnapQuantization(double val);

    public abstract double getPartViewSnapQuantization();

    public abstract void setPartViewSnapQuantization(double val);

    public abstract boolean isPianoRollSnapQuantized();

    public abstract boolean isPartViewSnapQuantized();

    public abstract void setPianoRollSnapQuantized(boolean val);

    public abstract void setPartViewSnapQuantized(boolean val);

    public abstract void add(Lane lane);

    public abstract void add(int index, Lane lane);

    public abstract BufferedRandomAccessFileManager getAudioFileManager();

    public abstract double tickAtMicros(double micros);

    public abstract double microsAtTick(double tick);

    public abstract File getAudioDirectory();

    public abstract FrinikaTrackWrapper getTempoTrack();

    public abstract Integer getMidiDeviceIndex(MidiDevice midiDevice);

    public abstract void createSequence();

    public static Icon getIconResource(String name) {
            try {
                    Icon icon = new javax.swing.ImageIcon(AbstractProjectContainer.class.getResource("/com/frinika/resources/icons/" + name));
                    return icon;
            } catch (Exception e) {
                    e.printStackTrace();
            }

            return null;
    }

    private static Icon default_midi_icon = getIconResource("midi.png");

    public static Icon getMidiDeviceIcon(MidiDevice dev) {
            Icon icon = getMidiDeviceLargeIcon(dev);
            if (icon.getIconHeight() > 16 || icon.getIconWidth() > 16) {
                    BufferedImage img = new BufferedImage(icon.getIconWidth(), icon
                                    .getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);
                    Graphics2D g = img.createGraphics();
                    icon.paintIcon(null, g, 0, 0);
                    g.dispose();
                    Image im = img.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
                    icon = new ImageIcon(im);
            }
            return icon;
    }

    public static Icon getMidiDeviceLargeIcon(MidiDevice dev) {
            if (dev instanceof SynthWrapper) {
                    dev = ((SynthWrapper) dev).getRealDevice();
            }
            Icon icon = default_midi_icon;
            try {
                    Method icon_method = dev.getClass().getMethod("getIcon");
                    icon = (Icon) icon_method.invoke(dev);
            } catch (Exception e) {
            }
            return icon;
    }
    
    // NBP
    MidiDevicesPanel midiDevicesPanel;

    // NBP
    public MidiDevicesPanel getMidiDevicesPanel() {
       return midiDevicesPanel;
    }
    
    // NBP
    public void createMidiDevicesPanel() {
        midiDevicesPanel = new MidiDevicesPanel(this);
    }

	private ActionListener addMidiDevices_listener = null;

	private MidiDevice addMidiDevices_selected = null;

	private Icon addMidiDevices_selected_icon = null;

    // NBP
    public void addMidiDevices(JComponent menu, List<MidiDevice.Info> infos,
                    List<Icon> icons) {
            Iterator<Icon> icon_iterator = icons.iterator();
            for (MidiDevice.Info info : infos) {
                    JMenuItem item = new JMenuItem(info.toString());
                    item.setIcon(icon_iterator.next());
                    item.addActionListener(new MidiDevicesActionListener(this, item.getIcon(), info));
                    menu.add(item);
            }
    }

    // NBP
    public void addMidiDevices(JComponent menu) {
            List<MidiDevice.Info> infos = new ArrayList<MidiDevice.Info>();
            List<Icon> icons = new ArrayList<Icon>();

            List<MidiDevice.Info> infos1 = new ArrayList<MidiDevice.Info>();
            List<Icon> icons1 = new ArrayList<Icon>();

            List<MidiDevice.Info> infos2 = new ArrayList<MidiDevice.Info>();
            List<Icon> icons2 = new ArrayList<Icon>();

            for (MidiDevice.Info info : MidiSystem.getMidiDeviceInfo()) {
                    try {
                            MidiDevice dev = MidiSystem.getMidiDevice(info);
                            if (dev.getMaxReceivers() != 0) {
                                    Icon icon = getMidiDeviceIcon(dev);
                                    if (dev instanceof Synthesizer) {
                                            infos1.add(info);
                                            icons1.add(icon);
                                    } else if (icon == default_midi_icon) {
                                            infos2.add(info);
                                            icons2.add(icon);
                                    } else {
                                            infos.add(info);
                                            icons.add(icon);
                                    }
                            }

                    } catch (Exception e) {
                    }
            }

            addMidiDevices(menu, infos1, icons1);
            menu.add(new JSeparator());
            addMidiDevices(menu, infos, icons);
            menu.add(new JSeparator());
            addMidiDevices(menu, infos2, icons2);

    }

    // NBP
    public abstract MidiDeviceDescriptorIntf addMidiOutDevice(MidiDevice midiDev) throws MidiUnavailableException;

    public void repaintViews() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void repaintPartView() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // NBP
    private class MidiDevicesActionListener implements ActionListener {

        AbstractSequencerProjectContainer project;
        final Icon icon;
        final MidiDevice.Info info;

        public MidiDevicesActionListener(AbstractSequencerProjectContainer project, Icon icon, MidiDevice.Info info) {
            this.info = info;
            this.icon = icon;
        }
        
        public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub

                MidiDevice midiDevice = null;
                try {
                        midiDevice = MidiSystem.getMidiDevice(info);

                        if (addMidiDevices_listener != null) {
                                addMidiDevices_selected = midiDevice;
                                addMidiDevices_selected_icon = icon;
                                addMidiDevices_listener.actionPerformed(null);
                                return;
                        }

                        midiDevice = new SynthWrapper(project, midiDevice);

                } catch (Exception e2) {
                        // TODO Auto-generated catch block
                        e2.printStackTrace();
                }

                try {
                        getEditHistoryContainer().mark("X");
                        addMidiOutDevice(midiDevice);
                        getEditHistoryContainer()
                                        .notifyEditHistoryListeners();
                } catch (MidiUnavailableException e2) {
                        // TODO Auto-generated catch block
                        e2.printStackTrace();
                }
                
                midiDevicesPanel.updateDeviceTabs();
        }
    }

    public void createMidiLanesFromSequence(Sequence seq, MidiDevice midiDevice) {

        // Vector<MidiLane> lanesToLoad = new Vector<MidiLane>();

        // FrinikaSequence fSeq = sequence;

        if (seq.getDivisionType() == Sequence.PPQ) {
            int ticksPerQuarterNote1 = seq.getResolution();
            System.out.println(" Project PPQ = " + ticksPerQuarterNote);
            System.out.println(" Midi    PPQ = " + ticksPerQuarterNote1);
        } else {
            System.out.println("WARNING: The resolution type of the imported Sequence is not supported by Frinika");
        }

        // Vector<FrinikaTrackWrapper> origTracks = new
        // Vector<FrinikaTrackWrapper>(
        // sequence.getFrinikaTrackWrappers());
        //
        // Vector<FrinikaTrackWrapper> midiTracks = sequence
        // .addSequence(

        Sequence splitSeq = MidiSequenceConverter.splitChannelsToMultiTrack(seq);



        int nTrack = splitSeq.getTracks().length;
        System.out.println(" Adding " + (nTrack) + " tracks ");

        // sequencer.setSequence(sequence);

        // create a copy

        // we are going to rebuild this
        // origTracks.removeAllElements();

        getEditHistoryContainer().mark(
                getMessage("sequencer.project.add_midi_lane"));

        for (int iTrack = 0; iTrack < nTrack; iTrack++) {

            int chan = 0;
            Track track = splitSeq.getTracks()[iTrack];
            // if (origTracks.contains(ftw)) continue;
            // Use the first MidiEvent in ftw to detect the channel used
            // Detect by first ShortMessage find (FIX by KH)
            for (int i = 0; i < track.size(); i++) {
                MidiMessage msg = track.get(i).getMessage();
                if (msg instanceof ShortMessage) {
                    chan = ((ShortMessage) msg).getChannel();
                    break;
                }
            }

            // PJS: The resolving of channel and device has to be done before
            // the ftw is attached to a lane (cause the following uperation can
            // change the first midi event)
            // FrinikaTrackWrapper ftw=sequence.createFrinikaTrack();
            MidiLane lane = createMidiLane(); // new MidiLane(ftw, this);

            // lanesToLoad.add(lane);

            lane.setMidiChannel(chan);

            MidiPart part = new MidiPart(lane);
            long startTick = 0;
            long endTick = Long.MAX_VALUE;
            part.importFromMidiTrack(track, startTick, endTick);

            // Find name of the track from the sequence file
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                if (event.getTick() > 0) {
                    break;
                }
                MidiMessage msg = event.getMessage();
                if (msg instanceof MetaMessage) {
                    MetaMessage meta = (MetaMessage) msg;
                    if (meta.getType() == 3) // Track text
                    {
                        if (meta.getLength() > 0) {
                            try {
                                String txt = new String(meta.getData());
                                lane.setName(txt);
                            } catch (Throwable t) {
                                t.printStackTrace();
                            }
                        }
                    }
                }
            }
            part.commitEventsAdd();
        // seq.(ftw);
        // add(lane);
        // part.onLoad();
        }

        rebuildGUI();

        if (midiDevice != null) {
            try {
                // midiDevice.open();
                midiDevice = new SynthWrapper(this, midiDevice);

                addMidiOutDevice(midiDevice);

            } catch (Exception e2) {
                e2.printStackTrace();
                midiDevice = null;
            }
        }

        // for (FrinikaTrackWrapper ftw : midiTracks) {
        // // if (origTracks.contains(ftw)) continue;
        // if (midiDevice != null)
        // ftw.setMidiDevice(midiDevice);
        // }

        // for (MidiLane lane : lanesToLoad) {
        // add(lane);
        // }

        getEditHistoryContainer().notifyEditHistoryListeners();
    }

    public void rebuildGUI() {
        ViewableLaneList list = new ViewableLaneList(this);
        list.rebuild();
        /*
         * for (Lane lane : list) {
         * 
         * if (lane.getParts() != null) { for (Part p : lane.getParts()) {
         * System.out.println(p); } } }
         */

        // projectLane.setHidden(true);
        // projectLane.getChildren().get(0).setHidden(true);
        // TODO should this part of the loadProject ?
        // Myabe not. Resources vary from machine to machine.
        // Need to discuss this ?
        midiResource = new MidiResource(sequencer);
    }
}
