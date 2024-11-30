package bg.sofia.uni.fmi.mjt.glovo.controlcenter;

import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.Location;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntity;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntityType;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryInfo;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryType;
import bg.sofia.uni.fmi.mjt.glovo.delivery.ShippingMethod;
import bg.sofia.uni.fmi.mjt.glovo.delivery.comparator.DeliveryInfoByPriceComparator;
import bg.sofia.uni.fmi.mjt.glovo.delivery.comparator.DeliveryInfoByTimeComparator;
import bg.sofia.uni.fmi.mjt.glovo.exception.InvalidLocationException;
import bg.sofia.uni.fmi.mjt.glovo.exception.InvalidOrderException;
import bg.sofia.uni.fmi.mjt.glovo.mapper.MapLayoutMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class ControlCenter implements ControlCenterApi {
    private static final int NO_CONSTRAINT_VALUE = -1;
    private MapEntity[][] mapLayout;

    public ControlCenter(char[][] mapLayout) {
        this.mapLayout = MapLayoutMapper.toMapEntityLayout(mapLayout);
    }

    @Override
    public DeliveryInfo findOptimalDeliveryGuy(Location restaurantLocation, Location clientLocation, double maxPrice,
                                               int maxTime, ShippingMethod shippingMethod) {
        validateInputData(restaurantLocation, clientLocation);
        int restaurantToClientPathSize =
            ShortestPathFinder.findShortestPathLength(mapLayout, restaurantLocation, clientLocation);

        if (restaurantToClientPathSize == 0) {
            return null;
        }

        Map<Location, Integer> deliveryGuysPathsToRestaurant = getDeliveryGuysPathsToRestaurant(restaurantLocation);

        Set<DeliveryInfo> deliveryInfoSet;
        if (shippingMethod == ShippingMethod.CHEAPEST) {
            deliveryInfoSet = new TreeSet<>(new DeliveryInfoByPriceComparator());

            addDeliveryInfo(deliveryInfoSet, deliveryGuysPathsToRestaurant,
                restaurantToClientPathSize, maxPrice, maxTime);
        } else {
            deliveryInfoSet = new TreeSet<>(new DeliveryInfoByTimeComparator());

            addDeliveryInfo(deliveryInfoSet, deliveryGuysPathsToRestaurant,
                restaurantToClientPathSize, maxPrice, maxTime);
        }

        if (deliveryInfoSet.isEmpty()) {
            return null;
        }

        return deliveryInfoSet.iterator().next();
    }

    private void addDeliveryInfo(Set<DeliveryInfo> deliveryInfoSet,
                                 Map<Location, Integer> deliveryGuysPathsToRestaurant,
                                 int restaurantToClientPathSize,
                                 double maxPrice,
                                 int maxTime) {
        for (Map.Entry<Location, Integer> entry : deliveryGuysPathsToRestaurant.entrySet()) {
            int pathLen = restaurantToClientPathSize + entry.getValue();

            if (mapLayout[entry.getKey().x()][entry.getKey().y()].type() == MapEntityType.DELIVERY_GUY_CAR) {
                double price = pathLen * DeliveryType.CAR.getPricePerKilometer();
                int time = pathLen * DeliveryType.CAR.getTimePerKilometer();

                if ((price > maxPrice && maxPrice != NO_CONSTRAINT_VALUE) ||
                    (time > maxTime && maxTime != NO_CONSTRAINT_VALUE)) {
                    continue;
                }

                deliveryInfoSet.add(new DeliveryInfo(entry.getKey(), price, time, DeliveryType.CAR));
            } else if (mapLayout[entry.getKey().x()][entry.getKey().y()].type() == MapEntityType.DELIVERY_GUY_BIKE) {
                double price = pathLen * DeliveryType.BIKE.getPricePerKilometer();
                int time = pathLen * DeliveryType.BIKE.getTimePerKilometer();

                if ((price > maxPrice && maxPrice != NO_CONSTRAINT_VALUE) ||
                    (time > maxTime && maxTime != NO_CONSTRAINT_VALUE)) {
                    continue;
                }

                deliveryInfoSet.add(new DeliveryInfo(entry.getKey(), price, time, DeliveryType.BIKE));
            }
        }
    }

    private void validateInputData(Location restaurantLocation, Location clientLocation) {
        if (restaurantLocation == null || clientLocation == null) {
            throw new InvalidLocationException("Location cannot be null");
        }

        if (restaurantLocation.x() < 0 || restaurantLocation.x() > mapLayout.length ||
            clientLocation.y() < 0 || clientLocation.y() > mapLayout[0].length) {
            throw new InvalidOrderException("Location out of bounds");
        }

        if (mapLayout[restaurantLocation.x()][restaurantLocation.y()].type().getSymbol() !=
            MapEntityType.RESTAURANT.getSymbol()) {
            throw new InvalidOrderException("Invalid restaurant location");
        }

        if (mapLayout[restaurantLocation.x()][restaurantLocation.y()].type().getSymbol() !=
            MapEntityType.CLIENT.getSymbol()) {
            throw new InvalidOrderException("Invalid client location");
        }
    }

    private Map<Location, Integer> getDeliveryGuysPathsToRestaurant(Location restaurantLocation) {
        Map<Location, Integer> deliveryGuysPathsToRestaurant = new HashMap<>();

        for (MapEntity[] row : mapLayout) {
            for (MapEntity current : row) {
                if (current.type() == MapEntityType.DELIVERY_GUY_BIKE ||
                    current.type() == MapEntityType.DELIVERY_GUY_CAR) {

                    deliveryGuysPathsToRestaurant.put(
                        current.location(),
                        ShortestPathFinder.findShortestPathLength(
                            mapLayout,
                            current.location(),
                            restaurantLocation
                        )
                    );

                }
            }
        }

        return deliveryGuysPathsToRestaurant;
    }

    @Override
    public MapEntity[][] getLayout() {
        return mapLayout;
    }
}
