package com.karthikeyan;

/**
 * @author Karthikeyan on 20-06-2021
 */

public class Row {
    private String name;
    private Object value;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Row(String name, Object value, String type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }
}
