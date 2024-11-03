package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;

import java.time.Duration;
import java.time.LocalDateTime;

public final class Caravan extends MotorVehicle {
    private static final int PRICE_PER_BED = 10;
    private int numberOfBeds;


    public Caravan(String id, String model, FuelType fuelType, int numberOfSeats, int numberOfBeds, double pricePerWeek, double pricePerDay, double pricePerHour) {
        super(id, model, fuelType, numberOfSeats, pricePerWeek, pricePerDay, pricePerHour);
        this.numberOfBeds = numberOfBeds;
    }

    @Override
    public double calculateRentalPrice(LocalDateTime startOfRent, LocalDateTime endOfRent) throws InvalidRentingPeriodException {
        Duration rentalDuration = Duration.between(startOfRent, endOfRent);
        long hours = rentalDuration.toHours();

        if (rentalDuration.toHours() < HOURS_IN_A_DAY) {
            throw new InvalidRentingPeriodException("Caravans must be rented for at least one day.");
        }

        long weeks = hours / HOURS_IN_A_WEEK;
        hours = hours - weeks * HOURS_IN_A_WEEK;

        long days = hours / HOURS_IN_A_DAY;
        hours = hours - days * HOURS_IN_A_DAY;

        double fuelCost = 0;
        double seatsCost = numberOfSeats * PRICE_PER_SEAT;
        double bedsCost = numberOfBeds * PRICE_PER_BED;

        if (weeks > 0) {
            fuelCost = (weeks * DAYS_IN_A_WEEK + days) * fuelType.getTax();

            return (weeks * pricePerWeek) + (days * pricePerDay) + (hours * pricePerHour) + fuelCost + seatsCost + bedsCost + renter.ageGroup().getTax();
        } else if(days > 0) {
            fuelCost = days * fuelType.getTax();

            return (days * pricePerDay) + (hours * pricePerHour) + fuelCost + seatsCost + bedsCost + renter.ageGroup().getTax();
        } else {
            fuelCost = fuelType.getTax();

            return (hours * pricePerHour) + fuelCost + seatsCost + renter.ageGroup().getTax();
        }
    }
}
