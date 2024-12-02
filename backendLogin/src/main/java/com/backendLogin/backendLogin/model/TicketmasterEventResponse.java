package com.backendLogin.backendLogin.model;

import java.util.List;

public class TicketmasterEventResponse {

    private Embedded _embedded;

    public Embedded getEmbedded() {
        return _embedded;
    }

    public void setEmbedded(Embedded embedded) {
        this._embedded = embedded;
    }

    public static class Embedded {
        private List<Event> events;

        public List<Event> getEvents() {
            return events;
        }

        public void setEvents(List<Event> events) {
            this.events = events;
        }

        public static class Event {
            private String name;
            private String id;

            // Getters and setters
            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }
    }
}