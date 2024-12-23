package com.backendLogin.backendLogin.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class TicketmasterEventResponse {

    @JsonProperty("_embedded")
    private Embedded _embedded;

    public Embedded get_embedded() {
        return _embedded;
    }

    public void set_embedded(Embedded _embedded) {
        this._embedded = _embedded;
    }

    public static class Embedded {

        @JsonProperty("events")
        private List<Event> events;

        @JsonProperty("venues")
        private List<Venue> venues;

        public List<Event> getEvents() {
            return events;
        }

        public void setEvents(List<Event> events) {
            this.events = events;
        }

        public List<Venue> getVenues() {
            return venues;
        }

        public void setVenues(List<Venue> venues) {
            this.venues = venues;
        }

        public static class Event {
            @JsonProperty("name")
            private String name;

            @JsonProperty("type")
            private String type;

            @JsonProperty("id")
            private String id;

            @JsonProperty("test")
            private boolean test;

            @JsonProperty("url")
            private String url;

            @JsonProperty("locale")
            private String locale;

            @JsonProperty("images")
            private List<Image> images;

            @JsonProperty("sales")
            private Sales sales;

            @JsonProperty("dates")
            private Dates dates;

            @JsonProperty("classifications")
            private List<Classification> classifications;

            @JsonProperty("promoter")
            private Promoter promoter;

            @JsonProperty("priceRanges")
            private List<PriceRange> priceRanges;

            @JsonProperty("_links")
            private Links _links;

            @JsonProperty("_embedded")
            private Embedded _embedded;

            // Getters and setters
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

            public List<PriceRange> getPriceRanges() {
                return priceRanges;
            }

            public void setPriceRanges(List<PriceRange> priceRanges) {
                this.priceRanges = priceRanges;
            }

            public Links get_links() {
                return _links;
            }

            public void set_links(Links _links) {
                this._links = _links;
            }

            public Embedded get_embedded() {
                return _embedded;
            }

            public void set_embedded(Embedded _embedded) {
                this._embedded = _embedded;
            }

            // Inner classes for Event
            public static class Image {
                @JsonProperty("ratio")
                private String ratio;

                @JsonProperty("url")
                private String url;

                @JsonProperty("width")
                private int width;

                @JsonProperty("height")
                private int height;

                @JsonProperty("fallback")
                private boolean fallback;

                // Getters and setters
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
                @JsonProperty("publicSale")
                private PublicSale publicSale;

                public PublicSale getPublicSale() {
                    return publicSale;
                }

                public void setPublicSale(PublicSale publicSale) {
                    this.publicSale = publicSale;
                }

                public static class PublicSale {
                    @JsonProperty("startDateTime")
                    private String startDateTime;

                    @JsonProperty("endDateTime")
                    private String endDateTime;

                    public String getStartDateTime() {
                        return startDateTime;
                    }

                    public void setStartDateTime(String startDateTime) {
                        this.startDateTime = startDateTime;
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
                @JsonProperty("start")
                private Start start;

                public Start getStart() {
                    return start;
                }

                public void setStart(Start start) {
                    this.start = start;
                }

                public static class Start {
                    @JsonProperty("localDate")
                    private String localDate;

                    @JsonProperty("localTime")
                    private String localTime;

                    @JsonProperty("dateTime")
                    private String dateTime;

                    @JsonProperty("timezone")
                    private String timezone;

                    @JsonProperty("status")
                    private Status status;

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

                    public static class Status {
                        @JsonProperty("code")
                        private String code;

                        public String getCode() {
                            return code;
                        }

                        public void setCode(String code) {
                            this.code = code;
                        }
                    }
                }
            }

            public static class Classification {
                @JsonProperty("primary")
                private boolean primary;

                @JsonProperty("segment")
                private Segment segment;

                @JsonProperty("genre")
                private Genre genre;

                @JsonProperty("subGenre")
                private SubGenre subGenre;

                @JsonProperty("family")
                private boolean family;

                // Getters and setters
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

                // Inner classes for Classification
                public static class Segment {
                    @JsonProperty("id")
                    private String id;

                    @JsonProperty("name")
                    private String name;

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
                    @JsonProperty("id")
                    private String id;

                    @JsonProperty("name")
                    private String name;

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
                    @JsonProperty("id")
                    private String id;

                    @JsonProperty("name")
                    private String name;

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
                @JsonProperty("id")
                private String id;

                @JsonProperty("name")
                private String name;

                // Getters and setters
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
                @JsonProperty("min")
                private double min;

                @JsonProperty("max")
                private double max;

                @JsonProperty("currency")
                private String currency;

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

                public String getCurrency() {
                    return currency;
                }

                public void setCurrency(String currency) {
                    this.currency = currency;
                }
            }

            public static class Links {
                @JsonProperty("self")
                private Link self;

                @JsonProperty("next")
                private Link next;

                public Link getSelf() {
                    return self;
                }

                public void setSelf(Link self) {
                    this.self = self;
                }

                public Link getNext() {
                    return next;
                }

                public void setNext(Link next) {
                    this.next = next;
                }

                public static class Link {
                    @JsonProperty("href")
                    private String href;

                    public String getHref() {
                        return href;
                    }

                    public void setHref(String href) {
                        this.href = href;
                    }
                }
            }
        }

        public static class Venue {
            @JsonProperty("name")
            private String name;

            @JsonProperty("id")
            private String id;

            @JsonProperty("city")
            private City city;

            @JsonProperty("state")
            private State state;

            @JsonProperty("country")
            private Country country;

            @JsonProperty("location")
            private Location location;

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

            public City getCity() {
                return city;
            }

            public void setCity(City city) {
                this.city = city;
            }

            public State getState() {
                return state;
            }

            public void setState(State state) {
                this.state = state;
            }

            public Country getCountry() {
                return country;
            }

            public void setCountry(Country country) {
                this.country = country;
            }

            public Location getLocation() {
                return location;
            }

            public void setLocation(Location location) {
                this.location = location;
            }

            public static class City {
                @JsonProperty("name")
                private String name;

                // Getter and setter
                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class State {
                @JsonProperty("name")
                private String name;

                @JsonProperty("stateCode")
                private String stateCode;

                // Getter and setter
                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getStateCode() {
                    return stateCode;
                }

                public void setStateCode(String stateCode) {
                    this.stateCode = stateCode;
                }
            }

            public static class Country {
                @JsonProperty("name")
                private String name;

                // Getter and setter
                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class Location {
                @JsonProperty("latitude")
                private double latitude;

                @JsonProperty("longitude")
                private double longitude;

                // Getters and setters
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
    }
}