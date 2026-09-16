package com.hackgov.api.model;

/** Indicador de status/transparência da plataforma (uptime, incidentes, certificações...). */
public class StatusIndicator {

    private String icon;
    private String label;
    private String value;

    public StatusIndicator() {
    }

    public StatusIndicator(String icon, String label, String value) {
        this.icon = icon;
        this.label = label;
        this.value = value;
    }

    public String getIcon() {
        return icon;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }
}
