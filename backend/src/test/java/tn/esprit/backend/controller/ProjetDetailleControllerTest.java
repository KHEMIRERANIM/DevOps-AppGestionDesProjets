package tn.esprit.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.backend.entity.ProjetDetaille;
import tn.esprit.backend.service.IProjetDetailleService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProjetDetailleControllerTest {

    private final IProjetDetailleService service = mock(IProjetDetailleService.class);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new ProjetDetailleController(service)).build();

    private ProjetDetaille pd() {
        ProjetDetaille p = new ProjetDetaille();
        p.setId(1L);
        p.setDescription("Desc");
        return p;
    }

    @Test
    void add() throws Exception {
        when(service.addProjetDetaille(any(ProjetDetaille.class))).thenReturn(pd());
        mockMvc.perform(post("/projet-detaille/add").contentType(MediaType.APPLICATION_JSON).content("{\"description\":\"Desc\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Desc"));
    }

    @Test
    void update() throws Exception {
        when(service.updateProjetDetaille(any(ProjetDetaille.class))).thenReturn(pd());
        mockMvc.perform(put("/projet-detaille/update").contentType(MediaType.APPLICATION_JSON).content("{\"id\":1,\"description\":\"Desc\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete("/projet-detaille/delete/1")).andExpect(status().isOk());
        verify(service).deleteProjetDetaille(1L);
    }

    @Test
    void getById() throws Exception {
        when(service.getProjetDetailleById(1L)).thenReturn(pd());
        mockMvc.perform(get("/projet-detaille/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Desc"));
    }

    @Test
    void getAll() throws Exception {
        when(service.getAllProjetsDetailles()).thenReturn(List.of(pd()));
        mockMvc.perform(get("/projet-detaille/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void getByProjet() throws Exception {
        when(service.getProjetDetaillesByProjet(5L)).thenReturn(List.of(pd()));
        mockMvc.perform(get("/projet-detaille/by-projet/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void assignProjet() throws Exception {
        when(service.assignProjetDetailleToProjet(1L, 2L)).thenReturn(pd());
        mockMvc.perform(put("/projet-detaille/assign-projet/1/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }
}
