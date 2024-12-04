package com.backendLogin.backendLogin.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class TicketmasterEventResponse {

    @JsonProperty("_embedded")
    private Embedded embedded;

    public Embedded getEmbedded() {
        return embedded;
    }

    public void setEmbedded(Embedded embedded) {
        this.embedded = embedded;
    }

    // Clase interna para mapear el campo "_embedded"
    public static class Embedded {
        @JsonProperty("events")  // Mapea la propiedad "events" del JSON
        private List<Event> events;

        public List<Event> getEvents() {
            return events;
        }

        public void setEvents(List<Event> events) {
            this.events = events;
        }
    }

    // Clase interna para mapear cada "event"
    public static class Event {
        private String name;
        private String type;
        private String id;
        private String url;
        private String locale;
        
        @JsonProperty("images")  // Mapea el campo "images" si está presente
        private List<Image> images;

        // Getters y Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getLocale() {
            return locale;
        }

        public void setLocale(String locale) {
            this.locale = locale;
        }

        public List<Image> getImages() {
            return images;
        }

        public void setImages(List<Image> images) {
            this.images = images;
        }
    }

    // Clase para mapear las imágenes del evento
    public static class Image {
        private String ratio;
        private String url;
        private int width;
        private int height;

        // Getters y Setters
        public String getRatio() {
            return ratio;
        }

        public void setRatio(String ratio) {
            this.ratio = ratio;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }
}

