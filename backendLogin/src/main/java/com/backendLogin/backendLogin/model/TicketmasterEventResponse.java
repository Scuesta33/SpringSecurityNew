package com.backendLogin.backendLogin.model;

import java.util.List;

public class TicketmasterEventResponse {

    private Embedded _embedded;
    private int _total;
    private int _filtered;

    // Getters y setters
    public Embedded get_embedded() {
        return _embedded;
    }

    public void set_embedded(Embedded _embedded) {
        this._embedded = _embedded;
    }

    public int get_total() {
        return _total;
    }

    public void set_total(int _total) {
        this._total = _total;
    }

    public int get_filtered() {
        return _filtered;
    }

    public void set_filtered(int _filtered) {
        this._filtered = _filtered;
    }

    public static class Embedded {
        private List<Event> events;

        // Getters y setters
        public List<Event> getEvents() {
            return events;
        }

        public void setEvents(List<Event> events) {
            this.events = events;
        }
    }

    public static class Event {
        private String id;
        private String name;
        private String url;
        private String locale;
        private List<Image> images;
        private Sales sales;
        private Dates dates;
        private List<Classification> classifications;
        private PriceRange[] priceRanges;

        // Getters y setters
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

        public PriceRange[] getPriceRanges() {
            return priceRanges;
        }

        public void setPriceRanges(PriceRange[] priceRanges) {
            this.priceRanges = priceRanges;
        }
    }

    public static class Image {
        private String ratio;
        private String url;
        private int width;
        private int height;
        private boolean fallback;

        // Getters y setters
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
        private PublicSale publicSale;

        // Getters y setters
        public PublicSale getPublicSale() {
            return publicSale;
        }

        public void setPublicSale(PublicSale publicSale) {
            this.publicSale = publicSale;
        }

        public static class PublicSale {
            private String startDateTime;
            private String endDateTime;

            // Getters y setters
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
        private Start start;

        // Getters y setters
        public Start getStart() {
            return start;
        }

        public void setStart(Start start) {
            this.start = start;
        }

        public static class Start {
            private String localDate;
            private String localTime;

            // Getters y setters
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
        }
    }

    public static class Classification {
        private boolean primary;
        private Segment segment;
        private Genre genre;

        // Getters y setters
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

        public static class Segment {
            private String name;

            // Getters y setters
            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class Genre {
            private String name;

            // Getters y setters
            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }

    public static class PriceRange {
        private String type;
        private String currency;
        private double min;
        private double max;

        // Getters y setters
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
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
}
