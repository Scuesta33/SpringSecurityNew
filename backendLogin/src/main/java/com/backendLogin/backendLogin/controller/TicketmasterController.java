package com.backendLogin.backendLogin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backendLogin.backendLogin.model.TicketmasterEventResponse;
import com.backendLogin.backendLogin.service.TicketmasterService;

@RestController
public class TicketmasterController {

    private final TicketmasterService ticketmasterService;

    @Autowired
    public TicketmasterController(TicketmasterService ticketmasterService) {
        this.ticketmasterService = ticketmasterService;
    }

    @GetMapping("/events")
    public ResponseEntity<?> getEvents(@RequestParam String city) {
        // Llamada al servicio para obtener los eventos de Ticketmaster
        List<TicketmasterEventResponse.Embedded.Event> events = ticketmasterService.getEvents(city);

        // Verifica si la lista de eventos está vacía
        if (events == null || events.isEmpty()) {
            System.out.println("No se encontraron eventos para la ciudad: " + city); // Log para depuración
            return ResponseEntity.status(404).body("No se encontraron eventos para la ciudad: " + city);
        }

        // Devuelve la lista de eventos como respuesta
        return ResponseEntity.ok(events);
    }
}