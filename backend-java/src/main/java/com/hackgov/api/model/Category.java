package com.hackgov.api.model;

/** Uma categoria de serviços (usada para navegação/filtro no front-end). */
public class Category {

    private String icon;
    private String name;

    public Category() {
    }

    public Category(String icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }
}
