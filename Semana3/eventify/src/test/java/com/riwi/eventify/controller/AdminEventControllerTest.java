package com.riwi.eventify.controller;

import com.riwi.eventify.model.Event;
import com.riwi.eventify.service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest; // ¡Ruta nueva de Boot 4!
import org.springframework.test.context.bean.override.mockito.MockitoBean; // ¡Nuevo reemplazo de MockBean en Boot 4!
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminEventController.class) // Prueba enfocada solo en este controlador visual
public class AdminEventControllerTest {

    @Autowired
    private MockMvc mockMvc; // Herramienta para simular peticiones HTTP al navegador

    @MockitoBean
    private EventService eventService; // Simulamos el servicio para no usar la BD real

    @Test
    void showEventPanel_ReturnsViewAndModel() throws Exception {
        // 1. Preparación (Given)
        // Creamos una página simulada con un evento de prueba
        Event testEvent = new Event();
        testEvent.setName("Evento de Prueba HTML");
        Page<Event> mockPage = new PageImpl<>(List.of(testEvent));

        // Le decimos al mock qué responder cuando el controlador llame al servicio
        when(eventService.getAllEvents(any(PageRequest.class))).thenReturn(mockPage);

        // 2. Ejecución y Verificación (When & Then) -> ¡Este es el Escenario 4!
        mockMvc.perform(get("/admin/events"))
                .andExpect(status().isOk()) // Confirma que la página carga con código 200 OK
                .andExpect(view().name("event-list")) // Confirma que retorna el archivo "event-list.html"
                .andExpect(model().attributeExists("events")); // Confirma que la maleta 'Model' lleva los datos
    }
}