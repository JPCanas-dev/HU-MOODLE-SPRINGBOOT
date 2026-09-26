package com.riwi.eventify.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Lombok: Genera automáticamente Getters, Setters, y otros métodos útiles por detrás.
@AllArgsConstructor // Lombok: Crea un constructor con todos los atributos (id, name, address, capacity).
@NoArgsConstructor // Lombok: Crea un constructor vacío (Spring lo necesita obligatoriamente).
@Builder // Lombok: Nos permite crear objetos de forma más fácil en las pruebas o en el Seeder.
public class Venue {
    private String id;
    private String name;
    private String address;
    private Integer capacity;
}