package bg.sofia.uni.fmi.mjt.glovo.delivery.comparator;

import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryInfo;

import java.util.Comparator;

public class DeliveryInfoByPriceComparator implements Comparator<DeliveryInfo> {
    @Override
    public int compare(DeliveryInfo o1, DeliveryInfo o2) {
        return Double.compare(o1.price(), o2.price());
    }
}
