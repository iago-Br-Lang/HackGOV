package com.hackgov.api.model;

/**
 * Um fator individual avaliado de forma independente pelo sistema de análise de risco
 * (defesa em profundidade: nenhum sinal isolado decide sozinho).
 */
public class FraudSignal {

    private String label;
    private String detail;
    private boolean ok;

    public FraudSignal() {
    }

    public FraudSignal(String label, String detail, boolean ok) {
        this.label = label;
        this.detail = detail;
        this.ok = ok;
    }

    public String getLabel() {
        return label;
    }

    public String getDetail() {
        return detail;
    }

    public boolean isOk() {
        return ok;
    }
}
