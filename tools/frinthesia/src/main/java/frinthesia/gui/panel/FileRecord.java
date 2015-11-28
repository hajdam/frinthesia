/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frinthesia.gui.panel;

import java.io.File;

/**
 *
 *
 * @version 0.1.0 2015/11/28
 * @author Frinthesia Project
 */
public class FileRecord {

    private File file;
    private String fileName;
    private FileType fileType;
    private String duration;

    public FileRecord(File file, String fileName) {
        this.file = file;
        this.fileName = fileName == null ? file.getName() : fileName;
        if (file.isDirectory()) {
            fileType = FileType.DIRECTORY;
        } else if (file.getName().toLowerCase().endsWith(".mid")) {
            fileType = FileType.MIDI;
            duration = "00:00";
        } else {
            fileType = FileType.FILE;
        }
    }

    public FileRecord(File file) {
        this(file, null);
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public enum FileType {
        FILE,
        MIDI,
        DIRECTORY
    }
}
