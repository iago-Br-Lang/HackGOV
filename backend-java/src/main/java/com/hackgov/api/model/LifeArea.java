package com.hackgov.api.model;

/** Um "cartão" da vida do cidadão para o painel "Minha vida no governo". */
public class LifeArea {

    private String icon;
    private String name;
    private String status;
    private LifeState state;
    private String relatedSlug;

    public LifeArea() {
    }

    public LifeArea(String icon, String name, String status, LifeState state, String relatedSlug) {
        this.icon = icon;
        this.name = name;
        this.status = status;
        this.state = state;
        this.relatedSlug = relatedSlug;
    }

    public String getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public LifeState getState() {
        return state;
    }

    public String getRelatedSlug() {
        return relatedSlug;
    }
}
