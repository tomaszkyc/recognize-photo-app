package com.tomaszkyc.app.ui.model;

public class UIResult {

    public UIResult(String name, String value) {
        this.name = name;
        this.value = value;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String value;


}
