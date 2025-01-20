package bg.sofia.uni.fmi.mjt.newsfeed.builder;

import java.util.List;

public class NewsArticleRequest {

    private String category;
    private String sources;
    private List<String> q;
    private String pageSize;
    private String page;
    private String country;
    private String language;

    private NewsArticleRequest(Builder builder) {
        this.category = builder.category;
        this.sources = builder.sources;
        this.q = builder.q;
        this.pageSize = builder.pageSize;
        this.page = builder.page;
        this.country = builder.country;
        this.language = builder.language;
    }

    public String getCategory() {
        return category;
    }

    public String getSources() {
        return sources;
    }

    public List<String> getQ() {
        return q;
    }

    public String getPageSize() {
        return pageSize;
    }

    public String getPage() {
        return page;
    }

    public String getCountry() {
        return country;
    }

    public String getLanguage() {
        return language;
    }

    public static Builder builder(List<String> q) {
        return new Builder(q);
    }

    public static class Builder {

        private String category;
        private String sources;
        private List<String> q;
        private String pageSize;
        private String page;
        private String country;
        private String language;

        public Builder(List<String> q) {
            this.q = q;
        }

        public Builder q(List<String> q) {
            this.q = q;
            return this;
        }

        public Builder category(String category) {
            this.category = category;
            return this;
        }

        public Builder sources(String sources) {
            this.sources = sources;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder language(String language) {
            this.language = language;
            return this;
        }

        public Builder pageSize(int pageSize) {
            this.pageSize = String.valueOf(pageSize);
            return this;
        }

        public Builder page(int page) {
            this.page = String.valueOf(page);
            return this;
        }

        public NewsArticleRequest build() {
            return new NewsArticleRequest(this);
        }
    }
}