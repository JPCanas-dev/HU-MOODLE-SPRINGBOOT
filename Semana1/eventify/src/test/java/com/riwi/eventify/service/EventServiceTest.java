package com.riwi.eventify.service;

import com.riwi.eventify.model.Event;
import com.riwi.eventify.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Esta etiqueta le avisa a JUnit que usaremos Mockito para simular objetos en esta prueba
@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    // Creamos un "falso" repositorio (un mock). No se conecta a bases de datos reales, solo simula su comportamiento.
    @Mock
    private EventRepository eventRepository;

    // Creamos la instancia del Servicio que queremos probar, y Mockito le inyecta automaticamente el repositorio falso de arriba.
    @InjectMocks
    private EventService eventService;

    // La etiqueta @Test indica que este metodo es una prueba unitaria que se ejecutara.
    @Test
    void createEvent_Success() {
        // --- CAMINO FELIZ ---

        // 1. Creamos un evento de prueba con un nombre valido usando el Patron Builder
        Event event = Event.builder().name("Fiesta de fin de año").build();

        // 2. Simulamos el comportamiento del repositorio: cuando le pidan guardar este evento, retornalo exitosamente.
        when(eventRepository.save(event)).thenReturn(event);

        // 3. Ejecutamos el metodo real que queremos probar en el Servicio
        Event createdEvent = eventService.createEvent(event);

        // 4. Comprobamos los resultados (Aserciones)
        // Verificamos que el objeto creado no sea nulo
        assertNotNull(createdEvent);
        // Verificamos que el nombre guardado sea exactamente el que esperamos
        assertEquals("Fiesta de fin de año", createdEvent.getName());
        // Verificamos que el metodo "save" del repositorio falso fue llamado exactamente 1 sola vez
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    void createEvent_EmptyNameThrowsException() {
        // --- CAMINO DE ERROR (REGLA DE NEGOCIO) ---

        // 1. Creamos un evento con el nombre vacio (lo cual viola la regla de negocio)
        Event event = Event.builder().name("").build();

        // 2. Verificamos que al intentar crearlo, el sistema lance una excepcion de tipo IllegalArgumentException
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            eventService.createEvent(event);
        });

        // 3. Comprobamos que el mensaje de error de la excepcion sea exactamente el que programamos en el servicio
        assertEquals("El nombre del evento no puede estar vacío", exception.getMessage());

        // 4. Verificamos un punto clave: como el nombre estaba vacio, el repositorio NUNCA debio ser llamado para guardar.
        verify(eventRepository, never()).save(any(Event.class));
    }
}