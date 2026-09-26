package com.riwi.eventify.controller;

import com.riwi.eventify.model.Event;
import com.riwi.eventify.service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller // Etiqueta vital: Indica que este controlador devuelve vistas (HTML), no datos JSON.
@RequestMapping("/admin/events") // Separamos las rutas de interfaz en /admin/
public class AdminEventController {

    private final EventService eventService;

    // Reutilizamos el mismo servicio que ya creaste. ¡La lógica de negocio no cambia!
    public AdminEventController(EventService eventService) {
        this.eventService = eventService;
    }

    // --- 1. Mostrar la lista de eventos ---
    @GetMapping
    public String showEventPanel(Model model) {
        // Le enviamos la configuración: Tráeme la página 0 con hasta 100 elementos
        var events = eventService.getAllEvents(org.springframework.data.domain.PageRequest.of(0, 100));

        // El objeto 'Model' es la maleta que viaja de Java hacia el HTML.
        // Aquí metemos la lista de eventos en la maleta y le ponemos la etiqueta "events".
        model.addAttribute("events", events);

        // Retorna el nombre exacto del archivo HTML que crearemos (sin poner ".html")
        return "event-list";
    }

    // --- 2. Mostrar el formulario en blanco ---
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        // Enviamos un evento vacío al formulario para que el usuario lo llene
        model.addAttribute("event", new Event());
        return "event-form";
    }

    // --- 3. Recibir los datos del formulario y guardar ---
    @PostMapping("/new")
    public String createEvent(@ModelAttribute Event event) {
        // @ModelAttribute captura lo que el usuario escribió en el HTML y lo vuelve un objeto Event
        try {
            eventService.createEvent(event);

            // Patrón Post-Redirect-Get: Si guarda bien, recarga la página enviándote a la tabla
            return "redirect:/admin/events";
        } catch (IllegalArgumentException e) {
            // Si hay un error (ej. nombre vacío), te devuelve al formulario
            return "redirect:/admin/events/new?error";
        }
    }
}