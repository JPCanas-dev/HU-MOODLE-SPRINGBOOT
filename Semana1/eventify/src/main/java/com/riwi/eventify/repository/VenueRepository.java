package com.riwi.eventify.repository;

import com.riwi.eventify.model.Venue;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository // Etiqueta vital: Le dice a Spring que esta clase guarda y busca datos.
public class VenueRepository {

    // Como aún no conectamos una base de datos real, usamos esta lista en la memoria RAM.
    private final List<Venue> venues = new ArrayList<>();

    // Metodo para devolver todos los lugares guardados.
    public List<Venue> findAll() {
        return venues;
    }

    // Metodo para guardar un nuevo lugar en nuestra lista.
    public Venue save(Venue venue) {
        // Si el lugar que nos llega no tiene ID, le generamos uno aleatorio.
        if (venue.getId() == null || venue.getId().isEmpty()) {
            // UUID crea códigos únicos (ejemplo: 123e4567-e89b-12d3-a456-426614174000)
            venue.setId(UUID.randomUUID().toString());
        }
        venues.add(venue); // Metemos el lugar a la lista
        return venue; // Devolvemos el lugar ya con su ID asignado
    }
}