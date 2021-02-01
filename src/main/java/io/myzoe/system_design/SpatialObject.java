package io.myzoe.system_design;

public interface SpatialObject {
    void setLongitude(double longitude);
    void setLatitude(double latitude);
    double getLongitude();
    double getLatitude();
    boolean intersect(double x0, double y0, double x1, double y1);
}
