
package com.backendLogin.backendLogin.service;

import com.backendLogin.backendLogin.model.TicketmasterEventResponse;
import com.backendLogin.backendLogin.model.TicketmasterEventResponse.Embedded;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TicketmasterService {

    private static final String URL = "https://app.ticketmaster.com/discovery/v2/events.json";
    private static final String API_KEY = "pEcMNJSJ3LHWyJdLfjhfGyy4q7bO0XlD";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(TicketmasterService.class);

    @Autowired
    public TicketmasterService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public List<TicketmasterEventResponse.Embedded.Event> getEvents(String city) {
        String requestUrl = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("apikey", API_KEY)
                .queryParam("city", city)
                .toUriString();

        ResponseEntity<String> response = restTemplate.exchange(
                requestUrl, HttpMethod.GET, null, String.class);

        logger.debug("URL generada para la consulta: {}", requestUrl);
        logger.debug("Respuesta cruda de Ticketmaster: {}", response.getBody());

        try {
            TicketmasterEventResponse ticketmasterResponse = objectMapper.readValue(response.getBody(), TicketmasterEventResponse.class);

            if (ticketmasterResponse != null && ticketmasterResponse.get_embedded() != null
                    && ticketmasterResponse.get_embedded().getEvents() != null) {
                List<TicketmasterEventResponse.Embedded.Event> events = ticketmasterResponse.get_embedded().getEvents();

                for (TicketmasterEventResponse.Embedded.Event event : events) {
                    if (event.getImages() != null && !event.getImages().isEmpty()) {
                        event.setImages(Collections.singletonList(event.getImages().get(0)));
                    }

                    if (event.get_embedded() != null && event.get_embedded().getVenues() != null && !event.get_embedded().getVenues().isEmpty()) {
                        Embedded.Venue venue = event.get_embedded().getVenues().get(0);
                        if (venue.getLocation() != null) {
                            Double latitude = venue.getLocation().getLatitude();
                            Double longitude = venue.getLocation().getLongitude();

                            if (latitude != null && longitude != null) {
                                logger.debug("Evento: {}, Latitud: {}, Longitud: {}", event.getName(), latitude, longitude);
                            }
                        }
                    }
                }

                List<TicketmasterEventResponse.Embedded.Event> uniqueEvents = removeDuplicateEvents(events);

                return uniqueEvents;
            } else {
                logger.warn("No se encontraron eventos para la ciudad: {}", city);
                return Collections.emptyList();
            }
        } catch (JsonProcessingException e) {
            logger.error("Error al procesar la respuesta JSON para la URL: {}", requestUrl, e);
            return Collections.emptyList();
        }
    }

    public List<TicketmasterEventResponse.Embedded.Event> getPopularEvents() {
        String requestUrl = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("apikey", API_KEY)
                .queryParam("sort", "relevance,desc")
                .queryParam("size", 9)
                .queryParam("countryCode", "ES")
                .toUriString();

        ResponseEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.GET, null, String.class);

        logger.debug("URL generada para la consulta: {}", requestUrl);
        logger.debug("Respuesta cruda de Ticketmaster: {}", response.getBody());

        try {
            TicketmasterEventResponse ticketmasterResponse = objectMapper.readValue(response.getBody(), TicketmasterEventResponse.class);

            if (ticketmasterResponse != null && ticketmasterResponse.get_embedded() != null
                    && ticketmasterResponse.get_embedded().getEvents() != null) {
                List<TicketmasterEventResponse.Embedded.Event> events = ticketmasterResponse.get_embedded().getEvents();

                for (TicketmasterEventResponse.Embedded.Event event : events) {
                    if (event.getImages() != null && !event.getImages().isEmpty()) {
                        event.setImages(Collections.singletonList(event.getImages().get(0)));
                    }

                    if (event.get_embedded() != null && event.get_embedded().getVenues() != null && !event.get_embedded().getVenues().isEmpty()) {
                        Embedded.Venue venue = event.get_embedded().getVenues().get(0);
                        if (venue.getLocation() != null) {
                            Double latitude = venue.getLocation().getLatitude();
                            Double longitude = venue.getLocation().getLongitude();

                            if (latitude != null && longitude != null) {
                                logger.debug("Evento: {}, Latitud: {}, Longitud: {}", event.getName(), latitude, longitude);
                            }
                        }
                    }
                }

                List<TicketmasterEventResponse.Embedded.Event> uniqueEvents = removeDuplicateEvents(events);

                return uniqueEvents;
            } else {
                logger.warn("No se encontraron eventos populares.");
                return Collections.emptyList();
            }
        } catch (JsonProcessingException e) {
            logger.error("Error al procesar la respuesta JSON para la URL: {}", requestUrl, e);
            return Collections.emptyList();
        }
    }

    public List<TicketmasterEventResponse.Embedded.Event> searchEventsByKeyword(String keyword) {
        String requestUrl = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("apikey", API_KEY)
                .queryParam("keyword", keyword)
                .queryParam("size", 9)
                .toUriString();

        ResponseEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.GET, null, String.class);

        logger.debug("URL generada para la consulta: {}", requestUrl);
        logger.debug("Respuesta cruda de Ticketmaster: {}", response.getBody());

        try {
            TicketmasterEventResponse ticketmasterResponse = objectMapper.readValue(response.getBody(), TicketmasterEventResponse.class);

            if (ticketmasterResponse != null && ticketmasterResponse.get_embedded() != null
                    && ticketmasterResponse.get_embedded().getEvents() != null) {
                List<TicketmasterEventResponse.Embedded.Event> events = ticketmasterResponse.get_embedded().getEvents();

                for (TicketmasterEventResponse.Embedded.Event event : events) {
                    if (event.getImages() != null && !event.getImages().isEmpty()) {
                        event.setImages(Collections.singletonList(event.getImages().get(0)));
                    }

                    if (event.get_embedded() != null && event.get_embedded().getVenues() != null && !event.get_embedded().getVenues().isEmpty()) {
                        Embedded.Venue venue = event.get_embedded().getVenues().get(0);
                        if (venue.getLocation() != null) {
                            Double latitude = venue.getLocation().getLatitude();
                            Double longitude = venue.getLocation().getLongitude();

                            if (latitude != null && longitude != null) {
                                logger.debug("Evento: {}, Latitud: {}, Longitud: {}", event.getName(), latitude, longitude);
                            }
                        }
                    }
                }

                List<TicketmasterEventResponse.Embedded.Event> uniqueEvents = removeDuplicateEvents(events);

                return uniqueEvents;
            } else {
                logger.warn("No se encontraron eventos para la palabra clave: {}", keyword);
                return Collections.emptyList();
            }
        } catch (JsonProcessingException e) {
            logger.error("Error al procesar la respuesta JSON para la URL: {}", requestUrl, e);
            return Collections.emptyList();
        }
    }

    private List<TicketmasterEventResponse.Embedded.Event> removeDuplicateEvents(List<TicketmasterEventResponse.Embedded.Event> events) {
        Set<String> eventNames = new HashSet<>();
        return events.stream()
                .filter(event -> eventNames.add(event.getName()))
                .collect(Collectors.toList());
    }

    public TicketmasterEventResponse.Embedded.Event getEventDetails(String id) {
        // Aquí construimos la URL correctamente, asegurándonos de que la base de URL es correcta
        String requestUrl = UriComponentsBuilder.fromHttpUrl("https://app.ticketmaster.com/discovery/v2/events/" + id + ".json")
                .queryParam("apikey", API_KEY)
                .toUriString();

        logger.debug("URL generada para el evento: {}", requestUrl);

        try {
            // Realizamos la solicitud GET a la API de Ticketmaster
            ResponseEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.GET, null, String.class);
            logger.debug("Respuesta cruda de Ticketmaster: {}", response.getBody());

            // Procesamos la respuesta JSON
            TicketmasterEventResponse ticketmasterResponse = objectMapper.readValue(response.getBody(), TicketmasterEventResponse.class);

            // Validamos que se haya encontrado el evento en la respuesta
            if (ticketmasterResponse != null && ticketmasterResponse.get_embedded() != null
                    && ticketmasterResponse.get_embedded().getEvents() != null && !ticketmasterResponse.get_embedded().getEvents().isEmpty()) {
                return ticketmasterResponse.get_embedded().getEvents().get(0);
            } else {
                logger.warn("No se encontró el evento con el ID: {}", id);
                return null;
            }
        } catch (HttpClientErrorException.NotFound e) {
            logger.error("Evento no encontrado para el ID: {}", id, e);
            return null;
        } catch (JsonProcessingException e) {
            logger.error("Error al procesar la respuesta JSON para el evento con ID: {}", id, e);
            return null;
        }
    }


}