package com.hackgov.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HackGovApiApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    void listServicesReturnsSevenFictitiousServices() throws Exception {
        mockMvc.perform(get("/api/services"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(7))
                .andExpect(jsonPath("$[0].slug").value("assinatura-eletronica"));
    }

    @Test
    void getServiceBySlugReturnsService() throws Exception {
        mockMvc.perform(get("/api/services/cnh-digital"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("CNH Digital e Segunda Via"));
    }

    @Test
    void getServiceByUnknownSlugReturns404() throws Exception {
        mockMvc.perform(get("/api/services/nao-existe"))
                .andExpect(status().isNotFound());
    }

    @Test
    void searchServicesMatchesByKeyword() throws Exception {
        mockMvc.perform(get("/api/services/search").param("q", "perdi minha carteira"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].slug").value("cnh-digital"));
    }

    @Test
    void fraudRiskReturnsScoreLevelAndSignals() throws Exception {
        mockMvc.perform(get("/api/fraud/risk"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.score").value(34))
                .andExpect(jsonPath("$.level").value("MONITORING"))
                .andExpect(jsonPath("$.signals").isArray());
    }

    @Test
    void statusReturnsFourIndicators() throws Exception {
        mockMvc.perform(get("/api/status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4));
    }
}
