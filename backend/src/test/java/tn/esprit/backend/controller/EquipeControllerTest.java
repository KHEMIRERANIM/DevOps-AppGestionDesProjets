package tn.esprit.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.backend.entity.Equipe;
import tn.esprit.backend.service.IEquipeService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EquipeControllerTest {

    private final IEquipeService service = mock(IEquipeService.class);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new EquipeController(service)).build();

    private Equipe equipe() {
        Equipe e = new Equipe();
        e.setId(1L);
        e.setNom("Alpha");
        return e;
    }

    @Test
    void add() throws Exception {
        when(service.addEquipe(any(Equipe.class))).thenReturn(equipe());
        mockMvc.perform(post("/equipe/add").contentType(MediaType.APPLICATION_JSON).content("{\"nom\":\"Alpha\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Alpha"));
    }

    @Test
    void update() throws Exception {
        when(service.updateEquipe(any(Equipe.class))).thenReturn(equipe());
        mockMvc.perform(put("/equipe/update").contentType(MediaType.APPLICATION_JSON).content("{\"id\":1,\"nom\":\"Alpha\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete("/equipe/delete/1")).andExpect(status().isOk());
        verify(service).deleteEquipe(1L);
    }

    @Test
    void getById() throws Exception {
        when(service.getEquipeById(1L)).thenReturn(equipe());
        mockMvc.perform(get("/equipe/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Alpha"));
    }

    @Test
    void getAll() throws Exception {
        when(service.getAllEquipes()).thenReturn(List.of(equipe()));
        mockMvc.perform(get("/equipe/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void getByEntreprise() throws Exception {
        when(service.getEquipesByEntreprise(3L)).thenReturn(List.of(equipe()));
        mockMvc.perform(get("/equipe/by-entreprise/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void assignEntreprise() throws Exception {
        when(service.assignEquipeToEntreprise(1L, 2L)).thenReturn(equipe());
        mockMvc.perform(put("/equipe/assign-entreprise/1/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void assignProjet() throws Exception {
        when(service.assignEquipeToProjet(1L, 2L)).thenReturn(equipe());
        mockMvc.perform(put("/equipe/assign-projet/1/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }
}
