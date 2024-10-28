package bg.sofia.uni.fmi.mjt.gameplatform.store.item.category;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.AbstractStoreItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Game extends AbstractStoreItem {
    private String genre;

    public Game(String title, BigDecimal price, LocalDateTime releaseDate, String genre) {
        super(title, price, releaseDate);
        this.genre = genre;
    }

    public String getGenre() {
        return this.genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
