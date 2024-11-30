package bg.sofia.uni.fmi.mjt.glovo.delivery;

import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.Location;

public class Delivery {
    private Location client;
    private Location restaurant;
    private Location deliveryGuy;
    private String foodItem;
    private double price;
    private int estimatedTime;

    public Delivery(Location client, Location restaurant, Location deliveryGuy,
                    String foodItem, double price, int estimatedTime) {
        this.client = client;
        this.restaurant = restaurant;
        this.deliveryGuy = deliveryGuy;
        this.foodItem = foodItem;
        this.price = price;
        this.estimatedTime = estimatedTime;
    }

    @Override
    public String toString() {
        return "Delivery{" +
            "client=" + client +
            ", restaurant=" + restaurant +
            ", deliveryGuy=" + deliveryGuy +
            ", foodItem='" + foodItem + '\'' +
            ", price=" + price +
            ", estimatedTime=" + estimatedTime +
            '}';
    }

    public Location getClient() {
        return client;
    }

    public Location getRestaurant() {
        return restaurant;
    }

    public Location getDeliveryGuy() {
        return deliveryGuy;
    }

    public String getFoodItem() {
        return foodItem;
    }

    public double getPrice() {
        return price;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }
}
