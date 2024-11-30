package bg.sofia.uni.fmi.mjt.glovo;

import bg.sofia.uni.fmi.mjt.glovo.controlcenter.ControlCenter;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntity;
import bg.sofia.uni.fmi.mjt.glovo.delivery.Delivery;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryInfo;
import bg.sofia.uni.fmi.mjt.glovo.delivery.ShippingMethod;
import bg.sofia.uni.fmi.mjt.glovo.exception.NoAvailableDeliveryGuyException;

public class Glovo implements GlovoApi {
    private ControlCenter controlCenter;

    public Glovo(char[][] mapLayout) {
        controlCenter = new ControlCenter(mapLayout);
    }

    @Override
    public Delivery getCheapestDelivery(MapEntity client, MapEntity restaurant, String foodItem)
        throws NoAvailableDeliveryGuyException {
        DeliveryInfo deliveryInfo = controlCenter.findOptimalDeliveryGuy(
            restaurant.location(),
            client.location(),
            -1,
            -1,
            ShippingMethod.CHEAPEST
        );

        if (deliveryInfo == null) {
            throw new NoAvailableDeliveryGuyException("No available delivery guy");
        }

        return new Delivery(
            client.location(),
            restaurant.location(),
            deliveryInfo.deliveryGuyLocation(),
            foodItem,
            deliveryInfo.price(),
            deliveryInfo.estimatedTime()
        );
    }

    @Override
    public Delivery getFastestDelivery(MapEntity client, MapEntity restaurant, String foodItem)
        throws NoAvailableDeliveryGuyException {
        DeliveryInfo deliveryInfo = controlCenter.findOptimalDeliveryGuy(
            restaurant.location(),
            client.location(),
            -1,
            -1,
            ShippingMethod.FASTEST
        );

        if (deliveryInfo == null) {
            throw new NoAvailableDeliveryGuyException("No available delivery guy");
        }

        return new Delivery(
            client.location(),
            restaurant.location(),
            deliveryInfo.deliveryGuyLocation(),
            foodItem,
            deliveryInfo.price(),
            deliveryInfo.estimatedTime()
        );
    }

    @Override
    public Delivery getFastestDeliveryUnderPrice(MapEntity client, MapEntity restaurant, String foodItem,
                                                 double maxPrice) throws NoAvailableDeliveryGuyException {
        DeliveryInfo deliveryInfo = controlCenter.findOptimalDeliveryGuy(
            restaurant.location(),
            client.location(),
            maxPrice,
            -1,
            ShippingMethod.FASTEST
        );

        if (deliveryInfo == null) {
            throw new NoAvailableDeliveryGuyException("No available delivery guy");
        }

        return new Delivery(
            client.location(),
            restaurant.location(),
            deliveryInfo.deliveryGuyLocation(),
            foodItem,
            deliveryInfo.price(),
            deliveryInfo.estimatedTime()
        );
    }

    @Override
    public Delivery getCheapestDeliveryWithinTimeLimit(MapEntity client, MapEntity restaurant, String foodItem,
                                                       int maxTime) throws NoAvailableDeliveryGuyException {
        DeliveryInfo deliveryInfo = controlCenter.findOptimalDeliveryGuy(
            restaurant.location(),
            client.location(),
            -1,
            maxTime,
            ShippingMethod.CHEAPEST
        );

        if (deliveryInfo == null) {
            throw new NoAvailableDeliveryGuyException("No available delivery guy");
        }

        return new Delivery(
            client.location(),
            restaurant.location(),
            deliveryInfo.deliveryGuyLocation(),
            foodItem,
            deliveryInfo.price(),
            deliveryInfo.estimatedTime()
        );
    }
}
