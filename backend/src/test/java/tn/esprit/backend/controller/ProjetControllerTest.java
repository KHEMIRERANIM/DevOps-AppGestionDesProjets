package tn.esprit.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.backend.entity.Projet;
import tn.esprit.backend.service.IProjetService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProjetControllerTest {

    private final IProjetService service = mock(IProjetService.class);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new ProjetController(service)).build();

    private Projet projet() {
        Projet p = new Projet();
        p.setId(1L);
        p.setSujet("IA");
        return p;
    }

    @Test
    void add() throws Exception {
        when(service.addProjet(any(Projet.class))).thenReturn(projet());
        mockMvc.perform(post("/projet/add").contentType(MediaType.APPLICATION_JSON).content("{\"sujet\":\"IA\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sujet").value("IA"));
    }

    @Test
    void update() throws Exception {
        when(service.updateProjet(any(Projet.class))).thenReturn(projet());
        mockMvc.perform(put("/projet/update").contentType(MediaType.APPLICATION_JSON).content("{\"id\":1,\"sujet\":\"IA\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete("/projet/delete/1")).andExpect(status().isOk());
        verify(service).deleteProjet(1L);
    }

    @Test
    void getById() throws Exception {
        when(service.getProjetById(1L)).thenReturn(projet());
        mockMvc.perform(get("/projet/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sujet").value("IA"));
    }

    @Test
    void getAll() throws Exception {
        when(service.getAllProjets()).thenReturn(List.of(projet()));
        mockMvc.perform(get("/projet/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }
}
