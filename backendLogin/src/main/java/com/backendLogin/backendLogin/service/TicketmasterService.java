package com.backendLogin.backendLogin.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
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

    public void getEvents() {
        // Composición de la URL de la API
        String url = apiUrl + "/discovery/v2/events.json?apikey=" + apiKey;

        // Realizando la solicitud a la API de Ticketmaster
        try {
            // Se realiza la solicitud GET
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            // Imprimir la respuesta en consola
            System.out.println(response.getBody());
        } catch (Exception e) {
            // Manejo de errores en caso de que la solicitud falle
            System.err.println("Error al realizar la solicitud: " + e.getMessage());
        }
    }
}
