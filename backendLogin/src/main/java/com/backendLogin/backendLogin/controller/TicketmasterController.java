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
        try {
            // Llamada al servicio para obtener los eventos de Ticketmaster
            List<TicketmasterEventResponse.Embedded.Event> events = ticketmasterService.getEvents(city);

            // Verifica si la lista de eventos está vacía
            if (events.isEmpty()) {
                // Si no se encuentran eventos, se devuelve una respuesta con código 404
                return ResponseEntity.status(404).body("No se encontraron eventos para la ciudad: " + city);
            }

            // Devuelve la lista de eventos como respuesta si se encuentran eventos
            return ResponseEntity.ok(events);
        } catch (Exception e) {
            // Si ocurre algún error en la llamada al servicio (por ejemplo, problemas de red o con la API externa)
            return ResponseEntity.status(500).body("Hubo un problema al obtener los eventos. Inténtelo de nuevo más tarde.");
        }
    }
}
