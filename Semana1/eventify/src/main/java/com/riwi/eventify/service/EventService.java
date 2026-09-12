package com.riwi.eventify.service;

import com.riwi.eventify.model.Event;
import com.riwi.eventify.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    // Inyección de dependencias por constructor (Buena práctica)
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event createEvent(Event event) {
        // Regla de negocio: El nombre no puede estar vacío
        if (event.getName() == null || event.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del evento no puede estar vacío");
        }

        return eventRepository.save(event);
    }
}