package com.hackgov.api.controller;

import com.hackgov.api.data.FictitiousDataStore;
import com.hackgov.api.model.HelpReason;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Endpoint de motivos de bloqueio do botão "Não consigo resolver" e suas soluções. */
@RestController
@RequestMapping("/api/help-reasons")
public class HelpController {

    private final FictitiousDataStore store;

    public HelpController(FictitiousDataStore store) {
        this.store = store;
    }

    @GetMapping
    public List<HelpReason> listHelpReasons() {
        return store.helpReasons();
    }
}
