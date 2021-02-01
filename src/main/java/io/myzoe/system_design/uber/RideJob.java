package io.myzoe.system_design.uber;

import io.myzoe.system_design.QuadTree;
import io.myzoe.system_design.SpatialObject;

import java.util.Random;

public class RideJob implements Runnable {

    private final long from;
    private final long to;
    private final long startTs;
    private final long job_duration_in_seconds;
    private QuadTree qt;

    public RideJob(long from, long to, long job_duration_in_seconds, QuadTree qt) {
        this.from = from;
        this.to = to;
        this.startTs = System.currentTimeMillis();
        this.job_duration_in_seconds = job_duration_in_seconds;
        this.qt = qt;
    }

    @Override
    public void run() {
        Random r = new Random();
        long cnt = 0;
        long len = to - from;
        long i = 0;

        while (startTs + job_duration_in_seconds * 1000L > System.currentTimeMillis()) {
            System.out.println(from + " - " + to + " : " + (i + from));
            SpatialObject so = this.qt.remove(new Ride(i + from, new Passenger(0, "f", "l")));
            // TODO: quadtree cannot find old ride with the old ride coordinates. Option to pass all Ride object to Runner

            if (so == null) {
                System.out.println(Thread.currentThread().getName() + " HIBA " + (i + from));
            }
            Ride ride = (Ride)so;
            ride.setLongitude(r.nextDouble() * qt.WIDTH);
            ride.setLatitude(r.nextDouble() * qt.HEIGHT);
            qt.put(ride);
            cnt++;

            i = (i + 1) % len;
        }
        System.out.println(Thread.currentThread().getName() + " finished " + cnt + " update over " + job_duration_in_seconds + " seconds");
        System.out.println(Thread.currentThread().getName() + " " + System.currentTimeMillis() + " vs " + (startTs + job_duration_in_seconds * 1000));

    }
}
