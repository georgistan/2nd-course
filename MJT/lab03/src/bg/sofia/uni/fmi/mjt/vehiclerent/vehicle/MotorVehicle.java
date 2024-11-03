package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

public sealed abstract class MotorVehicle extends Vehicle permits Car, Caravan {
    protected static final int PRICE_PER_SEAT = 5;
    protected static final int HOURS_IN_A_WEEK = 168;
    protected double pricePerWeek;
    protected FuelType fuelType;
    protected int numberOfSeats;

    public MotorVehicle(String id, String model, FuelType fuelType, int numberOfSeats, double pricePerWeek, double pricePerDay, double pricePerHour) {
        super(id, model, pricePerDay, pricePerHour);
        this.pricePerWeek = pricePerWeek;
        this.fuelType = fuelType;
        this.numberOfSeats = numberOfSeats;
    }
}
