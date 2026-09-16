package com.hackgov.api.model;

/** Um motivo de bloqueio para o botão "Não consigo resolver", com a solução sugerida. */
public class HelpReason {

    private String icon;
    private String label;
    private String solution;

    public HelpReason() {
    }

    public HelpReason(String icon, String label, String solution) {
        this.icon = icon;
        this.label = label;
        this.solution = solution;
    }

    public String getIcon() {
        return icon;
    }

    public String getLabel() {
        return label;
    }

    public String getSolution() {
        return solution;
    }
}
