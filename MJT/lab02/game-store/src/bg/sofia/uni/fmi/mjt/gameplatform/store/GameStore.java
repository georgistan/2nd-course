package bg.sofia.uni.fmi.mjt.gameplatform.store;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;
import bg.sofia.uni.fmi.mjt.gameplatform.store.item.filter.ItemFilter;

import java.math.BigDecimal;

public class GameStore implements StoreAPI {
    private static final String VAN40 = "VAN40";
    private static final String _100YO = "100YO";
    private boolean isVan40Used;
    private StoreItem[] availableItems;

    public GameStore(StoreItem[] availableItems) {
        this.availableItems = availableItems;
        isVan40Used = false;
    }

    @Override
    public StoreItem[] findItemByFilters(ItemFilter[] itemFilters) {
        if(itemFilters.length == 0) {
            return availableItems;
        }

        int[] indexes = new int[availableItems.length];
        int counter = 0;

        for (int i = 0; i < availableItems.length; i++) {
            boolean shouldBeAdded = true;

            for (ItemFilter filer : itemFilters) {
                if (!filer.matches(availableItems[i])) {
                    shouldBeAdded = false;
                    break;
                }
            }

            if (shouldBeAdded) {
                indexes[counter] = i;
                counter++;
            }
        }

        StoreItem[] itemsMatchingFilters = new StoreItem[counter];
        for (int i = 0; i < counter; i++) {
            itemsMatchingFilters[i] = availableItems[indexes[i]];
        }

        return itemsMatchingFilters;
    }

    @Override
    public void applyDiscount(String promoCode) {
        if(isVan40Used) {
            return;
        }

        BigDecimal discount;

        switch (promoCode) {
            case VAN40 -> {
                discount = new BigDecimal("0.40");
                isVan40Used = true;
            }
            case _100YO -> {
                discount = new BigDecimal("1.00");
            }
            default -> {
                return;
            }
        }

        for(StoreItem item : availableItems) {
            item.setPrice(
                    item.getPrice().subtract(
                            item.getPrice().multiply(discount)
                    )
            );
        }
    }

    @Override
    public boolean rateItem(StoreItem item, int rating) {
        if(rating < 1 || rating > 5) {
            return false;
        }

        item.rate(rating);

        return true;
    }
}
