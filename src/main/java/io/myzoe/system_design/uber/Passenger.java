package io.myzoe.system_design.uber;

import io.myzoe.system_design.SpatialObject;

import java.util.Objects;

public class Passenger implements SpatialObject {
    private final long passengerId;
    private double rating;
    private String firstname;
    private String lastname;
    private double longitude;
    private double latitude;


    public Passenger(long passengerId, String firstname, String lastname) {
        this.passengerId = passengerId;
        this.rating = 0;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public long getPassengerId() {
        return passengerId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
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
        Passenger passenger = (Passenger) o;
        return passengerId == passenger.passengerId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(passengerId);
    }
}
