package com.backendLogin.backendLogin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backendLogin.backendLogin.service.TicketmasterService;

@RestController
public class TicketmasterController {

    private final TicketmasterService ticketmasterService;

    @Autowired
    public TicketmasterController(TicketmasterService ticketmasterService) {
        this.ticketmasterService = ticketmasterService;
    }

    @GetMapping("/events")
    public String getEvents(@RequestParam String city) {
        ticketmasterService.getEvents(city);
        return "Eventos obtenidos de Ticketmaster. Revisa la consola para ver los resultados.";
    }
}
