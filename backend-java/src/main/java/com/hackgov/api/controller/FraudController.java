package com.hackgov.api.controller;

import com.hackgov.api.data.FictitiousDataStore;
import com.hackgov.api.model.FraudRiskLevel;
import com.hackgov.api.model.FraudSignal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Endpoints do simulador de sistema antifraude ("defesa em profundidade"). */
@RestController
@RequestMapping("/api/fraud")
public class FraudController {

    private final FictitiousDataStore store;

    public FraudController(FictitiousDataStore store) {
        this.store = store;
    }

    @GetMapping("/risk")
    public FraudRiskResponse getRisk() {
        int score = store.fraudRiskScore();
        FraudRiskLevel level = store.fraudLevel(score);
        return new FraudRiskResponse(score, level, store.fraudSignals());
    }

    @GetMapping("/signals")
    public List<FraudSignal> getSignals() {
        return store.fraudSignals();
    }

    /** Corpo de resposta combinando pontuação, nível de risco e sinais avaliados. */
    public static class FraudRiskResponse {
        private final int score;
        private final FraudRiskLevel level;
        private final List<FraudSignal> signals;

        public FraudRiskResponse(int score, FraudRiskLevel level, List<FraudSignal> signals) {
            this.score = score;
            this.level = level;
            this.signals = signals;
        }

        public int getScore() {
            return score;
        }

        public FraudRiskLevel getLevel() {
            return level;
        }

        public List<FraudSignal> getSignals() {
            return signals;
        }
    }
}
