package bg.sofia.uni.fmi.mjt.glovo.delivery;

public enum DeliveryType {
    CAR(5.0, 3),
    BIKE(3.0, 5);

    private final double pricePerKilometer;
    private final int timePerKilometer;

    DeliveryType(double pricePerKilometer, int timePerKilometer) {
        this.pricePerKilometer = pricePerKilometer;
        this.timePerKilometer = timePerKilometer;
    }

    public double getPricePerKilometer() {
        return pricePerKilometer;
    }

    public int getTimePerKilometer() {
        return timePerKilometer;
    }
}
