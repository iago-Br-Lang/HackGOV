package com.hackgov.api.controller;

import com.hackgov.api.data.FictitiousDataStore;
import com.hackgov.api.model.StatusIndicator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Endpoint de status/transparência da plataforma (uptime, incidentes, certificações). */
@RestController
@RequestMapping("/api/status")
public class StatusController {

    private final FictitiousDataStore store;

    public StatusController(FictitiousDataStore store) {
        this.store = store;
    }

    @GetMapping
    public List<StatusIndicator> getStatus() {
        return store.statusIndicators();
    }
}
