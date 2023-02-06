package com.example.nicnamegenerator;

public class Nicname {
    String str;
    boolean isSelected;

    public Nicname(String str, boolean isSelected) {
        this.str = str;
        this.isSelected = isSelected;
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
