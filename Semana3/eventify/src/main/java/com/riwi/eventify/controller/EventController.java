package com.riwi.eventify.controller;

import com.riwi.eventify.model.Event;
import com.riwi.eventify.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
@Tag(name = "Eventos", description = "Operaciones relacionadas con los eventos")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // Soporta URLs como: /api/events?page=0&size=5&sort=name,asc
    @GetMapping
    @Operation(summary = "Obtener lista de eventos paginada")
    public ResponseEntity<Page<Event>> getAllEvents(
            @RequestParam(required = false) String name,
            Pageable pageable) {
        Page<Event> events = (name != null && !name.isEmpty())
                ? eventService.searchByName(name, pageable)
                : eventService.getAllEvents(pageable);

        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener evento por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evento encontrado"),
            @ApiResponse(responseCode = "404", description = "Evento no existe")
    })
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo evento")
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        return new ResponseEntity<>(eventService.createEvent(event), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un evento completo")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event event) {
        return ResponseEntity.ok(eventService.updateEvent(id, event));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un evento")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Evento no existe")
    })
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}