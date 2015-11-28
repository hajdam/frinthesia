/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frinthesia.gui.panel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import javax.swing.table.AbstractTableModel;

/**
 * Frinthesia panel interface.
 *
 * @version 0.1.0 2015/11/28
 * @author Frinthesia Project
 */
public class MidiBrowserModel extends AbstractTableModel {

    private final List<FileRecord> fileRecords = new ArrayList<>();
    private final BlockingQueue<UpdateFileRecord> updateQueue = new LinkedBlockingQueue<>();
    private Thread updateThread;
    private Sequencer sequencer = null;

    public MidiBrowserModel() {
    }

    public void init() {
        Runnable updateMethod = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        UpdateFileRecord updateRecord = updateQueue.take();

                        try {
                            if (sequencer == null) {
                                sequencer = MidiSystem.getSequencer();
                                sequencer.open();
                            }

                            sequencer.setSequence(new FileInputStream(updateRecord.fileRecord.getFile()));
                        } catch (MidiUnavailableException | IOException | InvalidMidiDataException ex) {
                            Logger.getLogger(MidiBrowserModel.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        if (sequencer != null) {
                            Calendar calendar = Calendar.getInstance();
                            long microsecondLength = sequencer.getMicrosecondLength();
                            calendar.setTimeInMillis(microsecondLength / 1000);
                            int hour = calendar.get(Calendar.HOUR_OF_DAY) - 1;
                            int minute = calendar.get(Calendar.MINUTE);
                            int second = calendar.get(Calendar.SECOND);
                            String duration = (hour > 0 ? hour + ":" : "") + intToString(minute, 2) + ":" + intToString(second, 2);
                            updateRecord.fileRecord.setDuration(duration);
                            fireTableRowsUpdated(updateRecord.row, updateRecord.row);
                        }
                    } catch (InterruptedException ex) {
                    }
                }
            }
        };

        updateThread = new Thread(updateMethod);
        updateThread.start();
    }

    private FilenameFilter filter = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            return true;
        }
    };

    public void loadForPath(File directory) {
        updateQueue.clear();
        fileRecords.clear();
        if (directory.getParentFile() != null) {
            fileRecords.add(new FileRecord(directory.getParentFile(), ".."));
        }

        File[] foundFiles = directory.listFiles(filter);

        for (File foundFile : foundFiles) {
            if (foundFile.isDirectory()) {
                fileRecords.add(new FileRecord(foundFile));
            }
        }

        int row = fileRecords.size() - 1;
        for (File foundFile : foundFiles) {
            if (!foundFile.isDirectory()) {
                FileRecord fileRecord = new FileRecord(foundFile);
                fileRecords.add(fileRecord);
                row++;

                if (fileRecord.getFileType() == FileRecord.FileType.MIDI) {
                    updateQueue.add(new UpdateFileRecord(fileRecord, row));
                }
            }
        }

        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return fileRecords.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Filename";
            case 1:
                return "Duration";
            case 2:
                return "Difficulty";
            case 3:
                return "";
        }

        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        FileRecord fileRecord = fileRecords.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return fileRecord.getFileName();
            case 1:
                return fileRecord.getDuration();
            case 2:
                return "";
            case 3:
                return "";
        }

        return null;
    }

    public FileRecord getRecord(int rowIndex) {
        return fileRecords.get(rowIndex);
    }

    private static class UpdateFileRecord {

        public UpdateFileRecord(FileRecord fileRecord, int row) {
            this.fileRecord = fileRecord;
            this.row = row;
        }

        FileRecord fileRecord;
        int row;
    }

    private static String intToString(int value, int digitsCount) {
        String result = Integer.toString(value);
        if (result.length() < digitsCount) {
            int missingDigits = digitsCount - result.length();
            switch (missingDigits) {
                case 1:
                    return "0" + result;
                case 2:
                    return "00" + result;
                default: {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < missingDigits; i++) {
                        builder.append("0");
                    }

                    return builder.toString() + result;
                }
            }
        }

        return result;
    }
}
