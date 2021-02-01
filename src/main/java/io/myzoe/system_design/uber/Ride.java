package io.myzoe.system_design.uber;

import io.myzoe.system_design.SpatialObject;

import java.util.Objects;

public class Ride implements SpatialObject {
    private final long rideId;
    private Driver driver;
    private final Passenger passenger;
    private RideStatus status;
    private double estimatedDistance;
    private double estimatedPrice;

    public Ride(long rideId, Passenger passenger) {
        this.rideId = rideId;
        this.passenger = passenger;
        this.status = RideStatus.INITIATED;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public RideStatus getStatus() {
        return status;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
    }

    public double getEstimatedDistance() {
        return estimatedDistance;
    }

    public void setEstimatedDistance(double estimatedDistance) {
        this.estimatedDistance = estimatedDistance;
    }

    public double getEstimatedPrice() {
        return estimatedPrice;
    }

    public void setEstimatedPrice(double estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    @Override
    public void setLongitude(double longitude) {
        if (driver != null) {
            driver.setLongitude(longitude);
        }
        else {
            passenger.setLongitude(longitude);
        }
    }

    @Override
    public void setLatitude(double latitude) {
        if (driver != null) {
            driver.setLatitude(latitude);
        }
        else {
            passenger.setLatitude(latitude);
        }
    }

    @Override
    public double getLongitude() {
        if (driver != null) {
            return driver.getLongitude();
        }
        else {
            return passenger.getLongitude();
        }
    }

    @Override
    public double getLatitude() {
        if (driver != null) {
            return driver.getLatitude();
        }
        else {
            return passenger.getLatitude();
        }
    }

    @Override
    public boolean intersect(double x0, double y0, double x1, double y1) {
        if (driver != null) {
            return driver.intersect(x0, y0, x1, y1);
        }
        else {
            return passenger.intersect(x0, y0, x1, y1);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ride ride = (Ride) o;
        return rideId == ride.rideId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rideId);
    }
}
