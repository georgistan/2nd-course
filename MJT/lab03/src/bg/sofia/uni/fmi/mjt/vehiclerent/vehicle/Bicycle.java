package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;

import java.time.Duration;
import java.time.LocalDateTime;

public final class Bicycle extends Vehicle {
    public Bicycle(String id, String model, double pricePerDay, double pricePerHour) {
        super(id, model, pricePerDay, pricePerHour);
    }

    @Override
    public double calculateRentalPrice(LocalDateTime startOfRent, LocalDateTime endOfRent) throws InvalidRentingPeriodException {
        Duration rentalDuration = Duration.between(startOfRent, endOfRent);
        long hours = rentalDuration.toHours();
        long days = rentalDuration.toDays();

        if (rentalDuration.toDays() >= DAYS_IN_A_WEEK) {
            throw new InvalidRentingPeriodException("Bicycles cannot be rented for more than a week.");
        }

        if (days > 0) {
            return (days * pricePerDay) + ((hours % HOURS_IN_A_DAY) * pricePerHour);
        } else {
            return hours * pricePerHour;
        }
    }
}
