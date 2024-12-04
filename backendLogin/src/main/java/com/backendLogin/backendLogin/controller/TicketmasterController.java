package com.backendLogin.backendLogin.controller;

import com.backendLogin.backendLogin.model.TicketmasterEventResponse;
import com.backendLogin.backendLogin.service.TicketmasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class TicketmasterController {

    private final TicketmasterService ticketmasterService;

    @Autowired
    public TicketmasterController(TicketmasterService ticketmasterService) {
        this.ticketmasterService = ticketmasterService;
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<TicketmasterEventResponse.Event>> getEvents(@PathVariable String city) {
        List<TicketmasterEventResponse.Event> events = ticketmasterService.getEvents(city);

        if (events.isEmpty()) {
            return ResponseEntity.noContent().build();  // 204 No Content si no se encuentran eventos
        }

        return ResponseEntity.ok(events);  // 200 OK con los eventos encontrados
    }
}
