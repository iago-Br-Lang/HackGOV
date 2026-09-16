package com.hackgov.api.controller;

import com.hackgov.api.data.FictitiousDataStore;
import com.hackgov.api.model.Alert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Endpoint de alertas simulados do cidadão (prazos, pendências, valores disponíveis). */
@RestController
@RequestMapping("/api/alerts")
public class AlertsController {

    private final FictitiousDataStore store;

    public AlertsController(FictitiousDataStore store) {
        this.store = store;
    }

    @GetMapping
    public List<Alert> listAlerts() {
        return store.alerts();
    }
}
