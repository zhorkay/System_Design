package io.myzoe.system_design;

import sun.java2d.loops.DrawRect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class QuadTreeTest {


    public static void main(String[] args) {
        Random r = new Random();
        int n = 10000;
        int cap = 4;
        long x0 = 0;
        long y0 = 0;
        long x1 = 3200;
        long y1 = 1300;

        QuadTree qt = new QuadTree(cap, x0, y0, x1, y1);
        Restaurant[] oldLoc = new Restaurant[n];
        Restaurant[] nextLoc = new Restaurant[n];
        for (int i = 0; i < n; i++) {
            Restaurant res0 = new Restaurant(r.nextDouble()*x1, r.nextDouble()*y1);
            Restaurant res1 = new Restaurant(r.nextDouble()*x1, r.nextDouble()*y1);
            oldLoc[i] = res0;
            nextLoc[i] = res1;
        }

        long t0 = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            qt.put(oldLoc[i]);
        }
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            qt.remove(oldLoc[i]);
            qt.put(nextLoc[i]);
        }
        long t2 = System.currentTimeMillis();
        System.out.println(qt.getSize());
        long diff0 = t1 - t0;
        long diff1 = t2 - t1;
        System.out.println(n + " insert time: " + (double)diff0 / 1000 + " seconds");
        System.out.println(n + " update time: " + (double)diff1 / 1000 + " seconds");

        Plotter plotter = new Plotter(qt);
        JFrame frame = new JFrame("Draw QuadTree");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.BLACK);
        frame.setSize((int)qt.WIDTH, (int)qt.HEIGHT);
        frame.add(plotter);
        frame.pack();
        frame.setVisible(true);
    }

    private static class Plotter extends JPanel {
        private static final Color STARS = new Color(255, 97, 250);
        private static final Color COVERED_STARS = new Color(255, 0, 0);
        QuadTree quadTree;
        MouseHandler mouseHandler = new MouseHandler();
        Point p1 = new Point(0, 0);
        Point p2 = new Point(0, 0);
        boolean drawing;

        public Plotter(QuadTree qt) {
            this.quadTree = qt;
            this.setPreferredSize(new Dimension((int)qt.WIDTH, (int)qt.HEIGHT));
            this.addMouseListener(mouseHandler);
        }
/*
        public void paint(Graphics g) {
            Graphics2D ga = (Graphics2D)g;
            setBackground(Color.BLACK);
            ga.setPaint(Color.red);

            List<SpatialObject> res = quadTree.getAll();
            int r = 1;
            for (SpatialObject so : res) {
                ga.drawOval((int)so.getLongitude()-r,(int)so.getLatitude()-r,r,r);
            }
        }
        */

        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;
            super.paintComponent(g2);
            setBackground(Color.BLACK);
            g2.setPaint(STARS);

            List<SpatialObject> res = quadTree.getAll();
            int r = 2;
            for (SpatialObject so : res) {
                g2.drawOval((int)so.getLongitude()-r,(int)so.getLatitude()-r,r,r);
            }
            g2.setColor(Color.GREEN);
            int w = Math.abs(p2.x-p1.x);
            int h = Math.abs(p2.y-p1.y);
            int x0 = Math.min(p2.x,p1.x);
            int y0 = Math.min(p2.y,p1.y);
            g2.drawRect(x0, y0, w, h);
            List<SpatialObject> covered = quadTree.getIntersect(x0, y0, x0 + w, y0 + h);
            r = 3;
            g2.setPaint(COVERED_STARS);
            for (SpatialObject so : covered) {
                g2.drawOval((int)so.getLongitude()-r,(int)so.getLatitude()-r,r,r);
            }

        }

        private class MouseHandler extends MouseAdapter {


            public void mousePressed(MouseEvent e) {
                drawing = true;
                p1 = e.getPoint();
                p2 = p1;
                //System.out.println(p1 + " - " + p2);
                //repaint();
            }

            public void mouseReleased(MouseEvent e) {
                drawing = false;
                p2 = e.getPoint();
                System.out.println(p1 + " - " + p2);
                repaint();
            }
        }
    }


    private static class Restaurant implements SpatialObject {
        double longitude;
        double latitude;

        public Restaurant(double longitude, double latitude) {
            this.longitude = longitude;
            this.latitude = latitude;
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
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Restaurant that = (Restaurant) o;
            return Double.compare(that.longitude, longitude) == 0 &&
                    Double.compare(that.latitude, latitude) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(longitude, latitude);
        }

        @Override
        public String toString() {
            return "Restaurant{" +
                    "longitude=" + longitude +
                    ", latitude=" + latitude +
                    '}';
        }

        @Override
        public boolean intersect(double x0, double y0, double x1, double y1) {
            return x0 <= longitude && longitude < x1 && y0 <= latitude && latitude < y1;
        }
    }
}
