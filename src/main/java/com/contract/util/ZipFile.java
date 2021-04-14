package com.contract.util;

import java.io.File;

public class ZipFile {
    private File file;
    private String newName;

    public ZipFile(File file, String newName) {
        this.file = file;
        this.newName = newName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }
}
