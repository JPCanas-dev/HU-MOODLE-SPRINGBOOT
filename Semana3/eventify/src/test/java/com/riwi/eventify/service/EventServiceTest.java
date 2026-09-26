package com.riwi.eventify.service;

import com.riwi.eventify.model.Event;
import com.riwi.eventify.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime; // Falta esta importación

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @Test
    void createEvent_Success() {
        // --- CAMINO FELIZ ---
        // Usando el constructor tradicional
        Event event = new Event("Fiesta de fin de año", "Descripción de prueba", "Lugar de prueba", LocalDateTime.now());

        when(eventRepository.save(event)).thenReturn(event);

        Event createdEvent = eventService.createEvent(event);

        assertNotNull(createdEvent);
        assertEquals("Fiesta de fin de año", createdEvent.getName());
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    void createEvent_EmptyNameThrowsException() {
        // --- CAMINO DE ERROR (REGLA DE NEGOCIO) ---

        // 1. REEMPLAZADO: Quitamos el Builder y usamos el constructor con nombre vacío
        Event event = new Event("", "Descripción de prueba", "Lugar de prueba", LocalDateTime.now());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            eventService.createEvent(event);
        });

        assertEquals("El nombre del evento no puede estar vacío", exception.getMessage());
        verify(eventRepository, never()).save(any(Event.class));
    }
}