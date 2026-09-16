package com.hackgov.api.controller;

import com.hackgov.api.data.FictitiousDataStore;
import com.hackgov.api.model.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Endpoint de categorias de serviços. */
@RestController
@RequestMapping("/api/categories")
public class CategoriesController {

    private final FictitiousDataStore store;

    public CategoriesController(FictitiousDataStore store) {
        this.store = store;
    }

    @GetMapping
    public List<Category> listCategories() {
        return store.categories();
    }
}
