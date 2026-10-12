package tn.esprit.backend.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.backend.entity.Entreprise;
import tn.esprit.backend.entity.Equipe;
import tn.esprit.backend.entity.Projet;
import tn.esprit.backend.repository.EntrepriseRepository;
import tn.esprit.backend.repository.EquipeRepository;
import tn.esprit.backend.repository.ProjetRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EquipeServiceImplTest {

    @Mock
    private EquipeRepository equipeRepository;

    @Mock
    private EntrepriseRepository entrepriseRepository;

    @Mock
    private ProjetRepository projetRepository;

    @InjectMocks
    private EquipeServiceImpl service;

    @Test
    void addEquipe() {
        Equipe e = new Equipe();
        when(equipeRepository.save(e)).thenReturn(e);
        assertSame(e, service.addEquipe(e));
    }

    @Test
    void updateEquipe() {
        Equipe e = new Equipe();
        when(equipeRepository.save(e)).thenReturn(e);
        assertSame(e, service.updateEquipe(e));
    }

    @Test
    void deleteEquipe() {
        service.deleteEquipe(1L);
        verify(equipeRepository).deleteById(1L);
    }

    @Test
    void getEquipeById_trouve() {
        Equipe e = new Equipe();
        when(equipeRepository.findById(1L)).thenReturn(Optional.of(e));
        assertSame(e, service.getEquipeById(1L));
    }

    @Test
    void getEquipeById_introuvable() {
        when(equipeRepository.findById(99L)).thenReturn(Optional.empty());
        assertNull(service.getEquipeById(99L));
    }

    @Test
    void getAllEquipes() {
        when(equipeRepository.findAll()).thenReturn(List.of(new Equipe(), new Equipe()));
        assertEquals(2, service.getAllEquipes().size());
    }

    @Test
    void getEquipesByEntreprise() {
        when(equipeRepository.findByEntrepriseId(3L)).thenReturn(List.of(new Equipe()));
        assertEquals(1, service.getEquipesByEntreprise(3L).size());
        verify(equipeRepository).findByEntrepriseId(3L);
    }

    @Test
    void assignEquipeToEntreprise() {
        Equipe equipe = new Equipe();
        Entreprise entreprise = new Entreprise();
        when(equipeRepository.findById(1L)).thenReturn(Optional.of(equipe));
        when(entrepriseRepository.findById(2L)).thenReturn(Optional.of(entreprise));
        when(equipeRepository.save(equipe)).thenReturn(equipe);

        Equipe result = service.assignEquipeToEntreprise(1L, 2L);

        assertSame(entreprise, result.getEntreprise());
        verify(equipeRepository).save(equipe);
    }

    @Test
    void assignEquipeToProjet() {
        Equipe equipe = new Equipe();
        Projet projet = new Projet();
        when(equipeRepository.findById(1L)).thenReturn(Optional.of(equipe));
        when(projetRepository.findById(2L)).thenReturn(Optional.of(projet));
        when(equipeRepository.save(equipe)).thenReturn(equipe);

        Equipe result = service.assignEquipeToProjet(1L, 2L);

        assertTrue(result.getProjets().contains(projet));
        verify(equipeRepository).save(equipe);
    }
}
