package com.riwi.eventify.controller;

import com.riwi.eventify.model.Venue;
import com.riwi.eventify.service.VenueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Etiqueta vital: Indica que esta clase expone direcciones web (Endpoints) que devuelven datos en formato JSON.
@RequestMapping("/api/venues") // Define la URL base para este controlador.
@Tag(name = "Lugares (Venues)", description = "Operaciones relacionadas con los lugares de los eventos") // Documentación visual para Swagger.
public class VenueController {

    private final VenueService venueService;

    // Conectamos el servicio al controlador
    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    // @GetMapping indica que este metodo se ejecuta cuando alguien entra a la URL con una petición de "Leer" (GET)
    @GetMapping
    @Operation(summary = "Obtener todos los lugares")
    public ResponseEntity<List<Venue>> getAllVenues() {
        List<Venue> venues = venueService.getAllVenues();
        // Respondemos con la lista y un código HTTP 200 (OK)
        return ResponseEntity.ok(venues);
    }

    // @PostMapping indica que este metodo se ejecuta cuando alguien envía datos nuevos a la URL (POST)
    @PostMapping
    @Operation(summary = "Crear un nuevo lugar")
    public ResponseEntity<?> createVenue(@RequestBody Venue venue) {
        // @RequestBody agarra el texto JSON que nos envían por internet y lo transforma mágicamente en un objeto Java "Venue".
        try {
            Venue createdVenue = venueService.createVenue(venue);
            // Respondemos con el objeto guardado y un código HTTP 201 (Created), justo como pide tu tarea.
            return ResponseEntity.status(HttpStatus.CREATED).body(createdVenue);
        } catch (IllegalArgumentException e) {
            // Si el servicio detectó un error (ej. nombre vacío), capturamos ese error aquí y devolvemos un estado HTTP 400 (Bad Request).
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}