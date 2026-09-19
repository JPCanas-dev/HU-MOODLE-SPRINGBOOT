package com.riwi.eventify.service;

import com.riwi.eventify.model.Venue;
import com.riwi.eventify.repository.VenueRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Etiqueta vital: Le dice a Spring que aquí van las reglas lógicas de tu programa.
public class VenueService {

    private final VenueRepository venueRepository;

    // Inyección de dependencias por constructor: Spring automáticamente conecta el Repositorio con este Servicio.
    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    // Pide al repositorio que le traiga todos los lugares
    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    // Recibe un lugar nuevo y verifica que esté correcto antes de pasarlo al repositorio
    public Venue createVenue(Venue venue) {
        // Regla de negocio: El nombre del lugar no puede estar vacío ni ser nulo.
        if (venue.getName() == null || venue.getName().trim().isEmpty()) {
            // Si está vacío, "lanzamos una excepción" (un error), lo que detiene el proceso y evita que se guarde.
            throw new IllegalArgumentException("El nombre del lugar no puede estar vacío");
        }

        // Si todo está bien, le mandamos el objeto al repositorio para que lo guarde definitivamente.
        return venueRepository.save(venue);
    }
}