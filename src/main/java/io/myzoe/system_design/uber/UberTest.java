package io.myzoe.system_design.uber;

import io.myzoe.system_design.QuadTree;
import io.myzoe.system_design.SpatialObject;

import java.util.Arrays;
import java.util.Random;

public class UberTest {
    public static void main(String[] args) {
        int P = 1_000_000;
        int D = 1_000_000;
        Random r = new Random();
        int cap = 4;
        long x0 = 0;
        long y0 = 0;
        long x1 = 3200;
        long y1 = 1300;

        QuadTree qt = new QuadTree(cap, x0, y0, x1, y1);

        Ride[] rides = new Ride[D];
        for (int i = 0; i < D; i++) {
            Driver d = new Driver(2*D+i, "car_"+ 2*D+i, "firstname_" + 2*D+i, "lastname_" + 2*D+i);
            d.setStatus(DriverStatus.STAND_BY);
            d.setLongitude(r.nextDouble() * x1);
            d.setLatitude(r.nextDouble() * y1);

            Passenger p = new Passenger(i+D, "firstname_" + i+D, "lastname_" + i+D);
            p.setLongitude(r.nextDouble() * x1);
            p.setLatitude(r.nextDouble() * y1);
            Ride ride = new Ride(i, p);
            ride.setDriver(d);
            qt.put(ride);
            rides[i] = ride;
        }

        System.out.println(qt.getSize());


        int T = 4;
        int len = D / T;
        long SEC = 5;
        for (int i = 0; i < T; i++) {
            RideJob rj = new RideJob(i * len, (i+1) * len, SEC, qt, Arrays.copyOfRange(rides, i * len, (i+1) * len));

            Thread threadRideJob = new Thread(rj);
            threadRideJob.start();
        }
    }
}
