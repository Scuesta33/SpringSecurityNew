package com.backendLogin.backendLogin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.backendLogin.backendLogin.model.TicketmasterEventResponse;

@Service
public class TicketmasterService {

    @Value("${ticketmaster.api.url}")
    private String apiUrl;

    @Value("${ticketmaster.api.key}")
    private String apiKey;

    @Autowired
    private final RestTemplate restTemplate;

    public TicketmasterService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<TicketmasterEventResponse.Embedded.Event> getEvents(String city) {
        // Eliminar espacios en blanco y saltos de línea adicionales
        city = city.trim(); // trim() elimina espacios al principio y al final

        // Construcción de la URL con los parámetros de la consulta
        String url = UriComponentsBuilder.fromHttpUrl(apiUrl + "/discovery/v2/events.json")
                .queryParam("apikey", apiKey)
                .queryParam("city", city)  // Ciudad sin saltos de línea ni espacios
                .toUriString();

        try {
            // Realizamos la solicitud GET y obtenemos la respuesta directamente como String
            String jsonResponse = restTemplate.getForObject(url, String.class);

            // Imprimir la respuesta JSON completa para depuración
            System.out.println("Respuesta completa de la API: " + jsonResponse);

            // Deserializar la respuesta a TicketmasterEventResponse
            TicketmasterEventResponse response = restTemplate.getForObject(url, TicketmasterEventResponse.class);

            // Si la respuesta contiene eventos, los devolvemos
            if (response != null && response.getEmbedded() != null && response.getEmbedded().getEvents() != null) {
                return response.getEmbedded().getEvents();  // Accedemos correctamente a los eventos dentro de _embedded
            } else {
                // Si no se encontraron eventos, devolver una lista vacía
                return List.of();
            }
        } catch (Exception e) {
            // Manejo de errores
            System.err.println("Error al realizar la solicitud: " + e.getMessage());
            e.printStackTrace(); // Imprimir el stack trace para obtener más detalles del error
            return List.of(); // En caso de error, devolver una lista vacía
        }
    }


}