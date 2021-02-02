package io.myzoe.system_design.uber;

import io.myzoe.system_design.QuadTree;
import io.myzoe.system_design.SpatialObject;

import java.util.Random;

public class RideJob implements Runnable {

    private final int from;
    private final int to;
    private final long startTs;
    private final long job_duration_in_seconds;
    private QuadTree qt;
    private Ride[] rides;

    public RideJob(int from, int to, long job_duration_in_seconds, QuadTree qt, Ride[] rides) {
        this.from = from;
        this.to = to;
        this.startTs = System.currentTimeMillis();
        this.job_duration_in_seconds = job_duration_in_seconds;
        this.qt = qt;
        this.rides = rides;
    }

    @Override
    public void run() {
        Random r = new Random();
        long cnt = 0;
        int len = to - from;
        int i = 0;

        while (startTs + job_duration_in_seconds * 1000L > System.currentTimeMillis()) {

            SpatialObject so = this.qt.remove(rides[i]);
            Ride ride = (Ride)so;
            ride.setLongitude(r.nextDouble() * qt.WIDTH);
            ride.setLatitude(r.nextDouble() * qt.HEIGHT);
            qt.put(ride);
            rides[i] = ride;
            cnt++;

            i = (i + 1) % len;
        }
        System.out.println(Thread.currentThread().getName() + " finished " + cnt + " update over " + job_duration_in_seconds + " seconds");


    }
}
