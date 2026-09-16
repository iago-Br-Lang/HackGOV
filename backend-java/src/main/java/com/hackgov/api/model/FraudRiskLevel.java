package com.hackgov.api.model;

/** Faixa de ação de um sistema antifraude, conforme a pontuação de risco combinada. */
public enum FraudRiskLevel {
    NORMAL,
    MONITORING,
    VERIFICATION,
    BLOCKED
}
