package io.myzoe.system_design.uber;

import io.myzoe.system_design.SpatialObject;

import java.util.Objects;

public class Driver implements SpatialObject {
    private final long driverId;
    private DriverStatus status;
    private double rating;
    private String car;
    private String firstname;
    private String lastname;
    private double longitude;
    private double latitude;

    public Driver(long driverId, String car, String firstname, String lastname) {
        this.driverId = driverId;
        this.status = DriverStatus.UNAVAILABLE;
        this.rating = 0;
        this.car = car;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public long getDriverId() {
        return driverId;
    }

    public DriverStatus getStatus() {
        return status;
    }

    public void setStatus(DriverStatus status) {
        this.status = status;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    @Override
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public boolean intersect(double x0, double y0, double x1, double y1) {
        return x0 <= longitude && longitude < x1 && y0 <= latitude && latitude < y1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return driverId == driver.driverId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(driverId);
    }
}
