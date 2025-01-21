package bg.sofia.uni.fmi.mjt.newsfeed.builder;

import bg.sofia.uni.fmi.mjt.newsfeed.exception.NoKeywordException;

import java.util.List;

public class NewsArticleRequest {

    private static final int MAX_RESPONSE_SIZE = 100;

    private List<String> q;
    private String category;
    private String country;
    private String pageSize;
    private String page;
    private transient String sources;
    private transient String language;

    private NewsArticleRequest(Builder builder) {
        this.q = builder.q;
        this.category = builder.category;
        this.country = builder.country;
        this.pageSize = builder.pageSize;
        this.page = builder.page;
        this.sources = builder.sources;
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

    public String getPageSize() {
        return pageSize;
    }

    public String getPage() {
        return page;
    }

    public String getSources() {
        return sources;
    }

    public String getLanguage() {
        return language;
    }

    public static Builder builder(List<String> q) {
        return new Builder(q);
    }

    public static class Builder {

        private List<String> q;
        private String category;
        private String country;
        private String pageSize;
        private String page;
        private String sources;
        private String language;

        public Builder(List<String> q) {
            if (q == null || q.isEmpty()) {
                throw new NoKeywordException("Keywords must not be null or empty");
            }

            this.q = q;
        }

        public Builder q(List<String> q) {
            if (q == null || q.isEmpty()) {
                throw new NoKeywordException("Keywords must not be null or empty");
            }

            this.q = q;
            return this;
        }

        public Builder category(String category) {
            this.category = category;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder pageSize(int pageSize) {
            if (pageSize <= 0 || pageSize > MAX_RESPONSE_SIZE) {
                throw new IllegalArgumentException("Page size should be greater than 0");
            }

            this.pageSize = String.valueOf(pageSize);
            return this;
        }

        public Builder page(int page) {
            if (page <= 0) {
                throw new IllegalArgumentException("Page should be greater than 0");
            }

            this.page = String.valueOf(page);
            return this;
        }

        public Builder sources(String sources) {
            this.sources = sources;
            return this;
        }

        public Builder language(String language) {
            this.language = language;
            return this;
        }

        public NewsArticleRequest build() {
            return new NewsArticleRequest(this);
        }
    }
}