package com.example.library_management;
public class BookManagement {
    private int id;
    private String name;
    private int page;
    private boolean isActive;

    //constructors

    public BookManagement(int id, String name, int page, boolean isActive) {
        this.id = id;
        this.name = name;
        this.page = page;
        this.isActive = isActive;
    }
    //toString is necessary for printing the contents of the class object

    @Override
    public String toString() {
        return
                "ID:" + id +
                "\nName:" + name + '\'' +
                "\nNo. of Pages:" + page +
                "\nIssued?:" + isActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }}

