package bg.sofia.uni.fmi.mjt.newsfeed.builder;

import java.util.List;

public class NewsArticleRequest {

    private List<String> q;
    private String category;
    private String country;
    private transient String sources;
    private String pageSize;
    private String page;
    private transient String language;

    private NewsArticleRequest(Builder builder) {
        this.q = builder.q;
        this.category = builder.category;
        this.country = builder.country;
        this.sources = builder.sources;
        this.pageSize = builder.pageSize;
        this.page = builder.page;
        this.language = builder.language;
    }

    @Override
    public String toString() {
        return "NewsArticleRequest [q=" + q +
            ",\ncategory=" + category +
            ",\ncountry=" + country +
            ",\npage=" + page +
            ",\npageSize=" + pageSize;
    }

    public List<String> getQ() {
        return q;
    }

    public String getCategory() {
        return category;
    }

    public String getCountry() {
        return country;
    }

    public String getSources() {
        return sources;
    }

    public String getPageSize() {
        return pageSize;
    }

    public String getPage() {
        return page;
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