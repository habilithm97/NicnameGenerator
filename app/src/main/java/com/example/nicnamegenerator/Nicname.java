package com.example.nicnamegenerator;

public class Nicname {
    int id;
    String str;
    boolean isSelected;

    public Nicname(int id, String str, boolean isSelected) {
        this.id = id;
        this.str = str;
        this.isSelected = isSelected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
