package com.backendLogin.backendLogin.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.backendLogin.backendLogin.model.TicketmasterEventResponse;

import org.springframework.http.ResponseEntity;


@Service
public class TicketmasterService {

    @Value("${ticketmaster.api.url}")
    private String apiUrl;

    @Value("${ticketmaster.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    // Constructor para inyectar RestTemplate
    public TicketmasterService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void getEvents(String city) {
        // Construcción de la URL con los parámetros de la consulta
        String url = UriComponentsBuilder.fromHttpUrl(apiUrl + "/discovery/v2/events.json")
                .queryParam("apikey", apiKey)
                .queryParam("city", city)  // Puedes agregar otros parámetros como fechas, categorías, etc.
                .toUriString();

        // Realizar la solicitud GET
        try {
            ResponseEntity<TicketmasterEventResponse> response = restTemplate.getForEntity(url, TicketmasterEventResponse.class);
            
            // Procesar la respuesta (en este caso, solo imprimimos el nombre de los eventos)
            TicketmasterEventResponse eventResponse = response.getBody();
            if (eventResponse != null && eventResponse.getEmbedded() != null) {
                eventResponse.getEmbedded().getEvents().forEach(event -> {
                    System.out.println("Evento: " + event.getName() + " ID: " + event.getId());
                });
            } else {
                System.out.println("No se encontraron eventos.");
            }
        } catch (Exception e) {
            // Manejo de errores
            System.err.println("Error al realizar la solicitud: " + e.getMessage());
        }
    }
}
