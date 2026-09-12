package com.riwi.eventify.repository;

import com.riwi.eventify.model.Event;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class EventRepository {
    // Simulamos la base de datos con una lista en memoria
    private final List<Event> events = new ArrayList<>();

    public List<Event> findAll() {
        return events;
    }

    public Event save(Event event) {
        // Generamos un ID único si no lo tiene
        if (event.getId() == null || event.getId().isEmpty()) {
            event.setId(UUID.randomUUID().toString());
        }
        events.add(event);
        return event;
    }
}