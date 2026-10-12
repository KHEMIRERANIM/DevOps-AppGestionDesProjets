package tn.esprit.backend.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.backend.entity.Entreprise;
import tn.esprit.backend.repository.EntrepriseRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EntrepriseServiceImplTest {

    @Mock
    private EntrepriseRepository entrepriseRepository;

    @InjectMocks
    private EntrepriseServiceImpl service;

    @Test
    void addEntreprise() {
        Entreprise e = new Entreprise();
        when(entrepriseRepository.save(e)).thenReturn(e);
        assertSame(e, service.addEntreprise(e));
        verify(entrepriseRepository).save(e);
    }

    @Test
    void updateEntreprise() {
        Entreprise e = new Entreprise();
        when(entrepriseRepository.save(e)).thenReturn(e);
        assertSame(e, service.updateEntreprise(e));
        verify(entrepriseRepository).save(e);
    }

    @Test
    void deleteEntreprise() {
        service.deleteEntreprise(1L);
        verify(entrepriseRepository).deleteById(1L);
    }

    @Test
    void getEntrepriseById_trouve() {
        Entreprise e = new Entreprise();
        when(entrepriseRepository.findById(1L)).thenReturn(Optional.of(e));
        assertSame(e, service.getEntrepriseById(1L));
    }

    @Test
    void getEntrepriseById_introuvable() {
        when(entrepriseRepository.findById(99L)).thenReturn(Optional.empty());
        assertNull(service.getEntrepriseById(99L));
    }

    @Test
    void getAllEntreprises() {
        when(entrepriseRepository.findAll()).thenReturn(List.of(new Entreprise(), new Entreprise()));
        assertEquals(2, service.getAllEntreprises().size());
    }
}
