package com.sujeet.filemanager.model;

public class FileModel {

    private String name;
    private String data;
    private String path;
    private boolean folder;
    private boolean parent;

    public FileModel(String name, String data, String path) {
        this.name = name;
        this.data = data;
        this.path = path;
    }

    public FileModel(String name, String data, String path, boolean folder, boolean parent) {
        this.name = name;
        this.data = data;
        this.path = path;
        this.folder = folder;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isFolder() {
        return folder;
    }

    public void setFolder(boolean folder) {
        this.folder = folder;
    }

    public boolean isParent() {
        return parent;
    }

    public void setParent(boolean parent) {
        this.parent = parent;
    }
}
