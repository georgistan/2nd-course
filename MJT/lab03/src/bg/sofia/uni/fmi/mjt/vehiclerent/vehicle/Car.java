package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;

import java.time.Duration;
import java.time.LocalDateTime;

public final class Car extends MotorVehicle {
    public Car(String id, String model, FuelType fuelType, int numberOfSeats, double pricePerWeek, double pricePerDay, double pricePerHour) {
        super(id, model, fuelType, numberOfSeats, pricePerWeek, pricePerDay, pricePerHour);
    }

    @Override
    public double calculateRentalPrice(LocalDateTime startOfRent, LocalDateTime endOfRent) throws InvalidRentingPeriodException {
        Duration rentalDuration = Duration.between(startOfRent, endOfRent);
        long hours = rentalDuration.toHours();

        long weeks = hours / HOURS_IN_A_WEEK;
        hours = hours - weeks * HOURS_IN_A_WEEK;

        long days = hours / HOURS_IN_A_DAY;
        hours = hours - days * HOURS_IN_A_DAY;

        double fuelCost = 0;
        double seatCost = numberOfSeats * PRICE_PER_SEAT;

        if (weeks > 0) {
            fuelCost = (weeks * DAYS_IN_A_WEEK + days) * fuelType.getTax();

            return (weeks * pricePerWeek) + (days * pricePerDay) + (hours * pricePerHour) + fuelCost + seatCost + renter.ageGroup().getTax();
        } else if (days > 0) {
            fuelCost = days * fuelType.getTax();

            return (days * pricePerDay) + (hours * pricePerHour) + fuelCost + seatCost + renter.ageGroup().getTax();
        } else {
            fuelCost = fuelType.getTax();

            return (hours * pricePerHour) + fuelCost + seatCost + renter.ageGroup().getTax();
        }
    }
}
