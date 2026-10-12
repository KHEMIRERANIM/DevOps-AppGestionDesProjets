package tn.esprit.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.backend.entity.Entreprise;
import tn.esprit.backend.service.IEntrepriseService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EntrepriseControllerTest {

    private final IEntrepriseService service = mock(IEntrepriseService.class);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new EntrepriseController(service)).build();

    private Entreprise entreprise() {
        Entreprise e = new Entreprise();
        e.setId(1L);
        e.setNom("Esprit");
        return e;
    }

    @Test
    void add() throws Exception {
        when(service.addEntreprise(any(Entreprise.class))).thenReturn(entreprise());
        mockMvc.perform(post("/entreprise/add").contentType(MediaType.APPLICATION_JSON).content("{\"nom\":\"Esprit\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Esprit"));
    }

    @Test
    void update() throws Exception {
        when(service.updateEntreprise(any(Entreprise.class))).thenReturn(entreprise());
        mockMvc.perform(put("/entreprise/update").contentType(MediaType.APPLICATION_JSON).content("{\"id\":1,\"nom\":\"Esprit\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete("/entreprise/delete/1")).andExpect(status().isOk());
        verify(service).deleteEntreprise(1L);
    }

    @Test
    void getById() throws Exception {
        when(service.getEntrepriseById(1L)).thenReturn(entreprise());
        mockMvc.perform(get("/entreprise/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Esprit"));
    }

    @Test
    void getAll() throws Exception {
        when(service.getAllEntreprises()).thenReturn(List.of(entreprise()));
        mockMvc.perform(get("/entreprise/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }
}
