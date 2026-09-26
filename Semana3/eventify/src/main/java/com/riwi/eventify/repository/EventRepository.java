package com.riwi.eventify.repository;

import com.riwi.eventify.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    // Consulta derivada: Spring construye el SQL automáticamente para buscar por nombre
    // ignorando mayúsculas/minúsculas y aplicando la paginación requerida.
    Page<Event> findByNameContainingIgnoreCase(String name, Pageable pageable);
}