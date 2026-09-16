package com.hackgov.api.model;

/** Alerta simulado do cidadão (prazos, pendências, valores disponíveis). */
public class Alert {

    private String icon;
    private String title;
    private String message;
    private AlertLevel level;

    public Alert() {
    }

    public Alert(String icon, String title, String message, AlertLevel level) {
        this.icon = icon;
        this.title = title;
        this.message = message;
        this.level = level;
    }

    public String getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public AlertLevel getLevel() {
        return level;
    }
}
