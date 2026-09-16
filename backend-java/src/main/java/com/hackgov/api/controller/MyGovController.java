package com.hackgov.api.controller;

import com.hackgov.api.data.FictitiousDataStore;
import com.hackgov.api.model.LifeArea;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Endpoint do painel "Minha vida no governo" (visão consolidada e fictícia do cidadão). */
@RestController
@RequestMapping("/api/my-gov")
public class MyGovController {

    private final FictitiousDataStore store;

    public MyGovController(FictitiousDataStore store) {
        this.store = store;
    }

    @GetMapping("/life-areas")
    public List<LifeArea> listLifeAreas() {
        return store.lifeAreas();
    }
}
