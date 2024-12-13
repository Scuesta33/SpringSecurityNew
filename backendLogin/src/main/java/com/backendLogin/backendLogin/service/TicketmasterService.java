
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

            if (ticketmasterResponse != null && ticketmasterResponse.getEmbedded() != null
                    && ticketmasterResponse.getEmbedded().getEvents() != null) {
                List<TicketmasterEventResponse.Embedded.Event> events = ticketmasterResponse.getEmbedded().getEvents();

                for (TicketmasterEventResponse.Embedded.Event event : events) {
                    if (event.getImages() != null && !event.getImages().isEmpty()) {
                        event.setImages(Collections.singletonList(event.getImages().get(0)));
                    }

                    if (event.getVenues() != null && !event.getVenues().isEmpty()) {
                        TicketmasterEventResponse.Embedded.Event.Venue venue = event.getVenues().get(0);
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

            if (ticketmasterResponse != null && ticketmasterResponse.getEmbedded() != null
                    && ticketmasterResponse.getEmbedded().getEvents() != null) {
                List<TicketmasterEventResponse.Embedded.Event> events = ticketmasterResponse.getEmbedded().getEvents();

                for (TicketmasterEventResponse.Embedded.Event event : events) {
                    if (event.getImages() != null && !event.getImages().isEmpty()) {
                        event.setImages(Collections.singletonList(event.getImages().get(0)));
                    }

                    if (event.getVenues() != null && !event.getVenues().isEmpty()) {
                        TicketmasterEventResponse.Embedded.Event.Venue venue = event.getVenues().get(0);
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

            if (ticketmasterResponse != null && ticketmasterResponse.getEmbedded() != null
                    && ticketmasterResponse.getEmbedded().getEvents() != null) {
                List<TicketmasterEventResponse.Embedded.Event> events = ticketmasterResponse.getEmbedded().getEvents();

                for (TicketmasterEventResponse.Embedded.Event event : events) {
                    if (event.getImages() != null && !event.getImages().isEmpty()) {
                        event.setImages(Collections.singletonList(event.getImages().get(0)));
                    }

                    if (event.getVenues() != null && !event.getVenues().isEmpty()) {
                        TicketmasterEventResponse.Embedded.Event.Venue venue = event.getVenues().get(0);
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
    String requestUrl = UriComponentsBuilder.fromHttpUrl(URL + "/" + id)
            .queryParam("apikey", API_KEY)
            .toUriString();

    logger.debug("URL generada para el evento: {}", requestUrl);

    ResponseEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.GET, null, String.class);

    logger.debug("Respuesta cruda de Ticketmaster: {}", response.getBody());

    try {
        TicketmasterEventResponse ticketmasterResponse = objectMapper.readValue(response.getBody(), TicketmasterEventResponse.class);

        if (ticketmasterResponse != null && ticketmasterResponse.getEmbedded() != null
                && ticketmasterResponse.getEmbedded().getEvents() != null && !ticketmasterResponse.getEmbedded().getEvents().isEmpty()) {
            return ticketmasterResponse.getEmbedded().getEvents().get(0);
        } else {
            logger.warn("No se encontró el evento con el ID: {}", id);
            return null;
        }
    } catch (JsonProcessingException e) {
        logger.error("Error al procesar la respuesta JSON para el evento con ID: {}", id, e);
        return null;
    }
}


    
}
