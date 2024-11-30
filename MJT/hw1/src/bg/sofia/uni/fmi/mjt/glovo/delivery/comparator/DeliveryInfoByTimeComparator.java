package bg.sofia.uni.fmi.mjt.glovo.delivery.comparator;

import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryInfo;

import java.util.Comparator;

public class DeliveryInfoByTimeComparator implements Comparator<DeliveryInfo> {
    @Override
    public int compare(DeliveryInfo o1, DeliveryInfo o2) {
        return Integer.compare(o1.estimatedTime(), o2.estimatedTime());
    }
}
