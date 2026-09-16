package com.hackgov.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Ponto de entrada da API fictícia do HackGOV.
 *
 * <p>Esta aplicação expõe, via REST, os mesmos dados de demonstração usados pelo
 * front-end em Rust/Dioxus (ver {@code src/data.rs} no projeto principal), agora
 * como um backend Java independente. Todos os dados são fictícios/simulados: não
 * existe integração real com sistemas do governo.
 */
@SpringBootApplication
public class HackGovApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HackGovApiApplication.class, args);
    }
}
