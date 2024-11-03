package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

import bg.sofia.uni.fmi.mjt.vehiclerent.driver.Driver;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.VehicleAlreadyRentedException;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.VehicleNotRentedException;

import java.time.Duration;
import java.time.LocalDateTime;

public sealed abstract class Vehicle permits MotorVehicle, Bicycle {
    protected static final int DAYS_IN_A_WEEK = 7;
    protected static final int HOURS_IN_A_DAY = 24;

    protected String id;
    protected String model;
    protected double pricePerDay;
    protected double pricePerHour;
    protected boolean isRented;
    protected Driver renter;
    protected LocalDateTime startRentTime;

    public Vehicle(String id, String model, double pricePerDay, double pricePerHour) {
        this.id = id;
        this.model = model;
        this.pricePerHour = pricePerHour;
        this.pricePerDay = pricePerDay;
        this.isRented = false;
    }

    /**
     * Simulates rental of the vehicle. The vehicle now is considered rented by the provided driver and the start of the rental is the provided date.
     * @param driver the driver that wants to rent the vehicle.
     * @param startRentTime the start time of the rent
     * @throws VehicleAlreadyRentedException in case the vehicle is already rented by someone else or by the same driver.
     */
    public void rent(Driver driver, LocalDateTime startRentTime) {
        if(renter != null && renter.equals(driver)) {
            throw new VehicleAlreadyRentedException("Vehicle is already rented by the same driver");
        }

        this.isRented = true;
        this.renter = driver;
        this.startRentTime = startRentTime;
    }

    /**
     * Simulates end of rental for the vehicle - it is no longer rented by a driver.
     * @param rentalEnd time of end of rental
     * @throws IllegalArgumentException in case @rentalEnd is null
     * @throws InvalidRentingPeriodException in case the rentalEnd is before the currently noted start date of rental or
     * in case the Vehicle does not allow the passed period for rental, e.g. Caravans must be rented for at least a day
     * and the driver tries to return them after an hour.
     */
    public void returnBack(LocalDateTime rentalEnd) throws InvalidRentingPeriodException {
        if(startRentTime.isAfter(rentalEnd)) {
            throw new InvalidRentingPeriodException("Ending date of rental cannot be before the starting date.");
        }

        Duration rentalDuration = Duration.between(startRentTime, rentalEnd);
        long hours = rentalDuration.toHours();

        if (hours < 1) {
            throw new InvalidRentingPeriodException("Vehicles cannot be rented for less than an honor total.");
        }

        this.isRented = false;
        this.renter = null;
        this.startRentTime = null;
    }

    /**
     * Used to calculate potential rental price without the vehicle to be rented.
     * The calculation is based on the type of the Vehicle (Car/Caravan/Bicycle).
     *
     * @param startOfRent the beginning of the rental
     * @param endOfRent the end of the rental
     * @return potential price for rent
     * @throws InvalidRentingPeriodException in case the vehicle cannot be rented for that period of time or
     * the period is not valid (end date is before start date)
     */
    public abstract double calculateRentalPrice(LocalDateTime startOfRent, LocalDateTime endOfRent) throws InvalidRentingPeriodException;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }

    public Driver getRenter() {
        return renter;
    }

    public void setRenter(Driver renter) {
        this.renter = renter;
    }

    public LocalDateTime getStartRentTime() {
        return startRentTime;
    }

    public void setStartRentTime(LocalDateTime startRentTime) {
        this.startRentTime = startRentTime;
    }
}
