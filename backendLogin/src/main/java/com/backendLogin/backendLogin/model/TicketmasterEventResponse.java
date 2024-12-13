
package com.backendLogin.backendLogin.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

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

        @JsonProperty("events")
        private List<Event> events;

        // Getters and Setters
        public List<Event> getEvents() {
            return events;
        }

        public void setEvents(List<Event> events) {
            this.events = events;
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

            @JsonProperty("promoters")
            private List<Promoter> promoters;

            @JsonProperty("priceRanges")
            private List<PriceRange> priceRanges;

            @JsonProperty("seatmap")
            private Seatmap seatmap;

            @JsonProperty("_links")
            private Links _links;

            @JsonProperty("venues")
            private List<Venue> venues;

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

            public List<Venue> getVenues() {
                return venues;
            }

            public void setVenues(List<Venue> venues) {
                this.venues = venues;
            }

            public static class Venue {
                @JsonProperty("name")
                private String name;

                @JsonProperty("city")
                private String city;

                @JsonProperty("state")
                private String state;

                @JsonProperty("country")
                private String country;

                @JsonProperty("location")
                private Location location;

                // Getters and Setters
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

                public Location getLocation() {
                    return location;
                }

                public void setLocation(Location location) {
                    this.location = location;
                }

                public static class Location {
                    @JsonProperty("latitude")
                    private double latitude;

                    @JsonProperty("longitude")
                    private double longitude;

                    // Getters and Setters
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
                    @JsonProperty("startDateTime")
                    private String startDateTime;

                    @JsonProperty("startTBD")
                    private boolean startTBD;

                    @JsonProperty("startTBA")
                    private boolean startTBA;

                    @JsonProperty("endDateTime")
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
                @JsonProperty("start")
                private Start start;

                @JsonProperty("timezone")
                private String timezone;

                @JsonProperty("status")
                private Status status;

                @JsonProperty("spanMultipleDays")
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
                    @JsonProperty("localDate")
                    private String localDate;

                    @JsonProperty("localTime")
                    private String localTime;

                    @JsonProperty("dateTime")
                    private String dateTime;

                    @JsonProperty("dateTBD")
                    private boolean dateTBD;

                    @JsonProperty("dateTBA")
                    private boolean dateTBA;

                    @JsonProperty("timeTBA")
                    private boolean timeTBA;

                    @JsonProperty("noSpecificTime")
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
                    @JsonProperty("code")
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
                    @JsonProperty("id")
                    private String id;

                    @JsonProperty("name")
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
                    @JsonProperty("id")
                    private String id;

                    @JsonProperty("name")
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
                    @JsonProperty("id")
                    private String id;

                    @JsonProperty("name")
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
                @JsonProperty("id")
                private String id;

                @JsonProperty("name")
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
                @JsonProperty("type")
                private String type;

                @JsonProperty("min")
                private double min;

                @JsonProperty("max")
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
                @JsonProperty("staticUrl")
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
                @JsonProperty("self")
                private Link self;

                // Getters and Setters
                public Link getSelf() {
                    return self;
                }

                public void setSelf(Link self) {
                    this.self = self;
                }

                public static class Link {
                    @JsonProperty("href")
                    private String href;

                    // Getters and Setters
                    public String getHref() {
                        return href;
                    }

                    public void setHref(String href) {
                    }
                }
            }
        }
    }
}
