package com.riwi.eventify.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

// Lombok @Data genera automaticamente los Getters, Setters, toString y equals/hashCode por ti
@Data
// Genera un constructor que recibe todos los atributos como parametros
@AllArgsConstructor
// Genera un constructor vacio (Spring lo necesita por debajo para funcionar)
@NoArgsConstructor
// Permite crear objetos de forma mas limpia usando el patron Builder
@Builder
public class Event {

    // Identificador unico del evento (ej: un codigo aleatorio)
    private String id;

    // Nombre o titulo principal del evento
    private String name;

    // Fecha en la que ocurrira el evento (usamos LocalDate para manejar anos, meses y dias)
    private LocalDate date;

    // Detalle o descripcion de lo que tratara el evento
    private String description;
}