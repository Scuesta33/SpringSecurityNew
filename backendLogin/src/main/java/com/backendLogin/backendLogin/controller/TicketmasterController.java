package com.backendLogin.backendLogin.controller;

import com.backendLogin.backendLogin.model.TicketmasterEventResponse;
import com.backendLogin.backendLogin.service.TicketmasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

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
    
    @GetMapping("/popular")
    public ResponseEntity<List<TicketmasterEventResponse.Event>> getPopularEvents(){
    	List<TicketmasterEventResponse.Event> events = ticketmasterService.getPopularEvents();
    	
    	if(events.isEmpty()) {
    		return ResponseEntity.noContent().build();
    	}
    	return ResponseEntity.ok(events);
    }
    //nuevo endpoint para buscar eventos por palabra clave
    //http://localhost:8080/api/events/search?keyword=     esta sería la url
    @GetMapping("/search")
    public ResponseEntity<List<TicketmasterEventResponse.Event>> searchEventsByKeyword(@RequestParam String keyword){
    	//llamar al servicio para obtener los eventos por palabra clave
    	List<TicketmasterEventResponse.Event> events = ticketmasterService.searchEventsByKeyword(keyword);
    	
    	//Si hay eventos que los devuelva con un código 200 sino devuelve una respuesta vacia 204
    	if (!events.isEmpty()) {
    		return ResponseEntity.ok(events);
    	} else {
    		return ResponseEntity.noContent().build();
    	}
    	
    }
    
    
}
