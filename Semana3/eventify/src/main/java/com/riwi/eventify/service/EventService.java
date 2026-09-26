package com.riwi.eventify.service;

import com.riwi.eventify.exception.ResourceNotFoundException;
import com.riwi.eventify.model.Event;
import com.riwi.eventify.repository.EventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Page<Event> getAllEvents(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }

    public Page<Event> searchByName(String name, Pageable pageable) {
        return eventRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    // Intenta buscar el recurso; si falla, lanza la excepción que detona el 404
    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con ID: " + id));
    }

    public Event createEvent(Event event) {
        // Validamos la regla de negocio que espera el test antes de guardar
        if (event.getName() == null || event.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del evento no puede estar vacío");
        }
        return eventRepository.save(event);
    }

    // Se apoya en getEventById para validar existencia antes de actualizar
    public Event updateEvent(Long id, Event eventDetails) {
        Event existingEvent = getEventById(id);

        existingEvent.setName(eventDetails.getName());
        existingEvent.setDescription(eventDetails.getDescription());
        existingEvent.setVenue(eventDetails.getVenue());
        existingEvent.setDate(eventDetails.getDate());

        return eventRepository.save(existingEvent);
    }

    // Valida existencia física antes del borrado
    public void deleteEvent(Long id) {
        Event existingEvent = getEventById(id);
        eventRepository.delete(existingEvent);
    }
}