package com.backendLogin.backendLogin.model;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TicketmasterEventResponse {

    @JsonProperty("_embedded")
    private Embedded embedded;

    // Getters and Setters
    public Embedded getEmbedded() {
        return embedded;
    }

    public void setEmbedded(Embedded embedded) {
        this.embedded = embedded;
    }

    public static class Embedded {

        private List<Event> events;

        // Getters and Setters
        public List<Event> getEvents() {
            return events;
        }

        public void setEvents(List<Event> events) {
            this.events = events;
        }

        public static class Event {

            private String name;
            private String type;
            private String id;
            private boolean test;
            private String url;
            private String locale;
            private List<Image> images;
            private Sales sales;
            private Dates dates;
            private List<Classification> classifications;
            private Promoter promoter;
            private List<Promoter> promoters;
            private List<PriceRange> priceRanges;
            private Seatmap seatmap;
            private Links _links;
            private Venue venue;
            // Getters and Setters
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

            public boolean isTest() {
                return test;
            }

            public void setTest(boolean test) {
                this.test = test;
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

            public Sales getSales() {
                return sales;
            }

            public void setSales(Sales sales) {
                this.sales = sales;
            }

            public Dates getDates() {
                return dates;
            }

            public void setDates(Dates dates) {
                this.dates = dates;
            }

            public List<Classification> getClassifications() {
                return classifications;
            }

            public void setClassifications(List<Classification> classifications) {
                this.classifications = classifications;
            }

            public Promoter getPromoter() {
                return promoter;
            }

            public void setPromoter(Promoter promoter) {
                this.promoter = promoter;
            }

            public List<Promoter> getPromoters() {
                return promoters;
            }

            public void setPromoters(List<Promoter> promoters) {
                this.promoters = promoters;
            }

            public List<PriceRange> getPriceRanges() {
                return priceRanges;
            }

            public void setPriceRanges(List<PriceRange> priceRanges) {
                this.priceRanges = priceRanges;
            }

            public Seatmap getSeatmap() {
                return seatmap;
            }

            public void setSeatmap(Seatmap seatmap) {
                this.seatmap = seatmap;
            }

            public Links get_links() {
                return _links;
            }

            public void set_links(Links _links) {
                this._links = _links;
            }
         // Agregar el método getVenue()
            public Venue getVenue() {
                return venue;
            }

            public void setVenue(Venue venue) {
                this.venue = venue;
            }
            public static class Venue {
                private String name;
                private String city;
                private String state;
                private String country;
                private Location location; // Agregar el campo location

                // Getters y Setters
                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getCity() {
                    return city;
                }

                public void setCity(String city) {
                    this.city = city;
                }

                public String getState() {
                    return state;
                }

                public void setState(String state) {
                    this.state = state;
                }

                public String getCountry() {
                    return country;
                }

                public void setCountry(String country) {
                    this.country = country;
                }

                // Modificar el método getLocation para que devuelva el objeto Location
                public Location getLocation() {
                    return location;
                }

                public void setLocation(Location location) {
                    this.location = location;
                }

                // Método para obtener la ubicación completa (ciudad, estado, país)
                public String getLocationString() {
                    StringBuilder location = new StringBuilder();
                    if (city != null) {
                        location.append(city);
                    }
                    if (state != null && !state.isEmpty()) {
                        if (location.length() > 0) location.append(", ");
                        location.append(state);
                    }
                    if (country != null && !country.isEmpty()) {
                        if (location.length() > 0) location.append(", ");
                        location.append(country);
                    }
                    return location.toString();
                }

                // Clase interna Location para almacenar latitud y longitud
                public static class Location {
                    private double latitude;
                    private double longitude;

                    // Getters y Setters
                    public double getLatitude() {
                        return latitude;
                    }

                    public void setLatitude(double latitude) {
                        this.latitude = latitude;
                    }

                    public double getLongitude() {
                        return longitude;
                    }

                    public void setLongitude(double longitude) {
                        this.longitude = longitude;
                    }
                }
            }

            public static class Image {
                private String ratio;
                private String url;
                private int width;
                private int height;
                private boolean fallback;

                // Getters and Setters
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

                public boolean isFallback() {
                    return fallback;
                }

                public void setFallback(boolean fallback) {
                    this.fallback = fallback;
                }
            }

            public static class Sales {
                @JsonProperty("public")
                private PublicSale publicSale;

                // Getters and Setters
                public PublicSale getPublicSale() {
                    return publicSale;
                }

                public void setPublicSale(PublicSale publicSale) {
                    this.publicSale = publicSale;
                }

                public static class PublicSale {
                    private String startDateTime;
                    private boolean startTBD;
                    private boolean startTBA;
                    private String endDateTime;

                    // Getters and Setters
                    public String getStartDateTime() {
                        return startDateTime;
                    }

                    public void setStartDateTime(String startDateTime) {
                        this.startDateTime = startDateTime;
                    }

                    public boolean isStartTBD() {
                        return startTBD;
                    }

                    public void setStartTBD(boolean startTBD) {
                        this.startTBD = startTBD;
                    }

                    public boolean isStartTBA() {
                        return startTBA;
                    }

                    public void setStartTBA(boolean startTBA) {
                        this.startTBA = startTBA;
                    }

                    public String getEndDateTime() {
                        return endDateTime;
                    }

                    public void setEndDateTime(String endDateTime) {
                        this.endDateTime = endDateTime;
                    }
                }
            }

            public static class Dates {
                private Start start;
                private String timezone;
                private Status status;
                private boolean spanMultipleDays;

                // Getters and Setters
                public Start getStart() {
                    return start;
                }

                public void setStart(Start start) {
                    this.start = start;
                }

                public String getTimezone() {
                    return timezone;
                }

                public void setTimezone(String timezone) {
                    this.timezone = timezone;
                }

                public Status getStatus() {
                    return status;
                }

                public void setStatus(Status status) {
                    this.status = status;
                }

                public boolean isSpanMultipleDays() {
                    return spanMultipleDays;
                }

                public void setSpanMultipleDays(boolean spanMultipleDays) {
                    this.spanMultipleDays = spanMultipleDays;
                }

                public static class Start {
                    private String localDate;
                    private String localTime;
                    private String dateTime;
                    private boolean dateTBD;
                    private boolean dateTBA;
                    private boolean timeTBA;
                    private boolean noSpecificTime;

                    // Getters and Setters
                    public String getLocalDate() {
                        return localDate;
                    }

                    public void setLocalDate(String localDate) {
                        this.localDate = localDate;
                    }

                    public String getLocalTime() {
                        return localTime;
                    }

                    public void setLocalTime(String localTime) {
                        this.localTime = localTime;
                    }

                    public String getDateTime() {
                        return dateTime;
                    }

                    public void setDateTime(String dateTime) {
                        this.dateTime = dateTime;
                    }

                    public boolean isDateTBD() {
                        return dateTBD;
                    }

                    public void setDateTBD(boolean dateTBD) {
                        this.dateTBD = dateTBD;
                    }

                    public boolean isDateTBA() {
                        return dateTBA;
                    }

                    public void setDateTBA(boolean dateTBA) {
                        this.dateTBA = dateTBA;
                    }

                    public boolean isTimeTBA() {
                        return timeTBA;
                    }

                    public void setTimeTBA(boolean timeTBA) {
                        this.timeTBA = timeTBA;
                    }

                    public boolean isNoSpecificTime() {
                        return noSpecificTime;
                    }

                    public void setNoSpecificTime(boolean noSpecificTime) {
                        this.noSpecificTime = noSpecificTime;
                    }
                }

                public static class Status {
                    private String code;

                    // Getters and Setters
                    public String getCode() {
                        return code;
                    }

                    public void setCode(String code) {
                        this.code = code;
                    }
                }
            }

            public static class Classification {
                private boolean primary;
                private Segment segment;
                private Genre genre;
                private SubGenre subGenre;
                private boolean family;

                // Getters and Setters
                public boolean isPrimary() {
                    return primary;
                }

                public void setPrimary(boolean primary) {
                    this.primary = primary;
                }

                public Segment getSegment() {
                    return segment;
                }

                public void setSegment(Segment segment) {
                    this.segment = segment;
                }

                public Genre getGenre() {
                    return genre;
                }

                public void setGenre(Genre genre) {
                    this.genre = genre;
                }

                public SubGenre getSubGenre() {
                    return subGenre;
                }

                public void setSubGenre(SubGenre subGenre) {
                    this.subGenre = subGenre;
                }

                public boolean isFamily() {
                    return family;
                }

                public void setFamily(boolean family) {
                    this.family = family;
                }

                public static class Segment {
                    private String id;
                    private String name;

                    // Getters and Setters
                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }
                }

                public static class Genre {
                    private String id;
                    private String name;

                    // Getters and Setters
                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }
                }

                public static class SubGenre {
                    private String id;
                    private String name;

                    // Getters and Setters
                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }
                }
            }

            public static class Promoter {
                private String id;
                private String name;

                // Getters and Setters
                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class PriceRange {
                private String type;
                private double min;
                private double max;

                // Getters and Setters
                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public double getMin() {
                    return min;
                }

                public void setMin(double min) {
                    this.min = min;
                }

                public double getMax() {
                    return max;
                }

                public void setMax(double max) {
                    this.max = max;
                }
            }

            public static class Seatmap {
                private String staticUrl;

                // Getters and Setters
                public String getStaticUrl() {
                    return staticUrl;
                }

                public void setStaticUrl(String staticUrl) {
                    this.staticUrl = staticUrl;
                }
            }

            public static class Links {
                private Link self;

                // Getters and Setters
                public Link getSelf() {
                    return self;
                }

                public void setSelf(Link self) {
                    this.self = self;
                }

                public static class Link {
                    private String href;

                    // Getters and Setters
                    public String getHref() {
                        return href;
                    }

                    public void setHref(String href) {
                        this.href = href;
                    }
                }
            }
        }
    }
}