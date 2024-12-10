package com.backendLogin.backendLogin.service;

import com.backendLogin.backendLogin.model.TicketmasterEventResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Service
public class TicketmasterService {

    private static final String API_KEY = "pEcMNJSJ3LHWyJdLfjhfGyy4q7bO0XlD";
    private static final String URL = "https://app.ticketmaster.com/discovery/v2/events.json";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(TicketmasterService.class);

    @Autowired
    public TicketmasterService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    // Método que obtiene los eventos por ciudad
    public List<TicketmasterEventResponse.Event> getEvents(String city) {
        // Construir la URL con los parámetros correctos
        String requestUrl = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("apikey", API_KEY)
                .queryParam("city", city)
                .toUriString();

        // Hacer la solicitud GET
        ResponseEntity<String> response = restTemplate.exchange(
                requestUrl, HttpMethod.GET, null, String.class); // Obtenemos la respuesta como String

        // Imprimir la URL y la respuesta cruda para depuración
        logger.debug("URL generada para la consulta: {}", requestUrl);
        logger.debug("Respuesta cruda de Ticketmaster: {}", response.getBody());

        // Procesar la respuesta
        try {
            // Deserializar la respuesta cruda JSON a TicketmasterEventResponse
            TicketmasterEventResponse ticketmasterResponse = objectMapper.readValue(response.getBody(), TicketmasterEventResponse.class);

            // Comprobar si la respuesta contiene eventos
            if (ticketmasterResponse != null && ticketmasterResponse.get_embedded() != null 
                    && ticketmasterResponse.get_embedded().getEvents() != null) {
                List<TicketmasterEventResponse.Event> events = ticketmasterResponse.get_embedded().getEvents();

                // Asegurarse de que cada evento tenga solo la primera imagen (si existe)
                for (TicketmasterEventResponse.Event event : events) {
                    if (event.getImages() != null && !event.getImages().isEmpty()) {
                        event.setImages(Collections.singletonList(event.getImages().get(0))); // Solo la primera imagen
                    }
                }

                // Eliminar eventos duplicados basados en el nombre
                List<TicketmasterEventResponse.Event> uniqueEvents = removeDuplicateEvents(events);

                return uniqueEvents;
            } else {
                logger.warn("No se encontraron eventos para la ciudad: {}", city);
                return Collections.emptyList(); // Retorna una lista vacía si no hay eventos
            }
        } catch (JsonProcessingException e) {
            logger.error("Error al procesar la respuesta JSON", e);
            return Collections.emptyList();
        }
    }

    // Método para obtener eventos populares en España
    public List<TicketmasterEventResponse.Event> getPopularEvents() {
        // Construir la URL con los parámetros correctos, incluyendo parámetro de popularidad
        String requestUrl = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("apikey", API_KEY)  // Correcto: "apikey" debe ir en minúsculas
                .queryParam("sort", "relevance,desc")  // Ordenar por relevancia (también puede implicar popularidad)
                .queryParam("size", 9)  // Limitar a 9 eventos más populares
                .queryParam("countryCode", "ES")  // Filtrar por España
                .toUriString();

        // Hacer la solicitud GET
        ResponseEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.GET, null, String.class);

        // Imprimir la URL y la respuesta cruda para depuración
        logger.debug("URL generada para la consulta: {}", requestUrl);
        logger.debug("Respuesta cruda de Ticketmaster: {}", response.getBody());

        try {
            // Deserializar la respuesta cruda JSON a TicketmasterEventResponse
            TicketmasterEventResponse ticketmasterResponse = objectMapper.readValue(response.getBody(), TicketmasterEventResponse.class);

            // Comprobar si la respuesta contiene eventos
            if (ticketmasterResponse != null && ticketmasterResponse.get_embedded() != null
                    && ticketmasterResponse.get_embedded().getEvents() != null) {
                List<TicketmasterEventResponse.Event> events = ticketmasterResponse.get_embedded().getEvents();

                // Asegurarse de que cada evento tenga solo la primera imagen (si existe)
                for (TicketmasterEventResponse.Event event : events) {
                    if (event.getImages() != null && !event.getImages().isEmpty()) {
                        event.setImages(Collections.singletonList(event.getImages().get(0))); // Solo la primera imagen
                    }
                }

                // Eliminar eventos duplicados basados en el nombre
                List<TicketmasterEventResponse.Event> uniqueEvents = removeDuplicateEvents(events);

                return uniqueEvents;  // Retorna los eventos únicos si están presentes
            } else {
                logger.warn("No se encontraron eventos populares.");
                return Collections.emptyList();  // Retorna una lista vacía si no hay eventos populares
            }
        } catch (JsonProcessingException e) {
            logger.error("Error al procesar la respuesta JSON", e);
            return Collections.emptyList();  // Retorna una lista vacía si hay error al procesar la respuesta
        }
    }

    // Método para búsqueda de eventos por palabra clave
    public List<TicketmasterEventResponse.Event> searchEventsByKeyword(String keyword){
        // Construir la URL con los parámetros correctos, incluyendo keyword
        String requestUrl = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("apikey", API_KEY)
                .queryParam("keyword", keyword)
                .queryParam("size", 9)
                .toUriString();

        // Hacer solicitud GET
        ResponseEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.GET, null, String.class);

        // Respuesta cruda de depuración de url
        logger.debug("URL generada para la consulta: {}", requestUrl);
        logger.debug("Respuesta cruda de Ticketmaster: {}", response.getBody());

        // Procesar la respuesta
        try {
            // Deserializar la respuesta cruda JSON a TicketmasterEventResponse
            TicketmasterEventResponse ticketmasterResponse = objectMapper.readValue(response.getBody(), TicketmasterEventResponse.class);

            // Comprobar si la respuesta contiene eventos
            if (ticketmasterResponse != null && ticketmasterResponse.get_embedded() != null 
                    && ticketmasterResponse.get_embedded().getEvents() != null) {
                List<TicketmasterEventResponse.Event> events = ticketmasterResponse.get_embedded().getEvents();

                // Asegurarse de que cada evento tenga solo la primera imagen (si existe)
                for (TicketmasterEventResponse.Event event : events) {
                    if (event.getImages() != null && !event.getImages().isEmpty()) {
                        event.setImages(Collections.singletonList(event.getImages().get(0))); // Solo la primera imagen
                    }
                }

                // Eliminar eventos duplicados basados en el nombre
                List<TicketmasterEventResponse.Event> uniqueEvents = removeDuplicateEvents(events);

                return uniqueEvents;  // Retorna los eventos únicos si están presentes
            } else {
                logger.warn("No se encontraron eventos para la palabra clave: {}", keyword);
                return Collections.emptyList(); // Retorna una lista vacía si no hay eventos
            }
        } catch (JsonProcessingException e) {
            logger.error("Error al procesar la respuesta JSON", e);
            return Collections.emptyList();
        }
    }

    // Método para eliminar duplicados por nombre
    private List<TicketmasterEventResponse.Event> removeDuplicateEvents(List<TicketmasterEventResponse.Event> events) {
        // Utiliza un Set para mantener los eventos únicos basados en el nombre
        Set<String> eventNames = new HashSet<>();
        List<TicketmasterEventResponse.Event> uniqueEvents = events.stream()
                .filter(event -> eventNames.add(event.getName())) // Si el nombre ya existe en el Set, lo ignora
                .collect(Collectors.toList());

        return uniqueEvents;
    }
}
