package com.hackgov.api.controller;

import com.hackgov.api.data.FictitiousDataStore;
import com.hackgov.api.model.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

/** Endpoints de serviços públicos (fictícios) e busca por palavra-chave. */
@RestController
@RequestMapping("/api/services")
public class ServicesController {

    private final FictitiousDataStore store;

    public ServicesController(FictitiousDataStore store) {
        this.store = store;
    }

    @GetMapping
    public List<Service> listServices() {
        return store.services();
    }

    @GetMapping("/{slug}")
    public Service getService(@PathVariable String slug) {
        return store.serviceBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Serviço não encontrado: " + slug));
    }

    /** Busca por texto livre, ex.: {@code /api/services/search?q=perdi minha carteira}. */
    @GetMapping("/search")
    public List<Service> searchServices(@RequestParam("q") String query) {
        return store.matchServices(query);
    }
}
